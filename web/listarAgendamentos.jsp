<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.controle.Agendamento" %>

<html>
    <head>
        <title>Lista de Agendamentos</title>
        <link rel="stylesheet" href="visao/css/style.css"/>
        <link rel="stylesheet" href="visao/css/lista.css">
    </head>
    <body>
        <!-- Botão de Voltar -->
        <div class="auth-buttons">
            <a href="PaginaADM/pag-Agendamentos.jsp"><button>Voltar</button></a>
        </div>

        <h2>Agendamentos Cadastrados</h2>

        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Cliente ID</th>
                    <th>Funcionário ID</th>
                    <th>Serviço ID</th>
                    <th>Data</th>
                    <th>Hora</th>
                    <th>Status</th>
                    <th>Forma de Pagamento</th>
                    <th>Ação</th>
                </tr>
            </thead>
            <tbody>
                <%
                List<Agendamento> agendamentos = (List<Agendamento>) request.getAttribute("agendamentos");
                    for (Agendamento a : agendamentos) {
                %>
                <tr>
                    <td><%= a.getId() %></td>
                    <td><%= a.getClienteId() %></td>
                    <td><%= a.getFuncionarioId() %></td>
                    <td><%= a.getServicoId() %></td>
                    <td><%= a.getDataAgendamento() %></td>
                    <td><%= a.getHoraAgendamento() %></td>
                    <td><%= a.getStatu() %></td>
                    <td><%= a.getPagamentoPontos() %></td>
                    <td>
                        <a href="editarAgendamento.jsp?id=<%= a.getId() %>">
                            <img class="icone-acao" src="visao/imagens/editar.png" alt="Editar" title="Editar">
                        </a>
                        <a href="DeletarAgendamento.do?id=<%= a.getId() %>" onclick="return confirm('Tem certeza que deseja excluir este agendamento?')">
                            <img class="icone-acao" src="visao/imagens/excluir.png" alt="Excluir" title="Excluir">
                        </a>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
