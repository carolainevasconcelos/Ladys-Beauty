<%@page import="br.com.controle.Servico"%>
<%@page import="br.com.entidade.ServicoDAO"%>
<%@page import="java.util.List"%>

<%
    int id = 0;
    Servico servico = null;

    try {
        id = Integer.parseInt(request.getParameter("id"));
        ServicoDAO dao = new ServicoDAO();
        List<Servico> servicos = dao.listar();

        for (Servico s : servicos) {
            if (s.getId() == id) {
                servico = s;
                break;
            }
        }

        if (servico == null) {
            out.println("<p>Serviço não encontrado.</p>");
            return;
        }
    } catch (Exception e) {
        out.println("<p>Erro ao buscar serviço: " + e.getMessage() + "</p>");
        return;
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Editar Serviço</title>
    <link rel="stylesheet" href="visao/css/cadastro.css">
</head>
<body>
<div class="container">
    <h2>Editar Serviço</h2>
    <form action="EditarServico.do" method="post">
        <input type="hidden" name="id" value="<%= servico.getId() %>"/>

        <label>Nome:</label>
        <input type="text" name="nome" value="<%= servico.getNome() %>" required/>

        <label>Descrição:</label>
        <input type="text" name="descricao" value="<%= servico.getDescricao() %>" required/>

        <label>Preço:</label>
        <input type="number" step="0.01" name="preco" value="<%= servico.getPreco() %>" required/>

        <label>Pontos Ganho:</label>
        <input type="number" name="pontosGanho" value="<%= servico.getPontosGanho() %>" required/>

        <label>Pontos Resgate:</label>
        <input type="number" name="pontosResgate" value="<%= servico.getPontosResgate() %>" required/>

        <label>Funcionário ID:</label>
        <input type="number" name="funcionarioId" value="<%= servico.getFuncionarioId() %>" required/>

        <label>Categoria:</label>
        <input type="text" name="categoria" value="<%= servico.getCategoria() %>" required/>

        <label>Status:</label>
        <select name="status">
            <option value="ativo" <%= servico.getStatus().equals("ativo") ? "selected" : "" %>>Ativo</option>
            <option value="inativo" <%= servico.getStatus().equals("inativo") ? "selected" : "" %>>Inativo</option>
        </select>

        <div class="button-group">
            <button type="submit">Salvar</button>
            <a href="ListarServico.do">Voltar</a>
        </div>
    </form>
</div>
</body>
</html>
