/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.modelo;

/**
 *
 * @author Jairo
 */
public class Contas {

    private int conCodigo;
    private String conDescricao;
    private int tipoCodigo;
    private double conSaldo;

    /**
     * @return the conCodigo
     */
    public int getConCodigo() {
        return conCodigo;
    }

    /**
     * @param conCodigo the conCodigo to set
     */
    public void setConCodigo(int conCodigo) {
        this.conCodigo = conCodigo;
    }

    /**
     * @return the conDescricao
     */
    public String getConDescricao() {
        return conDescricao;
    }

    /**
     * @param conDescricao the conDescricao to set
     */
    public void setConDescricao(String conDescricao) {
        this.conDescricao = conDescricao;
    }

    /**
     * @return the conTipo
     */
    public int getTipoCodigo() {
        return tipoCodigo;
    }

    /**
     * @param conTipo the conTipo to set
     */
    public void setTipoCodigo(int tipoCodigo) {
        this.tipoCodigo = tipoCodigo;
    }

    /**
     * @return the conSaldo
     */
    public double getConSaldo() {
        return conSaldo;
    }

    /**
     * @param conSaldo the conSaldo to set
     */
    public void setConSaldo(double conSaldo) {
        this.conSaldo = conSaldo;
    }
}
