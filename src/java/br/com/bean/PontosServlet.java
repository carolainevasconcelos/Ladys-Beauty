package br.com.bean;

import br.com.controle.Cliente;
import br.com.controle.Servico;
import br.com.controle.TransacaoPontos;
import br.com.entidade.ClienteDAO;
import br.com.entidade.ServicoDAO;
import br.com.entidade.TransacaoPontosDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PontosServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Cliente clienteLogado = (session != null) ? (Cliente) session.getAttribute("clienteLogado") : null;

        if (clienteLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = clienteDAO.buscarPorId(clienteLogado.getId());

            ServicoDAO servicoDAO = new ServicoDAO();
            List<Servico> servicosDisponiveis = servicoDAO.listar();

            TransacaoPontosDAO transacaoDAO = new TransacaoPontosDAO();
            List<TransacaoPontos> historico = transacaoDAO.listarPorCliente(cliente.getId());
            
            request.setAttribute("cliente", cliente);
            request.setAttribute("servicosDisponiveis", servicosDisponiveis);
            request.setAttribute("historicoPontos", historico);
            
            request.getRequestDispatcher("PaginaCliente/pontos.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagemErro", "Erro ao carregar a página de pontos: " + e.getMessage());
            request.getRequestDispatcher("PaginaCliente/pontos.jsp").forward(request, response);
        }
    }
}