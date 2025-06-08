<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- A linha abaixo é essencial para que o JSP entenda as tags <c:if> --%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

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

                <label>ID Cliente:</label>
                <input type="number" name="cliente_id" required>

                <label>ID Funcionário:</label>
                <input type="number" name="funcionario_id" required>

                <label>ID Serviço:</label>
                <input type="number" name="servico_id" required>

                <label>Data do Agendamento:</label>
                <input type="date" name="data_agendamento" required>

                <label>Hora do Agendamento:</label>
                <input type="time" name="hora_agendamento" required>

                <label>Status:</label>
                <select name="statu" required>
                    <option value="agendado">Agendado</option>
                    <option value="cancelado">Cancelado</option>
                    <option value="concluido">Concluído</option>
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