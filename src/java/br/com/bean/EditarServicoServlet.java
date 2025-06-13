package br.com.bean;

import br.com.controle.Servico;
import br.com.entidade.ServicoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EditarServicoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        double preco = Double.parseDouble(request.getParameter("preco"));
        int pontosGanho = Integer.parseInt(request.getParameter("pontosGanho"));
        int pontosResgate = Integer.parseInt(request.getParameter("pontosResgate"));
        int funcionarioId = Integer.parseInt(request.getParameter("funcionarioId"));
        String categoria = request.getParameter("categoria");
        String status = request.getParameter("status");

        Servico servico = new Servico();
        servico.setId(id);
        servico.setNome(nome);
        servico.setDescricao(descricao);
        servico.setPreco(preco);
        servico.setPontosGanho(pontosGanho);
        servico.setPontosResgate(pontosResgate);
        servico.setFuncionarioId(funcionarioId);
        servico.setCategoria(categoria);
        servico.setStatus(status);

        ServicoDAO dao = new ServicoDAO();
        dao.editar(servico);

        request.setAttribute("servicos", dao.listar());
        request.getRequestDispatcher("listarServicos.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para editar um serviço";
    }// </editor-fold>
}
