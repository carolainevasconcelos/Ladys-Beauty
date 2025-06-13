/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.Agendamento;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

/**
 *
 * @author carol
 */
public class AgendamentoDAO extends DAO {

    // insere novo agendamento no banco
    public void inserir(Agendamento agendamento) {
        try {
            abrirBanco();
            String query = "INSERT INTO agendamentos (cliente_id, funcionario_id, servico_id, data_agendamento, hora_agendamento, statu, pagamento_pontos) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, agendamento.getClienteId());
            pst.setInt(2, agendamento.getFuncionarioId());
            pst.setInt(3, agendamento.getServicoId());
            pst.setDate(4, new java.sql.Date(agendamento.getDataAgendamento().getTime()));
            pst.setTime(5, new java.sql.Time(agendamento.getHoraAgendamento().getTime()));
            pst.setString(6, agendamento.getStatu());
            pst.setString(7, agendamento.getPagamentoPontos());
            pst.execute();

            // recupera o id gerado automaticamente
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                agendamento.setId(generatedKeys.getInt(1));
            }
            generatedKeys.close();

            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao inserir agendamento: " + e.getMessage());
        }
    }

    // retorna todos os agendamentos cadastrados
    public List<Agendamento> listar() {
        List<Agendamento> lista = new ArrayList<>();

        try {
            abrirBanco();
            String sql = "SELECT * FROM agendamentos";
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Agendamento ag = new Agendamento();
                ag.setId(rs.getInt("id"));
                ag.setClienteId(rs.getInt("cliente_id"));
                ag.setFuncionarioId(rs.getInt("funcionario_id"));
                ag.setServicoId(rs.getInt("servico_id"));
                ag.setDataAgendamento(rs.getDate("data_agendamento"));
                ag.setHoraAgendamento(rs.getTime("hora_agendamento"));
                ag.setStatu(rs.getString("statu"));
                ag.setPagamentoPontos(rs.getString("pagamento_pontos"));
                lista.add(ag);
            }
            fecharBanco();

        } catch (Exception e) {
            System.out.println("Erro ao listar agendamentos: " + e.getMessage());
        }

        return lista;
    }

    // busca agendamento pelo id
    public Agendamento buscarPorId(int id) {
        Agendamento agendamento = null;
        try {
            abrirBanco();
            String sql = "SELECT * FROM agendamentos WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                agendamento = new Agendamento();
                agendamento.setId(rs.getInt("id"));
                agendamento.setClienteId(rs.getInt("cliente_id"));
                agendamento.setFuncionarioId(rs.getInt("funcionario_id"));
                agendamento.setServicoId(rs.getInt("servico_id"));
                agendamento.setDataAgendamento(rs.getDate("data_agendamento"));
                agendamento.setHoraAgendamento(rs.getTime("hora_agendamento"));
                agendamento.setStatu(rs.getString("statu"));
                agendamento.setPagamentoPontos(rs.getString("pagamento_pontos"));
            }
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao buscar agendamento por ID: " + e.getMessage());
        }
        return agendamento;
    }

    // atualiza um agendamento existente
    public void editar(Agendamento agendamento) {
        try {
            abrirBanco();
            String sql = "UPDATE agendamentos SET cliente_id = ?, funcionario_id = ?, servico_id = ?, data_agendamento = ?, hora_agendamento = ?, statu = ?, pagamento_pontos = ? WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, agendamento.getClienteId());
            pst.setInt(2, agendamento.getFuncionarioId());
            pst.setInt(3, agendamento.getServicoId());
            pst.setDate(4, new java.sql.Date(agendamento.getDataAgendamento().getTime()));
            pst.setTime(5, new java.sql.Time(agendamento.getHoraAgendamento().getTime()));
            pst.setString(6, agendamento.getStatu());
            pst.setString(7, agendamento.getPagamentoPontos());
            pst.setInt(8, agendamento.getId()); // atualiza pelo id
            pst.executeUpdate();
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao editar agendamento: " + e.getMessage());
        }
    }

    // remove um agendamento pelo id
    public void deletar(int id) {
        try {
            abrirBanco();
            String sql = "DELETE FROM agendamentos WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao deletar agendamento: " + e.getMessage());
        }
    }

    // retorna lista de agendamentos de um cliente especifico
    public List<Agendamento> buscarPorCliente(int clienteId) {
        List<Agendamento> lista = new ArrayList<>();

        try {
            abrirBanco();
            String sql = "SELECT * FROM agendamentos WHERE cliente_id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, clienteId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Agendamento ag = new Agendamento();
                ag.setId(rs.getInt("id"));
                ag.setClienteId(rs.getInt("cliente_id"));
                ag.setFuncionarioId(rs.getInt("funcionario_id"));
                ag.setServicoId(rs.getInt("servico_id"));
                ag.setDataAgendamento(rs.getDate("data_agendamento"));
                ag.setHoraAgendamento(rs.getTime("hora_agendamento"));
                ag.setStatu(rs.getString("statu"));
                ag.setPagamentoPontos(rs.getString("pagamento_pontos"));
                lista.add(ag);
            }
            fecharBanco();

        } catch (Exception e) {
            System.out.println("Erro ao buscar agendamentos por cliente: " + e.getMessage());
        }

        return lista;
    }

    // verifica se o horário já está ocupado para o funcionario
    public boolean isHorarioOcupado(int funcionarioId, java.sql.Date data, java.sql.Time hora) {
        boolean ocupado = false;
        try {
            abrirBanco();
            String query = "SELECT COUNT(*) FROM agendamentos WHERE funcionario_id = ? AND data_agendamento = ? AND hora_agendamento = ? AND statu != 'cancelado'";
            pst = con.prepareStatement(query);
            pst.setInt(1, funcionarioId);
            pst.setDate(2, data);
            pst.setTime(3, hora);
            rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    ocupado = true;
                }
            }
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao verificar horário ocupado: " + e.getMessage());
        }
        return ocupado;
    }
}