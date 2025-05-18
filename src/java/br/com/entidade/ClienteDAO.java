/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.Cliente;
import java.sql.PreparedStatement;

/**
 *
 * @author carol
 */
public class ClienteDAO extends DAO {

    public void inserir(Cliente CadCliente) {
        try {
            abrirBanco();
            String query = "INSERT INTO clientes (nome, email, telefone, senha_hash) VALUES (?, ?, ?, ?)";
            pst = (PreparedStatement) con.prepareStatement(query);
            pst.setString(1, CadCliente.getNome());
            pst.setString(2, CadCliente.getEmail());
            pst.setString(3, CadCliente.getTelefone());
            pst.setString(4, CadCliente.getSenha());
            pst.execute();
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
        }
    }

    public boolean emailJaCadastrado(String email) {
        boolean existe = false;
        try {
            abrirBanco();
            String query = "SELECT id FROM clientes WHERE email = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();

            if (rs.next()) {
                existe = true;
            }

            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao verificar email: " + e.getMessage());
        }

        return existe;
    }

    public Cliente buscarPorEmail(String email) {
        Cliente cliente = null;
        try {
            abrirBanco();
            String query = "SELECT * FROM clientes WHERE email = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setSenha(rs.getString("senha_hash"));
            }

            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }
        return cliente;
    }

    public Cliente buscarPorId(int id) {
        Cliente cliente = null;
        try {
            abrirBanco();
            String query = "SELECT * FROM clientes WHERE id = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setSenha(rs.getString("senha_hash"));
            }

            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente por ID: " + e.getMessage());
        }
        return cliente;
    }

    public void editar(Cliente cliente) {
        try {
            abrirBanco();
            String query = "UPDATE clientes SET nome = ?, email = ?, telefone = ? WHERE id = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getEmail());
            pst.setString(3, cliente.getTelefone());
            pst.setInt(4, cliente.getId());
            pst.executeUpdate();
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao editar cliente: " + e.getMessage());
        }
    }

    public void atualizarSenha(int id, String novaSenhaHash) {
        try {
            abrirBanco();
            String query = "UPDATE clientes SET senha_hash = ? WHERE id = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, novaSenhaHash);
            pst.setInt(2, id);
            pst.executeUpdate();
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar senha do cliente: " + e.getMessage());
        }
    }
}
