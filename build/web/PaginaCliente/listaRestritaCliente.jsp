<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="br.com.controle.Cliente"%>
<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Perfil do Cliente</title>
        <link rel="stylesheet" href="visao/css/perfil.css">
    </head>
    <body>
        <!-- Botão de Voltar -->
        <div class="auth-buttons">
            <a href="PaginaCliente/pag-Cliente.jsp"><button>Voltar</button></a>
        </div>      
        <div class="perfil-container">
            <h2 class="titulo">Perfil</h2>
            <%
                if (cliente != null) {
            %>
            <p><strong>Nome:</strong> <%= cliente.getNome() %></p>
            <p><strong>Email:</strong> <%= cliente.getEmail() %></p>
            <p><strong>Telefone:</strong> <%= cliente.getTelefone() %></p>
            <%
                } else {
            %>
            <p>Cliente não encontrado. Por favor, tente novamente mais tarde.</p>
            <%
                }
            %>
            <a href="PaginaCliente/editarCliente.jsp?id=<%= cliente.getId() %>" class="btn-editar">Editar Dados</a>
        </div>
    </body>
</html>
