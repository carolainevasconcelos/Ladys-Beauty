<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.controle.Servico" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lady's Beauty</title>
    <link rel="stylesheet" href="visao/css/style.css">
</head>

<body>
<header>
    <div id="logo">
        <img src="visao/imagens/logo.png" alt="Logo de uma tesoura">
    </div>
    <div class="auth-buttons">
        <form action="BuscarServico.do" method="get" class="busca">
            <input type="text" name="query" placeholder="Buscar serviços...">
            <button type="submit"><img src="visao/imagens/lupa.png" alt="Imagem de uma lupa"></button>
        </form>
        <a href="login.jsp"><button>Acessar</button></a>
    </div>
</header>

<!-- Capa (Slideshow) -->
<section class="capa">
    <div class="slide fade">
        <img src="visao/imagens/capa1.png" alt="Capa 1 - Spa dos pés">
    </div>
    <div class="slide fade">
        <img src="visao/imagens/capa2.png" alt="Capa 2 - Promoção em esmaltação">
    </div>
</section>

<!-- Ícones de Destaque -->
<section class="destaques">
    <div class="icone"><img src="visao/imagens/premio.png" alt="Imagem de um prêmio" id="premio"> Melhor Salão da Cidade</div>
    <div class="icone"><img src="visao/imagens/profissional.png" alt="Imagem de uma pessoa com um prêmio" id="profissional"> Profissionais Qualificados</div>
    <div class="icone"><img src="visao/imagens/calendario.png" alt="Imagem de um calendário" id="calendario"> Agendamento Online</div>
</section>

<hr style="border: 1px solid #fe97b0; width: 80%; margin: 30px auto;">

<!-- Serviços -->
<section class="servicos">
    <div class="container">
        <h2 class="titulo">Serviços Disponíveis</h2>
        <div class="servico-lista">
            <%
                List<Servico> servicos = (List<Servico>) request.getAttribute("servicos");
                boolean buscaRealizada = request.getAttribute("buscaRealizada") != null;

                if (servicos != null && !servicos.isEmpty()) {
                    for (Servico servico : servicos) {
            %>
            <div class="servico-card">
                <h3><%= servico.getNome() %></h3>
                <p class="preco">R$ <%= servico.getPreco() %></p>
                <p><%= servico.getDescricao() %></p>
                <p><strong>Categoria:</strong> <%= servico.getCategoria() %></p>
                <p><strong>Pontos para Ganho:</strong> <%= servico.getPontosGanho() %></p>
                <p><strong>Pontos para Resgate:</strong> <%= servico.getPontosResgate() %></p>
                <a href="#"><button>Agende Agora!</button></a>
            </div>
            <%
                    }
                } else {
            %>
            <p class="sem-servicos">
                <%
                    if (buscaRealizada) {
                %>
                    Nenhum serviço encontrado para "<%= request.getAttribute("termoBuscado") %>".
                <%
                    } else {
                %>
                    Não há serviços disponíveis no momento.
                <%
                    }
                %>
            </p>
            <% } %>
        </div>
    </div>
</section>

<!-- Rodapé -->
<footer>
    <div class="footer-container">
        <div class="footer-section about">
            <h3>Quem somos?</h3>
            <p>Lady's Beauty é um salão especializado em realçar a beleza e autoestima de nossos clientes. Oferecemos serviços de qualidade com uma equipe profissional dedicada.</p>
        </div>

        <div class="footer-section contact">
            <h3>Contato</h3>
            <p>📍 Rua das Flores, 123 - Centro</p>
            <p>📞 (61) 91234-5678</p>
            <p>📩 contato@ladysbeauty.com</p>
        </div>

        <div class="footer-section hours">
            <h3>Horário de Atendimento</h3>
            <p>🕘 Ter-Sex: 09h - 19h</p>
            <p>🕘 Sáb-Dom: 09h - 22h</p>
            <p>🚫 Segunda: Fechado</p>
        </div>
    </div>

    <div class="footer-bottom">
        <p>&copy; 2025 Lady's Beauty - Carolaine Vasconcelos</p>
    </div>
</footer>

<script>
    let currentIndex = 0;
    const slides = document.querySelectorAll('.slide');
    const totalSlides = slides.length;

    function showSlide() {
        slides.forEach((slide) => {
            slide.classList.remove('active');
        });
        currentIndex = (currentIndex + 1) % totalSlides;
        slides[currentIndex].classList.add('active');
    }

    showSlide();
    setInterval(showSlide, 5000);
</script>
</body>
</html>
