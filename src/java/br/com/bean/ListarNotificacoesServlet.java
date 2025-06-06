// Arquivo: Lady's Beauty/src/java/br/com/bean/ListarNotificacoesServlet.java
package br.com.bean;

import br.com.controle.Cliente;
import br.com.controle.Funcionario;
import br.com.controle.Notificacao;
import br.com.entidade.NotificacaoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ListarNotificacoesServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        NotificacaoDAO notificacaoDAO = new NotificacaoDAO();
        List<Notificacao> notificacoes = Collections.emptyList();
        int usuarioId = 0;
        String tipoUsuario = null;
        String nomeUsuario = null;

        // ADICIONADO: Variável booleana para ser passada para a JSP
        boolean isFuncionarioLogado = false; 

        if (session != null) {
            Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");
            Funcionario funcionarioLogado = (Funcionario) session.getAttribute("funcionarioLogado");

            if (clienteLogado != null) {
                usuarioId = clienteLogado.getId();
                tipoUsuario = "cliente";
                nomeUsuario = clienteLogado.getNome();
            } else if (funcionarioLogado != null) {
                usuarioId = funcionarioLogado.getId();
                tipoUsuario = "funcionario";
                nomeUsuario = funcionarioLogado.getNome();
                isFuncionarioLogado = true; // Define como true se for funcionário
            }

            if (usuarioId > 0 && tipoUsuario != null) {
                notificacoes = notificacaoDAO.listarPorUsuario(usuarioId, tipoUsuario);
                // Marcar como lidas ao serem visualizadas (opcional, pode ser uma ação separada)
                // Para este exemplo, vamos marcar todas como lidas ao carregar a página.
                notificacaoDAO.marcarTodasComoLidas(usuarioId, tipoUsuario);
                request.setAttribute("notificacoes", notificacoes);
                request.setAttribute("nomeUsuarioLogado", nomeUsuario);
            } else {
                // Usuário não logado ou tipo de usuário desconhecido
                response.sendRedirect("login.jsp"); // Redireciona para login
                return;
            }
        } else {
            // Sessão não existe, redireciona para login
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Adiciona a contagem de não lidas para o cabeçalho (exemplo)
        if (usuarioId > 0 && tipoUsuario != null) {
             int naoLidas = notificacaoDAO.contarNaoLidas(usuarioId, tipoUsuario);
             request.setAttribute("totalNaoLidas", naoLidas); // Após marcar todas como lidas, será 0. Se a marcação for por clique, isso mostrará o valor atual.
        }

        // ADICIONADO: Passa a variável para a JSP, para que a JSTL possa lê-la
        request.setAttribute("podeGerenciarNotificacoesEL", isFuncionarioLogado);


        request.getRequestDispatcher("notificacoes.jsp").forward(request, response);
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
        return "Servlet para listar notificações do usuário.";
    }
}