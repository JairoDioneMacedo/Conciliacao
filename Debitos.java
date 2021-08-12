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
public class Debitos {

    private int debCodigo;
    private Date debData;
    private int conCodigo;
    private double debValor;
    private String debHistorico;
    private double conSaldo;
    private Integer creditoID;

    /**
     * @return the debCodigo
     */
    public int getDebCodigo() {
        return debCodigo;
    }

    /**
     * @param debCodigo the debCodigo to set
     */
    public void setDebCodigo(int debCodigo) {
        this.debCodigo = debCodigo;
    }

    /**
     * @return the debData
     */
    public Date getDebData() {
        return debData;
    }

    /**
     * @param debData the debData to set
     */
    public void setDebData(Date debData) {
        this.debData = debData;
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
     * @return the debValor
     */
    public double getDebValor() {
        return debValor;
    }

    /**
     * @param debValor the debValor to set
     */
    public void setDebValor(double debValor) {
        this.debValor = debValor;
    }

    /**
     * @return the debHistorico
     */
    public String getDebHistorico() {
        return debHistorico;
    }

    /**
     * @param debHistorico the debHistorico to set
     */
    public void setDebHistorico(String debHistorico) {
        this.debHistorico = debHistorico;
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

    public Integer getCreditoID() {
        return creditoID;
    }

    public void setCreditoID(Integer creditoID) {
        this.creditoID = creditoID;
    }
}
