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
        Page<Alimento> alimentosLocales = alimentoRepository.findByNameContainingIgnoreCase(nombre, pageable);

        // Si hay alimentos en local, devolverlos primero
        if (!alimentosLocales.isEmpty()) {
            List<Alimento> alimentosAPI = obtenerAlimentosDesdeAPI(nombre);
            List<Alimento> combinedList = new ArrayList<>(alimentosLocales.getContent());
            combinedList.addAll(alimentosAPI);
            return new PageImpl<>(combinedList, pageable, combinedList.size());
        }

        // Si no hay en local, devolver solo los de la API
        List<Alimento> alimentosAPI = obtenerAlimentosDesdeAPI(nombre);
        return new PageImpl<>(alimentosAPI, pageable, alimentosAPI.size());
    }

    private List<Alimento> obtenerAlimentosDesdeAPI(String nombre) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(OPENFOODDATABASE_SEARCH_API, nombre.replace(" ", "+"));

        try {
            OpenFoodSearchResponse response = restTemplate.getForObject(url, OpenFoodSearchResponse.class);

            if (response != null && response.getProducts() != null) {
                return response.getProducts().stream()
                        .map(this::convertirProductoAAlimento)
                        .filter(Objects::nonNull)
                        .sorted(Comparator
                                .comparing((Alimento a) -> !a.getName().toLowerCase().contains(nombre.toLowerCase()))
                                .thenComparingInt(a -> calcularSimilitud(nombre, a.getName()))
                                .thenComparingInt(a -> -a.getName().length())
                        )
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of(); // Si no hay resultados en la API
    }


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

    private int calcularSimilitud(String input, String target) {
        input = input.toLowerCase().replace(" ", "");
        target = target.toLowerCase().replace(" ", "");

        int[][] dp = new int[input.length() + 1][target.length() + 1];

        for (int i = 0; i <= input.length(); i++) {
            for (int j = 0; j <= target.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + (input.charAt(i - 1) == target.charAt(j - 1) ? 0 : 1)
                    );
                }
            }
        }
        return dp[input.length()][target.length()];
    }
}
