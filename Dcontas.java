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
public class Dcontas {

    private int conCodigo;
    private String dConDescricao;
    private int tipoCodigo;
    private double dConSaldo;

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
     * @return the dConDescricao
     */
    public String getdConDescricao() {
        return dConDescricao;
    }

    /**
     * @param dConDescricao the dConDescricao to set
     */
    public void setdConDescricao(String dConDescricao) {
        this.dConDescricao = dConDescricao;
    }

    /**
     * @return the tipoCodigo
     */
    public int getTipoCodigo() {
        return tipoCodigo;
    }

    /**
     * @param tipoCodigo the tipoCodigo to set
     */
    public void setTipoCodigo(int tipoCodigo) {
        this.tipoCodigo = tipoCodigo;
    }

    /**
     * @return the dConSaldo
     */
    public double getdConSaldo() {
        return dConSaldo;
    }

    /**
     * @param dConSaldo the dConSaldo to set
     */
    public void setdConSaldo(double dConSaldo) {
        this.dConSaldo = dConSaldo;
    }
}
