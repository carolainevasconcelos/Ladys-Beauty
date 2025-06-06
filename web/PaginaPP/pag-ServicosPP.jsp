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
                    <li><a href="pag-PP.jsp">Início</a></li>
                    <li><a href="pag-AgendamentosPP.jsp">Gerenciar Agendamentos</a></li>
                    <li><a href="pag-FrequenciaPP.jsp">Frequência dos Clientes</a></li>
                </ul>
            </nav>

            <div class="auth-buttons">
                <a href="../login.jsp"><button>Sair</button></a>
            </div>
        </header>

        <!-- Opções de CRUD -->
        <section class="crud-options">
            <h2>Gerenciar Serviços</h2>
            <div class="option-list">
                <a href="../ListarServico.do" class="option-item">Listar Serviços</a>
                <a href="../cad-servico.jsp" class="option-item">Cadastrar Serviços</a>
            </div>
        </section>
    </main>

    <!-- Rodapé -->
    <footer>
        <div class="footer-bottom">
            <p>&copy; 2025 Lady's Beauty - Carolaine Vasconcelos</p>
        </div>
    </footer>
</body>

</html>