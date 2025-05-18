package br.com.entidade;

import br.com.controle.Servico;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO extends DAO {

    public void inserir(Servico servico) {
        try {
            abrirBanco();
            String query = "INSERT INTO servicos (nome, descricao, preco, pontos_ganho, pontos_resgate, funcionario_id, categoria, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(query);
            pst.setString(1, servico.getNome());
            pst.setString(2, servico.getDescricao());
            pst.setDouble(3, servico.getPreco());
            pst.setInt(4, servico.getPontosGanho());
            pst.setInt(5, servico.getPontosResgate());
            pst.setInt(6, servico.getFuncionarioId());
            pst.setString(7, servico.getCategoria());
            pst.setString(8, servico.getStatus());
            pst.execute();
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao inserir serviço: " + e.getMessage());
        }
    }

    public List<Servico> listar() {
        List<Servico> servicos = new ArrayList<>();
        ResultSet rs = null;
        try {
            abrirBanco();
            String sql = "SELECT * FROM servicos";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Servico s = new Servico();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setDescricao(rs.getString("descricao"));
                s.setPreco(rs.getDouble("preco"));
                s.setPontosGanho(rs.getInt("pontos_ganho"));
                s.setPontosResgate(rs.getInt("pontos_resgate"));
                s.setFuncionarioId(rs.getInt("funcionario_id"));
                s.setCategoria(rs.getString("categoria"));
                s.setStatus(rs.getString("status"));
                servicos.add(s);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar serviços: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                fecharBanco();
            } catch (Exception e) {
                System.out.println("Erro ao fechar ResultSet ou conexão: " + e.getMessage());
            }
        }
        return servicos;
    }

    public void editar(Servico servico) {
        try {
            abrirBanco();
            String sql = "UPDATE servicos SET nome = ?, descricao = ?, preco = ?, pontos_ganho = ?, pontos_resgate = ?, funcionario_id = ?, categoria = ?, status = ? WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, servico.getNome());
            pst.setString(2, servico.getDescricao());
            pst.setDouble(3, servico.getPreco());
            pst.setInt(4, servico.getPontosGanho());
            pst.setInt(5, servico.getPontosResgate());
            pst.setInt(6, servico.getFuncionarioId());
            pst.setString(7, servico.getCategoria());
            pst.setString(8, servico.getStatus());
            pst.setInt(9, servico.getId());
            pst.executeUpdate();
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao editar serviço: " + e.getMessage());
        }
    }

    public Servico buscarPorId(int id) {
        Servico s = null;
        ResultSet rs = null;
        try {
            abrirBanco();
            String sql = "SELECT * FROM servicos WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                s = new Servico();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setDescricao(rs.getString("descricao"));
                s.setPreco(rs.getDouble("preco"));
                s.setPontosGanho(rs.getInt("pontos_ganho"));
                s.setPontosResgate(rs.getInt("pontos_resgate"));
                s.setFuncionarioId(rs.getInt("funcionario_id"));
                s.setCategoria(rs.getString("categoria"));
                s.setStatus(rs.getString("status"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar serviço por ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                fecharBanco();
            } catch (Exception e) {
                System.out.println("Erro ao fechar ResultSet ou conexão: " + e.getMessage());
            }
        }
        return s;
    }

    public void deletar(int id) {
        try {
            abrirBanco();
            String sql = "DELETE FROM servicos WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.execute();
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao deletar serviço: " + e.getMessage());
        }
    }
}
