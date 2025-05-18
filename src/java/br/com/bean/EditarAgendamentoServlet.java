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
import java.sql.Date;
import java.sql.Time;

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
        try {
            int id = Integer.parseInt(request.getParameter("id"));
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

            Agendamento agendamento = new Agendamento();
            agendamento.setId(id);
            agendamento.setClienteId(clienteId);
            agendamento.setFuncionarioId(funcionarioId);
            agendamento.setServicoId(servicoId);
            agendamento.setDataAgendamento(dataAgendamento);
            agendamento.setHoraAgendamento(horaAgendamento);
            agendamento.setStatu(statu);
            agendamento.setPagamentoPontos(pagamentoPontos);

            AgendamentoDAO dao = new AgendamentoDAO();
            dao.editar(agendamento);

            request.setAttribute("agendamentos", dao.listar());
            request.getRequestDispatcher("listarAgendamentos.jsp").forward(request, response);
        } catch (Exception e) {
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
