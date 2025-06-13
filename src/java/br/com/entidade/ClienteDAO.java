/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.Cliente;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carol
 */
public class ClienteDAO extends DAO {

    // insere novo cliente no banco
    public void inserir(Cliente cadCliente) {
        try {
            abrirBanco();
            String query = "INSERT INTO clientes (nome, email, telefone, senha_hash) VALUES (?, ?, ?, ?)";
            pst = con.prepareStatement(query);
            pst.setString(1, cadCliente.getNome());
            pst.setString(2, cadCliente.getEmail());
            pst.setString(3, cadCliente.getTelefone());
            pst.setString(4, cadCliente.getSenha());
            pst.execute();
        } catch (Exception e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        } finally {
            fecharBanco();
        }
    }

    // verifica se email ja esta cadastrado
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
        } catch (Exception e) {
            System.out.println("Erro ao verificar email: " + e.getMessage());
        } finally {
            fecharBanco();
        }
        return existe;
    }

    // busca cliente pelo email
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
                cliente.setSaldoPontos(rs.getInt("saldo_pontos"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente por email: " + e.getMessage());
        } finally {
            fecharBanco();
        }
        return cliente;
    }

    // busca cliente pelo id
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
                cliente.setSaldoPontos(rs.getInt("saldo_pontos"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente por ID: " + e.getMessage());
        } finally {
            fecharBanco();
        }
        return cliente;
    }

    // edita os dados de um cliente
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
        } catch (Exception e) {
            System.out.println("Erro ao editar cliente: " + e.getMessage());
        } finally {
            fecharBanco();
        }
    }

    // atualiza a senha de um cliente
    public void atualizarSenha(int id, String novaSenhaHash) {
        try {
            abrirBanco();
            String query = "UPDATE clientes SET senha_hash = ? WHERE id = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, novaSenhaHash);
            pst.setInt(2, id);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar senha do cliente: " + e.getMessage());
        } finally {
            fecharBanco();
        }
    }

    // lista todos os clientes cadastrados
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        try {
            abrirBanco();
            String query = "SELECT * FROM clientes";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setSenha(rs.getString("senha_hash"));
                cliente.setSaldoPontos(rs.getInt("saldo_pontos"));
                clientes.add(cliente);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        } finally {
            fecharBanco();
        }
        return clientes;
    }

    // atualiza o saldo de pontos do cliente
    public void atualizarSaldoPontos(int clienteId, int novoSaldo) {
        try {
            abrirBanco();
            String query = "UPDATE clientes SET saldo_pontos = ? WHERE id = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, novoSaldo);
            pst.setInt(2, clienteId);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar saldo de pontos do cliente: " + e.getMessage());
            e.printStackTrace();
        } finally {
            fecharBanco();
        }
    }
}