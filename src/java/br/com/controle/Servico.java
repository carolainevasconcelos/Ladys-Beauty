/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.controle;

/**
 *
 * @author carol
 */
public class Servico {
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private int pontosGanho;
    private int pontosResgate;
    private int funcionarioId;
    private String categoria; 
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getPontosGanho() {
        return pontosGanho;
    }

    public void setPontosGanho(int pontosGanho) {
        this.pontosGanho = pontosGanho;
    }

    public int getPontosResgate() {
        return pontosResgate;
    }

    public void setPontosResgate(int pontosResgate) {
        this.pontosResgate = pontosResgate;
    }

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(int funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}