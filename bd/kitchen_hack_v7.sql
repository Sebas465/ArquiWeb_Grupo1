-- ================================================================
--  KITCHEN HACK — Base de Datos v6.2 (ULTRA OPTIMIZED & LINKED)
--  PostgreSQL 15+
-- ================================================================

-- 0. LIMPIEZA TOTAL
DROP TABLE IF EXISTS contacto_profesional CASCADE;
DROP TABLE IF EXISTS sistema_evento        CASCADE;
DROP TABLE IF EXISTS progreso_diario       CASCADE;
DROP TABLE IF EXISTS suscripcion_plan      CASCADE;
DROP TABLE IF EXISTS dia_plan_item         CASCADE;
DROP TABLE IF EXISTS ejercicio             CASCADE;
DROP TABLE IF EXISTS plan_maestro          CASCADE;
DROP TABLE IF EXISTS interaccion           CASCADE;
DROP TABLE IF EXISTS receta_detalle        CASCADE;
DROP TABLE IF EXISTS receta                CASCADE;
DROP TABLE IF EXISTS ingrediente           CASCADE;
DROP TABLE IF EXISTS progreso_salud        CASCADE;
DROP TABLE IF EXISTS perfil_profesional    CASCADE;
DROP TABLE IF EXISTS usuario               CASCADE;
DROP TABLE IF EXISTS etiqueta              CASCADE;
DROP TABLE IF EXISTS rol                   CASCADE;

-- 1. MÓDULO ESTRUCTURAL
CREATE TABLE rol (
    id      SERIAL      PRIMARY KEY,
    nombre  VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE etiqueta (
    id      SERIAL      PRIMARY KEY,
    nombre  VARCHAR(100) NOT NULL UNIQUE,
    grupo   VARCHAR(50)  NOT NULL -- 'receta', 'ingrediente', 'ejercicio'
);

-- 2. MÓDULO USUARIOS
CREATE TABLE usuario (
    id              SERIAL          PRIMARY KEY,
    username        VARCHAR(80)     NOT NULL UNIQUE,
    email           VARCHAR(150)    NOT NULL UNIQUE,
    contrasena_hash VARCHAR(255)    NOT NULL,
    nombre          VARCHAR(100)    NOT NULL,
    apellido        VARCHAR(100)    NOT NULL,
    id_rol          INT             NOT NULL DEFAULT 1 REFERENCES rol(id),
    fecha_registro  TIMESTAMP       NOT NULL DEFAULT NOW(),
    ultima_act      TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE perfil_profesional (
    id                  SERIAL          PRIMARY KEY,
    id_usuario          INT             NOT NULL UNIQUE REFERENCES usuario(id) ON DELETE CASCADE,
    especialidad        VARCHAR(100),
    numero_colegiatura  VARCHAR(100),
    verificado          BOOLEAN         NOT NULL DEFAULT FALSE
);

CREATE TABLE progreso_salud (
    id          SERIAL          PRIMARY KEY,
    id_usuario  INT             NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    fecha       DATE            NOT NULL DEFAULT CURRENT_DATE,
    peso_kg     NUMERIC(5,2),
    talla_cm    INT,
    imc         NUMERIC(4,2),
    alergias    TEXT
);

-- 3. MÓDULO NUTRICIÓN Y RECETAS
CREATE TABLE ingrediente (
    id              SERIAL          PRIMARY KEY,
    nombre          VARCHAR(150)    NOT NULL UNIQUE,
    unidad_medida   VARCHAR(20)     NOT NULL, 
    id_etiqueta     INT             REFERENCES etiqueta(id),
    calorias_100    NUMERIC(6,2),   -- Base para cálculos dinámicos
    proteinas_100   NUMERIC(6,2),
    carbos_100      NUMERIC(6,2),
    grasas_100      NUMERIC(6,2)
);

CREATE TABLE receta (
    id          SERIAL          PRIMARY KEY,
    titulo      VARCHAR(200)    NOT NULL,
    descripcion TEXT,
    id_autor    INT             NOT NULL REFERENCES usuario(id),
    tiempo_min  INT,
    dificultad  VARCHAR(20)     CHECK (dificultad IN ('facil','medio','dificil')),
    publicada   BOOLEAN         DEFAULT TRUE,
    ultima_act  TIMESTAMP       DEFAULT NOW()
);

CREATE TABLE receta_detalle (
    id             SERIAL          PRIMARY KEY,
    id_receta      INT             NOT NULL REFERENCES receta(id) ON DELETE CASCADE,
    id_ingrediente INT             REFERENCES ingrediente(id),
    cantidad       NUMERIC(8,2),   -- Cantidad de ingrediente
    es_paso        BOOLEAN         DEFAULT FALSE, -- TRUE si es una instrucción de texto
    orden          INT             NOT NULL,
    contenido      TEXT            -- Instrucción del paso
);

CREATE TABLE interaccion (
    id            SERIAL          PRIMARY KEY,
    id_usuario    INT             NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    id_receta     INT             NOT NULL REFERENCES receta(id) ON DELETE CASCADE,
    tipo          VARCHAR(20)     NOT NULL, -- 'favorito', 'historial', 'resena'
    calificacion  SMALLINT        CHECK (calificacion BETWEEN 1 AND 5),
    comentario    TEXT,
    fecha         TIMESTAMP       DEFAULT NOW(),
    UNIQUE (id_usuario, id_receta, tipo)
);

-- 4. MÓDULO ENTRENAMIENTO Y PLANES
CREATE TABLE ejercicio (
    id             SERIAL          PRIMARY KEY,
    nombre         VARCHAR(150)    NOT NULL,
    grupo_muscular VARCHAR(100),
    duracion_min   INT,
    met_valor      NUMERIC(4,1)    -- Gasto energético estimado
);

CREATE TABLE plan_maestro (
    id             SERIAL          PRIMARY KEY,
    titulo         VARCHAR(200)    NOT NULL,
    id_autor       INT             NOT NULL REFERENCES usuario(id),
    tipo_plan      VARCHAR(20)     NOT NULL, -- 'alimenticio', 'ejercicio', 'hibrido'
    duracion_dias  INT             NOT NULL,
    objetivo       VARCHAR(100)
);

CREATE TABLE dia_plan_item (
    id             SERIAL          PRIMARY KEY,
    id_plan        INT             NOT NULL REFERENCES plan_maestro(id) ON DELETE CASCADE,
    num_dia        INT             NOT NULL,
    id_receta      INT             REFERENCES receta(id) ON DELETE SET NULL,
    id_ejercicio   INT             REFERENCES ejercicio(id) ON DELETE SET NULL,
    momento        VARCHAR(30),    -- 'desayuno', 'entrenamiento', 'cena'
    orden          INT,
    CONSTRAINT check_item_presente CHECK (id_receta IS NOT NULL OR id_ejercicio IS NOT NULL)
);

-- 5. SUSCRIPCIÓN Y PROGRESO
CREATE TABLE suscripcion_plan (
    id             SERIAL          PRIMARY KEY,
    id_usuario     INT             NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    id_plan        INT             NOT NULL REFERENCES plan_maestro(id),
    fecha_inicio   DATE            NOT NULL,
    activo         BOOLEAN         DEFAULT TRUE
);

CREATE TABLE progreso_diario (
    id                 SERIAL      PRIMARY KEY,
    id_suscripcion     INT         NOT NULL REFERENCES suscripcion_plan(id) ON DELETE CASCADE,
    id_dia_plan_item   INT         NOT NULL REFERENCES dia_plan_item(id) ON DELETE CASCADE,
    completado         BOOLEAN     DEFAULT FALSE,
    fecha_registro     TIMESTAMP   DEFAULT NOW()
);

-- 6. SOPORTE, IA Y CONTACTO
CREATE TABLE sistema_evento (
    id             SERIAL          PRIMARY KEY,
    id_usuario     INT             NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    tipo           VARCHAR(30)     NOT NULL, -- 'notificacion', 'logro', 'ia_recomendacion'
    titulo         VARCHAR(200),
    contenido      TEXT            NOT NULL,
    leido_guardado BOOLEAN         DEFAULT FALSE,
    fecha          TIMESTAMP       DEFAULT NOW()
);

CREATE TABLE contacto_profesional (
    id             SERIAL          PRIMARY KEY,
    id_usuario     INT             NOT NULL REFERENCES usuario(id),
    id_profesional INT             NOT NULL REFERENCES usuario(id),
    estado         VARCHAR(20)     DEFAULT 'pendiente' CHECK (estado IN ('pendiente','aceptado','rechazado'))
);

-- ================================================================
-- DATOS SEMILLA (INSERTS)
-- ================================================================

-- Roles y Etiquetas
INSERT INTO rol (nombre) VALUES ('usuario'), ('nutricionista'), ('entrenador'), ('admin');
INSERT INTO etiqueta (nombre, grupo) VALUES ('Pasta', 'receta'), ('Cereal', 'ingrediente'), ('Pierna', 'ejercicio');

-- Usuarios y Perfiles
INSERT INTO usuario (username, email, contrasena_hash, nombre, apellido, id_rol) VALUES
('jair_dev', 'jair@kh.pe', 'hash_secure_1', 'Jair', 'More', 4),
('ana_nutri', 'ana@kh.pe', 'hash_secure_2', 'Ana', 'García', 2),
('carlos_fit', 'carlos@kh.pe', 'hash_secure_3', 'Carlos', 'Ruiz', 1);

INSERT INTO perfil_profesional (id_usuario, especialidad, numero_colegiatura, verificado) VALUES
(2, 'Nutrición Clínica', 'CNP-9988', TRUE);

INSERT INTO progreso_salud (id_usuario, peso_kg, talla_cm, imc, alergias) VALUES
(3, 70.0, 170, 24.2, 'Lactosa'),
(3, 69.5, 170, 24.0, 'Lactosa'),
(1, 82.0, 180, 25.3, 'Ninguna');

-- Nutrición y Recetas
INSERT INTO ingrediente (nombre, unidad_medida, id_etiqueta, calorias_100, proteinas_100, carbos_100, grasas_100) VALUES
('Quinoa', 'g', 2, 368.0, 14.1, 64.2, 6.1),
('Atún en agua', 'g', 2, 116.0, 26.0, 0.0, 1.0),
('Brócoli', 'g', 2, 34.0, 2.8, 6.6, 0.4);

INSERT INTO receta (titulo, descripcion, id_autor, tiempo_min, dificultad) VALUES
('Bowl de Quinoa', 'Almuerzo rápido y sano', 2, 15, 'facil'),
('Atún con Brócoli', 'Cena baja en carbos', 2, 10, 'facil'),
('Pasta Integral', 'Energía pre-entreno', 1, 12, 'medio');

INSERT INTO receta_detalle (id_receta, id_ingrediente, cantidad, es_paso, orden, contenido) VALUES
(1, 1, 100, FALSE, 1, NULL),
(1, 2, 80, FALSE, 2, NULL),
(1, NULL, NULL, TRUE, 3, 'Mezclar la quinoa cocida con el atún y servir frío.');

INSERT INTO interaccion (id_usuario, id_receta, tipo, calificacion, comentario) VALUES
(3, 1, 'resena', 5, 'Excelente para llevar al trabajo.'),
(3, 1, 'favorito', NULL, NULL),
(3, 3, 'historial', NULL, 'Me demoré más de 12 minutos.');

-- Ejercicios y Planes
INSERT INTO ejercicio (nombre, grupo_muscular, duracion_min, met_valor) VALUES
('Zancadas', 'Pierna', 12, 5.0),
('Burpees', 'Full Body', 10, 8.0),
('Curl de Bíceps', 'Brazos', 15, 3.0);

INSERT INTO plan_maestro (titulo, id_autor, tipo_plan, duracion_dias, objetivo) VALUES
('Reto Fit 15', 2, 'hibrido', 15, 'Tonificar'),
('Dieta Keto', 2, 'alimenticio', 30, 'Quema grasa'),
('Fuerza Base', 1, 'ejercicio', 45, 'Ganar fuerza');

INSERT INTO dia_plan_item (id_plan, num_dia, id_receta, id_ejercicio, momento, orden) VALUES
(1, 1, 1, NULL, 'almuerzo', 1),
(1, 1, NULL, 2, 'entrenamiento', 2),
(1, 2, 2, NULL, 'cena', 1);

-- Suscripción y Progreso Real
INSERT INTO suscripcion_plan (id_usuario, id_plan, fecha_inicio, activo) VALUES
(3, 1, '2026-04-28', TRUE),
(1, 3, '2026-04-20', TRUE),
(3, 2, '2026-05-01', FALSE);

INSERT INTO progreso_diario (id_suscripcion, id_dia_plan_item, completado) VALUES
(1, 1, TRUE),
(1, 2, TRUE),
(1, 3, FALSE);

-- Soporte y Contacto
INSERT INTO sistema_evento (id_usuario, tipo, titulo, contenido) VALUES
(3, 'notificacion', 'Nuevo Plan', 'Ana ha asignado un nuevo reto.'),
(3, 'logro', 'Constancia', 'Has completado 3 días seguidos.'),
(1, 'ia_recomendacion', 'Ajuste Calórico', 'Aumenta 20g de proteína en tu almuerzo.');

INSERT INTO contacto_profesional (id_usuario, id_profesional, estado) VALUES
(3, 2, 'aceptado'),
(1, 2, 'pendiente'),
(3, 1, 'rechazado');