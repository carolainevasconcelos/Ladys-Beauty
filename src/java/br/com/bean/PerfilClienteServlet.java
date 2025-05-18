package br.com.bean;

import br.com.controle.Cliente;
import br.com.entidade.ClienteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

public class PerfilClienteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");

        if (clienteLogado != null) {
            ClienteDAO dao = new ClienteDAO();
            Cliente clienteAtualizado = dao.buscarPorId(clienteLogado.getId());

            if (clienteAtualizado != null) {
                request.setAttribute("cliente", clienteAtualizado);
                request.getRequestDispatcher("PaginaCliente/listaRestritaCliente.jsp").forward(request, response);
            } else {
                request.setAttribute("erro", "Cliente não encontrado.");
                request.getRequestDispatcher("erro.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
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

    @Override
    public String getServletInfo() {
        return "Servlet que gerencia o perfil do cliente";
    }
}
