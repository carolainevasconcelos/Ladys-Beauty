<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.controle.Notificacao" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="br.com.controle.Funcionario" %>
<%@ page import="br.com.controle.Cliente" %>

<html>
    <head>
        <title>Minhas Notificações - Lady's Beauty</title>
        <link rel="stylesheet" href="visao/css/style.css"/>
        <link rel="stylesheet" href="visao/css/lista.css"/>
        <style>
            /* Estilos para os botões dentro da coluna de Ação */
            form {
                display: inline-block;
                margin-right: 5px;
            }
            button {
                color: white; /* Cor do texto padrão para os botões */
                border: none;
                padding: 8px 12px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
                /* Removido: background-color padrão e hover genérico para .acoes button */
            }

            /* Estilo para o botão Confirmar: rosa claro */
            .confirmar {
                background-color: #fe97b0; /* Rosa claro: #fe97b0 */
            }
            .confirmar:hover {
                background-color: #fd3765; /* Hover para rosa mais forte: #fd3765 */
            }

            /* Estilo para o botão Cancelar: azul escuro */
            .cancelar {
                background-color: #4c4f72; /* Azul escuro: #4c4f72 */
            }
            .cancelar:hover {
                background-color: #fd3765; /* Um tom ligeiramente mais escuro para o hover do azul */
            }
        </style>
    </head>
    <body>
        <%
            Funcionario funcionarioLogado = (Funcionario) session.getAttribute("funcionarioLogado");
            Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");
            String paginaVoltar = "login.jsp";

            if (funcionarioLogado != null) {
                paginaVoltar = "PaginaADM/pag-ADM.jsp";
                if ("funcionario".equalsIgnoreCase(funcionarioLogado.getCargo())) {
                    paginaVoltar = "PaginaPP/pag-PP.jsp";
                }
            } else if (clienteLogado != null) {
                paginaVoltar = "PaginaCliente/pag-Cliente.jsp";
            }
        %>

        <div class="auth-buttons" style="padding: 10px 20px; text-align: left; margin-bottom: 20px;">
            <a href="<%= paginaVoltar %>"><button>Voltar</button></a>
        </div>

        <c:if test="${not empty sessionScope.mensagemSucesso}">
            <div class="mensagem-status mensagem-sucesso">
                <c:out value="${sessionScope.mensagemSucesso}"/>
            </div>
            <c:remove var="mensagemSucesso" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.mensagemErro}">
            <div class="mensagem-status mensagem-erro">
                <c:out value="${sessionScope.mensagemErro}"/>
            </div>
            <c:remove var="mensagemErro" scope="session"/>
        </c:if>

        <h2 class="titulo-pagina">
            Notificações
            <c:if test="${not empty requestScope.nomeUsuarioLogado}">
                para <c:out value="${requestScope.nomeUsuarioLogado}"/>
            </c:if>
        </h2>

        <c:choose>
            <c:when test="${not empty requestScope.notificacoes}">
                <table border="1" class="tabela-notificacoes" style="width: 95%; margin: 20px auto; border-collapse: collapse;">
                    <thead>
                        <tr>
                            <th style="width:15%;">Data</th>
                            <th style="width:20%;">Assunto</th>
                            <th>Mensagem</th>
                            <th style="width:10%;">Status</th>
                            <th style="width:22%;">Ação</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="notificacao" items="${requestScope.notificacoes}">
                            <c:set var="mensagemOriginal" value="${notificacao.mensagem}"/>
                            <c:set var="fraseParaRemover" value="Por favor, acesse o sistema para confirmar ou gerenciar este agendamento."/>
                            <c:set var="mensagemModificada" value="${fn:replace(mensagemOriginal, fraseParaRemover, '')}"/>
                            <c:set var="mensagemModificada" value="${fn:trim(mensagemModificada)}"/>
                            
                            <tr class="${notificacao.lida ? '' : 'notificacao-item-nao-lida'}">
                                <td><fmt:formatDate value="${notificacao.dataCriacao}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                                <td><c:out value="${notificacao.assunto}"/></td>
                                <td class="corpo-notificacao-mensagem"><c:out value="${mensagemModificada}"/></td>
                                <td><c:out value="${notificacao.lida ? 'Lida' : 'Não lida'}"/></td>
                                <td>
                                    <%-- Condição para exibir botões --%>
                                    <c:if test="${requestScope.podeGerenciarNotificacoesEL && notificacao.agendamentoId > 0 && fn:toLowerCase(fn:trim(notificacao.assunto)) eq 'novo agendamento recebido'}">
                                        <form action="ProcessarCienciaAgendamentoServlet" method="POST" style="display:inline-block; margin-right: 5px;">
                                            <input type="hidden" name="agendamentoId" value="${notificacao.agendamentoId}">
                                            <input type="hidden" name="notificacaoId" value="${notificacao.id}">
                                            <button type="submit" class="confirmar">Confirmar</button>
                                        </form>

                                        <form action="CancelarAgendamentoServlet" method="POST" style="display:inline-block;" onsubmit="return confirm('Tem certeza que deseja CANCELAR este agendamento?');">
                                            <input type="hidden" name="agendamentoId" value="${notificacao.agendamentoId}">
                                            <input type="hidden" name="notificacaoId" value="${notificacao.id}">
                                            <button type="submit" class="cancelar">Cancelar</button>
                                        </form>
                                    </c:if>
                                    <%-- Caso contrário --%>
                                    <c:if test="${!(requestScope.podeGerenciarNotificacoesEL && notificacao.agendamentoId > 0 && fn:toLowerCase(fn:trim(notificacao.assunto)) eq 'novo agendamento recebido')}">
                                        <span>-</span>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p style="text-align:center;">Você não tem nenhuma notificação no momento.</p>
            </c:otherwise>
        </c:choose>
    </body>
</html>