<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.controle.Servico" %>

<html>
    <head>
        <title>Lista de Serviços</title>
        <link rel="stylesheet" href="visao/css/style.css"/>
        <link rel="stylesheet" href="visao/css/lista.css">
    </head>
    <body>
        <!-- Botão de Voltar -->
        <div class="auth-buttons">
            <a href="PaginaADM/pag-servicos.jsp"><button>Voltar</button></a>
        </div>

        <h2>Serviços Cadastrados</h2>

        <table border="1">
            <thead>
                <tr>
                    <th>Nome</th>
                    <th>Descrição</th>
                    <th>Preço</th>
                    <th>Pontos Ganhos</th>
                    <th>Pontos Resgate</th>
                    <th>Funcionário</th>
                    <th>Categoria</th>
                    <th>Status</th>
                    <th>Ação</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Servico> servicos = (List<Servico>) request.getAttribute("servicos");
                    for (Servico s : servicos) {
                %>
                <tr>
                    <td><%= s.getNome() %></td>
                    <td><%= s.getDescricao() %></td>
                    <td><%= s.getPreco() %></td>
                    <td><%= s.getPontosGanho() %></td>
                    <td><%= s.getPontosResgate() %></td>
                    <td><%= s.getFuncionarioId() %></td>
                    <td><%= s.getCategoria() %></td>
                    <td><%= s.getStatus() %></td>
                    <td>
                        <!-- Link para editar -->
                        <a href="editarServico.jsp?id=<%= s.getId() %>">
                            <img class="icone-acao" src="visao/imagens/editar.png" alt="Editar" title="Editar">
                        </a>
                        <!-- Link para deletar -->
                        <a href="DeletarServico.do?id=<%= s.getId() %>" onclick="return confirm('Tem certeza que deseja excluir este serviço?')">
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