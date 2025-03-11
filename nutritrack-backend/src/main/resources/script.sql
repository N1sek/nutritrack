-- Borrar datos existentes para evitar duplicados en pruebas
DELETE FROM usuarios_recetas_favoritas;
DELETE FROM usuarios_alergenos;
DELETE FROM recetas_alimentos;
DELETE FROM recetas;
DELETE FROM alimentos_alergenos;
DELETE FROM alimentos;
DELETE FROM alergenos;
DELETE FROM users;

-- Restablecer AUTO_INCREMENT
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE alimentos AUTO_INCREMENT = 1;
ALTER TABLE recetas AUTO_INCREMENT = 1;
ALTER TABLE alergenos AUTO_INCREMENT = 1;

-- Crear usuarios de prueba
INSERT INTO users (rol, email, password, nickname, full_name, weight, height, gender, age, nivel_actividad, objetivo, created_on)
VALUES
    ('USER', 'usuario1@test.com', '$2a$10$IF6MinpH.juE49Ri7dZK5OM5BIGygEsxrdZ.z1ni63ZulYwI0uRGC', 'usuario1', 'Usuario de Prueba 1', 70.00, 175.00, 'Male', 30, 'MODERADO', 'MANTENERPESO', NOW()),
    ('USER', 'usuario2@test.com', '$2a$10$IF6MinpH.juE49Ri7dZK5OM5BIGygEsxrdZ.z1ni63ZulYwI0uRGC', 'usuario2', 'Usuario de Prueba 2', 65.00, 168.00, 'Female', 28, 'LIGERO', 'PERDERPESO', NOW()),
    ('ADMIN', 'admin@test.com', '$2a$10$IF6MinpH.juE49Ri7dZK5OM5BIGygEsxrdZ.z1ni63ZulYwI0uRGC', 'adminUser', 'Administrador', 80.00, 180.00, 'Male', 35, 'SEDENTARIO', 'GANARPESO', NOW()),
    ('USER', 'openfood@database.com', '$2a$10$IF6MinpH.juE49Ri7dZK5OM5BIGygEsxrdZ.z1ni63ZulYwI0uRGC', 'OpenFoodDB', 'Open Food Database', 0.00, 0.00, 'Unknown', 0, 'SEDENTARIO', 'MANTENERPESO', NOW());

-- Crear alergenos
INSERT INTO alergenos (nombre) VALUES
                                   ('Gluten'),
                                   ('Lácteos'),
                                   ('Frutos Secos'),
                                   ('Mariscos'),
                                   ('Soja');

-- Crear alimentos con valores ENUM en unidad_medida
INSERT INTO alimentos (name, description, calories, proteins, fats, carbs, quantity, unidad_medida, image_url, user_id, created_by)
VALUES
    ('Manzana Roja', 'Fruta dulce y crujiente', 52.00, 0.30, 0.20, 14.00, 100.00, 'GRAMOS', 'https://example.com/manzana.jpg', 1, 'usuario1'),
    ('Manzana Verde', 'Fruta ácida', 48.00, 0.40, 0.10, 12.00, 100.00, 'GRAMOS', 'https://example.com/manzana_verde.jpg', 2, 'usuario2'),
    ('Leche Deslactosada', 'Leche sin lactosa', 42.00, 3.40, 1.00, 5.00, 200.00, 'MILILITROS', 'https://example.com/leche.jpg', 1, 'usuario1'),
    ('Almendras', 'Fruto seco', 579.00, 21.00, 50.00, 22.00, 30.00, 'GRAMOS', 'https://example.com/almendras.jpg', 2, 'usuario2'),
    ('Camarón', 'Marisco rico en proteínas', 99.00, 24.00, 0.30, 1.00, 100.00, 'GRAMOS', 'https://example.com/camaron.jpg', 1, 'usuario1');

-- Asociar alimentos con alérgenos
INSERT INTO alimentos_alergenos (id_alimento, id_alergeno)
VALUES
    (3, 2), -- Leche Deslactosada tiene Lácteos
    (4, 3), -- Almendras tienen Frutos Secos
    (5, 4); -- Camarón tiene Mariscos

-- Crear recetas con tipo_comida y calorias_totales
INSERT INTO recetas (nombre, descripcion, user_id, tipo_comida, calorias_totales)
VALUES
    ('Ensalada de Manzana', 'Manzana roja, nueces y miel', 1, 'ALMUERZO', 200.00),
    ('Batido de Proteínas', 'Leche, almendras y plátano', 2, 'DESAYUNO', 450.00),
    ('Tacos de Camarón', 'Camarones con tortilla de maíz y salsa', 1, 'CENA', 600.00);

-- Asociar alimentos con recetas
INSERT INTO recetas_alimentos (receta_id, alimento_id, cantidad, unidad_medida)
VALUES
    (1, 1, 150.00, 'GRAMOS'), -- Ensalada de Manzana tiene Manzana Roja
    (1, 4, 30.00, 'GRAMOS'),  -- Ensalada de Manzana tiene Almendras
    (2, 3, 200.00, 'MILILITROS'), -- Batido de Proteínas tiene Leche
    (2, 4, 15.00, 'GRAMOS'),  -- Batido de Proteínas tiene Almendras
    (3, 5, 100.00, 'GRAMOS'); -- Tacos de Camarón tiene Camarón


-- Asociar recetas favoritas a usuarios
INSERT INTO usuarios_recetas_favoritas (user_id, receta_id)
VALUES
    (1, 1), -- Usuario 1 tiene Ensalada de Manzana como favorita
    (1, 3), -- Usuario 1 tiene Tacos de Camarón como favorito
    (2, 2); -- Usuario 2 tiene Batido de Proteínas como favorito

-- Asociar alérgenos a usuarios
INSERT INTO usuarios_alergenos (user_id, alergeno_id)
VALUES
    (1, 2), -- Usuario 1 es alérgico a Lácteos
    (2, 3); -- Usuario 2 es alérgico a Frutos Secos
