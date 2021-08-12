/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.modelo;

import java.util.Date;

/**
 *
 * @author Jairo
 */
public class ExtratoTransiente {

    private Date data;
    private String historico;
    private double valor;
    private double saldo;
    private int tipo;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    /*
    private int conCodigo;
    private Date dataCredito;
    private String historicoCredito;
    private double valorCredito;
    private double saldoConta;
    private int conCodigo1;
    private Date dataDebito;
    private String historicoDebito;
    private double valorDebito;
    private double saldoConta1;

     *
     */
}
