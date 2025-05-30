/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

/**
 *
 * @author carol
 */
public class FrequenciaCliente {
    private int idCliente;
    private String nomeCliente;
    private int frequencia;

    public FrequenciaCliente() {}

    public FrequenciaCliente(int idCliente, String nomeCliente, int frequencia) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.frequencia = frequencia;
    }

    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getFrequencia() {
        return frequencia;
    }
    public void setFrequencia(int frequencia) {
        this.frequencia = frequencia;
    }
}
