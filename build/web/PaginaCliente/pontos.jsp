<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Meus Pontos - Lady's Beauty</title>
    <link rel="stylesheet" href="visao/css/pagInicial.css" />
    <style>
        :root {
            --cor1: #f4eeec;
            --cor2: #4c4f72;
            --cor3: #fd3765;
            --cor4: #fd6907;
            --cor5: #fe97b0;
        }

        .container-pontos {
            max-width: 960px;
            margin: 40px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
            font-family: 'Poppins', sans-serif;
        }

        .saldo-card {
            background: linear-gradient(135deg, var(--cor3), var(--cor5));
            color: white;
            padding: 30px;
            border-radius: 8px;
            text-align: center;
            margin-bottom: 40px;
        }

        .saldo-card h2 {
            margin: 0;
            font-size: 1.5em;
            font-weight: 500;
        }

        .saldo-card .pontos {
            font-size: 4.5em;
            font-weight: 700;
            line-height: 1.2;
        }

        .secao-titulo {
            font-family: 'Tenor Sans', sans-serif;
            border-bottom: 2px solid var(--cor3);
            padding-bottom: 10px;
            margin-bottom: 20px;
            color: var(--cor2);
            font-size: 1.8em;
        }

        .servico-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px 15px;
            border-bottom: 1px solid #eee;
            transition: background-color 0.3s;
        }

        .servico-item:hover {
            background-color: #fafafa;
        }

        .servico-item .info h4 {
            margin: 0 0 5px 0;
            color: var(--cor2);
            font-size: 1.1em;
        }

        .servico-item .info p {
            margin: 0;
            color: #777;
            font-size: 0.9em;
        }

        .custo-resgate {
            text-align: right;
        }

        .custo-resgate .custo {
            display: block;
            font-size: 1.2em;
            font-weight: 600;
            color: var(--cor3);
            margin-bottom: 5px;
        }

        .custo-resgate button {
            background-color: var(--cor3);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 20px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s;
        }

        .custo-resgate button:hover:not(:disabled) {
            background-color: var(--cor2);
        }

        .custo-resgate button:disabled {
            background-color: #ccc;
            cursor: not-allowed;
            opacity: 0.7;
        }

        .historico-tabela {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            font-size: 0.95em;
        }

        .historico-tabela th,
        .historico-tabela td {
            border-bottom: 1px solid #ddd;
            padding: 12px 15px;
            text-align: left;
        }

        .historico-tabela th {
            background-color: var(--cor1);
            color: var(--cor2);
            font-weight: 600;
        }

        .historico-tabela tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .tipo-ganho {
            color: #2e7d32;
            font-weight: bold;
        }

        .tipo-resgate {
            color: #c62828;
            font-weight: bold;
        }

        .mensagem {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
        }

        .sucesso {
            color: #3c763d;
            background-color: #dff0d8;
            border-color: #d6e9c6;
        }

        .erro {
            color: #a94442;
            background-color: #f2dede;
            border-color: #ebccd1;
        }
    </style>
</head>
<body>
    <header>
        <div id="logo"><a href="PaginaCliente/pag-Cliente.jsp"><img src="visao/imagens/logo.png" alt="Logo" /></a></div>
        <nav>
            <ul>
                <li><a href="PaginaCliente/pag-Cliente.jsp">Início</a></li>
                <li><a href="<%= request.getContextPath() %>/AgendamentosRestritosServlet">Meus Agendamentos</a></li>
                <li><a href="<%= request.getContextPath() %>/PontosServlet" class="active">Pontos</a></li>
            </ul>
        </nav>
        <div class="auth-buttons"><a href="Logout.do"><button>Sair</button></a></div>
    </header>

    <div class="container-pontos">
        <h1>Programa de Fidelidade</h1>

        <c:if test="${not empty sessionScope.mensagemSucessoPontos}">
            <div class="mensagem sucesso">${sessionScope.mensagemSucessoPontos}</div>
            <c:remove var="mensagemSucessoPontos" scope="session" />
        </c:if>
        <c:if test="${not empty sessionScope.mensagemErroPontos}">
            <div class="mensagem erro">${sessionScope.mensagemErroPontos}</div>
            <c:remove var="mensagemErroPontos" scope="session" />
        </c:if>

        <div class="saldo-card">
            <h2>Seu Saldo Atual</h2>
            <p class="pontos">${cliente.saldoPontos}</p>
        </div>

        <h2 class="secao-titulo">Resgate seus Pontos</h2>
        <div>
            <c:forEach var="servico" items="${servicosDisponiveis}">
                <div class="servico-item">
                    <div class="info">
                        <h4>${servico.nome}</h4>
                        <p>${servico.descricao}</p>
                    </div>
                    <div class="custo-resgate">
                        <span class="custo">${servico.pontosResgate} pontos</span>

                        <c:if test="${cliente.saldoPontos >= servico.pontosResgate}">
                            <form action="ResgatarPontosServlet" method="POST" style="display: inline;">
                                <input type="hidden" name="servicoId" value="${servico.id}" />
                                <button type="submit">Resgatar</button>
                            </form>
                        </c:if>
                        <c:if test="${cliente.saldoPontos < servico.pontosResgate}">
                            <button disabled>Resgatar</button>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>

        <h2 class="secao-titulo" style="margin-top: 40px;">Seu Extrato</h2>
        <table class="historico-tabela">
            <thead>
                <tr>
                    <th>Data</th>
                    <th>Tipo</th>
                    <th>Quantidade</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty historicoPontos}">
                    <tr>
                        <td colspan="3" style="text-align:center;">Você ainda não possui transações.</td>
                    </tr>
                </c:if>
                <c:forEach var="t" items="${historicoPontos}">
                    <tr>
                        <td><fmt:formatDate value="${t.dataTransacao}" pattern="dd/MM/yyyy HH:mm" /></td>
                        <td>
                            <c:if test="${t.tipo == 'ganho'}"><span class="tipo-ganho">Ganho</span></c:if>
                            <c:if test="${t.tipo == 'resgate'}"><span class="tipo-resgate">Resgate</span></c:if>
                        </td>
                        <td>
                            <c:if test="${t.tipo == 'ganho'}">+${t.quantidade}</c:if>
                            <c:if test="${t.tipo == 'resgate'}">-${t.quantidade}</c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>