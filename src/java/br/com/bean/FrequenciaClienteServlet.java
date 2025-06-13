package br.com.bean;

import br.com.controle.FrequenciaCliente;
import br.com.entidade.FrequenciaClienteDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class FrequenciaClienteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        FrequenciaClienteDAO dao = new FrequenciaClienteDAO(); 
        List<FrequenciaCliente> frequenciaLista = dao.listarFrequencia();

        request.setAttribute("frequenciaLista", frequenciaLista);
        RequestDispatcher rd = request.getRequestDispatcher("frequencia-cliente.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
