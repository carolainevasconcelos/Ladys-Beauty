<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="br.com.controle.Funcionario"%>
<%
    Funcionario funcionario = (Funcionario) request.getAttribute("funcionario");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Perfil do Funcionário</title>
        <link rel="stylesheet" href="visao/css/perfil.css">
    </head>
    <body>
        <!-- Botão de Voltar -->
        <div class="auth-buttons">
            <a href="PaginaPP/pag-PP.jsp"><button>Voltar</button></a>
        </div>      
        <div class="perfil-container">
            <h2 class="titulo">Perfil</h2>
            <%
                if (funcionario != null) {
            %>
            <p><strong>Nome:</strong> <%= funcionario.getNome() %></p>
            <p><strong>Email:</strong> <%= funcionario.getEmail() %></p>
            <p><strong>Telefone:</strong> <%= funcionario.getTelefone() %></p>
            <p><strong>Cargo:</strong> <%= funcionario.getCargo() %></p>
            <p><strong>Especialidade:</strong> <%= funcionario.getEspecialidade() %></p>
            <%
                } else {
            %>
            <p>Funcionário não encontrado. Por favor, tente novamente mais tarde.</p>
            <%
                }
            %>
            <a href="editarFuncionario.jsp?id=<%= funcionario.getId() %>" class="btn-editar">Editar Dados</a>
        </div>
    </body>
</html>
