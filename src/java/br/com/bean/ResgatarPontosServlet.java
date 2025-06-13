package br.com.bean;

import br.com.controle.Cliente;
import br.com.controle.Servico;
import br.com.entidade.ClienteDAO;
import br.com.entidade.ServicoDAO;
import br.com.entidade.TransacaoPontosDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ResgatarPontosServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Cliente clienteLogado = (session != null) ? (Cliente) session.getAttribute("clienteLogado") : null;

        // validaçao de segurança
        if (clienteLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int servicoId = Integer.parseInt(request.getParameter("servicoId"));

            ClienteDAO clienteDAO = new ClienteDAO();
            ServicoDAO servicoDAO = new ServicoDAO();
            
            Cliente cliente = clienteDAO.buscarPorId(clienteLogado.getId());
            Servico servico = servicoDAO.buscarPorId(servicoId);

            if (servico == null) {
                session.setAttribute("mensagemErroPontos", "Serviço inválido para resgate.");
                response.sendRedirect("PontosServlet");
                return;
            }

            // validaçao de segurança no lado do servidor
            if (cliente.getSaldoPontos() >= servico.getPontosResgate()) {
                int novoSaldo = cliente.getSaldoPontos() - servico.getPontosResgate();
                
                clienteDAO.atualizarSaldoPontos(cliente.getId(), novoSaldo);

                TransacaoPontosDAO transacaoDAO = new TransacaoPontosDAO();
                transacaoDAO.inserir(cliente.getId(), servico.getId(), 0, "resgate", servico.getPontosResgate());

                session.setAttribute("mensagemSucessoPontos", "Resgate realizado com sucesso! Agende seu serviço gratuito quando quiser.");
            } else {
                session.setAttribute("mensagemErroPontos", "Saldo de pontos insuficiente para realizar este resgate.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("mensagemErroPontos", "Ocorreu um erro ao processar o resgate.");
        }
        
        response.sendRedirect("PontosServlet");
    }
}