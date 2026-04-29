# Resolución: JAVA_HOME no está configurado

## 🔴 Problema
El proyecto no puede ejecutarse porque:
- **Error**: `The JAVA_HOME environment variable is not defined correctly`
- **Causa**: Java 17 JDK no está instalado o no está en el PATH del sistema

## ✅ Solución

### Paso 1: Instalar Java 17 JDK

**Opción A: Descargar desde Oracle (Recomendado)**
1. Abre https://www.oracle.com/java/technologies/downloads/#java17
2. Descarga el **Windows x64 Installer** (`.exe`)
3. Ejecuta el instalador y sigue las instrucciones por defecto
4. El instalador configurará automáticamente `JAVA_HOME` (típicamente en `C:\Program Files\Java\jdk-17.x.x`)

**Opción B: Descargar desde OpenJDK (Alternativa gratuita)**
1. Abre https://adoptium.net/
2. Selecciona:
   - Version: **17** (LTS)
   - OS: **Windows**
   - Architecture: **x64**
   - Package: **JDK**
3. Descarga e instala el `.msi`

### Paso 2: Verificar la instalación

Abre una **nueva terminal PowerShell** (importante: cierrar la anterior) y ejecuta:

```powershell
java -version
```

Debe mostrar algo como:
```
java version "17.x.x" 2024-...
Java(TM) SE Runtime Environment (build 17.x.x+...)
Java HotSpot(TM) 64-Bit Server VM (build 17.x.x+..., ...)
```

### Paso 3: Compilar el proyecto

En la **nueva terminal**, navega al proyecto:

```powershell
cd C:\Users\More\Documents\ArquiWeb_Grupo1

# Compilar
./mvnw.cmd clean compile

# Ejecutar pruebas
./mvnw.cmd test

# Ejecutar la aplicación
./mvnw.cmd spring-boot:run
```

## 🔍 Verificación Manual de JAVA_HOME (Opcional)

Si deseas confirmar que `JAVA_HOME` está configurado, ejecuta:

```powershell
$env:JAVA_HOME
```

Debe mostrar una ruta como:
```
C:\Program Files\Java\jdk-17.0.x
```

Si está vacío, el instalador de Java lo configurará automáticamente cuando lo instales.

## 📋 Requisitos de BD (Importante)

Para que las pruebas y la ejecución funcionen correctamente:
- **PostgreSQL** debe estar corriendo en `localhost:5432`
- Base de datos `kitchenhack` debe existir
- Ejecutar el script SQL de seed: `C:\Users\More\Documents\ArquiWeb_Grupo1\bd\kitchen_hack_v7.sql`

## 🆘 Si aún no funciona

1. Cierra todas las terminales y reabre PowerShell como **Administrador**
2. Ejecuta:
   ```powershell
   $env:JAVA_HOME
   ```
3. Si sigue vacío, reinicia tu computadora
4. Verifica que `C:\Program Files\Java\` tenga una carpeta `jdk-17.x.x`

---

**Después de instalar Java, todo debería funcionar correctamente. ¡Prueba con `./mvnw.cmd clean compile`!**

