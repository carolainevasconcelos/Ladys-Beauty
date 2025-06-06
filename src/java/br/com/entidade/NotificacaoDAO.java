// Arquivo: Lady's Beauty/src/java/br/com/entidade/NotificacaoDAO.java
package br.com.entidade;

import br.com.controle.Notificacao;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types; // Importar para pst.setNull
import java.util.ArrayList;
import java.util.List;

public class NotificacaoDAO extends DAO {

    public void inserir(Notificacao notificacao) {
        try {
            abrirBanco();
            // Query com agendamento_id (7 colunas, 7 parâmetros)
            String query = "INSERT INTO notificacoes (usuario_id, tipo_usuario, assunto, mensagem, lida, data_criacao, agendamento_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, notificacao.getUsuarioId());
            pst.setString(2, notificacao.getTipoUsuario());
            pst.setString(3, notificacao.getAssunto());
            pst.setString(4, notificacao.getMensagem());
            pst.setBoolean(5, notificacao.isLida());
            pst.setTimestamp(6, notificacao.getDataCriacao() != null ? notificacao.getDataCriacao() : new Timestamp(System.currentTimeMillis()));
            
            if (notificacao.getAgendamentoId() > 0) { // Verifica se é um ID válido
                pst.setInt(7, notificacao.getAgendamentoId());
            } else {
                pst.setNull(7, Types.INTEGER); // Permite nulo se não houver agendamento_id
            }
            
            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                notificacao.setId(rs.getInt(1));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro SQL ao inserir notificação: " + e.getMessage());
            e.printStackTrace();
        } finally {
            fecharBanco();
        }
    }

    public List<Notificacao> listarPorUsuario(int usuarioId, String tipoUsuario) {
        List<Notificacao> notificacoes = new ArrayList<>();
        try {
            abrirBanco();
            // Query com agendamento_id
            String query = "SELECT id, usuario_id, tipo_usuario, assunto, mensagem, data_criacao, lida, agendamento_id FROM notificacoes WHERE usuario_id = ? AND tipo_usuario = ? ORDER BY data_criacao DESC";
            pst = con.prepareStatement(query);
            pst.setInt(1, usuarioId);
            pst.setString(2, tipoUsuario);
            rs = pst.executeQuery();

            while (rs.next()) {
                Notificacao notificacao = new Notificacao();
                notificacao.setId(rs.getInt("id"));
                notificacao.setUsuarioId(rs.getInt("usuario_id"));
                notificacao.setTipoUsuario(rs.getString("tipo_usuario"));
                notificacao.setAssunto(rs.getString("assunto"));
                notificacao.setMensagem(rs.getString("mensagem"));
                notificacao.setDataCriacao(rs.getTimestamp("data_criacao"));
                notificacao.setLida(rs.getBoolean("lida"));
                notificacao.setAgendamentoId(rs.getInt("agendamento_id")); // Carregar o agendamento_id
                notificacoes.add(notificacao);
            }
        } catch (SQLException e) {
            System.out.println("Erro SQL ao listar notificações por usuário: " + e.getMessage());
            e.printStackTrace();
        } finally {
            fecharBanco();
        }
        return notificacoes;
    }

    public void marcarComoLida(int notificacaoId) {
        try {
            abrirBanco();
            String query = "UPDATE notificacoes SET lida = TRUE WHERE id = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, notificacaoId);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro SQL ao marcar notificação como lida: " + e.getMessage());
            e.printStackTrace();
        } finally {
            fecharBanco();
        }
    }
    
    public void marcarTodasComoLidas(int usuarioId, String tipoUsuario) {
        try {
            abrirBanco();
            String query = "UPDATE notificacoes SET lida = TRUE WHERE usuario_id = ? AND tipo_usuario = ? AND lida = FALSE";
            pst = con.prepareStatement(query);
            pst.setInt(1, usuarioId);
            pst.setString(2, tipoUsuario);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro SQL ao marcar todas as notificações como lidas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            fecharBanco();
        }
    }

    public int contarNaoLidas(int usuarioId, String tipoUsuario) {
        int count = 0;
        try {
            abrirBanco();
            String query = "SELECT COUNT(*) AS total FROM notificacoes WHERE usuario_id = ? AND tipo_usuario = ? AND lida = FALSE";
            pst = con.prepareStatement(query);
            pst.setInt(1, usuarioId);
            pst.setString(2, tipoUsuario);
            rs = pst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Erro SQL ao contar notificações não lidas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            fecharBanco();
        }
        return count;
    }
}