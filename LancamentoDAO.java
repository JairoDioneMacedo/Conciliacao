/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
import br.com.jairo.modelo.Lancamentos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jairo
 */
public class LancamentoDAO {

    private Connection connection;

    public LancamentoDAO() {
        this.connection = new FabricaConexao().getConnection();
    }

    //metodo para incluir lancamentos
    public void novoLancamento(Lancamentos lancamentos) throws SQLException {
        String sql = "insert into lancamentos (landata,lanconcred,lanvalcred,lanhistcred,lansalantcred,lancondeb,lanvaldeb,lanhistdeb,lansalantdeb) values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(lancamentos.getLanData().getTime()));
            ps.setInt(2, lancamentos.getLanConCred());
            ps.setDouble(3, lancamentos.getLanValCred());
            ps.setString(4, lancamentos.getLanHistCred());
            ps.setDouble(5, lancamentos.getLanSalAntCred());
            ps.setInt(6, lancamentos.getLanConDeb());
            ps.setDouble(7, lancamentos.getLanValDeb());
            ps.setString(8, lancamentos.getLanHistDeb());
            ps.setDouble(9, lancamentos.getLanSalAntDeb());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
    }
}
