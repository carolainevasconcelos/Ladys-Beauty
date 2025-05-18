package br.com.entidade;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {

    public Connection con; // objeto de conexão com o banco
    public PreparedStatement pst; // prepara comandos SQL com segurança (evita SQL Injection)
    protected ResultSet rs; // armazena o resultado das consultas SQL

    public void abrirBanco() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // carrega o driver JDBC do MySQL 

            String url = "jdbc:mysql://localhost:3306/SistemaAgendamentos?useSSL=false&serverTimezone=UTC";
            // caminho do banco: protocolo, endereço, porta, nome do banco e configs extras

            String user = "root";  
            String senha = "Carolaine22"; 
            
            con = DriverManager.getConnection(url, user, senha);
            // cria a conexão com o banco

            System.out.println("Conectado ao banco de dados SistemaAgendamentos.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver JDBC não encontrado.");
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            System.out.println("Erro de conexão: " + e.getMessage());
            throw new RuntimeException(e); // lança o erro pra cima
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
                con.close(); // fecha a conexão com o banco
            }
            System.out.println("Conexão com o banco encerrada.");
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
            // erro ao tentar fechar algum recurso
        }
    }
}
