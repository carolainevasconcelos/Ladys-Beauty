// Arquivo: Lady's Beauty/src/java/br/com/bean/CancelarAgendamentoServlet.java
package br.com.bean;

import br.com.controle.Agendamento;
import br.com.controle.Cliente;
import br.com.controle.Funcionario;
import br.com.controle.Servico;
import br.com.entidade.AgendamentoDAO;
import br.com.entidade.ClienteDAO;
import br.com.entidade.FuncionarioDAO;
import br.com.entidade.NotificacaoDAO;
import br.com.entidade.ServicoDAO;
import br.com.notification.NotificationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class CancelarAgendamentoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Funcionario funcionarioLogado = (session != null) ? (Funcionario) session.getAttribute("funcionarioLogado") : null;

        // Apenas funcionários logados podem executar esta ação
        if (funcionarioLogado == null) {
            // Se não houver funcionário logado, redireciona para o login ou página de erro de permissão.
            // Usando o caminho absoluto a partir da raiz do contexto para login.jsp
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String agendamentoIdStr = request.getParameter("agendamentoId");
        String notificacaoIdStr = request.getParameter("notificacaoId"); // ID da notificação original do funcionário

        if (agendamentoIdStr != null && !agendamentoIdStr.isEmpty()) {
            try {
                int agendamentoId = Integer.parseInt(agendamentoIdStr);
                AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
                Agendamento agendamento = agendamentoDAO.buscarPorId(agendamentoId);

                if (agendamento != null) {
                    // 1. Mudar o status do agendamento para "cancelado"
                    agendamento.setStatu("cancelado"); // Conforme o ENUM da sua tabela
                    agendamentoDAO.editar(agendamento); // Salva a alteração no banco

                    // 2. Notificar o cliente sobre o cancelamento (EPIC 20)
                    ClienteDAO clienteDAO = new ClienteDAO(); //
                    ServicoDAO servicoDAO = new ServicoDAO(); //
                    
                    Cliente cliente = clienteDAO.buscarPorId(agendamento.getClienteId());
                    Servico servico = servicoDAO.buscarPorId(agendamento.getServicoId());

                    if (cliente != null && servico != null) {
                        NotificationService notificationService = new NotificationService(); //
                        // O funcionário que cancelou é o funcionárioLogado
                        notificationService.notifyClienteCancelamentoAgendamento(cliente, agendamento, funcionarioLogado, servico);
                    } else {
                        System.err.println("CancelarAgendamentoServlet: Não foi possível obter dados do cliente ou serviço para notificar o cancelamento.");
                        if(cliente == null) System.err.println("Cliente ID " + agendamento.getClienteId() + " não encontrado.");
                        if(servico == null) System.err.println("Serviço ID " + agendamento.getServicoId() + " não encontrado.");
                    }

                    // 3. Marcar a notificação original do funcionário como lida (ou processada)
                    if (notificacaoIdStr != null && !notificacaoIdStr.isEmpty()) {
                        try {
                            int notificacaoIdParaMarcar = Integer.parseInt(notificacaoIdStr);
                            NotificacaoDAO notificacaoDAO = new NotificacaoDAO(); //
                            notificacaoDAO.marcarComoLida(notificacaoIdParaMarcar);
                        } catch (NumberFormatException nfe) {
                            System.err.println("CancelarAgendamentoServlet: ID da notificação inválido para marcar como lida - " + notificacaoIdStr);
                        }
                    }
                    
                    session.setAttribute("mensagemSucesso", "Agendamento ID " + agendamentoId + " cancelado com sucesso e cliente notificado.");

                } else {
                    session.setAttribute("mensagemErro", "Agendamento ID " + agendamentoId + " não encontrado para cancelamento.");
                }
            } catch (NumberFormatException e) {
                session.setAttribute("mensagemErro", "ID do agendamento fornecido é inválido.");
                e.printStackTrace();
            } catch (Exception e) {
                session.setAttribute("mensagemErro", "Erro ao processar o cancelamento do agendamento: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            session.setAttribute("mensagemErro", "ID do agendamento não fornecido para cancelamento.");
        }
        // Redireciona de volta para a lista de notificações do funcionário
        response.sendRedirect(request.getContextPath() + "/ListarNotificacoesServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ações de modificação de dados (como cancelar) devem ser feitas via POST por segurança e boas práticas.
        // Se um GET for recebido, pode-se redirecionar para uma página de erro ou para o doPost se apropriado,
        // mas é melhor não permitir GET para esta ação.
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Método GET não é suportado para esta ação. Use POST.");
    }

    @Override
    public String getServletInfo() {
        return "Servlet para cancelar um agendamento a pedido de um funcionário e notificar o cliente.";
    }
}