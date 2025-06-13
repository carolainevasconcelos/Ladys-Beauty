package br.com.bean;

import br.com.controle.Agendamento;
import br.com.controle.Cliente;
import br.com.controle.Funcionario;
import br.com.controle.Servico;
import br.com.entidade.AgendamentoDAO;
import br.com.entidade.ClienteDAO;
import br.com.entidade.FuncionarioDAO;
import br.com.entidade.ServicoDAO;
import br.com.notification.NotificationService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CadAgendamentosServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Este método agora contém o código de teste para depuração.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("--- DEBUG: Iniciando o carregamento do formulário de agendamento ---");
        
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            ServicoDAO servicoDAO = new ServicoDAO();
            
            System.out.println("--- DEBUG: Buscando as listas no banco de dados...");
            
            List<Cliente> clientes = clienteDAO.listar();
            List<Funcionario> funcionarios = funcionarioDAO.listar();
            List<Servico> servicos = servicoDAO.listar();

            System.out.println("--- DEBUG: Total de Clientes encontrados: " + (clientes != null ? clientes.size() : "lista nula"));
            System.out.println("--- DEBUG: Total de Funcionários encontrados: " + (funcionarios != null ? funcionarios.size() : "lista nula"));
            System.out.println("--- DEBUG: Total de Serviços encontrados: " + (servicos != null ? servicos.size() : "lista nula"));
            
            request.setAttribute("clientes", clientes);
            request.setAttribute("funcionarios", funcionarios);
            request.setAttribute("servicos", servicos);

            System.out.println("--- DEBUG: Encaminhando os dados para a página cad-agendamentos.jsp ---");
            request.getRequestDispatcher("cad-agendamentos.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.out.println("--- DEBUG: OCORREU UM ERRO GRAVE! ---");
            e.printStackTrace(); 
            
            request.setAttribute("mensagemErro", "Erro Crítico ao carregar dados: " + e.getMessage());
            request.getRequestDispatcher("cad-agendamentos.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int clienteId = Integer.parseInt(request.getParameter("cliente_id"));
            int funcionarioId = Integer.parseInt(request.getParameter("funcionario_id"));
            int servicoId = Integer.parseInt(request.getParameter("servico_id"));
            String dataStr = request.getParameter("data_agendamento");
            String horaStr = request.getParameter("hora_agendamento");
            String statu = request.getParameter("statu");
            String pagamentoPontos = request.getParameter("pagamento_pontos");

            java.sql.Date dataAgendamento = java.sql.Date.valueOf(dataStr);
            if (horaStr != null && horaStr.matches("\\d{2}:\\d{2}")) {
                horaStr = horaStr + ":00";
            }
            java.sql.Time horaAgendamento = java.sql.Time.valueOf(horaStr);

            LocalDateTime agora = LocalDateTime.now();
            LocalDateTime horarioAgendamento = LocalDateTime.of(dataAgendamento.toLocalDate(), LocalTime.parse(horaStr));

            if (horarioAgendamento.isBefore(agora)) {
                request.setAttribute("mensagemErro", "Não é possível agendar em um horário que já passou.");
                doGet(request, response);
                return;
            }

            AgendamentoDAO dao = new AgendamentoDAO();
            if (dao.isHorarioOcupado(funcionarioId, dataAgendamento, horaAgendamento)) {
                request.setAttribute("mensagemErro", "Este profissional já possui um agendamento neste horário. Por favor, escolha outro.");
                doGet(request, response);
                return;
            }

            Agendamento agendamento = new Agendamento();
            agendamento.setClienteId(clienteId);
            agendamento.setFuncionarioId(funcionarioId);
            agendamento.setServicoId(servicoId);
            agendamento.setDataAgendamento(dataAgendamento);
            agendamento.setHoraAgendamento(horaAgendamento);
            agendamento.setStatu(statu);
            agendamento.setPagamentoPontos(pagamentoPontos);

            dao.inserir(agendamento);

            NotificationService notificationService = new NotificationService();
            ClienteDAO clienteDAO = new ClienteDAO();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            ServicoDAO servicoDAO = new ServicoDAO();
            Cliente cliente = clienteDAO.buscarPorId(clienteId);
            Funcionario funcionario = funcionarioDAO.buscarPorId(funcionarioId);
            Servico servico = servicoDAO.buscarPorId(servicoId);

            if (cliente != null && funcionario != null && servico != null) {
                notificationService.notifyFuncionarioNovoAgendamento(funcionario, cliente, servico, agendamento);
                request.setAttribute("mensagemSucesso", "Agendamento cadastrado com sucesso e profissional notificado!");
            } else {
                request.setAttribute("mensagemSucesso", "Agendamento cadastrado, mas houve um problema ao notificar o profissional.");
            }
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagemErro", "Ocorreu um erro inesperado: " + e.getMessage());
            doGet(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Servlet para cadastrar agendamentos e preparar o formulário.";
    }
}