<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@page import="java.util.List, br.com.controle.Cliente, br.com.controle.Funcionario, br.com.controle.Servico" %>
<%
    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
    List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("funcionarios");
    List<Servico> servicos = (List<Servico>) request.getAttribute("servicos");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agendar Serviço</title>
    <link rel="stylesheet" href="visao/css/cadastro.css">
</head>
<body>
    <div class="container">
        <form action="CadAgendamentos.do" method="post">
            <h2>Agendar Serviço</h2>

            <c:if test="${not empty mensagemErro}">
                <div style="padding: 15px; margin-bottom: 20px; border: 1px solid transparent; border-radius: 4px; color: #a94442; background-color: #f2dede; border-color: #ebccd1;">
                    <strong>Erro:</strong> ${mensagemErro}
                </div>
            </c:if>
            
            <c:if test="${not empty mensagemSucesso}">
                 <div style="padding: 15px; margin-bottom: 20px; border: 1px solid transparent; border-radius: 4px; color: #3c763d; background-color: #dff0d8; border-color: #d6e9c6;">
                    <strong>Sucesso:</strong> ${mensagemSucesso}
                </div>
            </c:if>

            <label>Cliente:</label>
            <select name="cliente_id" required>
                <option value="" disabled selected>Selecione um cliente...</option>
                <% if (clientes != null) { for (Cliente c : clientes) { %>
                    <option value="<%= c.getId() %>"><%= c.getNome() %></option>
                <% }} %>
            </select>

            <label>Funcionário:</label>
            <select name="funcionario_id" required>
                <option value="" disabled selected>Selecione um funcionário...</option>
                <% if (funcionarios != null) { for (Funcionario f : funcionarios) { %>
                    <option value="<%= f.getId() %>"><%= f.getNome() %> - (<%= f.getEspecialidade() %>)</option>
                <% }} %>
            </select>

            <label>Serviço:</label>
            <select name="servico_id" required>
                <option value="" disabled selected>Selecione um serviço...</option>
                <% if (servicos != null) { for (Servico s : servicos) { %>
                    <option value="<%= s.getId() %>"><%= s.getNome() %> - R$ <%= String.format("%.2f", s.getPreco()) %></option>
                <% }} %>
            </select>

            <label>Data do Agendamento:</label>
            <input type="date" name="data_agendamento" required>

            <label>Hora do Agendamento:</label>
            <input type="time" name="hora_agendamento" required>

            <label>Status:</label>
            <select name="statu" required>
                <option value="agendado">Agendado</option>
                <option value="concluido">Concluído</option>
                <option value="cancelado">Cancelado</option>
            </select>

            <label>Pagar com pontos?</label>
            <select name="pagamento_pontos" required>
                <option value="nao">Não</option>
                <option value="sim">Sim</option>
            </select><br>

            <div class="button-group">
                <button type="submit">Agendar</button>
                <a href="PaginaADM/pag-Agendamentos.jsp">Voltar</a>
            </div>
        </form>
    </div>
</body>
</html>