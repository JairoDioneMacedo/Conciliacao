/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
import br.com.jairo.modelo.Contas;
import br.com.jairo.modelo.Creditos;
import br.com.jairo.modelo.Debitos;
import br.com.jairo.modelo.Movimentos;
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
public class MovimentoDAO {

    private Connection connection;

    public MovimentoDAO() {
        this.connection = new FabricaConexao().getConnection();
    }

    //metodo para incluir credito no movimento
    public void novoMovimentoCredito(Movimentos movimentos, Creditos creditos, Contas contas, Contas contaComSaldo) throws SQLException {
        String sql = "insert into movimentos (mdata,mcredito,mvcredito,mhiscredito,msalantcredito) values (?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(creditos.getCreData().getTime()));
            ps.setInt(2, creditos.getConCodigo());
            ps.setDouble(3, creditos.getCreValor());
            ps.setString(4, creditos.getCreHistorico());
            ps.setDouble(5, contaComSaldo.getConSaldo());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MovimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
    }

    //metodo para incluir debito no movimento
    public void novoMovimentoDebito(Movimentos movimentos, Debitos debitos, Contas contas, Contas contaComSaldo) throws SQLException {
        String sql = "insert into movimentos (mdatad,mdebito,mvdebito,mhisdebito,msalantdebito) values (?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(debitos.getDebData().getTime()));
            ps.setInt(2, debitos.getConCodigo());
            ps.setDouble(3, debitos.getDebValor() * -1);
            ps.setString(4, debitos.getDebHistorico());
            ps.setDouble(5, contaComSaldo.getConSaldo());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(MovimentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
    }

    //metodo que retorna os movimentos cadastrados no BD
    public List getMovimentos() throws SQLException {
        String sql = "select * from movimentos";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movimentos> listaMovimentos = new ArrayList<Movimentos>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Movimentos movimentos = new Movimentos();
                movimentos.setmCodigo(rs.getInt("mcodigo"));
                movimentos.setmData(rs.getDate("mdata"));
                movimentos.setmCredito(rs.getInt("mcredito"));
                movimentos.setmVcredito(rs.getDouble("mvcredito"));
                movimentos.setmHisCredito(rs.getString("mhiscredito"));
                movimentos.setmSalAntCredito(rs.getDouble("msalantcredito"));
                movimentos.setmDatad(rs.getDate("mdatad"));
                movimentos.setmDebito(rs.getInt("mdebito"));
                movimentos.setmVdebito(rs.getDouble("mvdebito"));
                movimentos.setmHisDebito(rs.getString("mhisdebito"));
                movimentos.setmSalAntDebito(rs.getDouble("msalantdebito"));
                listaMovimentos.add(movimentos);
            }
            return listaMovimentos;
        } catch (SQLException erro) {
            Logger.getLogger(MovimentoDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            connection.close();
            ps.close();
            rs.close();
        }
        return null;
    }

    //metodo que retorna os movimentos entre data cadastrados no BD
    public List getExtratoMovimentos() throws SQLException {
        String sql = "select mdata,mcredito,mvcredito,mhiscredito,msalantcredito,mdatad,mdebito,mvdebito,mhisdebito,msalantdebito from movimentos where mcredito=? and mdebito=? mdata=? and mdatad=? ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movimentos> listaMovimentos = new ArrayList<Movimentos>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Movimentos movimentos = new Movimentos();
                movimentos.setmCodigo(rs.getInt("mcodigo"));
                movimentos.setmData(rs.getDate("mdata"));
                movimentos.setmCredito(rs.getInt("mcredito"));
                movimentos.setmVcredito(rs.getDouble("mvcredito"));
                movimentos.setmHisCredito(rs.getString("mhiscredito"));
                movimentos.setmSalAntCredito(rs.getDouble("msalantcredito"));
                movimentos.setmDatad(rs.getDate("mdatad"));
                movimentos.setmDebito(rs.getInt("mdebito"));
                movimentos.setmVdebito(rs.getDouble("mvdebito"));
                movimentos.setmHisDebito(rs.getString("mhisdebito"));
                movimentos.setmSalAntDebito(rs.getDouble("msalantdebito"));
                listaMovimentos.add(movimentos);
            }
            return listaMovimentos;
        } catch (SQLException erro) {
            Logger.getLogger(MovimentoDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            connection.close();
            ps.close();
            rs.close();
        }
        return null;
    }
}
