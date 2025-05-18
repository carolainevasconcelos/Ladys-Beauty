<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.controle.Funcionario" %>

<html>
    <head>
        <title>Lista de Funcionários</title>
        <link rel="stylesheet" href="visao/css/style.css"/>
        <link rel="stylesheet" href="visao/css/lista.css">
    </head>
    <body>
        <!-- Botão de Voltar -->
        <div class="auth-buttons">
            <a href="PaginaADM/pag-Funcionarios.jsp"><button>Voltar</button></a>
        </div>

        <h2>Funcionários Cadastrados</h2>

        <table border="1">
            <thead>
                <tr>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>Telefone</th>
                    <th>Cargo</th>
                    <th>Especialidade</th>
                    <th>Ação</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("funcionarios");
                    for (Funcionario f : funcionarios) {
                %>
                <tr>
                    <td><%= f.getNome() %></td>
                    <td><%= f.getEmail() %></td>
                    <td><%= f.getTelefone() %></td>
                    <td><%= f.getCargo() %></td>
                    <td><%= f.getEspecialidade() %></td>
                    <td>
                        <a href="editarFuncionario.jsp?id=<%= f.getId() %>">
                            <img class="icone-acao" src="visao/imagens/editar.png" alt="Editar" title="Editar">
                        </a>
                        <a href="DeletarFuncionario.do?id=<%= f.getId() %>" onclick="return confirm('Tem certeza que deseja excluir este funcionário?')">
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
