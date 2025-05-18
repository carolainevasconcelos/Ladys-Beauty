<%@page import="br.com.controle.Funcionario"%>
<%@page import="br.com.entidade.FuncionarioDAO"%>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    FuncionarioDAO dao = new FuncionarioDAO();
    Funcionario f = dao.buscarPorId(id);
    for (Funcionario func : dao.listar()) {
        if (func.getId() == id) {
            f = func;
            break;
        }
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Funcionário</title>
        <link rel="stylesheet" href="visao/css/cadastro.css">
    </head>
    <body>
        <div class="container">
            <h2>Editar Funcionário</h2>
            <form action="EditarFuncionario.do" method="post">
                <input type="hidden" name="id" value="<%= f.getId() %>"/>

                <label>Nome:</label> 
                <input type="text" name="nome" value="<%= f.getNome() %>" required/>

                <label>Email:</label> 
                <input type="email" name="email" value="<%= f.getEmail() %>" required/>

                <label>Telefone:</label> 
                <input type="text" name="telefone" value="<%= f.getTelefone() %>" required/>

                <label>Cargo:</label> 
                <select name="cargo" required>
                    <option value="funcionario" <%= f.getCargo().equals("funcionario") ? "selected" : "" %>>Funcionário</option>
                    <option value="administrador" <%= f.getCargo().equals("administrador") ? "selected" : "" %>>Administrador</option>
                </select>

                <label>Especialidade:</label> 
                <input type="text" name="especialidade" value="<%= f.getEspecialidade() %>" required/>

                <div class="button-group">
                    <button type="submit">Salvar</button>
                    <a href="ListarFuncionario.do">Voltar</a>
                </div>
            </form>
        </div>
    </body>
</html>
