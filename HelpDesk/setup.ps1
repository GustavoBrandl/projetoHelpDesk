#!/usr/bin/env powershell
# Script para configurar e testar a conexão com o banco de dados Help Desk

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Configurador do Help Desk" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se MySQL está instalado
Write-Host "[1/5] Verificando MySQL..." -ForegroundColor Yellow
$mysqlPath = Get-Command mysql -ErrorAction SilentlyContinue

if ($mysqlPath) {
    Write-Host "✓ MySQL encontrado em: $($mysqlPath.Source)" -ForegroundColor Green
} else {
    Write-Host "✗ MySQL não encontrado" -ForegroundColor Red
    Write-Host ""
    Write-Host "Para instalar MySQL:" -ForegroundColor Yellow
    Write-Host "1. Baixe de: https://dev.mysql.com/downloads/mysql/" -ForegroundColor Gray
    Write-Host "2. Execute o instalador" -ForegroundColor Gray
    Write-Host "3. Use credenciais padrão:" -ForegroundColor Gray
    Write-Host "   - Usuário: root" -ForegroundColor Gray
    Write-Host "   - Senha: (deixe em branco)" -ForegroundColor Gray
    Write-Host ""
    exit 1
}

Write-Host ""
Write-Host "[2/5] Verificando Java..." -ForegroundColor Yellow
$javaPath = Get-Command java -ErrorAction SilentlyContinue

if ($javaPath) {
    Write-Host "✓ Java encontrado" -ForegroundColor Green
    java -version
} else {
    Write-Host "✗ Java não encontrado" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "[3/5] Testando conexão com MySQL..." -ForegroundColor Yellow

try {
    # Tenta conectar ao MySQL
    $result = mysql -u root -e "SELECT VERSION();" 2>&1
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ Conexão com MySQL funcionando" -ForegroundColor Green
    } else {
        Write-Host "✗ Não conseguiu conectar" -ForegroundColor Red
        Write-Host "Erro: $result" -ForegroundColor Gray
    }
} catch {
    Write-Host "✗ Erro ao testar conexão: $_" -ForegroundColor Red
}

Write-Host ""
Write-Host "[4/5] Criando banco de dados..." -ForegroundColor Yellow

try {
    # Criar banco e tabelas
    $dbScript = Get-Content "database.sql" -Raw
    $dbScript | mysql -u root 2>&1
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ Banco de dados criado com sucesso" -ForegroundColor Green
        Write-Host "✓ Dados de teste inseridos" -ForegroundColor Green
        Write-Host ""
        Write-Host "Credenciais de teste:" -ForegroundColor Cyan
        Write-Host "  Email: admin@admin.com" -ForegroundColor Gray
        Write-Host "  Senha: 123456" -ForegroundColor Gray
    } else {
        Write-Host "✗ Erro ao criar banco de dados" -ForegroundColor Red
    }
} catch {
    Write-Host "✗ Erro: $_" -ForegroundColor Red
}

Write-Host ""
Write-Host "[5/5] Verificando driver MySQL..." -ForegroundColor Yellow

if (Test-Path "lib") {
    $jars = Get-ChildItem "lib" -Filter "mysql-connector*.jar" -ErrorAction SilentlyContinue
    if ($jars.Count -gt 0) {
        Write-Host "✓ Driver MySQL encontrado:" -ForegroundColor Green
        $jars | ForEach-Object { Write-Host "  - $($_.Name)" -ForegroundColor Gray }
    } else {
        Write-Host "✗ Driver MySQL não encontrado em ./lib/" -ForegroundColor Red
        Write-Host ""
        Write-Host "Para baixar o driver:" -ForegroundColor Yellow
        Write-Host "1. Acesse: https://dev.mysql.com/downloads/connector/j/" -ForegroundColor Gray
        Write-Host "2. Baixe: mysql-connector-java-5.1.49.jar" -ForegroundColor Gray
        Write-Host "3. Crie a pasta: lib/" -ForegroundColor Gray
        Write-Host "4. Coloque o JAR em: lib/mysql-connector-java-5.1.49.jar" -ForegroundColor Gray
    }
} else {
    Write-Host "✗ Pasta lib/ não existe" -ForegroundColor Red
    Write-Host "Criando pasta lib/..." -ForegroundColor Yellow
    New-Item -ItemType Directory -Path "lib" -Force | Out-Null
    Write-Host "✓ Pasta lib/ criada" -ForegroundColor Green
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Configuração completa!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Para compilar e executar:" -ForegroundColor Yellow
Write-Host ""
Write-Host "1. Compilar:" -ForegroundColor Cyan
Write-Host "   javac -d bin -cp . src\*.java src\BO\*.java src\Conexao\*.java src\Controller\*.java src\DAO\*.java src\DTO\*.java src\ENUM\*.java src\Util\*.java src\View\*.java" -ForegroundColor Gray
Write-Host ""
Write-Host "2. Executar:" -ForegroundColor Cyan
Write-Host "   java -cp ""bin:lib/*"" Main" -ForegroundColor Gray
Write-Host ""
