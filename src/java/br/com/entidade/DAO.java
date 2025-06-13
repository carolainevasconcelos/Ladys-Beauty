/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carol
 */
public class DAO {
    public Connection con; 
    public PreparedStatement pst; 
    protected ResultSet rs; 

    public void abrirBanco() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // carrega o driver JDBC do MySQL 

            String url = "jdbc:mysql://localhost:3306/SistemaAgendamentos?useSSL=false&serverTimezone=UTC";
            // caminho do banco: protocolo, endereco, porta, nome do banco e configs extras

            String user = "root";  
            String senha = "Carolaine22"; 
            
            con = DriverManager.getConnection(url, user, senha);
            // cria a conexao com o banco

            System.out.println("Conectado ao banco de dados SistemaAgendamentos.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver JDBC nao encontrado.");
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            System.out.println("Erro de conexao: " + e.getMessage());
            throw new RuntimeException(e); // lanca o erro pra cima
        }
    }

    public void fecharBanco() {
        try {
            if (pst != null) {
                pst.close(); // fecha o PreparedStatement se estiver aberto
            }
            if (rs != null) {
                rs.close(); // fecha o ResultSet se estiver aberto
            }
            if (con != null) {
                con.close(); // fecha a conexao com o banco
            }
            System.out.println("Conexao com o banco encerrada.");
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexao: " + e.getMessage());
            // erro ao tentar fechar algum recurso
        }
    }
}