/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
import br.com.jairo.modelo.Contas;
import br.com.jairo.modelo.ExtratoTransiente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jairo
 */
public class ExtratoDAO {

    private final Connection connection;

    public ExtratoDAO() {
        this.connection = new FabricaConexao().getConnection();

    }

    public List exibeExtrato(String dataInicial, String dataFinal, String conCodigo, String conSaldo) throws ParseException, SQLException {

        Contas contas = new Contas();

        String sqlConta = "select * from contas where concodigo = " + conCodigo;
        PreparedStatement psConta = null;
        ResultSet rsConta = null;

        psConta = connection.prepareStatement(sqlConta);
        rsConta = psConta.executeQuery();
        String conta = null;
        double saldo1 = 0;

        while (rsConta.next()) {
            contas.setConCodigo(rsConta.getInt("concodigo"));
            contas.setConDescricao(rsConta.getString("condescricao"));
            contas.setTipoCodigo(rsConta.getInt("tipocodigo"));
            contas.setConSaldo(rsConta.getDouble("consaldo"));
            //System.out.println("Extrato da Conta-> "+contas.getConDescricao());
            conta = contas.getConDescricao();
            saldo1 = contas.getConSaldo();
            //conSaldo = String.valueOf(saldo1);
            System.out.println("Extrato da Conta-> " + conta + ": Saldo Altual-> " + saldo1);
        }

        Date dataInicio = null;
        Date dataFim = null;

        DateFormat formatoDataBanco = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        if (dataInicial != null) {
            dataInicio = formatoData.parse(dataInicial);
        }
        if (dataFinal != null) {
            dataFim = formatoData.parse(dataFinal);
        }

        String sqlUltimoCredito = "select c.concodigo,c.credata as data,c.crehistorico as historico,c.crevalor as valor,c.consaldo as saldo, 1 as tipo "
                + "from creditos c where c.concodigo = ? and c.credata >= ? and c.credata <= ? "
                + "UNION ALL "
                + "select d.concodigo,d.debdata as data,d.debhistorico as historico,d.debvalor as valor,d.consaldo as saldo, 2 as tipo "
                + "from debitos d where d.concodigo = ? and d.debdata >= ? and d.debdata <= ? order by data";

        PreparedStatement pscUltimoCredito = null;
        ResultSet rscUltimoCredito = null;
        List<ExtratoTransiente> extratoTransientes = new ArrayList<ExtratoTransiente>();

        try {
            pscUltimoCredito = connection.prepareStatement(sqlUltimoCredito);
            pscUltimoCredito.setInt(1, Integer.parseInt(conCodigo));
            pscUltimoCredito.setDate(2, new java.sql.Date(dataInicio.getTime()));
            pscUltimoCredito.setDate(3, new java.sql.Date(dataFim.getTime()));
            pscUltimoCredito.setInt(4, Integer.parseInt(conCodigo));
            pscUltimoCredito.setDate(5, new java.sql.Date(dataInicio.getTime()));
            pscUltimoCredito.setDate(6, new java.sql.Date(dataFim.getTime()));
            rscUltimoCredito = pscUltimoCredito.executeQuery();
            while (rscUltimoCredito.next()) {
                ExtratoTransiente extratoTransiente = new ExtratoTransiente();
                extratoTransiente.setData(rscUltimoCredito.getDate("data"));
                extratoTransiente.setValor(rscUltimoCredito.getDouble("valor"));
                extratoTransiente.setHistorico(rscUltimoCredito.getString("historico"));
                extratoTransiente.setSaldo(rscUltimoCredito.getDouble("saldo"));
                extratoTransiente.setTipo(rscUltimoCredito.getInt("tipo"));
                extratoTransientes.add(extratoTransiente);
            }

        } catch (Exception e) {
            Logger.getLogger(ExtratoDAO.class.getName()).log(Level.SEVERE, null, e);

        }

        return extratoTransientes;
    }
}
