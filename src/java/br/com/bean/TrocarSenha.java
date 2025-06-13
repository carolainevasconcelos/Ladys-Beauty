package br.com.bean;

import br.com.controle.Cliente;
import br.com.controle.Funcionario;
import br.com.entidade.ClienteDAO;
import br.com.entidade.FuncionarioDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

public class TrocarSenha extends HttpServlet {

    private String criptografarSenha(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String novaSenha = request.getParameter("novaSenha");

        // criptografa a senha antes de atualizar
        String novaSenhaCriptografada = criptografarSenha(novaSenha);

        ClienteDAO clienteDAO = new ClienteDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        boolean atualizou = false;

        Cliente cliente = clienteDAO.buscarPorEmail(email);
        if (cliente != null) {
            clienteDAO.atualizarSenha(cliente.getId(), novaSenhaCriptografada);
            atualizou = true;
        } else {
            Funcionario funcionario = funcionarioDAO.buscarPorEmail(email);
            if (funcionario != null) {
                funcionarioDAO.atualizarSenha(funcionario.getId(), novaSenhaCriptografada);
                atualizou = true;
            }
        }

        if (atualizou) {
            response.sendRedirect("login.jsp");
        } else {
            response.sendRedirect("trocarSenha.jsp?erro=Email não encontrado");
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
}