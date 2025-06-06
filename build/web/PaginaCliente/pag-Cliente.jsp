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
                    <li><a href="../index.jsp">Site</a></li>
                    <li><a href="<%= request.getContextPath() %>/AgendamentosRestritosServlet">Gerenciar Agendamentos</a></li>
                    <li><a href="pag-AgendamentosPP.jsp">Pontos</a></li>
                </ul>
            </nav>

            <div id="notificacao">
                <a href="../ListarNotificacoesServlet" title="Ver Notificações">
                    <img src="../visao/imagens/comNotificacao.png" alt="Ícone de Notificação">
                </a>
            </div>

            <!-- Ícone do perfil e popover -->
            <div id="notificacao" class="perfil-container">
                <a href="<%= request.getContextPath() %>/PerfilClienteServlet">
                    <img src="../visao/imagens/perfil.png" alt="Perfil">
                </a>
                <div id="dadosCliente"></div>
            </div>


            <div class="auth-buttons">
                <a href="../Logout.do"><button>Sair</button></a>
            </div>
        </header>

        <!-- Capa -->
        <main>
            <section class="capa">
                <img src="../visao/imagens/capa-cliente.png" alt="Imagem ilustrativa para clientes">
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