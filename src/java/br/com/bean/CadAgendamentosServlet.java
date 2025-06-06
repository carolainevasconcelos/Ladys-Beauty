// Arquivo: Lady's Beauty/src/java/br/com/bean/CadAgendamentosServlet.java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package br.com.bean;

import br.com.controle.Agendamento;
import br.com.entidade.AgendamentoDAO;

// NOVAS IMPORTAÇÕES PARA NOTIFICAÇÃO - INÍCIO
import br.com.controle.Cliente;
import br.com.controle.Funcionario;
import br.com.controle.Servico;
import br.com.entidade.ClienteDAO;
import br.com.entidade.FuncionarioDAO;
import br.com.entidade.ServicoDAO;
import br.com.notification.NotificationService;
// NOVAS IMPORTAÇÕES PARA NOTIFICAÇÃO - FIM

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat; // Mantido, pois estava no seu original
import java.util.Date;             // Mantido, pois estava no seu original

/**
 *
 * @author carol
 */
public class CadAgendamentosServlet extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            // Este método não parece estar sendo usado para a lógica principal no seu código original,
            // a lógica está no doPost. Vou manter como está.
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
        processRequest(request, response);
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
        // Bloco try-catch adicionado para melhor tratamento de erros
        try {
            int clienteId = Integer.parseInt(request.getParameter("cliente_id"));
            int funcionarioId = Integer.parseInt(request.getParameter("funcionario_id"));
            int servicoId = Integer.parseInt(request.getParameter("servico_id"));
            String dataStr = request.getParameter("data_agendamento");
            String horaStr = request.getParameter("hora_agendamento");
            String statu = request.getParameter("statu"); // Mantido conforme seu original
            String pagamentoPontos = request.getParameter("pagamento_pontos");

            java.sql.Date dataAgendamento = java.sql.Date.valueOf(dataStr);
            // Ajuste para o formato de hora HH:MM vindo do formulário, adicionando segundos se necessário.
            if (horaStr != null && horaStr.matches("\\d{2}:\\d{2}")) {
                 horaStr = horaStr + ":00";
            }
            java.sql.Time horaAgendamento = java.sql.Time.valueOf(horaStr);
            
            Agendamento agendamento = new Agendamento();
            agendamento.setClienteId(clienteId);
            agendamento.setFuncionarioId(funcionarioId);
            agendamento.setServicoId(servicoId);
            agendamento.setDataAgendamento(dataAgendamento);
            agendamento.setHoraAgendamento(horaAgendamento);
            agendamento.setStatu(statu); // Mantido conforme seu original. Considere setar um padrão se for um novo agendamento.
            agendamento.setPagamentoPontos(pagamentoPontos);

            AgendamentoDAO dao = new AgendamentoDAO();
            dao.inserir(agendamento);

            // --- INÍCIO DO ACRÉSCIMO PARA NOTIFICAÇÃO ---
            NotificationService notificationService = new NotificationService();
            ClienteDAO clienteDAO = new ClienteDAO();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            ServicoDAO servicoDAO = new ServicoDAO();

            Cliente cliente = clienteDAO.buscarPorId(clienteId);
            Funcionario funcionario = funcionarioDAO.buscarPorId(funcionarioId);
            Servico servico = servicoDAO.buscarPorId(servicoId);

            // O objeto 'agendamento' já tem os dados necessários (data, hora, etc.)
            // Se o ID do agendamento fosse necessário para o corpo da notificação, 
            // o AgendamentoDAO.inserir precisaria retornar o ID ou o objeto Agendamento com ID.
            // Para a notificação atual (EPIC 18), os dados já disponíveis são suficientes.

            if (cliente != null && funcionario != null && servico != null) {
                System.out.println("DEBUG: CadAgendamentosServlet - Chamando notificationService.notifyFuncionarioNovoAgendamento para func_id: " + funcionario.getId());
                notificationService.notifyFuncionarioNovoAgendamento(funcionario, cliente, servico, agendamento);
                request.setAttribute("mensagemSucesso", "Agendamento cadastrado com sucesso e profissional notificado!");
            } else {
                System.err.println("ERRO: CadAgendamentosServlet - Não foi possível buscar todos os dados para enviar a notificação (cliente, funcionário ou serviço nulo).");
                if (cliente == null) System.err.println("Cliente ID " + clienteId + " não encontrado.");
                if (funcionario == null) System.err.println("Funcionário ID " + funcionarioId + " não encontrado.");
                if (servico == null) System.err.println("Serviço ID " + servicoId + " não encontrado.");
                request.setAttribute("mensagemSucesso", "Agendamento cadastrado, mas houve um problema ao notificar o profissional (dados não encontrados)."); // Mensagem informativa
            }
            // --- FIM DO ACRÉSCIMO PARA NOTIFICAÇÃO ---
            
            request.getRequestDispatcher("cad-agendamentos.jsp").forward(request, response);

        } catch (NumberFormatException nfe) {
            System.err.println("ERRO no CadAgendamentosServlet: Parâmetro numérico inválido - " + nfe.getMessage());
            nfe.printStackTrace();
            request.setAttribute("mensagemErro", "Erro nos dados fornecidos: verifique os números (IDs).");
            request.getRequestDispatcher("cad-agendamentos.jsp").forward(request, response);
        } catch (IllegalArgumentException iae) {
            System.err.println("ERRO no CadAgendamentosServlet: Argumento inválido (data/hora) - " + iae.getMessage());
            iae.printStackTrace();
            request.setAttribute("mensagemErro", "Erro nos dados fornecidos: verifique o formato da data e hora.");
            request.getRequestDispatcher("cad-agendamentos.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("ERRO SEVERO no CadAgendamentosServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("mensagemErro", "Ocorreu um erro inesperado ao processar o agendamento.");
            request.getRequestDispatcher("cad-agendamentos.jsp").forward(request, response);
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