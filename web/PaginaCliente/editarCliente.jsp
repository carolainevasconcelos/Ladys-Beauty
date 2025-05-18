<%@page import="br.com.controle.Cliente"%>
<%@page import="br.com.entidade.ClienteDAO"%>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    ClienteDAO dao = new ClienteDAO();
    Cliente c = dao.buscarPorId(id);
%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Cliente</title>
        <link rel="stylesheet" href="../visao/css/cadastro.css">
    </head>
    <body>
        <div class="container">
            <h2>Editar Cliente</h2>
            <form action="../EditarClienteServlet" method="post">
                <input type="hidden" name="id" value="<%= c.getId() %>"/>

                <label>Nome:</label> 
                <input type="text" name="nome" value="<%= c.getNome() %>" required/>

                <label>Email:</label> 
                <input type="email" name="email" value="<%= c.getEmail() %>" required/>

                <label>Telefone:</label> 
                <input type="text" name="telefone" value="<%= c.getTelefone() %>" required/>

                <div class="button-group">
                    <button type="submit">Salvar</button>
                    <a href="../PerfilClienteServlet">Voltar</a>
                </div>
            </form>
        </div>
    </body>
</html>