-- ================================================================
--  KITCHEN HACK — Base de Datos v4.2 (Optimized)
-- ================================================================

-- 0. LIMPIEZA TOTAL (Orden jerárquico)
DROP TABLE IF EXISTS logro_usuario CASCADE;
DROP TABLE IF EXISTS notificacion CASCADE;
DROP TABLE IF EXISTS recomendacion_ia CASCADE;
DROP TABLE IF EXISTS contacto_profesional CASCADE;
DROP TABLE IF EXISTS progreso_plan CASCADE;
DROP TABLE IF EXISTS plan_usuario_ejercicio CASCADE;
DROP TABLE IF EXISTS plan_usuario_alimenticio CASCADE;
DROP TABLE IF EXISTS dia_plan_ejercicio CASCADE;
DROP TABLE IF EXISTS ejercicio CASCADE;
DROP TABLE IF EXISTS plan_ejercicio CASCADE;
DROP TABLE IF EXISTS dia_plan_receta CASCADE;
DROP TABLE IF EXISTS dia_plan CASCADE;
DROP TABLE IF EXISTS calificacion_plan CASCADE;
DROP TABLE IF EXISTS plan_alimenticio CASCADE;
DROP TABLE IF EXISTS comentario_receta CASCADE;
DROP TABLE IF EXISTS receta_ingrediente CASCADE;
DROP TABLE IF EXISTS paso_receta CASCADE;
DROP TABLE IF EXISTS receta CASCADE;
DROP TABLE IF EXISTS ingrediente CASCADE;
DROP TABLE IF EXISTS tipo_ingrediente CASCADE;
DROP TABLE IF EXISTS categoria_receta CASCADE;
DROP TABLE IF EXISTS progreso_salud CASCADE;
DROP TABLE IF EXISTS perfil_profesional CASCADE;
DROP TABLE IF EXISTS usuario CASCADE;
DROP TABLE IF EXISTS rol CASCADE;

-- 1. MÓDULO USUARIOS
CREATE TABLE rol (
                     id SERIAL PRIMARY KEY,
                     nombre VARCHAR(50) NOT NULL UNIQUE,
                     descripcion TEXT
);

CREATE TABLE usuario (
                         id SERIAL PRIMARY KEY,
                         username VARCHAR(80) NOT NULL UNIQUE,
                         nombre VARCHAR(100) NOT NULL,
                         apellido VARCHAR(100) NOT NULL,
                         email VARCHAR(150) NOT NULL UNIQUE,
                         telefono VARCHAR(20) UNIQUE,
                         contrasena_hash VARCHAR(255) NOT NULL,
                         foto_perfil_url VARCHAR(500),
                         bio TEXT,
                         id_rol INT NOT NULL DEFAULT 1 REFERENCES rol(id),
                         fecha_registro TIMESTAMP NOT NULL DEFAULT NOW(),
                         activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE perfil_profesional (
                                    id SERIAL PRIMARY KEY,
                                    id_usuario INT NOT NULL UNIQUE REFERENCES usuario(id) ON DELETE CASCADE,
                                    especialidad VARCHAR(100),
                                    numero_colegiatura VARCHAR(100),
                                    documento_titulo_url VARCHAR(500),
                                    descripcion_profesional TEXT,
                                    verificado BOOLEAN NOT NULL DEFAULT FALSE,
                                    fecha_verificacion TIMESTAMP
);

CREATE TABLE progreso_salud (
                                id SERIAL PRIMARY KEY,
                                id_usuario INT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
                                fecha_registro DATE NOT NULL DEFAULT CURRENT_DATE,
                                peso_kg NUMERIC(5,2),
                                talla_cm NUMERIC(5,2),
                                imc NUMERIC(4,2),
                                objetivo VARCHAR(100),
                                alergias TEXT,
                                tipo_dieta VARCHAR(50)
);

-- 2. MÓDULO RECETAS
CREATE TABLE categoria_receta (
                                  id SERIAL PRIMARY KEY,
                                  nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE tipo_ingrediente (
                                  id SERIAL PRIMARY KEY,
                                  nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE ingrediente (
                             id SERIAL PRIMARY KEY,
                             nombre VARCHAR(150) NOT NULL UNIQUE,
                             unidad VARCHAR(50),
                             id_tipo_ingrediente INT NOT NULL REFERENCES tipo_ingrediente(id)
);

CREATE TABLE receta (
                        id SERIAL PRIMARY KEY,
                        titulo VARCHAR(200) NOT NULL,
                        descripcion TEXT,
                        id_categoria INT REFERENCES categoria_receta(id) ON DELETE SET NULL,
                        id_autor INT NOT NULL REFERENCES usuario(id),
                        tiempo_preparacion INT,
                        porciones INT DEFAULT 1,
                        dificultad VARCHAR(20) CHECK (dificultad IN ('facil','medio','dificil')),
                        imagen_url VARCHAR(500),
                        calorias_totales NUMERIC(7,2),
                        proteinas_g NUMERIC(6,2),
                        carbohidratos_g NUMERIC(6,2),
                        grasas_g NUMERIC(6,2),
                        calificacion_promedio NUMERIC(3,2) DEFAULT 0.00,
                        publicada BOOLEAN NOT NULL DEFAULT TRUE,
                        fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE receta_ingrediente (
                                    id SERIAL PRIMARY KEY,
                                    id_receta INT NOT NULL REFERENCES receta(id) ON DELETE CASCADE,
                                    id_ingrediente INT NOT NULL REFERENCES ingrediente(id),
                                    cantidad NUMERIC(8,2) NOT NULL,
                                    nota VARCHAR(200)
);

CREATE TABLE paso_receta (
                             id SERIAL PRIMARY KEY,
                             id_receta INT NOT NULL REFERENCES receta(id) ON DELETE CASCADE,
                             numero_paso INT NOT NULL,
                             descripcion TEXT NOT NULL,
                             UNIQUE (id_receta, numero_paso)
);

-- 3. MÓDULO PLANES
CREATE TABLE plan_alimenticio (
                                  id SERIAL PRIMARY KEY,
                                  titulo VARCHAR(200) NOT NULL,
                                  descripcion TEXT,
                                  id_autor INT NOT NULL REFERENCES usuario(id),
                                  duracion_dias INT NOT NULL,
                                  objetivo VARCHAR(200),
                                  calorias_diarias NUMERIC(7,2),
                                  activo BOOLEAN NOT NULL DEFAULT TRUE,
                                  fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE dia_plan (
                          id SERIAL PRIMARY KEY,
                          id_plan INT NOT NULL REFERENCES plan_alimenticio(id) ON DELETE CASCADE,
                          numero_dia INT NOT NULL,
                          UNIQUE (id_plan, numero_dia)
);

CREATE TABLE dia_plan_receta (
                                 id SERIAL PRIMARY KEY,
                                 id_dia_plan INT NOT NULL REFERENCES dia_plan(id) ON DELETE CASCADE,
                                 id_receta INT NOT NULL REFERENCES receta(id),
                                 momento VARCHAR(30) NOT NULL CHECK (momento IN ('desayuno','almuerzo','cena','snack')),
                                 orden INT
);

CREATE TABLE ejercicio (
                           id SERIAL PRIMARY KEY,
                           nombre VARCHAR(150) NOT NULL,
                           grupo_muscular VARCHAR(100),
                           duracion_min INT,
                           series INT,
                           repeticiones INT
);

CREATE TABLE plan_ejercicio (
                                id SERIAL PRIMARY KEY,
                                titulo VARCHAR(200) NOT NULL,
                                id_autor INT NOT NULL REFERENCES usuario(id),
                                duracion_dias INT NOT NULL,
                                nivel_dificultad VARCHAR(30) CHECK (nivel_dificultad IN ('principiante','intermedio','avanzado')),
                                activo BOOLEAN NOT NULL DEFAULT TRUE,
                                fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE dia_plan_ejercicio (
                                    id SERIAL PRIMARY KEY,
                                    id_plan_ejercicio INT NOT NULL REFERENCES plan_ejercicio(id) ON DELETE CASCADE,
                                    numero_dia INT NOT NULL,
                                    id_ejercicio INT NOT NULL REFERENCES ejercicio(id),
                                    orden INT,
                                    UNIQUE (id_plan_ejercicio, numero_dia, id_ejercicio)
);

-- 4. INTERACCIONES Y COMUNICACIÓN
CREATE TABLE comentario_receta (
                                   id SERIAL PRIMARY KEY,
                                   id_receta INT NOT NULL REFERENCES receta(id) ON DELETE CASCADE,
                                   id_usuario INT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
                                   id_padre INT REFERENCES comentario_receta(id) ON DELETE SET NULL,
                                   contenido TEXT NOT NULL,
                                   fecha TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE calificacion_plan (
                                   id SERIAL PRIMARY KEY,
                                   id_usuario INT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
                                   id_plan INT NOT NULL REFERENCES plan_alimenticio(id) ON DELETE CASCADE,
                                   estrellas SMALLINT NOT NULL CHECK (estrellas BETWEEN 1 AND 5),
                                   comentario TEXT,
                                   fecha TIMESTAMP NOT NULL DEFAULT NOW(),
                                   UNIQUE (id_usuario, id_plan)
);

CREATE TABLE plan_usuario_alimenticio (
                                          id SERIAL PRIMARY KEY,
                                          id_usuario INT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
                                          id_plan INT NOT NULL REFERENCES plan_alimenticio(id) ON DELETE CASCADE,
                                          fecha_inicio DATE NOT NULL DEFAULT CURRENT_DATE,
                                          activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE notificacion (
                              id SERIAL PRIMARY KEY,
                              id_usuario INT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
                              titulo VARCHAR(200) NOT NULL,
                              mensaje TEXT,
                              leida BOOLEAN NOT NULL DEFAULT FALSE,
                              fecha_envio TIMESTAMP NOT NULL DEFAULT NOW()
);

-- 5. ÍNDICES
CREATE INDEX idx_usuario_rol ON usuario(id_rol);
CREATE INDEX idx_receta_autor ON receta(id_autor);
CREATE INDEX idx_notif_usuario ON notificacion(id_usuario, leida);

-- 6. DATOS SEMILLA (Inserts)
INSERT INTO rol (nombre, descripcion) VALUES
                                          ('usuario', 'Estándar'),
                                          ('nutricionista', 'Profesional salud'),
                                          ('entrenador', 'Profesional deporte'),
                                          ('admin', 'Administrador sistema');

INSERT INTO categoria_receta (nombre) VALUES
                                          ('Desayuno'), ('Almuerzo'), ('Cena'), ('Snack');

INSERT INTO tipo_ingrediente (nombre) VALUES
                                          ('Proteína'), ('Carbohidrato'), ('Vegetal');

INSERT INTO usuario (username, nombre, apellido, email, contrasena_hash, id_rol) VALUES
                                                                                     ('admin_kh', 'Admin', 'Hack', 'admin@kh.pe', 'secure_hash_here', 4),
                                                                                     ('nutri_maria', 'Maria', 'Sanz', 'maria@kh.pe', 'secure_hash_here', 2);

INSERT INTO ingrediente (nombre, unidad, id_tipo_ingrediente) VALUES
                                                                  ('Pollo', 'gramos', 1),
                                                                  ('Quinoa', 'gramos', 2),
                                                                  ('Brócoli', 'gramos', 3);

INSERT INTO receta (titulo, descripcion, id_categoria, id_autor, dificultad, calorias_totales) VALUES
    ('Bowl de Quinoa y Pollo', 'Receta balanceada', 2, 2, 'facil', 450.00);

INSERT INTO receta_ingrediente (id_receta, id_ingrediente, cantidad) VALUES
                                                                         (1, 1, 150), (1, 2, 100);