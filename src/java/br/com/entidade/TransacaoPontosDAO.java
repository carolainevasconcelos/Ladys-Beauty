/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.TransacaoPontos;
import java.sql.SQLException;
import java.sql.Timestamp; 
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carol
 */
public class TransacaoPontosDAO extends DAO {

    public void inserir(int clienteId, int servicoId, int agendamentoId, String tipo, int quantidade) {
        try {
            abrirBanco();
            String query = "INSERT INTO transacoes_pontos (cliente_id, servico_id, agendamento_id, tipo, quantidade, data_transacao) VALUES (?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(query);
            pst.setInt(1, clienteId);
            pst.setInt(2, servicoId);

            if (agendamentoId > 0) {
                pst.setInt(3, agendamentoId);
            } else {
                pst.setNull(3, Types.INTEGER);
            }
            
            pst.setString(4, tipo);
            pst.setInt(5, quantidade);
            pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pst.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Erro ao inserir transação de pontos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            fecharBanco();
        }
    }

    public List<TransacaoPontos> listarPorCliente(int clienteId) {
        List<TransacaoPontos> transacoes = new ArrayList<>();
        try {
            abrirBanco();
            String query = "SELECT * FROM transacoes_pontos WHERE cliente_id = ? ORDER BY data_transacao DESC";
            pst = con.prepareStatement(query);
            pst.setInt(1, clienteId);
            rs = pst.executeQuery();

            while(rs.next()) {
                TransacaoPontos transacao = new TransacaoPontos();
                transacao.setId(rs.getInt("id"));
                transacao.setClienteId(rs.getInt("cliente_id"));
                transacao.setServicoId(rs.getInt("servico_id"));
                transacao.setAgendamentoId(rs.getInt("agendamento_id"));
                transacao.setTipo(rs.getString("tipo"));
                transacao.setQuantidade(rs.getInt("quantidade"));
                transacao.setDataTransacao(rs.getTimestamp("data_transacao"));
                transacoes.add(transacao);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar transações de pontos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            fecharBanco();
        }
        return transacoes;
    }
}