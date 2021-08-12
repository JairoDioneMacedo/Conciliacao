/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
import br.com.jairo.modelo.Contas;
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
public class ContaDAO {

    private double salAntCre = 0;
    private Connection connection;

    public ContaDAO() {
        this.connection = new FabricaConexao().getConnection();
    }

    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            this.connection = new FabricaConexao().getConnection();
        }
        return this.connection;
    }

    //exibe contas cadastradas no sistema, paginado
    public List getListaContaPaginado(int pagina, String ordenacao, String pesquisa, String campopesquisa) throws SQLException {
        int limite = 8;
        String sql = "";
        int offset = (pagina * limite) - limite;
        if (campopesquisa.equals("concodigo")) {
            if (pesquisa.equals("")) {
                sql = "select * from contas where " + campopesquisa + " > 0 order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
            } else {
                sql = "select * from contas where " + campopesquisa + " = " + pesquisa + " order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
            }
        } else {
            sql = "select * from contas where " + campopesquisa + " like '%" + pesquisa + "%' order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Contas> listaContas = new ArrayList<Contas>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Contas contas = new Contas();
                contas.setConCodigo(rs.getInt("conCodigo"));
                contas.setConDescricao(rs.getString("conDescricao"));
                contas.setTipoCodigo(rs.getInt("tipocodigo"));
                contas.setConSaldo(rs.getDouble("conSaldo"));
                listaContas.add(contas);
            }
            return listaContas;
        } catch (SQLException erro) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            //connection.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //lista contas cadastradas no sistema
    public List getListaContaCombo() throws SQLException {
        String sql = "select * from contas order by condescricao";

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Contas> listaContas = new ArrayList<Contas>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Contas contas = new Contas();
                contas.setConCodigo(rs.getInt("conCodigo"));
                contas.setConDescricao(rs.getString("conDescricao"));
                contas.setConSaldo(rs.getDouble("conSaldo"));
                listaContas.add(contas);
            }
            return listaContas;
        } catch (SQLException erro) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            //connection.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //lista contas cadastradas no sistema com saldo
    public List getListaContaSaldo() throws SQLException {
        String sql = "select * from contas order by condescricao";

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
                //System.out.println("Nome: " + contas.getConDescricao() + " Saldo: " + contas.getConSaldo());
            }
            return listaContas;
        } catch (SQLException erro) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            //connection.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //metodo que retorna saldo da conta
    public void getContaSaldo() throws SQLException {
        String sql = "select consaldo from contas where concodigo=?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            Contas contas = new Contas();
            contas.setConCodigo(rs.getInt("concodigo"));
            contas.setConSaldo(rs.getDouble("consaldo"));

            System.out.println(" Saldo: " + contas.getConSaldo());

        } catch (SQLException erro) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            //connection.close();
            //ps.close();
            //rs.close();
        }
    }

    //metodo para excluir conta
    public boolean excluiConta(Contas contas) throws SQLException {
        String sql = "delete from contas where concodigo=?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, contas.getConCodigo());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
        return false;
    }

    //metodo para atualizar conta
    public void alteraConta(Contas contas) throws SQLException {
        String sql = "update contas set condescricao=?,tipocodigo=?,consaldo=? where concodigo=?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, contas.getConDescricao());
            ps.setInt(2, contas.getTipoCodigo());
            ps.setDouble(3, contas.getConSaldo());
            ps.setInt(4, contas.getConCodigo());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
    }

    //metodo para incluir conta
    public void novaConta(Contas contas) throws SQLException {
        String sql = "insert into contas (condescricao,tipocodigo,consaldo) values (?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, contas.getConDescricao());
            ps.setInt(2, contas.getTipoCodigo());
            ps.setDouble(3, contas.getConSaldo());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
    }

    //metodo para retornar o numero de registros na paginação
    public String totalRegistros(String pesquisa, String campopesquisa) throws SQLException {
        PreparedStatement psConta = null;
        ResultSet rsConta = null;
        String sqlConta = "";
        try {
            if (campopesquisa.equals("concodigo")) {
                if (pesquisa.equals("")) {
                    sqlConta = "select count(*) as contaRegistros from contas where " + campopesquisa + " > 0";
                } else {
                    sqlConta = "select count(*) as contaRegistros from contas where " + campopesquisa + " = " + pesquisa;
                }
            } else {
                sqlConta = "select count(*) as contaRegistros from contas where " + campopesquisa + " like '%" + pesquisa + "%'";
            }
            psConta = connection.prepareStatement(sqlConta);
            rsConta = psConta.executeQuery();
            rsConta.next();
            String qtdTotalRegistros = rsConta.getString("contaRegistros");
            return qtdTotalRegistros;
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            psConta.close();
            rsConta.close();
        }
        return null;
    }

    public double pegaSaldoContaCre() {

        Contas contas = new Contas();
        salAntCre = contas.getConSaldo();
        System.out.println(salAntCre);
        return salAntCre;
    }

    public double atualizaSaldo(Contas conta, double valor) {
        PreparedStatement psConta = null;

        // alteração do saldo da conta no objeto
        conta.setConSaldo(conta.getConSaldo() + valor);
        //System.out.println(conta);

        String sqlConta = "update contas set consaldo = ? where concodigo = ?";
        try {
            // pega conexão
            connection = getConnection();
            // alteração do saldo da conta no banco
            psConta = connection.prepareStatement(sqlConta);
            psConta.setDouble(1, conta.getConSaldo());
            psConta.setInt(2, conta.getConCodigo());
            psConta.execute();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                psConta.close();
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conta.getConSaldo();
    }

    public Contas buscarConta(int concodigo) {
        PreparedStatement psConta = null;
        ResultSet rsConta = null;

        String sqlConta = "select * from contas where concodigo = ? limit 1";
        try {
            // alteração do saldo da conta no banco
            psConta = connection.prepareStatement(sqlConta);
            psConta.setInt(1, concodigo);
            rsConta = psConta.executeQuery();

            Contas conta = new Contas();
            if (rsConta.next()) {
                conta.setConCodigo(rsConta.getInt("concodigo"));
                conta.setConDescricao(rsConta.getString("condescricao"));
                conta.setConSaldo(rsConta.getDouble("consaldo"));
                conta.setTipoCodigo(rsConta.getInt("tipocodigo"));
                return conta;
            }

            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                psConta.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //metodo para zerar saldo de contas de receita e despesa
    public void zeraSaldo() throws SQLException {
        PreparedStatement psConta = null;
        //ResultSet rsConta = null;
        String sql = "update contas set consaldo=0.00 where tipocodigo=2";
        Contas contas = new Contas();

        try {
            psConta = connection.prepareStatement(sql);
            psConta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            psConta.close();
        }
    }
}
