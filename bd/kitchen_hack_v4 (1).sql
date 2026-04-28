-- ================================================================
--  KITCHEN HACK — Base de Datos v4.2 (Full Script)
--  PostgreSQL 15+
-- ================================================================

-- ──────────────────────────────────────────────────────────────
-- 0. LIMPIEZA TOTAL (Orden inverso por dependencias)
-- ──────────────────────────────────────────────────────────────
DROP TABLE IF EXISTS logro_usuario           CASCADE;
DROP TABLE IF EXISTS mensaje                 CASCADE;
DROP TABLE IF EXISTS conversacion             CASCADE;
DROP TABLE IF EXISTS plan_notificacion        CASCADE;
DROP TABLE IF EXISTS notificacion             CASCADE;
DROP TABLE IF EXISTS recomendacion_ia         CASCADE;
DROP TABLE IF EXISTS contacto_profesional     CASCADE;
DROP TABLE IF EXISTS sesion_videollamada      CASCADE;
DROP TABLE IF EXISTS plan_usuario_ejercicio   CASCADE;
DROP TABLE IF EXISTS plan_usuario_alimenticio CASCADE;
DROP TABLE IF EXISTS dia_plan_ejercicio       CASCADE;
DROP TABLE IF EXISTS plan_ejercicio           CASCADE;
DROP TABLE IF EXISTS ejercicio                CASCADE;
DROP TABLE IF EXISTS progreso_plan            CASCADE;
DROP TABLE IF EXISTS dia_plan_receta          CASCADE;
DROP TABLE IF EXISTS dia_plan                 CASCADE;
DROP TABLE IF EXISTS calificacion_plan        CASCADE;
DROP TABLE IF EXISTS plan_alimenticio         CASCADE;
DROP TABLE IF EXISTS calificacion_receta      CASCADE;
DROP TABLE IF EXISTS comentario_receta        CASCADE;
DROP TABLE IF EXISTS receta_favorita          CASCADE;
DROP TABLE IF EXISTS historial_receta         CASCADE;
DROP TABLE IF EXISTS receta_ingrediente       CASCADE;
DROP TABLE IF EXISTS paso_receta              CASCADE;
DROP TABLE IF EXISTS receta                   CASCADE;
DROP TABLE IF EXISTS ingrediente              CASCADE;
DROP TABLE IF EXISTS tipo_ingrediente         CASCADE;
DROP TABLE IF EXISTS categoria_receta         CASCADE;
DROP TABLE IF EXISTS progreso_salud           CASCADE;
DROP TABLE IF EXISTS perfil_profesional       CASCADE;
DROP TABLE IF EXISTS red_social_usuario       CASCADE;
DROP TABLE IF EXISTS usuario                  CASCADE;
DROP TABLE IF EXISTS rol                      CASCADE;

-- ──────────────────────────────────────────────────────────────
-- 1. MÓDULO USUARIOS
-- ──────────────────────────────────────────────────────────────
CREATE TABLE rol (
    id          SERIAL          PRIMARY KEY,
    nombre      VARCHAR(50)     NOT NULL UNIQUE,
    descripcion TEXT
);

CREATE TABLE usuario (
    id              SERIAL          PRIMARY KEY,
    username        VARCHAR(80)     NOT NULL UNIQUE,
    nombre          VARCHAR(100)    NOT NULL,
    apellido        VARCHAR(100)    NOT NULL,
    email           VARCHAR(150)    NOT NULL UNIQUE,
    telefono        VARCHAR(20)     UNIQUE,
    contrasena_hash VARCHAR(255),
    foto_perfil_url VARCHAR(500),
    bio             TEXT,
    id_rol          INT             NOT NULL DEFAULT 1 REFERENCES rol(id),
    fecha_registro  TIMESTAMP       NOT NULL DEFAULT NOW(),
    activo          BOOLEAN         NOT NULL DEFAULT TRUE
);

CREATE TABLE red_social_usuario (
    id              SERIAL          PRIMARY KEY,
    id_usuario      INT             NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    proveedor       VARCHAR(50)     NOT NULL,
    token_externo   VARCHAR(500)    NOT NULL,
    fecha_vinculado TIMESTAMP       NOT NULL DEFAULT NOW(),
    UNIQUE (proveedor, token_externo)
);

CREATE TABLE perfil_profesional (
    id                      SERIAL          PRIMARY KEY,
    id_usuario              INT             NOT NULL UNIQUE REFERENCES usuario(id) ON DELETE CASCADE,
    especialidad            VARCHAR(100),
    numero_colegiatura      VARCHAR(100),
    documento_titulo_url    VARCHAR(500),
    descripcion_profesional TEXT,
    verificado              BOOLEAN         NOT NULL DEFAULT FALSE,
    fecha_verificacion      TIMESTAMP
);

CREATE TABLE progreso_salud (
    id                  SERIAL          PRIMARY KEY,
    id_usuario          INT             NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    fecha_registro      DATE            NOT NULL DEFAULT CURRENT_DATE,
    peso_kg             NUMERIC(5,2),
    talla_cm            INT,
    imc                 NUMERIC(4,2),
    objetivo            VARCHAR(100),
    alergias            TEXT,
    tipo_dieta          VARCHAR(50)
);

-- ──────────────────────────────────────────────────────────────
-- 2. MÓDULO RECETAS
-- ──────────────────────────────────────────────────────────────
CREATE TABLE categoria_receta (
    id      SERIAL          PRIMARY KEY,
    nombre  VARCHAR(100)    NOT NULL UNIQUE
);

CREATE TABLE tipo_ingrediente (
    id          SERIAL          PRIMARY KEY,
    nombre      VARCHAR(100)    NOT NULL UNIQUE,
    descripcion TEXT
);

CREATE TABLE ingrediente (
    id                  SERIAL          PRIMARY KEY,
    nombre              VARCHAR(150)    NOT NULL UNIQUE,
    unidad              VARCHAR(50),
    id_tipo_ingrediente INT             NOT NULL REFERENCES tipo_ingrediente(id)
);

CREATE TABLE receta (
    id                    SERIAL          PRIMARY KEY,
    titulo                VARCHAR(200)    NOT NULL,
    descripcion           TEXT,
    id_categoria          INT             REFERENCES categoria_receta(id),
    id_autor              INT             NOT NULL REFERENCES usuario(id),
    tiempo_preparacion    INT,
    porciones             INT             DEFAULT 1,
    dificultad            VARCHAR(20)     CHECK (dificultad IN ('facil','medio','dificil')),
    imagen_url            VARCHAR(500),
    calorias_totales      NUMERIC(7,2),
    proteinas_g           NUMERIC(6,2),
    carbohidratos_g       NUMERIC(6,2),
    grasas_g              NUMERIC(6,2),
    calificacion_promedio NUMERIC(3,2)    DEFAULT 0.00,
    publicada             BOOLEAN         NOT NULL DEFAULT TRUE,
    fecha_creacion        TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE receta_ingrediente (
    id              SERIAL          PRIMARY KEY,
    id_receta       INT             NOT NULL REFERENCES receta(id) ON DELETE CASCADE,
    id_ingrediente  INT             NOT NULL REFERENCES ingrediente(id),
    cantidad        NUMERIC(8,2)    NOT NULL,
    nota            VARCHAR(200)
);

CREATE TABLE paso_receta (
    id          SERIAL  PRIMARY KEY,
    id_receta   INT     NOT NULL REFERENCES receta(id) ON DELETE CASCADE,
    numero_paso INT     NOT NULL,
    descripcion TEXT    NOT NULL,
    UNIQUE (id_receta, numero_paso)
);

-- ──────────────────────────────────────────────────────────────
-- 3. MÓDULO PLANES (Alimenticio y Ejercicio)
-- ──────────────────────────────────────────────────────────────
CREATE TABLE plan_alimenticio (
    id               SERIAL          PRIMARY KEY,
    titulo           VARCHAR(200)    NOT NULL,
    descripcion      TEXT,
    id_autor         INT             NOT NULL REFERENCES usuario(id),
    duracion_dias    INT             NOT NULL,
    objetivo         VARCHAR(200),
    calorias_diarias NUMERIC(7,2),
    activo           BOOLEAN         NOT NULL DEFAULT TRUE,
    fecha_creacion   TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE dia_plan (
    id          SERIAL  PRIMARY KEY,
    id_plan     INT     NOT NULL REFERENCES plan_alimenticio(id) ON DELETE CASCADE,
    numero_dia  INT     NOT NULL,
    UNIQUE (id_plan, numero_dia)
);

CREATE TABLE dia_plan_receta (
    id          SERIAL          PRIMARY KEY,
    id_dia_plan INT             NOT NULL REFERENCES dia_plan(id) ON DELETE CASCADE,
    id_receta   INT             NOT NULL REFERENCES receta(id),
    momento     VARCHAR(30)     NOT NULL CHECK (momento IN ('desayuno','almuerzo','cena','snack')),
    orden       INT
);

CREATE TABLE ejercicio (
    id              SERIAL          PRIMARY KEY,
    nombre          VARCHAR(150)    NOT NULL,
    grupo_muscular  VARCHAR(100),
    duracion_min    INT,
    series          INT,
    repeticiones    INT
);

CREATE TABLE plan_ejercicio (
    id               SERIAL          PRIMARY KEY,
    titulo           VARCHAR(200)    NOT NULL,
    id_autor         INT             NOT NULL REFERENCES usuario(id),
    duracion_dias    INT             NOT NULL,
    nivel_dificultad VARCHAR(30)     CHECK (nivel_dificultad IN ('principiante','intermedio','avanzado')),
    activo           BOOLEAN         NOT NULL DEFAULT TRUE,
    fecha_creacion   TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE dia_plan_ejercicio (
    id                  SERIAL  PRIMARY KEY,
    id_plan_ejercicio   INT     NOT NULL REFERENCES plan_ejercicio(id) ON DELETE CASCADE,
    numero_dia          INT     NOT NULL,
    id_ejercicio        INT     NOT NULL REFERENCES ejercicio(id),
    orden               INT,
    UNIQUE (id_plan_ejercicio, numero_dia, id_ejercicio)
);

-- ──────────────────────────────────────────────────────────────
-- 4. INTERACCIONES Y PROGRESO
-- ──────────────────────────────────────────────────────────────
CREATE TABLE comentario_receta (
    id          SERIAL      PRIMARY KEY,
    id_receta   INT         NOT NULL REFERENCES receta(id) ON DELETE CASCADE,
    id_usuario  INT         NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    id_padre    INT         REFERENCES comentario_receta(id) ON DELETE SET NULL,
    contenido   TEXT        NOT NULL,
    fecha       TIMESTAMP   NOT NULL DEFAULT NOW()
);

CREATE TABLE calificacion_receta (
    id          SERIAL      PRIMARY KEY,
    id_usuario  INT         NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    id_receta   INT         NOT NULL REFERENCES receta(id) ON DELETE CASCADE,
    estrellas   SMALLINT    NOT NULL CHECK (estrellas BETWEEN 1 AND 5),
    UNIQUE (id_usuario, id_receta)
);

CREATE TABLE calificacion_plan (
    id          SERIAL      PRIMARY KEY,
    id_usuario  INT         NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    id_plan     INT         NOT NULL REFERENCES plan_alimenticio(id) ON DELETE CASCADE,
    estrellas   SMALLINT    NOT NULL CHECK (estrellas BETWEEN 1 AND 5),
    comentario  TEXT,
    fecha       TIMESTAMP   NOT NULL DEFAULT NOW(),
    UNIQUE (id_usuario, id_plan)
);

CREATE TABLE plan_usuario_alimenticio (
    id              SERIAL      PRIMARY KEY,
    id_usuario      INT         NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    id_plan         INT         NOT NULL REFERENCES plan_alimenticio(id) ON DELETE CASCADE,
    fecha_inicio    DATE        NOT NULL,
    activo          BOOLEAN     NOT NULL DEFAULT TRUE
);

CREATE TABLE plan_usuario_ejercicio (
    id              SERIAL      PRIMARY KEY,
    id_usuario      INT         NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    id_plan         INT         NOT NULL REFERENCES plan_ejercicio(id) ON DELETE CASCADE,
    fecha_inicio    DATE        NOT NULL,
    activo          BOOLEAN     NOT NULL DEFAULT TRUE
);

CREATE TABLE progreso_plan (
    id               SERIAL      PRIMARY KEY,
    id_plan_usuario  INT         NOT NULL REFERENCES plan_usuario_alimenticio(id) ON DELETE CASCADE,
    id_dia_plan      INT         NOT NULL REFERENCES dia_plan(id),
    completado       BOOLEAN     NOT NULL DEFAULT FALSE,
    fecha_completado TIMESTAMP
);

-- ──────────────────────────────────────────────────────────────
-- 5. VIDEOLLAMADA, IA Y CHAT
-- ──────────────────────────────────────────────────────────────
CREATE TABLE sesion_videollamada (
    id              SERIAL          PRIMARY KEY,
    id_usuario      INT             NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    id_profesional  INT             NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    fecha_hora      TIMESTAMP       NOT NULL,
    estado          VARCHAR(20)     DEFAULT 'agendada'
);

CREATE TABLE contacto_profesional (
    id               SERIAL          PRIMARY KEY,
    id_usuario       INT             NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    id_profesional   INT             NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    estado           VARCHAR(30)     DEFAULT 'pendiente'
);

CREATE TABLE recomendacion_ia (
    id               SERIAL          PRIMARY KEY,
    id_usuario       INT             NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    tipo             VARCHAR(50)     NOT NULL,
    respuesta_ia     TEXT            NOT NULL,
    fecha_generacion TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE notificacion (
    id          SERIAL          PRIMARY KEY,
    id_usuario  INT             NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    titulo      VARCHAR(200)    NOT NULL,
    leida       BOOLEAN         NOT NULL DEFAULT FALSE,
    fecha_envio TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE plan_notificacion (
    id                   SERIAL          PRIMARY KEY,
    id_plan_alimenticio  INT             REFERENCES plan_usuario_alimenticio(id) ON DELETE CASCADE,
    id_plan_ejercicio    INT             REFERENCES plan_usuario_ejercicio(id)   ON DELETE CASCADE,
    id_usuario           INT             NOT NULL REFERENCES usuario(id)         ON DELETE CASCADE,
    hora_notificacion    TIME            NOT NULL,
    activa               BOOLEAN         NOT NULL DEFAULT TRUE
);

CREATE TABLE conversacion (
    id           SERIAL      PRIMARY KEY,
    id_usuario_1 INT         NOT NULL REFERENCES usuario(id),
    id_usuario_2 INT         NOT NULL REFERENCES usuario(id),
    UNIQUE (id_usuario_1, id_usuario_2)
);

CREATE TABLE mensaje (
    id               SERIAL          PRIMARY KEY,
    id_conversacion  INT             NOT NULL REFERENCES conversacion(id) ON DELETE CASCADE,
    id_remitente     INT             NOT NULL REFERENCES usuario(id),
    contenido        TEXT            NOT NULL,
    fecha_envio      TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE logro_usuario (
    id          SERIAL          PRIMARY KEY,
    id_usuario  INT             NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    nombre      VARCHAR(150)    NOT NULL,
    fecha       TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- ──────────────────────────────────────────────────────────────
-- 6. ÍNDICES
-- ──────────────────────────────────────────────────────────────
CREATE INDEX idx_usuario_rol            ON usuario(id_rol);
CREATE INDEX idx_usuario_username       ON usuario(username);
CREATE INDEX idx_receta_categoria       ON receta(id_categoria);
CREATE INDEX idx_receta_autor           ON receta(id_autor);
CREATE INDEX idx_ingrediente_tipo       ON ingrediente(id_tipo_ingrediente);
CREATE INDEX idx_ri_receta              ON receta_ingrediente(id_receta);
CREATE INDEX idx_notif_usuario          ON notificacion(id_usuario);
CREATE INDEX idx_cal_plan               ON calificacion_plan(id_plan);
CREATE INDEX idx_progreso_plan          ON progreso_plan(id_plan_usuario);

-- ──────────────────────────────────────────────────────────────
-- 7. DATOS SEMILLA
-- ──────────────────────────────────────────────────────────────
INSERT INTO rol (nombre, descripcion) VALUES
    ('usuario',       'Usuario estándar'),
    ('nutricionista', 'Nutricionista verificado'),
    ('entrenador',    'Entrenador verificado'),
    ('admin',         'Administrador');

INSERT INTO tipo_ingrediente (nombre, descripcion) VALUES
    ('Proteína',     'Carnes, huevos, legumbres'),
    ('Carbohidrato', 'Arroces, pastas, tubérculos'),
    ('Grasa',        'Aceites, palta, frutos secos'),
    ('Vegetal',      'Verduras'),
    ('Fruta',        'Frutas'),
    ('Lácteo',       'Leche, queso'),
    ('Cereal',       'Avena, quinoa'),
    ('Legumbre',     'Lentejas, frijoles'),
    ('Condimento',   'Especias');

INSERT INTO categoria_receta (nombre) VALUES
    ('Desayuno'), ('Almuerzo'), ('Cena'), ('Snack'), ('Postres saludables'), ('Bebidas');

INSERT INTO ingrediente (nombre, unidad, id_tipo_ingrediente) VALUES
    ('Pechuga de pollo', 'gramos', 1),
    ('Arroz integral',   'gramos', 2),
    ('Espinaca',         'gramos', 4),
    ('Palta',            'piezas', 3);

INSERT INTO usuario (username, nombre, apellido, email, contrasena_hash, id_rol) VALUES
    ('admin_kh',   'Admin', 'Kitchen Hack', 'admin@kitchenhack.pe', 'hash123', 4),
    ('nutri_demo', 'Maria', 'Nutri', 'nutri@kitchenhack.pe', 'hash123', 2),
    ('coach_demo', 'Juan', 'Entrenador', 'coach@kitchenhack.pe', 'hash123', 3);