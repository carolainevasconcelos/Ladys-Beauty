package br.com.bean;

import br.com.controle.Agendamento;
import br.com.controle.Cliente;
import br.com.controle.Servico;
import br.com.entidade.AgendamentoDAO;
import br.com.entidade.ClienteDAO;
import br.com.entidade.ServicoDAO;
import br.com.entidade.TransacaoPontosDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Time;

public class EditarAgendamentoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String agendamentoIdStr = request.getParameter("id");

        if (agendamentoIdStr == null || agendamentoIdStr.isEmpty()) {
            session.setAttribute("mensagemErro", "ID do agendamento não fornecido.");
            response.sendRedirect("ListarAgendamentos.do");
            return;
        }

        try {
            int agendamentoId = Integer.parseInt(agendamentoIdStr);
            AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
            
            Agendamento agendamentoParaEditar = agendamentoDAO.buscarPorId(agendamentoId);

            if (agendamentoParaEditar == null) {
                session.setAttribute("mensagemErro", "Agendamento com ID " + agendamentoId + " não encontrado.");
                response.sendRedirect("ListarAgendamentos.do");
                return;
            }

            String statusAntigo = agendamentoParaEditar.getStatu();
            
            String novoStatus = request.getParameter("statu");
            if (novoStatus != null && !novoStatus.isEmpty()) {
                agendamentoParaEditar.setStatu(novoStatus);
            }
            
            agendamentoDAO.editar(agendamentoParaEditar);
            
            // logica dos pontos
            boolean pagouComPontos = "sim".equals(agendamentoParaEditar.getPagamentoPontos());
            boolean mudouParaConcluido = "concluido".equals(novoStatus) && !"concluido".equals(statusAntigo);

            if (mudouParaConcluido && !pagouComPontos) {
                ClienteDAO clienteDAO = new ClienteDAO();
                ServicoDAO servicoDAO = new ServicoDAO();
                
                Cliente cliente = clienteDAO.buscarPorId(agendamentoParaEditar.getClienteId());
                Servico servico = servicoDAO.buscarPorId(agendamentoParaEditar.getServicoId());

                if (cliente != null && servico != null && servico.getPontosGanho() > 0) {
                    int pontosGanhos = servico.getPontosGanho();
                    int novoSaldo = cliente.getSaldoPontos() + pontosGanhos;
                    clienteDAO.atualizarSaldoPontos(cliente.getId(), novoSaldo);
                    
                    TransacaoPontosDAO transacaoDAO = new TransacaoPontosDAO();
                    transacaoDAO.inserir(cliente.getId(), servico.getId(), agendamentoId, "ganho", pontosGanhos);
                    
                    session.setAttribute("mensagemSucesso", "Agendamento ID " + agendamentoId + " concluído e " + pontosGanhos + " pontos adicionados!");
                }
            } else {
                 session.setAttribute("mensagemSucesso", "Status do Agendamento ID " + agendamentoId + " atualizado para '" + novoStatus + "'!");
            }
            
            response.sendRedirect("ListarAgendamentos.do");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("mensagemErro", "Ocorreu um erro inesperado ao editar o agendamento: " + e.getMessage());
            response.sendRedirect("ListarAgendamentos.do");
        }
    }
}