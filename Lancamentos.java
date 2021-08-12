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
public class Lancamentos {

    private int lanCod;
    private Date lanData;
    private int lanConCred;
    private double lanValCred;
    private String lanHistCred;
    private double lanSalAntCred;
    private int lanConDeb;
    private double lanValDeb;
    private String lanHistDeb;
    private double lanSalAntDeb;

    /**
     * @return the lanCod
     */
    public int getLanCod() {
        return lanCod;
    }

    /**
     * @param lanCod the lanCod to set
     */
    public void setLanCod(int lanCod) {
        this.lanCod = lanCod;
    }

    /**
     * @return the lanData
     */
    public Date getLanData() {
        return lanData;
    }

    /**
     * @param lanData the lanData to set
     */
    public void setLanData(Date lanData) {
        this.lanData = lanData;
    }

    /**
     * @return the lanConCred
     */
    public int getLanConCred() {
        return lanConCred;
    }

    /**
     * @param lanConCred the lanConCred to set
     */
    public void setLanConCred(int lanConCred) {
        this.lanConCred = lanConCred;
    }

    /**
     * @return the lanValCred
     */
    public double getLanValCred() {
        return lanValCred;
    }

    /**
     * @param lanValCred the lanValCred to set
     */
    public void setLanValCred(double lanValCred) {
        this.lanValCred = lanValCred;
    }

    /**
     * @return the lanHistCred
     */
    public String getLanHistCred() {
        return lanHistCred;
    }

    /**
     * @param lanHistCred the lanHistCred to set
     */
    public void setLanHistCred(String lanHistCred) {
        this.lanHistCred = lanHistCred;
    }

    /**
     * @return the lanSalAntCred
     */
    public double getLanSalAntCred() {
        return lanSalAntCred;
    }

    /**
     * @param lanSalAntCred the lanSalAntCred to set
     */
    public void setLanSalAntCred(double lanSalAntCred) {
        this.lanSalAntCred = lanSalAntCred;
    }

    /**
     * @return the lanConDeb
     */
    public int getLanConDeb() {
        return lanConDeb;
    }

    /**
     * @param lanConDeb the lanConDeb to set
     */
    public void setLanConDeb(int lanConDeb) {
        this.lanConDeb = lanConDeb;
    }

    /**
     * @return the lanValDeb
     */
    public double getLanValDeb() {
        return lanValDeb;
    }

    /**
     * @param lanValDeb the lanValDeb to set
     */
    public void setLanValDeb(double lanValDeb) {
        this.lanValDeb = lanValDeb;
    }

    /**
     * @return the lanHistDeb
     */
    public String getLanHistDeb() {
        return lanHistDeb;
    }

    /**
     * @param lanHistDeb the lanHistDeb to set
     */
    public void setLanHistDeb(String lanHistDeb) {
        this.lanHistDeb = lanHistDeb;
    }

    /**
     * @return the lanSalAntDeb
     */
    public double getLanSalAntDeb() {
        return lanSalAntDeb;
    }

    /**
     * @param lanSalAntDeb the lanSalAntDeb to set
     */
    public void setLanSalAntDeb(double lanSalAntDeb) {
        this.lanSalAntDeb = lanSalAntDeb;
    }
}
