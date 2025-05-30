package br.com.bean;

import br.com.entidade.ServicoDAO;
import br.com.controle.Servico;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BuscarServicoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query").toLowerCase();

        ServicoDAO dao = new ServicoDAO();
        List<Servico> todosServicos = dao.listar(); // método já existente no seu DAO
        List<Servico> resultados = todosServicos.stream()
            .filter(s -> s.getNome().toLowerCase().contains(query))
            .collect(Collectors.toList());

        request.setAttribute("servicos", resultados);
        request.setAttribute("buscaRealizada", true);
        request.setAttribute("termoBuscado", query);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
