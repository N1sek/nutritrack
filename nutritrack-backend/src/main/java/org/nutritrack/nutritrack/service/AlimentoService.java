package org.nutritrack.nutritrack.service;

import org.nutritrack.nutritrack.dto.OpenFoodSearchResponse;
import org.nutritrack.nutritrack.dto.OpenFoodProduct;
import org.nutritrack.nutritrack.enums.UnidadMedida;
import org.nutritrack.nutritrack.model.Alimento;
import org.nutritrack.nutritrack.model.User;
import org.nutritrack.nutritrack.repository.AlimentoRepository;
import org.nutritrack.nutritrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageImpl;
@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    private static final String OPENFOODDATABASE_SEARCH_API = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=%s&json=1&page_size=10";
    @Autowired
    private UserRepository userRepository;



    public Page<Alimento> buscarAlimentosPorNombre(String nombre, Pageable pageable) {
        // Obtener alimentos de la base de datos local
        Page<Alimento> alimentosLocales = alimentoRepository.findByNameContainingIgnoreCase(nombre, pageable);

        // Obtener alimentos desde la API
        List<Alimento> alimentosAPI = obtenerAlimentosDesdeAPI(nombre);

        // Combinar las listas y filtrar aquellos que contienen la palabra clave
        List<Alimento> todosLosAlimentos = new ArrayList<>();
        todosLosAlimentos.addAll(alimentosLocales.getContent());
        todosLosAlimentos.addAll(alimentosAPI);

        List<Alimento> alimentosFiltrados = todosLosAlimentos.stream()
                .filter(alimento -> alimento.getName().toLowerCase().contains(nombre.toLowerCase())) // Solo coincidencias exactas
                .sorted(Comparator.comparingInt(a -> a.getName().toLowerCase().indexOf(nombre.toLowerCase()))) // Priorizar las coincidencias al inicio del nombre
                .collect(Collectors.toList());

        // Aplicar paginación después de filtrar
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), alimentosFiltrados.size());
        List<Alimento> pagedList = alimentosFiltrados.subList(start, end);

        return new PageImpl<>(pagedList, pageable, alimentosFiltrados.size());
    }




    private List<Alimento> obtenerAlimentosDesdeAPI(String nombre) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(OPENFOODDATABASE_SEARCH_API, nombre.replace(" ", "+"));

        try {
            OpenFoodSearchResponse response = restTemplate.getForObject(url, OpenFoodSearchResponse.class);

            if (response != null && response.getProducts() != null) {
                return response.getProducts().stream()
                        .map(this::convertirProductoAAlimento)
                        .filter(Objects::nonNull) // Solo elimina nulos, pero no filtra por nombre
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of(); // Si no hay resultados en la API
    }


    @Transactional
    public Alimento guardarAlimentoSeleccionado(Alimento alimento) {
        if (alimentoRepository.findByName(alimento.getName()).isEmpty()) {
            // Buscar usuario de OpenFoodDatabase
            User userSistema = userRepository.findByEmail("openfood@database.com")
                    .orElseThrow(() -> new RuntimeException("Usuario de OpenFoodDatabase no encontrado"));

            // Asignar el usuario al alimento
            alimento.setUser(userSistema);
            alimento.setCreatedBy("SYSTEM"); // Para diferenciar los productos guardados desde OpenFoodDatabase

            return alimentoRepository.save(alimento);
        }
        return alimento; // Si ya existe, no lo guarda de nuevo
    }

    @Transactional
    public Alimento crearAlimento(Alimento alimento, Long userId) {
        if (alimento.getName() == null || alimento.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del alimento no puede estar vacío.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        alimento.setUser(user); // Asignar el usuario al alimento antes de guardarlo

        return alimentoRepository.save(alimento);
    }


    private Alimento convertirProductoAAlimento(OpenFoodProduct product) {
        if (product.getProductName() == null || product.getProductName().isBlank()) {
            return null;
        }

        return Alimento.builder()
                .name(product.getProductName())
                .description(product.getGenericName() != null ? product.getGenericName() : "Sin descripción")
                .calories(BigDecimal.valueOf(product.getNutriments().getEnergyKcal()))
                .proteins(BigDecimal.valueOf(product.getNutriments().getProteins()))
                .fats(BigDecimal.valueOf(product.getNutriments().getFat()))
                .carbs(BigDecimal.valueOf(product.getNutriments().getCarbohydrates()))
                .quantity(BigDecimal.valueOf(100))
                .unidadMedida(UnidadMedida.GRAMOS)
                .createdBy("OpenFoodDatabase")
                .build();
    }
}
