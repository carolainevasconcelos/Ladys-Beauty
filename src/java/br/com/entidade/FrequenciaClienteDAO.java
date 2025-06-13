/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.FrequenciaCliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carol
 */
public class FrequenciaClienteDAO extends DAO {

    public List<FrequenciaCliente> listarFrequencia() {
        List<FrequenciaCliente> lista = new ArrayList<>();
        String sql = "SELECT c.id AS idCliente, c.nome AS nomeCliente, COUNT(a.id) AS frequencia " +
                     "FROM clientes c LEFT JOIN agendamentos a ON c.id = a.cliente_id " +
                     "GROUP BY c.id, c.nome ORDER BY frequencia DESC";

        try {
            abrirBanco(); // metodo da superclasse DAO
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                FrequenciaCliente fc = new FrequenciaCliente(
                    rs.getInt("idCliente"),
                    rs.getString("nomeCliente"),
                    rs.getInt("frequencia")
                );
                lista.add(fc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fecharBanco();
        }
        return lista;
    }
}