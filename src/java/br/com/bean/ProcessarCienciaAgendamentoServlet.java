// Arquivo: Lady's Beauty/src/java/br/com/bean/ProcessarCienciaAgendamentoServlet.java
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

public class ProcessarCienciaAgendamentoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Funcionario funcionarioLogado = (session != null) ? (Funcionario) session.getAttribute("funcionarioLogado") : null;

        if (funcionarioLogado == null) {
            response.sendRedirect("../login.jsp"); // Use o caminho correto para seu login.jsp a partir da raiz
            return;
        }

        String agendamentoIdStr = request.getParameter("agendamentoId");
        String notificacaoIdStr = request.getParameter("notificacaoId");

        if (agendamentoIdStr != null && !agendamentoIdStr.isEmpty() && notificacaoIdStr != null && !notificacaoIdStr.isEmpty()) {
            try {
                int agendamentoId = Integer.parseInt(agendamentoIdStr);
                int notificacaoIdDaAcao = Integer.parseInt(notificacaoIdStr);

                AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
                Agendamento agendamento = agendamentoDAO.buscarPorId(agendamentoId);

                if (agendamento != null) {
                    // O status do agendamento NÃO MUDA.
                    // Apenas disparamos a notificação para o cliente.

                    ClienteDAO clienteDAO = new ClienteDAO();
                    ServicoDAO servicoDAO = new ServicoDAO();
                    
                    Cliente cliente = clienteDAO.buscarPorId(agendamento.getClienteId());
                    Servico servico = servicoDAO.buscarPorId(agendamento.getServicoId());
                    // O funcionário que está dando ciência é o funcionárioLogado

                    if (cliente != null && servico != null) {
                        NotificationService notificationService = new NotificationService();
                        // Para notifyClienteConfirmacaoAgendamento, o último parâmetro é o funcionário que "confirmou" (deu ciência)
                        notificationService.notifyClienteConfirmacaoAgendamento(cliente, servico, agendamento, funcionarioLogado);
                        
                        NotificacaoDAO notificacaoDAO = new NotificacaoDAO();
                        notificacaoDAO.marcarComoLida(notificacaoIdDaAcao); // Marca a notificação original do funcionário como lida
                        
                        session.setAttribute("mensagemSucesso", "Cliente notificado da ciência do agendamento (ID: " + agendamentoId + ")!");
                    } else {
                         session.setAttribute("mensagemErro", "Não foi possível obter dados do cliente/serviço para notificar.");
                    }
                } else {
                    session.setAttribute("mensagemErro", "Agendamento ID " + agendamentoId + " não encontrado.");
                }
            } catch (NumberFormatException e) {
                session.setAttribute("mensagemErro", "ID de agendamento ou notificação inválido.");
                e.printStackTrace();
            } catch (Exception e) {
                session.setAttribute("mensagemErro", "Erro ao processar a ciência do agendamento: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            session.setAttribute("mensagemErro", "IDs necessários (agendamentoId, notificacaoId) não fornecidos.");
        }
        // Redireciona de volta para a lista de notificações do funcionário
        response.sendRedirect("../ListarNotificacoesServlet");
    }
}