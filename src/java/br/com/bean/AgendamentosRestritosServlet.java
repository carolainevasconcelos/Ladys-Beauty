package br.com.bean;

import br.com.controle.Agendamento;
import br.com.entidade.AgendamentoDAO;
import br.com.controle.Cliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AgendamentosRestritosServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Cliente cliente = (Cliente) session.getAttribute("clienteLogado");

        if (cliente != null) {
            AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
            List<Agendamento> agendamentos = agendamentoDAO.buscarPorCliente(cliente.getId());

            if (agendamentos.isEmpty()) {
                request.setAttribute("mensagem", "Você não tem nenhum agendamento.");
            } else {
                request.setAttribute("agendamentos", agendamentos);
            }
        } else {
            request.setAttribute("mensagem", "Você precisa estar logado para ver seus agendamentos.");
        }

        request.getRequestDispatcher("PaginaCliente/listaRestritaAgendamento.jsp").forward(request, response);
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
        return "Servlet para exibir os agendamentos do cliente logado.";
    }
}
