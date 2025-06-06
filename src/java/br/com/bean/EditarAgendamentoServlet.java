// Arquivo: Lady's Beauty/src/java/br/com/bean/EditarAgendamentoServlet.java
package br.com.bean;

import br.com.controle.Agendamento;
import br.com.entidade.AgendamentoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Time;

// NOVAS IMPORTAÇÕES PARA NOTIFICAÇÃO - INÍCIO
import br.com.controle.Cliente;
import br.com.controle.Funcionario;
import br.com.controle.Servico;
import br.com.entidade.ClienteDAO;
import br.com.entidade.FuncionarioDAO;
import br.com.entidade.ServicoDAO;
import br.com.notification.NotificationService;
import jakarta.servlet.http.HttpSession; // Para pegar o funcionário logado
// NOVAS IMPORTAÇÕES PARA NOTIFICAÇÃO - FIM

/**
 *
 * @author carol
 */
public class EditarAgendamentoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        AgendamentoDAO dao = new AgendamentoDAO();
        Agendamento agendamento = dao.buscarPorId(id);

        if (agendamento == null) {
            response.sendRedirect("ListarAgendamentos.do");
            return;
        }

        request.setAttribute("agendamento", agendamento);
        request.getRequestDispatcher("editarAgendamento.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Para obter o funcionário logado

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            
            AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
            Agendamento agendamentoAntigo = agendamentoDAO.buscarPorId(id); // ADICIONADO: Captura o agendamento antes da edição

            // Verifica se o agendamento antigo foi encontrado
            if (agendamentoAntigo == null) {
                session.setAttribute("mensagemErro", "Agendamento não encontrado para edição.");
                response.sendRedirect("ListarAgendamentos.do");
                return;
            }

            int clienteId = Integer.parseInt(request.getParameter("clienteId"));
            int funcionarioId = Integer.parseInt(request.getParameter("funcionarioId"));
            int servicoId = Integer.parseInt(request.getParameter("servicoId"));

            String dataStr = request.getParameter("dataAgendamento");
            String horaStr = request.getParameter("horaAgendamento");
            String statu = request.getParameter("statu");
            String pagamentoPontos = request.getParameter("pagamentoPontos");

            if (dataStr == null || horaStr == null || statu == null || pagamentoPontos == null) {
                throw new IllegalArgumentException("Campos obrigatórios ausentes.");
            }

            Date dataAgendamento = Date.valueOf(dataStr);
            Time horaAgendamento = Time.valueOf(horaStr);

            Agendamento agendamentoNovo = new Agendamento(); // Renomeado para agendamentoNovo para clareza
            agendamentoNovo.setId(id);
            agendamentoNovo.setClienteId(clienteId);
            agendamentoNovo.setFuncionarioId(funcionarioId);
            agendamentoNovo.setServicoId(servicoId);
            agendamentoNovo.setDataAgendamento(dataAgendamento);
            agendamentoNovo.setHoraAgendamento(horaAgendamento);
            agendamentoNovo.setStatu(statu);
            agendamentoNovo.setPagamentoPontos(pagamentoPontos);

            agendamentoDAO.editar(agendamentoNovo); // Usa agendamentoDAO para editar

            // --- INÍCIO DO ACRÉSCIMO PARA NOTIFICAÇÃO DE EDIÇÃO (EPIC 20) ---
            NotificationService notificationService = new NotificationService();
            ClienteDAO clienteDAO = new ClienteDAO();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            ServicoDAO servicoDAO = new ServicoDAO();

            // Obter os objetos completos para notificação
            Cliente cliente = clienteDAO.buscarPorId(agendamentoNovo.getClienteId());
            Funcionario funcionarioEditor = (Funcionario) session.getAttribute("funcionarioLogado"); // O funcionário que está logado e editando
            
            // Obter informações do serviço e funcionário antes e depois da edição, caso tenham mudado
            Servico servicoAntigo = servicoDAO.buscarPorId(agendamentoAntigo.getServicoId());
            Servico servicoNovo = servicoDAO.buscarPorId(agendamentoNovo.getServicoId());
            
            Funcionario funcAntigo = funcionarioDAO.buscarPorId(agendamentoAntigo.getFuncionarioId());
            Funcionario funcNovo = funcionarioDAO.buscarPorId(agendamentoNovo.getFuncionarioId());


            if (cliente != null && funcionarioEditor != null && servicoAntigo != null && servicoNovo != null && funcAntigo != null && funcNovo != null) {
                notificationService.notifyClienteEdicaoAgendamento(cliente, agendamentoNovo, agendamentoAntigo, funcionarioEditor, servicoNovo, servicoAntigo, funcNovo, funcAntigo);
                session.setAttribute("mensagemSucesso", "Agendamento editado com sucesso e cliente notificado!");
            } else {
                System.err.println("ERRO: EditarAgendamentoServlet - Dados insuficientes para notificar edição do agendamento. Cliente, Funcionário editor, Serviço (antigo/novo) ou Funcionário (antigo/novo) nulo.");
                session.setAttribute("mensagemSucesso", "Agendamento editado, mas houve um problema ao notificar o cliente (dados não encontrados).");
            }
            // --- FIM DO ACRÉSCIMO PARA NOTIFICAÇÃO DE EDIÇÃO ---

            request.setAttribute("agendamentos", agendamentoDAO.listar()); // Usa agendamentoDAO para listar
            request.getRequestDispatcher("listarAgendamentos.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            session.setAttribute("mensagemErro", "Erro nos dados fornecidos: verifique os números (IDs).");
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao editar agendamento: " + e.getMessage());
        } catch (IllegalArgumentException iae) {
            session.setAttribute("mensagemErro", "Erro nos dados fornecidos: " + iae.getMessage());
            iae.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao editar agendamento: " + iae.getMessage());
        } catch (Exception e) {
            session.setAttribute("mensagemErro", "Ocorreu um erro inesperado ao processar a edição do agendamento.");
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao editar agendamento: " + e.getMessage());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}