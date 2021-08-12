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
public class Creditos {

    private int creCodigo;
    private Date creData;
    private int conCodigo;
    private double creValor;
    private String creHistorico;
    private double conSaldo;

    /**
     * @return the creCodigo
     */
    public int getCreCodigo() {
        return creCodigo;
    }

    /**
     * @param creCodigo the creCodigo to set
     */
    public void setCreCodigo(int creCodigo) {
        this.creCodigo = creCodigo;
    }

    /**
     * @return the creData
     */
    public Date getCreData() {
        return creData;
    }

    /**
     * @param creData the creData to set
     */
    public void setCreData(Date creData) {
        this.creData = creData;
    }

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
     * @return the creValor
     */
    public double getCreValor() {
        return creValor;
    }

    /**
     * @param creValor the creValor to set
     */
    public void setCreValor(double creValor) {
        this.creValor = creValor;
    }

    /**
     * @return the creHistorico
     */
    public String getCreHistorico() {
        return creHistorico;
    }

    /**
     * @param creHistorico the creHistorico to set
     */
    public void setCreHistorico(String creHistorico) {
        this.creHistorico = creHistorico;
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
