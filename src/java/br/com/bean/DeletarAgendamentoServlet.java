package br.com.bean;

import br.com.entidade.AgendamentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeletarAgendamentoServlet extends HttpServlet {

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
        try {
            int id = Integer.parseInt(request.getParameter("id")); 
            AgendamentoDAO dao = new AgendamentoDAO();
            dao.deletar(id); 

//            response.sendRedirect("ListarAgendamentos.do");

        } catch (Exception e) {
            response.getWriter().println("Erro ao deletar agendamento: " + e.getMessage());
        }
    }
}