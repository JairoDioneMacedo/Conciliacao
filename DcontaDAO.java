/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
import br.com.jairo.modelo.Dcontas;
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
public class DcontaDAO {

    private Connection connection;

    public DcontaDAO() {
        this.connection = new FabricaConexao().getConnection();
    }

    //metodo para incluir dconta
    public void novaDconta(Dcontas dcontas) throws SQLException {
        String sql = "insert into dcontas (dcondescricao,tipocodigo,dconsaldo) values (?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, dcontas.getdConDescricao());
            ps.setInt(2, dcontas.getTipoCodigo());
            ps.setDouble(3, dcontas.getdConSaldo());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
    }

    //lista dcontas cadastradas no sistema
    public List getListadContaCombo() throws SQLException {
        String sql = "select * from dcontas order by dcondescricao";

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Dcontas> listadContas = new ArrayList<Dcontas>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Dcontas dcontas = new Dcontas();
                dcontas.setConCodigo(rs.getInt("conCodigo"));
                dcontas.setdConDescricao(rs.getString("dconDescricao"));
                listadContas.add(dcontas);
            }
            return listadContas;
        } catch (SQLException erro) {
            Logger.getLogger(DcontaDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            //connection.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }
}
