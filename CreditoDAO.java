/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
import br.com.jairo.modelo.Contas;
import br.com.jairo.modelo.Creditos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jairo
 */
public class CreditoDAO {

    private Connection connection;

    public CreditoDAO() {
        this.connection = new FabricaConexao().getConnection();
    }

    //metodo para incluir credito
    public void novaCredito(Creditos creditos, String conCodigo, Contas contaComSaldo) throws SQLException {

        String sqls = "select * from contas  where concodigo = " + conCodigo + " ";

        PreparedStatement psc = null;
        ResultSet rsc = null;
        try {
            psc = connection.prepareStatement(sqls);
            rsc = psc.executeQuery();
            while (rsc.next()) {
                contaComSaldo.setConCodigo(rsc.getInt("concodigo"));
                contaComSaldo.setConDescricao(rsc.getString("condescricao"));
                contaComSaldo.setTipoCodigo(rsc.getInt("tipocodigo"));
                contaComSaldo.setConSaldo(rsc.getDouble("consaldo"));
            }
        } catch (Exception e) {
            Logger.getLogger(CreditoDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        //if(contaComSaldo!= null){
        //  System.out.println("Objeto conta:"+ contaComSaldo.getConDescricao() );
        //System.out.println("Saldo conta :" + contaComSaldo.getConSaldo() );

        //}
        String sql = "insert into creditos (credata,concodigo,crevalor,crehistorico,consaldo) values (?,?,?,?,?)";
        PreparedStatement ps = null;
        ResultSet rs = null;

        //contas.setConSaldo(contas.getConSaldo() + creditos.getCreValor());
        try {
            ps = connection.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(creditos.getCreData().getTime()));
            ps.setInt(2, creditos.getConCodigo());
            ps.setDouble(3, creditos.getCreValor());
            ps.setString(4, creditos.getCreHistorico());
            ps.setDouble(5, contaComSaldo.getConSaldo());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //connection.close();
            ps.close();
        }
        /*double saldo = 0;
        if(creditos != null){
            
            saldo = Double.parseDouble(conSaldo) + Double.parseDouble(creValor);
            contaComSaldo.setConSaldo(saldo);
            System.out.println("Objeto conta:"+ contaComSaldo.getConSaldo());
        }
        String sqlas = "update contas set condescricao=?,tipocodigo=?,consaldo=? where concodigo=?;
        PreparedStatement psas = null;

        try {
            psas = connection.prepareStatement(sqlas);
            psas.setString(1, contaComSaldo.getConDescricao());
            psas.setInt(2, contaComSaldo.getTipoCodigo());
            psas.setDouble(3, contaComSaldo.getConSaldo() + creditos.getCreValor());
            psas.setInt(4, contaComSaldo.getConCodigo());
            psas.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }*/
    }

    //metodo para zerar conta com saldo negativo
    public void zeraCredito() throws SQLException {

        String sql = "select * from contas where tipocodigo = 1 order by condescricao";

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Contas> listaContas = new ArrayList<Contas>();

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Contas contas = new Contas();
                contas.setConCodigo(rs.getInt("concodigo"));
                contas.setConDescricao(rs.getString("condescricao"));
                contas.setTipoCodigo(rs.getInt("tipocodigo"));
                contas.setConSaldo(rs.getDouble("consaldo"));
                listaContas.add(contas);
                //System.out.println("Conta: " + contas.getConDescricao() +"  ---> Saldo: "+contas.getConSaldo());
                if (contas.getConSaldo() < 0) {
                    //System.out.println("Conta: " + contas.getConDescricao() +"  ---> Saldo: "+contas.getConSaldo());
                    Creditos creditos = new Creditos();
                    creditos.setConCodigo(rs.getInt("concodigo"));
                    creditos.setCreValor(rs.getDouble("consaldo"));
                    creditos.setCreHistorico("Fechamento");
                }
            }
        } catch (SQLException erro) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            //connection.close();
            //ps.close();
            //rs.close();
        }
    }
}
