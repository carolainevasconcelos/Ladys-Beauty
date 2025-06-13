/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.Funcionario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carol
 */
public class FuncionarioDAO extends DAO {

    public void inserir(Funcionario funcionario) {
        try {
            abrirBanco();
            String query = "INSERT INTO funcionarios (nome, email, telefone, cargo, especialidade, senha_hash) VALUES (?, ?, ?, ?, ?, ?)";
            pst = (PreparedStatement) con.prepareStatement(query);
            pst.setString(1, funcionario.getNome());
            pst.setString(2, funcionario.getEmail());
            pst.setString(3, funcionario.getTelefone());
            pst.setString(4, funcionario.getCargo());
            pst.setString(5, funcionario.getEspecialidade());
            pst.setString(6, funcionario.getSenha());

            pst.execute();
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao inserir funcionário: " + e.getMessage());
        }
    }
    // verificar email
    public boolean emailJaCadastrado(String email) {
        boolean existe = false;
        try {
            abrirBanco();
            String query = "SELECT id FROM funcionarios WHERE email = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();

            if (rs.next()) {
                existe = true;
            }

            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao verificar email do funcionário: " + e.getMessage());
        }

        return existe;
    }
    // buscar email
    public Funcionario buscarPorEmail(String email) {
        Funcionario f = null;
        try {
            abrirBanco();
            String sql = "SELECT * FROM funcionarios WHERE email = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setEmail(rs.getString("email"));
                f.setTelefone(rs.getString("telefone"));
                f.setCargo(rs.getString("cargo"));
                f.setEspecialidade(rs.getString("especialidade"));
                f.setSenha(rs.getString("senha_hash"));
            }
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao buscar funcionário: " + e.getMessage());
        }
        return f;
    }

    public List<Funcionario> listar() {
        List<Funcionario> funcionarios = new ArrayList<>();
        try {
            abrirBanco();
            String sql = "SELECT * FROM funcionarios";
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setEmail(rs.getString("email"));
                f.setTelefone(rs.getString("telefone"));
                f.setCargo(rs.getString("cargo"));
                f.setEspecialidade(rs.getString("especialidade"));
                f.setSenha(rs.getString("senha_hash"));
                funcionarios.add(f);
            }
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }
        return funcionarios;
    }

    public void editar(Funcionario funcionario) {
        try {
            abrirBanco();
            String sql = "UPDATE funcionarios SET nome = ?, email = ?, telefone = ?, cargo = ?, especialidade = ? WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, funcionario.getNome());
            pst.setString(2, funcionario.getEmail());
            pst.setString(3, funcionario.getTelefone());
            pst.setString(4, funcionario.getCargo());
            pst.setString(5, funcionario.getEspecialidade());
            pst.setInt(6, funcionario.getId());
            pst.executeUpdate();
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao editar funcionário: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        try {
            abrirBanco();
            String sql = "DELETE FROM funcionarios WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao deletar funcionário: " + e.getMessage());
        }
    }

    public Funcionario buscarPorId(int id) {
        Funcionario funcionario = null;
        try {
            abrirBanco();
            String query = "SELECT * FROM funcionarios WHERE id = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setEspecialidade(rs.getString("especialidade"));
            }

            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao buscar funcionário por ID: " + e.getMessage());
        }
        return funcionario;
    }

    public void atualizarSenha(int id, String novaSenhaHash) {
        try {
            abrirBanco();
            String sql = "UPDATE funcionarios SET senha_hash = ? WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, novaSenhaHash);
            pst.setInt(2, id);
            pst.executeUpdate();
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar senha do funcionário: " + e.getMessage());
        }
    }
}
