package br.com.bean;

import br.com.controle.Funcionario;
import br.com.entidade.FuncionarioDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PerfilFuncionarioServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        Funcionario funcionarioLogado = (Funcionario) session.getAttribute("funcionarioLogado");
        
        if (funcionarioLogado != null) {
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            Funcionario funcionario = funcionarioDAO.buscarPorId(funcionarioLogado.getId());
            
            request.setAttribute("funcionario", funcionario);
            request.getRequestDispatcher("PaginaPP/listaRestritaPP.jsp").forward(request, response);
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
        return "Servlet para listar os dados do funcionário logado";
    }
}
