<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lady's Beauty - Meus Agendamentos</title>
        <link rel="stylesheet" href="visao/css/style.css"/>
        <link rel="stylesheet" href="visao/css/lista.css">
    </head>
    <body>
        <!-- Botão de Voltar -->
        <div class="auth-buttons">
            <a href="PaginaCliente/pag-Cliente.jsp"><button>Voltar</button></a>
        </div>
        <h2>Meus Agendamentos</h2>
        
        <c:if test="${not empty mensagem}">
            <p class="mensagem">${mensagem}</p>
        </c:if>

        <c:if test="${not empty agendamentos}">
            <table>
                <thead>
                    <tr>
                        <th>Data</th>
                        <th>Hora</th>
                        <th>Serviço</th>
                        <th>Status</th>
                        <th>Pagamento com Pontos</th>
                        <th>Ação</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="agendamento" items="${agendamentos}">
                        <tr>
                            <td>${agendamento.dataAgendamento}</td>
                            <td>${agendamento.horaAgendamento}</td>
                            <td>${agendamento.servicoId}</td>
                            <td>${agendamento.statu}</td>
                            <td>${agendamento.pagamentoPontos}</td>
                            <td>
                                <!-- Ação de Editar -->
                                <a href="editarAgendamento.jsp?id=${agendamento.id}">
                                    <img class="icone-acao" src="visao/imagens/editar.png" alt="Editar" title="Editar">
                                </a>
                                <!-- Ação de Deletar -->
                                <a href="DeletarAgendamento.do?id=${agendamento.id}" onclick="return confirm('Tem certeza que deseja excluir este agendamento?')">
                                    <img class="icone-acao" src="visao/imagens/excluir.png" alt="Excluir" title="Excluir">
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>
</html>