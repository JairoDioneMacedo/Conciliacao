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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jairo
 */
public class DebitoDAO {

    private Connection connection;

    public DebitoDAO() {
        this.connection = new FabricaConexao().getConnection();
    }

    //metodo para incluir debito
    public void novoDebito(Debitos debitos, String conCodigo, Contas contaComSaldo) throws SQLException {
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
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, e);

        }

        String sqlUltimoCredito = "select * from creditos order by crecodigo desc limit 1";

        PreparedStatement pscUltimoCredito = null;
        ResultSet rscUltimoCredito = null;

        Creditos creditos = new Creditos();

        try {

            pscUltimoCredito = connection.prepareStatement(sqlUltimoCredito);
            rscUltimoCredito = pscUltimoCredito.executeQuery();
            while (rscUltimoCredito.next()) {

                creditos.setCreCodigo(rscUltimoCredito.getInt("crecodigo"));
                creditos.setCreData(rscUltimoCredito.getDate("credata"));
                creditos.setConCodigo(rscUltimoCredito.getInt("concodigo"));
                creditos.setCreValor(rscUltimoCredito.getInt("crevalor"));
                creditos.setCreHistorico(rscUltimoCredito.getString("crehistorico"));
                creditos.setConSaldo(rscUltimoCredito.getDouble("consaldo"));
            }

        } catch (Exception e) {
            Logger.getLogger(DebitoDAO.class.getName()).log(Level.SEVERE, null, e);

        }

        String sql = "insert into debitos (debdata,concodigo,debvalor,debhistorico,consaldo,creditoid) values (?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(debitos.getDebData().getTime()));
            ps.setInt(2, debitos.getConCodigo());
            ps.setDouble(3, debitos.getDebValor() * -1);
            ps.setString(4, debitos.getDebHistorico());
            ps.setDouble(5, contaComSaldo.getConSaldo());
            ps.setInt(6, creditos.getCreCodigo());

            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CreditoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
    }
}
