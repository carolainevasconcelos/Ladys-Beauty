package br.com.entidade;

import br.com.controle.FrequenciaCliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FrequenciaClienteDAO extends DAO {

    public List<FrequenciaCliente> listarFrequencia() {
        List<FrequenciaCliente> lista = new ArrayList<>();
        String sql = "SELECT c.id AS idCliente, c.nome AS nomeCliente, COUNT(a.id) AS frequencia " +
                     "FROM clientes c LEFT JOIN agendamentos a ON c.id = a.cliente_id " +
                     "GROUP BY c.id, c.nome ORDER BY frequencia DESC";

        try {
            abrirBanco(); // método da superclasse DAO
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
