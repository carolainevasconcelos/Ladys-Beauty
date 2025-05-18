<%@page import="br.com.controle.Agendamento"%>
<%@page import="br.com.entidade.AgendamentoDAO"%>
<%@page import="java.util.List"%>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    AgendamentoDAO dao = new AgendamentoDAO();
    Agendamento agendamento = dao.buscarPorId(id);

    if (agendamento == null) {
        out.println("<p>Agendamento não encontrado.</p>");
        return;
    }

    String status = agendamento.getStatu() != null ? agendamento.getStatu() : "";
    String pagamento = agendamento.getPagamentoPontos() != null ? agendamento.getPagamentoPontos() : "";
%>

<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <title>Editar Agendamento</title>
        <link rel="stylesheet" href="visao/css/cadastro.css">
    </head>
    <body>
        <div class="container">
            <h2>Editar Agendamento</h2>
            <form action="EditarAgendamento.do" method="post">
                <input type="hidden" name="id" value="<%= agendamento.getId() %>"/>

                <label>Cliente ID:</label>
                <input type="number" name="clienteId" value="<%= agendamento.getClienteId() %>" required/>

                <label>Funcionário ID:</label>
                <input type="number" name="funcionarioId" value="<%= agendamento.getFuncionarioId() %>" required/>

                <label>Serviço ID:</label>
                <input type="number" name="servicoId" value="<%= agendamento.getServicoId() %>" required/>

                <label>Data Agendamento:</label>
                <input type="date" name="dataAgendamento" value="<%= agendamento.getDataAgendamento() %>" required/>

                <label>Hora Agendamento:</label>
                <input type="time" name="horaAgendamento" value="<%= agendamento.getHoraAgendamento() %>" required/>

                <label>Status:</label>
                <select name="statu" required>
                    <option value="agendado" <%= "agendado".equals(status) ? "selected" : "" %>>Agendado</option>
                    <option value="cancelado" <%= "cancelado".equals(status) ? "selected" : "" %>>Cancelado</option>
                    <option value="concluido" <%= "concluido".equals(status) ? "selected" : "" %>>Concluído</option>
                </select>

                <label>Pagar com Pontos?</label>
                <select name="pagamentoPontos">
                    <option value="sim" <%= "sim".equals(pagamento) ? "selected" : "" %>>Sim</option>
                    <option value="nao" <%= "nao".equals(pagamento) ? "selected" : "" %>>Não</option>
                </select>

                <div class="button-group">
                    <button type="submit">Salvar</button>
                    <a href="ListarAgendamentos.do">Voltar</a>
                </div>
            </form>
        </div>
    </body>
</html>
