<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lady's Beauty - Funcionário</title>
        <link rel="stylesheet" href="../visao/css/pagInicial.css">
    </head>

    <body>
        <!-- Header -->
        <header>
            <div id="logo">
                <img src="../visao/imagens/logo.png" alt="Logo de uma tesoura">
            </div>

            <nav>
                <ul>
                    <li><a href="pag-ADM.jsp">Início</a></li>
                    <li><a href="pag-servicos.jsp">Gerenciar Serviços</a></li>
                    <li><a href="pag-Funcionarios.jsp">Gerenciar Funcionários</a></li>
                    <li><a href="../FrequenciaClienteServlet">Frequência dos Clientes</a></li>
                </ul>
            </nav>

            <div class="auth-buttons">
                <a href="../Logout.do"><button>Sair</button></a>
            </div>
        </header>

        <!-- Opções de CRUD -->
        <section class="crud-options">
            <h2>Gerenciar Agendamentos</h2>
            <div class="option-list">
                <a href="../ListarAgendamentos.do" class="option-item">Listar Agendamentos</a>
                <a href="../CadAgendamentos.do" class="option-item">Cadastrar Agendamento</a>
            </div>
        </section>

        <!-- Rodapé -->
        <footer>
            <div class="footer-bottom">
                <p>&copy; 2025 Lady's Beauty - Carolaine Vasconcelos</p>
            </div>
        </footer>
    </body>

</html>