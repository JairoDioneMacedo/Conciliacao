/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
import br.com.jairo.modelo.Tipos;
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
public class TipoDAO {

    private Connection connection;

    public TipoDAO() {
        this.connection = new FabricaConexao().getConnection();
    }

    //exibe tipos cadastrados no sistema, paginado
    public List getListaTipoPaginado(int pagina, String ordenacao, String pesquisa, String campopesquisa) throws SQLException {
        int limite = 8;
        String sql = "";
        int offset = (pagina * limite) - limite;
        if (campopesquisa.equals("tipocodigo")) {
            if (pesquisa.equals("")) {
                sql = "select * from tipos where " + campopesquisa + " > 0 order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
            } else {
                sql = "select * from tipos where " + campopesquisa + " = " + pesquisa + " order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
            }
        } else {
            sql = "select * from tipos where " + campopesquisa + " like '%" + pesquisa + "%' order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Tipos> listaTipos = new ArrayList<Tipos>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Tipos tipos = new Tipos();
                tipos.setTipoCodigo(rs.getInt("tipoCodigo"));
                tipos.setTipoDescricao(rs.getString("tipoDescricao"));
                listaTipos.add(tipos);
            }
            return listaTipos;
        } catch (SQLException erro) {
            Logger.getLogger(TipoDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            //connection.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //lista tipos cadastrados no sistema
    public List getListaTipoCombo() throws SQLException {
        String sql = "select * from tipos order by tipodescricao";

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Tipos> listaTipos = new ArrayList<Tipos>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Tipos tipos = new Tipos();
                tipos.setTipoCodigo(rs.getInt("tipoCodigo"));
                tipos.setTipoDescricao(rs.getString("tipoDescricao"));
                listaTipos.add(tipos);
            }
            return listaTipos;
        } catch (SQLException erro) {
            Logger.getLogger(TipoDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            //connection.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //metodo para excluir tipo
    public boolean excluiTipo(Tipos tipos) throws SQLException {
        String sql = "delete from tipos where tipocodigo=?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, tipos.getTipoCodigo());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(TipoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
        return false;
    }

    //metodo para atualizar tipo
    public void alteraTipo(Tipos tipos) throws SQLException {
        String sql = "update tipos set tipodescricao=? where tipocodigo=?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, tipos.getTipoDescricao());
            ps.setInt(2, tipos.getTipoCodigo());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TipoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
    }

    //metodo para incluir tipo
    public void novoTipo(Tipos tipos) throws SQLException {
        String sql = "insert into tipos (tipodescricao) values (?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, tipos.getTipoDescricao());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TipoDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            if (campopesquisa.equals("tipocodigo")) {
                if (pesquisa.equals("")) {
                    sqlConta = "select count(*) as contaRegistros from tipos where " + campopesquisa + " > 0";
                } else {
                    sqlConta = "select count(*) as contaRegistros from tipos where " + campopesquisa + " = " + pesquisa;
                }
            } else {
                sqlConta = "select count(*) as contaRegistros from tipos where " + campopesquisa + " like '%" + pesquisa + "%'";
            }
            psConta = connection.prepareStatement(sqlConta);
            rsConta = psConta.executeQuery();
            rsConta.next();
            String qtdTotalRegistros = rsConta.getString("contaRegistros");
            return qtdTotalRegistros;
        } catch (SQLException ex) {
            Logger.getLogger(TipoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            psConta.close();
            rsConta.close();
        }
        return null;
    }
}
