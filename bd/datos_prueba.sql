-- ================================================================
--  KITCHEN HACK — Datos de Prueba v1.0
--  PostgreSQL 15+ | Solo ejecutar con la BD vacía
--
--  CONTRASEÑAS DE PRUEBA:
--    admin123   → hash BCrypt incluido abajo
--    user123    → hash BCrypt incluido abajo
--
--  NOTA BCRYPT: Cuando registras via POST /usuarios/nuevo
--  el backend hashea automáticamente. Este SQL usa hashes
--  pre-computados equivalentes para inserción directa.
-- ================================================================

-- ================================================================
-- 1. LIMPIEZA (orden inverso a FK)
-- ================================================================
TRUNCATE TABLE receta_detalle  RESTART IDENTITY CASCADE;
TRUNCATE TABLE receta          RESTART IDENTITY CASCADE;
TRUNCATE TABLE ingrediente     RESTART IDENTITY CASCADE;
TRUNCATE TABLE ejercicio       RESTART IDENTITY CASCADE;
TRUNCATE TABLE plan_maestro    RESTART IDENTITY CASCADE;
TRUNCATE TABLE usuario         RESTART IDENTITY CASCADE;
TRUNCATE TABLE etiqueta        RESTART IDENTITY CASCADE;
TRUNCATE TABLE rol             RESTART IDENTITY CASCADE;

-- ================================================================
-- 2. ROLES
-- ================================================================
INSERT INTO rol (nombre) VALUES
                             ('usuario'),
                             ('nutricionista'),
                             ('entrenador'),
                             ('admin');

-- ================================================================
-- 3. ETIQUETAS
-- ================================================================
INSERT INTO etiqueta (nombre, grupo) VALUES
                                         ('Cereal',     'ingrediente'),
                                         ('Proteína',   'ingrediente'),
                                         ('Verdura',    'ingrediente'),
                                         ('Lácteo',     'ingrediente'),
                                         ('Pasta',      'receta'),
                                         ('Ensalada',   'receta'),
                                         ('Pierna',     'ejercicio'),
                                         ('Full Body',  'ejercicio'),
                                         ('Brazos',     'ejercicio');

-- ================================================================
-- 4. USUARIOS
--    Contraseña "admin123"  → hash BCrypt (10 rounds)
--    Contraseña "user123"   → hash BCrypt (10 rounds)
--
--    Si prefieres crearlos via API (recomendado):
--      POST /usuarios/nuevo  { username, email, contrasenaHash, nombre, apellido, idRol }
-- ================================================================
INSERT INTO usuario (username, email, contrasena_hash, nombre, apellido, id_rol) VALUES
                                                                                     ('jose_admin',  'jose@kh.pe',   '$2a$12$knEg15MYN5WYaEetuPcyueHT5PtikE6yjMxvtOldk3koaIHFHgBkS', 'Jose',    'Milla',   4),
                                                                                     ('ana_nutri',   'ana@kh.pe',    '$2a$12$knEg15MYN5WYaEetuPcyueHT5PtikE6yjMxvtOldk3koaIHFHgBkS', 'Ana',     'Garcia',  2),
                                                                                     ('carlos_fit',  'carlos@kh.pe', '$2a$12$knEg15MYN5WYaEetuPcyueHT5PtikE6yjMxvtOldk3koaIHFHgBkS', 'Carlos',  'Ruiz',    3),
                                                                                     ('maria_user',  'maria@kh.pe',  '$2a$12$knEg15MYN5WYaEetuPcyueHT5PtikE6yjMxvtOldk3koaIHFHgBkS', 'Maria',   'Lopez',   1);

-- ================================================================
-- 5. INGREDIENTES
-- ================================================================
INSERT INTO ingrediente (nombre, unidad_medida, id_etiqueta, calorias_100, proteinas_100, carbos_100, grasas_100) VALUES
                                                                                                                      ('Quinoa',         'g',  1, 368.0, 14.1, 64.2,  6.1),
                                                                                                                      ('Atún en agua',   'g',  2, 116.0, 26.0,  0.0,  1.0),
                                                                                                                      ('Brócoli',        'g',  3,  34.0,  2.8,  6.6,  0.4),
                                                                                                                      ('Avena',          'g',  1, 389.0, 16.9, 66.3,  6.9),
                                                                                                                      ('Pechuga pollo',  'g',  2, 165.0, 31.0,  0.0,  3.6),
                                                                                                                      ('Espinaca',       'g',  3,  23.0,  2.9,  3.6,  0.4),
                                                                                                                      ('Leche entera',   'ml', 4,  61.0,  3.2,  4.8,  3.3),
                                                                                                                      ('Arroz integral', 'g',  1, 362.0,  7.5, 76.2,  2.7),
                                                                                                                      ('Huevo',          'und',2, 155.0, 13.0,  1.1, 11.0),
                                                                                                                      ('Tomate',         'g',  3,  18.0,  0.9,  3.9,  0.2);

-- ================================================================
-- 6. RECETAS
-- ================================================================
INSERT INTO receta (titulo, descripcion, id_autor, tiempo_min, dificultad, publicada) VALUES
                                                                                          ('Bowl de Quinoa con Atún',  'Almuerzo proteico y rápido',        2, 15, 'facil',  TRUE),
                                                                                          ('Pollo con Brócoli',        'Cena baja en carbohidratos',        2, 20, 'facil',  TRUE),
                                                                                          ('Avena con Frutas',         'Desayuno energético pre-entreno',   3, 10, 'facil',  TRUE),
                                                                                          ('Arroz con Huevo Frito',    'Clásico rápido y completo',        3, 12, 'medio',  TRUE),
                                                                                          ('Ensalada Espinaca-Tomate', 'Ensalada ligera para cenar',        2, 8,  'facil',  TRUE);

-- ================================================================
-- 7. DETALLE DE RECETAS (ingredientes + pasos)
-- ================================================================
-- Receta 1: Bowl de Quinoa con Atún
INSERT INTO receta_detalle (id_receta, id_ingrediente, cantidad, es_paso, orden, contenido) VALUES
                                                                                                (1, 1, 100, FALSE, 1, NULL),
                                                                                                (1, 2,  80, FALSE, 2, NULL),
                                                                                                (1, NULL, NULL, TRUE, 3, 'Cocinar la quinoa en agua con sal durante 15 min.'),
                                                                                                (1, NULL, NULL, TRUE, 4, 'Mezclar con el atún escurrido. Servir frío.');

-- Receta 2: Pollo con Brócoli
INSERT INTO receta_detalle (id_receta, id_ingrediente, cantidad, es_paso, orden, contenido) VALUES
                                                                                                (2, 5, 150, FALSE, 1, NULL),
                                                                                                (2, 3, 100, FALSE, 2, NULL),
                                                                                                (2, NULL, NULL, TRUE, 3, 'Saltear el pollo en sartén 10 min a fuego medio.'),
                                                                                                (2, NULL, NULL, TRUE, 4, 'Agregar brócoli al vapor y servir caliente.');

-- Receta 3: Avena con Frutas
INSERT INTO receta_detalle (id_receta, id_ingrediente, cantidad, es_paso, orden, contenido) VALUES
                                                                                                (3, 4,  50, FALSE, 1, NULL),
                                                                                                (3, 7, 150, FALSE, 2, NULL),
                                                                                                (3, NULL, NULL, TRUE, 3, 'Hervir la leche, agregar avena y revolver 5 min.');

-- Receta 4: Arroz con Huevo Frito
INSERT INTO receta_detalle (id_receta, id_ingrediente, cantidad, es_paso, orden, contenido) VALUES
                                                                                                (4, 8, 100, FALSE, 1, NULL),
                                                                                                (4, 9,   2, FALSE, 2, NULL),
                                                                                                (4, NULL, NULL, TRUE, 3, 'Cocinar el arroz. Freír los huevos y servir encima.');

-- Receta 5: Ensalada Espinaca-Tomate
INSERT INTO receta_detalle (id_receta, id_ingrediente, cantidad, es_paso, orden, contenido) VALUES
                                                                                                (5, 6,  80, FALSE, 1, NULL),
                                                                                                (5, 10, 60, FALSE, 2, NULL),
                                                                                                (5, NULL, NULL, TRUE, 3, 'Lavar y mezclar, aliñar con aceite de oliva y sal.');

-- ================================================================
-- 8. EJERCICIOS
-- ================================================================
INSERT INTO ejercicio (nombre, grupo_muscular, duracion_min, met_valor) VALUES
                                                                            ('Zancadas',          'Pierna',    12, 5.0),
                                                                            ('Burpees',           'Full Body', 10, 8.0),
                                                                            ('Curl de Bíceps',    'Brazos',    15, 3.0),
                                                                            ('Sentadillas',       'Pierna',    12, 5.5),
                                                                            ('Plancha abdominal', 'Full Body', 10, 4.0),
                                                                            ('Press de Banca',    'Pecho',     20, 4.5),
                                                                            ('Correr',            'Full Body', 30, 9.8);

-- ================================================================
-- 9. PLANES MAESTROS
-- ================================================================
INSERT INTO plan_maestro (titulo, id_autor, tipo_plan, duracion_dias, objetivo) VALUES
                                                                                    ('Reto Fit 15 días',    2, 'hibrido',     15, 'Tonificar y perder grasa'),
                                                                                    ('Dieta Proteica 30',   2, 'alimenticio', 30, 'Ganar masa muscular'),
                                                                                    ('Fuerza y Resistencia',3, 'ejercicio',   45, 'Ganar fuerza base'),
                                                                                    ('Plan Detox',          2, 'alimenticio', 14, 'Limpiar el organismo');


-- ================================================================
-- VERIFICACIÓN RÁPIDA
-- ================================================================
SELECT 'rol'           AS tabla, COUNT(*) AS registros FROM rol
UNION ALL
SELECT 'etiqueta',    COUNT(*) FROM etiqueta
UNION ALL
SELECT 'usuario',     COUNT(*) FROM usuario
UNION ALL
SELECT 'ingrediente', COUNT(*) FROM ingrediente
UNION ALL
SELECT 'receta',      COUNT(*) FROM receta
UNION ALL
SELECT 'receta_detalle', COUNT(*) FROM receta_detalle
UNION ALL
SELECT 'ejercicio',   COUNT(*) FROM ejercicio
UNION ALL
SELECT 'plan_maestro',COUNT(*) FROM plan_maestro;

-- ================================================================
-- 10. AJUSTE DE SECUENCIAS (evita duplicate key en inserts futuros)
-- ================================================================
SELECT setval(pg_get_serial_sequence('rol', 'id'), COALESCE((SELECT MAX(id) FROM rol), 0) + 1, false);
SELECT setval(pg_get_serial_sequence('etiqueta', 'id'), COALESCE((SELECT MAX(id) FROM etiqueta), 0) + 1, false);
SELECT setval(pg_get_serial_sequence('usuario', 'id'), COALESCE((SELECT MAX(id) FROM usuario), 0) + 1, false);
SELECT setval(pg_get_serial_sequence('ingrediente', 'id'), COALESCE((SELECT MAX(id) FROM ingrediente), 0) + 1, false);
SELECT setval(pg_get_serial_sequence('receta', 'id'), COALESCE((SELECT MAX(id) FROM receta), 0) + 1, false);
SELECT setval(pg_get_serial_sequence('receta_detalle', 'id'), COALESCE((SELECT MAX(id) FROM receta_detalle), 0) + 1, false);
SELECT setval(pg_get_serial_sequence('ejercicio', 'id'), COALESCE((SELECT MAX(id) FROM ejercicio), 0) + 1, false);
SELECT setval(pg_get_serial_sequence('plan_maestro', 'id'), COALESCE((SELECT MAX(id) FROM plan_maestro), 0) + 1, false);