// Arquivo: Lady's Beauty/src/java/br/com/notification/NotificationService.java
package br.com.notification;

import br.com.controle.Agendamento;
import br.com.controle.Cliente;
import br.com.controle.Funcionario;
import br.com.controle.Servico;
import br.com.controle.Notificacao;
import br.com.entidade.NotificacaoDAO;
import br.com.entidade.FuncionarioDAO;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;

public class NotificationService {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private NotificacaoDAO notificacaoDAO = new NotificacaoDAO();

    public void notifyFuncionarioNovoAgendamento(Funcionario funcionario, Cliente cliente, Servico servico, Agendamento agendamento) {
        if (funcionario == null || funcionario.getEmail() == null || cliente == null || servico == null || agendamento == null) {
            System.err.println("Dados insuficientes para criar notificação para funcionário sobre novo agendamento (ID Agendamento: " + (agendamento != null ? agendamento.getId() : "N/A") + ")");
            return;
        }
        String subject = "Novo Agendamento Recebido";
        // Incluindo o ID do agendamento na mensagem para referência, se desejar (opcional se já tiver o campo agendamento_id)
        String messageBody = String.format(
                "Olá %s,\n" +
                        "Um novo agendamento (ID Agendamento: %d) foi realizado e atribuído a você:\n" +
                        "Cliente: %s (Email: %s, Telefone: %s)\n" +
                        "Serviço: %s\n" +
                        "Data: %s\n" +
                        "Hora: %s", // Removida a frase "Por favor, acesse o sistema..."
                funcionario.getNome(),
                agendamento.getId(), // ID do agendamento na mensagem
                cliente.getNome(), cliente.getEmail(), cliente.getTelefone(),
                servico.getNome(),
                dateFormat.format(agendamento.getDataAgendamento()),
                timeFormat.format(agendamento.getHoraAgendamento()));

        Notificacao notificacao = new Notificacao();
        notificacao.setUsuarioId(funcionario.getId());
        notificacao.setTipoUsuario("funcionario");
        notificacao.setAssunto(subject);
        notificacao.setMensagem(messageBody);
        if (agendamento.getId() > 0) { // Garante que o ID do agendamento é válido antes de setar
            notificacao.setAgendamentoId(agendamento.getId());
        }
        notificacao.setDataCriacao(new Timestamp(System.currentTimeMillis()));
        notificacao.setLida(false);
        notificacaoDAO.inserir(notificacao);
        System.out.println("Notificação de novo agendamento (com agendamento_id=" + agendamento.getId() + ") salva para Funcionário ID: " + funcionario.getId());
    }

    public void notifyClienteConfirmacaoAgendamento(Cliente cliente, Servico servico, Agendamento agendamento, Funcionario funcionarioConfirmou) {
        if (cliente == null || cliente.getEmail() == null || servico == null || agendamento == null || funcionarioConfirmou == null) {
            System.err.println("Dados insuficientes para criar notificação para cliente sobre confirmação (ID Agendamento: " + (agendamento != null ? agendamento.getId() : "N/A") + ")");
            return;
        }
        String subject = "Seu Agendamento foi Visto pelo Profissional"; // Assunto ajustado
        String messageBody = String.format(
                "Olá %s,\n" +
                        "Seu agendamento para o serviço '%s', marcado para %s às %s com o profissional %s, foi visto e está tudo certo.\n" +
                        "Esperamos por você!", // Mensagem ajustada
                cliente.getNome(),
                servico.getNome(),
                dateFormat.format(agendamento.getDataAgendamento()),
                timeFormat.format(agendamento.getHoraAgendamento()),
                funcionarioConfirmou.getNome());

        Notificacao notificacao = new Notificacao();
        notificacao.setUsuarioId(cliente.getId());
        notificacao.setTipoUsuario("cliente");
        notificacao.setAssunto(subject);
        notificacao.setMensagem(messageBody);
        if (agendamento.getId() > 0) { // Pode ser útil ter o agendamento_id aqui também
            notificacao.setAgendamentoId(agendamento.getId());
        }
        notificacao.setDataCriacao(new Timestamp(System.currentTimeMillis()));
        notificacao.setLida(false);
        notificacaoDAO.inserir(notificacao);
        System.out.println("Notificação de 'ciência do agendamento' salva para Cliente ID: " + cliente.getId());
    }

    public void notifyClienteEdicaoAgendamento(Cliente cliente, Agendamento agendamentoNovo, Agendamento agendamentoAntigo, Funcionario funcionarioEditor, Servico servicoNovo, Servico servicoAntigo, Funcionario funcNovo, Funcionario funcAntigo) {
        if (cliente == null || cliente.getEmail() == null || agendamentoNovo == null || agendamentoAntigo == null || funcionarioEditor == null || servicoNovo == null || servicoAntigo == null || funcNovo == null || funcAntigo == null) {
            System.err.println("Dados insuficientes para notificação de edição.");
            return;
        }
        String subject = "Seu Agendamento foi Alterado";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Olá %s,\n", cliente.getNome()));
        sb.append(String.format("Seu agendamento (ID: %d) para o serviço '%s' foi alterado pelo funcionário %s.\n\n", agendamentoNovo.getId(), servicoAntigo.getNome(), funcionarioEditor.getNome()));
        sb.append("Detalhes Anteriores:\n");
        sb.append(String.format("- Serviço: %s\n", servicoAntigo.getNome()));
        sb.append(String.format("- Data: %s\n", dateFormat.format(agendamentoAntigo.getDataAgendamento())));
        sb.append(String.format("- Hora: %s\n", timeFormat.format(agendamentoAntigo.getHoraAgendamento())));
        sb.append(String.format("- Profissional: %s\n", funcAntigo.getNome()));
        sb.append(String.format("- Status: %s\n\n", agendamentoAntigo.getStatu()));
        sb.append("Novos Detalhes:\n");
        sb.append(String.format("- Serviço: %s\n", servicoNovo.getNome()));
        sb.append(String.format("- Data: %s\n", dateFormat.format(agendamentoNovo.getDataAgendamento())));
        sb.append(String.format("- Hora: %s\n", timeFormat.format(agendamentoNovo.getHoraAgendamento())));
        sb.append(String.format("- Profissional: %s\n", funcNovo.getNome()));
        sb.append(String.format("- Status: %s\n\n", agendamentoNovo.getStatu()));
        sb.append("Por favor, verifique as alterações. Se tiver dúvidas, entre em contato conosco.");

        Notificacao notificacao = new Notificacao();
        notificacao.setUsuarioId(cliente.getId());
        notificacao.setTipoUsuario("cliente");
        notificacao.setAssunto(subject);
        notificacao.setMensagem(sb.toString());
        if (agendamentoNovo.getId() > 0) {
            notificacao.setAgendamentoId(agendamentoNovo.getId());
        }
        notificacao.setDataCriacao(new Timestamp(System.currentTimeMillis()));
        notificacao.setLida(false);
        notificacaoDAO.inserir(notificacao);
        System.out.println("Notificação de edição de agendamento salva para Cliente ID: " + cliente.getId());
    }

    public void notifyClienteCancelamentoAgendamento(Cliente cliente, Agendamento agendamentoCancelado, Funcionario funcionarioCancelador, Servico servico) {
        if (cliente == null || cliente.getEmail() == null || agendamentoCancelado == null || funcionarioCancelador == null || servico == null) {
            System.err.println("Dados insuficientes para notificação de cancelamento.");
            return;
        }
        
        FuncionarioDAO funcDao = new FuncionarioDAO();
        Funcionario profissionalDoAgendamento = funcDao.buscarPorId(agendamentoCancelado.getFuncionarioId());
        String nomeProfissionalOriginal = (profissionalDoAgendamento != null) ? profissionalDoAgendamento.getNome() : "N/A";

        String subject = "Seu Agendamento foi Cancelado";
        String messageBody = String.format(
                "Olá %s,\n" +
                        "Informamos que seu agendamento para o serviço '%s' (ID: %d), que estava marcado para %s às %s com o profissional %s, foi cancelado pelo funcionário %s.\n" +
                        "Para reagendar ou se tiver dúvidas, por favor, entre em contato.",
                cliente.getNome(),
                servico.getNome(),
                agendamentoCancelado.getId(),
                dateFormat.format(agendamentoCancelado.getDataAgendamento()),
                timeFormat.format(agendamentoCancelado.getHoraAgendamento()),
                nomeProfissionalOriginal,
                funcionarioCancelador.getNome());

        Notificacao notificacao = new Notificacao();
        notificacao.setUsuarioId(cliente.getId());
        notificacao.setTipoUsuario("cliente");
        notificacao.setAssunto(subject);
        notificacao.setMensagem(messageBody);
        if (agendamentoCancelado.getId() > 0) {
            notificacao.setAgendamentoId(agendamentoCancelado.getId());
        }
        notificacao.setDataCriacao(new Timestamp(System.currentTimeMillis()));
        notificacao.setLida(false);
        notificacaoDAO.inserir(notificacao);
        System.out.println("Notificação de cancelamento salva para Cliente ID: " + cliente.getId());
    }
}