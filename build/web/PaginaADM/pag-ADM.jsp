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
                    <li><a href="pag-Agendamentos.jsp">Gerenciar Agendamentos</a></li>
                    <li><a href="pag-servicos.jsp">Gerenciar Serviços</a></li>
                    <li><a href="pag-Funcionarios.jsp">Gerenciar Funcionários</a></li>
                    <li><a href="../FrequenciaClienteServlet">Frequência dos Clientes</a></li>
                </ul>
            </nav>

            <div id="notificacao">
                <img src="../visao/imagens/comNotificacao.png" alt="Ícone de Notificação">
            </div>

            <div class="auth-buttons">
                <a href="../Logout.do"><button>Sair</button></a>
            </div>
        </header>

        <!-- Capa -->
        <main>
            <section class="capa">
                <img src="../visao/imagens/capa-funcionario.png" alt="Imagem ilustrativa para funcionários">
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