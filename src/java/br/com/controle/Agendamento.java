/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

import java.util.Date;

/**
 *
 * @author carol
 */
public class Agendamento {

    private int id;
    private int clienteId;
    private int funcionarioId;
    private int servicoId;
    private Date dataAgendamento;
    private java.sql.Time horaAgendamento;
    private String statu;
    private String pagamentoPontos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(int funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public int getServicoId() {
        return servicoId;
    }

    public void setServicoId(int servicoId) {
        this.servicoId = servicoId;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public java.sql.Time getHoraAgendamento() {
        return horaAgendamento;
    }

    public void setHoraAgendamento(java.sql.Time horaAgendamento) {
        this.horaAgendamento = horaAgendamento;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getPagamentoPontos() {
        return pagamentoPontos;
    }

    public void setPagamentoPontos(String pagamentoPontos) {
        this.pagamentoPontos = pagamentoPontos;
    }
}
