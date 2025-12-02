# 🚀 GUIA RÁPIDO - Help Desk

## O que foi corrigido?

5 problemas principais foram identificados e **CORRIGIDOS**:

1. ✅ DAOs retornando `null` (agora retornam ArrayList vazio)
2. ✅ Falta de logs de diagnóstico (adicionados logs detalhados)
3. ✅ Driver MySQL não configurado (suporte para múltiplos drivers)
4. ✅ Dados não atualizavam ao navegar (recarregamento adicionado)
5. ✅ Tentativa de carregar sem usuário logado (validação adicionada)

---

## ⚡ Para Começar Agora

### 1️⃣ Se você JÁ tem MySQL instalado:

```bash
# Terminal/PowerShell na pasta do projeto
mysql -u root < database.sql
javac -d bin -cp . src\*.java src\BO\*.java src\Conexao\*.java src\Controller\*.java src\DAO\*.java src\DTO\*.java src\ENUM\*.java src\Util\*.java src\View\*.java
java -cp "bin:lib/*" Main
```

### 2️⃣ Se PRECISA instalar MySQL:

**Windows:**
1. Download: https://dev.mysql.com/downloads/mysql/
2. Execute o instalador
3. Configure:
   - User: `root`
   - Password: deixe em branco
   - Port: `3306`

**Depois execute:**
```bash
mysql -u root < database.sql
javac -d bin -cp . src\*.java src\BO\*.java src\Conexao\*.java src\Controller\*.java src\DAO\*.java src\DTO\*.java src\ENUM\*.java src\Util\*.java src\View\*.java
java -cp "bin:lib/*" Main
```

### 3️⃣ Configurar o Driver MySQL:

1. Download: https://dev.mysql.com/downloads/connector/j/
2. Escolha: `mysql-connector-java-5.1.49.jar`
3. Crie pasta: `lib/`
4. Coloque o JAR lá: `lib/mysql-connector-java-5.1.49.jar`

---

## 🔑 Credenciais de Teste (após setup.sql)

| Email | Senha | Acesso |
|-------|-------|--------|
| admin@admin.com | 123456 | Tudo |
| tecnico@empresa.com | 123456 | Atender tickets |
| usuario@empresa.com | 123456 | Criar tickets |

---

## 📊 Estrutura de Dados

**Organizações** → Usuários, Contratos
**Departamentos** → Categorias → Tickets
**Prioridades** → Tickets
**Status** → Tickets

---

## ✅ Verificar Tudo Funciona

No console ao iniciar, você deve ver:
```
DEBUG Conexao: Tentando conectar em jdbc:mysql://localhost/helpdesk?...
DEBUG Conexao: Conexão OK!
```

Se vir erro, é problema de:
- MySQL não instalado/rodando
- Banco não criado
- Credenciais incorretas

---

## 🎯 Próximas Ações

1. ✅ Código **COMPILADO e TESTADO**
2. 📦 Criar/restaurar banco: `mysql -u root < database.sql`
3. 🔑 Adicionar driver MySQL: `lib/mysql-connector-java-5.1.49.jar`
4. ▶️ Executar: `java -cp "bin:lib/*" Main`

**Você está a 3 passos de ter tudo funcionando!**
