# Script para verificar el estado del proyecto y Java
# Ejecutar en PowerShell: .\verificar-estado.ps1

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   VERIFICACIÓN DE ESTADO DEL PROYECTO" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 1. Verificar Java
Write-Host "[1/5] Verificando Java 17..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1
    if ($javaVersion -match "17") {
        Write-Host "✅ Java 17 DETECTADO" -ForegroundColor Green
        Write-Host "   $($javaVersion[0])" -ForegroundColor Green
    } else {
        Write-Host "⚠️  Java detectado pero NOT es versión 17" -ForegroundColor Yellow
        Write-Host "   Versión: $($javaVersion[0])" -ForegroundColor Yellow
    }
} catch {
    Write-Host "❌ Java NOT detectado" -ForegroundColor Red
    Write-Host "   Instala Java 17 desde: https://www.oracle.com/java/technologies/downloads/#java17" -ForegroundColor Red
}
Write-Host ""

# 2. Verificar JAVA_HOME
Write-Host "[2/5] Verificando variable JAVA_HOME..." -ForegroundColor Yellow
$javaHome = $env:JAVA_HOME
if ($javaHome) {
    Write-Host "✅ JAVA_HOME configurado: $javaHome" -ForegroundColor Green
} else {
    Write-Host "❌ JAVA_HOME NO está configurado" -ForegroundColor Red
    Write-Host "   Reinicia la terminal o equipo después de instalar Java" -ForegroundColor Red
}
Write-Host ""

# 3. Verificar Maven Wrapper
Write-Host "[3/5] Verificando Maven Wrapper..." -ForegroundColor Yellow
if (Test-Path ".\mvnw.cmd") {
    Write-Host "✅ Maven Wrapper encontrado" -ForegroundColor Green
} else {
    Write-Host "❌ Maven Wrapper NOT encontrado" -ForegroundColor Red
}
Write-Host ""

# 4. Verificar PostgreSQL
Write-Host "[4/5] Verificando PostgreSQL (localhost:5432)..." -ForegroundColor Yellow
try {
    $socket = New-Object System.Net.Sockets.TcpClient
    $socket.Connect("localhost", 5432)
    $socket.Close()
    Write-Host "✅ PostgreSQL está DISPONIBLE en localhost:5432" -ForegroundColor Green
} catch {
    Write-Host "⚠️  PostgreSQL NO accesible en localhost:5432" -ForegroundColor Yellow
    Write-Host "   Inicia PostgreSQL o verifica la configuración" -ForegroundColor Yellow
}
Write-Host ""

# 5. Verificar ficheros de configuración
Write-Host "[5/5] Verificando ficheros de configuración..." -ForegroundColor Yellow
$configFile = ".\src\main\resources\application.properties"
if (Test-Path $configFile) {
    Write-Host "✅ application.properties encontrado" -ForegroundColor Green
    $content = Get-Content $configFile | Select-String "spring.datasource"
    Write-Host "   BD URL: $($content[0])" -ForegroundColor Green
} else {
    Write-Host "❌ application.properties NOT encontrado" -ForegroundColor Red
}
Write-Host ""

# Resumen
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "RESUMEN Y PRÓXIMOS PASOS" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

if ($env:JAVA_HOME -and (java -version 2>&1 | Select-String "17")) {
    Write-Host "✅ TODO LISTO PARA COMPILAR" -ForegroundColor Green
    Write-Host ""
    Write-Host "Ejecuta:" -ForegroundColor Yellow
    Write-Host "  ./mvnw.cmd clean compile" -ForegroundColor Cyan
    Write-Host "  ./mvnw.cmd spring-boot:run" -ForegroundColor Cyan
} else {
    Write-Host "❌ ACCIONES NECESARIAS:" -ForegroundColor Red
    Write-Host ""
    Write-Host "1. Instala Java 17 JDK desde:" -ForegroundColor Yellow
    Write-Host "   https://www.oracle.com/java/technologies/downloads/#java17" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "2. Reinicia este equipo" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "3. Abre una NUEVA terminal PowerShell en la carpeta del proyecto" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "4. Ejecuta de nuevo este script: .\verificar-estado.ps1" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan

