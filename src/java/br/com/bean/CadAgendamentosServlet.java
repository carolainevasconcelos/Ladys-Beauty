/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package br.com.bean;

import br.com.controle.Agendamento;
import br.com.entidade.AgendamentoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        int clienteId = Integer.parseInt(request.getParameter("cliente_id"));
        int funcionarioId = Integer.parseInt(request.getParameter("funcionario_id"));
        int servicoId = Integer.parseInt(request.getParameter("servico_id"));
        String dataStr = request.getParameter("data_agendamento");
        String horaStr = request.getParameter("hora_agendamento");
        String statu = request.getParameter("statu");
        String pagamentoPontos = request.getParameter("pagamento_pontos");

        java.sql.Date dataAgendamento = java.sql.Date.valueOf(dataStr);
        horaStr = horaStr + ":00"; 
        java.sql.Time horaAgendamento = java.sql.Time.valueOf(horaStr);
        Agendamento agendamento = new Agendamento();
        agendamento.setClienteId(clienteId);
        agendamento.setFuncionarioId(funcionarioId);
        agendamento.setServicoId(servicoId);
        agendamento.setDataAgendamento(dataAgendamento);
        agendamento.setHoraAgendamento(horaAgendamento);
        agendamento.setStatu(statu);
        agendamento.setPagamentoPontos(pagamentoPontos);

        AgendamentoDAO dao = new AgendamentoDAO();
        dao.inserir(agendamento);
        request.getRequestDispatcher("cad-agendamentos.jsp").forward(request, response);

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
