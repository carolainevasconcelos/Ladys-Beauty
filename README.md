# 💇 Lady's Beauty

Sistema de agendamento para salões de beleza, desenvolvido em **Java, HTML, CSS e JavaScript**. A aplicação possui funcionalidades completas para o gerenciamento de clientes, funcionários e administradores, focando em usabilidade e controle de agendamentos.

---

## 🚀 Como clonar o projeto

1. Abra o terminal ou Git Bash.
2. Escolha a pasta onde deseja salvar o projeto.
3. Execute o comando:

```bash
git clone https://github.com/carolainevasconcelos/Ladys-Beauty.git
```

4. Importe o projeto no NetBeans ou na IDE de sua preferência.

---

## 🧾 Sobre o projeto

Lady's Beauty é um sistema web completo para salões de beleza, com as seguintes funcionalidades:

### 👥 Atores do sistema:
- **Administrador**: controle total do sistema, incluindo gerenciamento de funcionários.
- **Funcionário**: gerenciamento de agendamentos e serviços.
- **Cliente**: agendamento, reagendamento e cancelamento de serviços, visualização de histórico e saldo de pontos.

### 🔧 Funcionalidades principais:
- **CRUD de Usuário, Serviço e Agendamento**
- **Autenticação com criptografia de senha**
- **Sistema de busca**
- **Notificações sobre status do agendamento**
- **Programa de pontos para fidelidade**
- **Relatório de frequência de clientes**
- **Controle de acesso por perfil**

---

## 🗃️ Script do Banco de Dados (MySQL)

```sql
-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS SistemaAgendamentos;
USE SistemaAgendamentos;

-- Tabela de Clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(15) NOT NULL, 
    senha_hash VARCHAR(255) NOT NULL,
    saldo_pontos INT DEFAULT 0
);

-- Tabela de Funcionários
CREATE TABLE IF NOT EXISTS funcionarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    cargo ENUM('administrador', 'funcionario') NOT NULL DEFAULT 'funcionario', 
    especialidade ENUM('cabelos', 'unhas', 'maquiagem', 'sobrancelhas', 'depilacao') DEFAULT NULL,
    senha_hash VARCHAR(255) NOT NULL
);

-- Tabela de Serviços
CREATE TABLE IF NOT EXISTS servicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2) NOT NULL,
    pontos_ganho INT DEFAULT 0,
    pontos_resgate INT DEFAULT 0,
    funcionario_id INT NOT NULL, 
    categoria ENUM('unha', 'cabelo', 'maquiagem', 'sobrancelhas', 'depilacao') NOT NULL, 
    status ENUM('ativo', 'inativo') DEFAULT 'ativo', 
    FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id) ON DELETE CASCADE
);

-- Tabela de Agendamentos
CREATE TABLE IF NOT EXISTS agendamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    funcionario_id INT NULL, 
    servico_id INT NOT NULL,
    data_agendamento DATE NOT NULL,  
    hora_agendamento TIME NOT NULL,  
    statu ENUM('agendado', 'cancelado', 'concluido'),
    pagamento_pontos ENUM('sim', 'nao'),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
    FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id) ON DELETE SET NULL,
    FOREIGN KEY (servico_id) REFERENCES servicos(id) ON DELETE CASCADE,
    UNIQUE (funcionario_id, data_agendamento, hora_agendamento)
);

-- Tabela de Transações de Pontos
CREATE TABLE IF NOT EXISTS transacoes_pontos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    servico_id INT NOT NULL,
    agendamento_id INT NULL,
    tipo ENUM('ganho', 'resgate') NOT NULL, 
    quantidade INT NOT NULL, 
    data_transacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
    FOREIGN KEY (servico_id) REFERENCES servicos(id) ON DELETE CASCADE,
    FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id) ON DELETE SET NULL
);

-- Tabela de Notificações
CREATE TABLE IF NOT EXISTS notificacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL,
    assunto VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lida BOOLEAN DEFAULT FALSE,
    agendamento_id INT NULL DEFAULT NULL,
    FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id) ON DELETE SET NULL
);

-- Consultas úteis
/*
-- Verificar conflitos de agendamento
SELECT funcionario_id, data_agendamento, hora_agendamento, COUNT(*) AS total_agendamentos
FROM agendamentos
GROUP BY funcionario_id, data_agendamento, hora_agendamento
HAVING COUNT(*) > 1;

-- Frequência de clientes
SELECT c.id AS idCliente, c.nome AS nomeCliente, COUNT(a.id) AS frequencia
FROM clientes c
LEFT JOIN agendamentos a ON c.id = a.cliente_id
GROUP BY c.id, c.nome
ORDER BY frequencia DESC;

-- Notificações de um funcionário
SELECT * FROM notificacoes 
WHERE usuario_id = 5 AND tipo_usuario = 'funcionario' 
ORDER BY data_criacao DESC;
*/

---

## 🛠️ Tecnologias usadas

- Java (Servlets + JSP)
- HTML5, CSS3 e JavaScript
- MySQL (banco de dados)
- NetBeans (IDE)
- Git/GitHub

---

## 📌 Observações

- O projeto **não possui integração com pagamento online** — os pagamentos são realizados presencialmente no salão.
- O agendamento com pontos **devolve os pontos ao cliente** em caso de cancelamento.
- As permissões são controladas por tipo de usuário (**cliente, funcionário, administrador**).

---

Feito com 💜 por [Carolaine Vasconcelos](https://github.com/carolainevasconcelos)
