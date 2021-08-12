/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
import br.com.jairo.modelo.Usuarios;
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
public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO() {
        this.connection = new FabricaConexao().getConnection();
    }
    //verifica usuario para acessar o sistema

    public boolean verificaUsuario(Usuarios usuarios) {
        String sql = "select * from usuarios where usuario=? and senha=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, usuarios.getUsuario());
            ps.setString(2, usuarios.getSenha());
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;

            }
        } catch (SQLException erro) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, erro);
        }
        return false;
    }
    //outra forma para verificar usuario para acessar o sistema

    public Usuarios getUsuario(String usuario, String senha) throws SQLException {
        String sql = "select * from usuarios where usuario=? and senha=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, senha);
            rs = ps.executeQuery();
            if (rs.next()) {
                Usuarios usuarios = new Usuarios();
                usuarios.setUsuario(usuario);
                usuarios.setSenha(senha);
                usuarios.setNivel(rs.getInt("nivel"));
                usuarios.setNomeCompleto(rs.getString("nomecompleto"));
                return usuarios;
            }
        } catch (SQLException erro) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            connection.close();
            ps.close();
            rs.close();
        }
        return null;
    }

    //exibe usuarios cadastrados no sistema
    public List getListaUsuario() throws SQLException {
        String sql = "select * from usuarios";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Usuarios> listaUsuarios = new ArrayList<Usuarios>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuarios usuarios = new Usuarios();
                usuarios.setUsuario(rs.getString("usuario"));
                usuarios.setSenha(rs.getString("senha"));
                usuarios.setNivel(rs.getInt("nivel"));
                usuarios.setNomeCompleto(rs.getString("nomecompleto"));
                listaUsuarios.add(usuarios);
            }
            return listaUsuarios;
        } catch (SQLException erro) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            connection.close();
            ps.close();
            rs.close();
        }
        return null;
    }

    //exibe usuarios cadastrados no sistema, paginado
    public List getListaUsuarioPaginado(int pagina, String ordenacao, String pesquisa, String campopesquisa) throws SQLException {
        int limite = 8;
        String sql = "";
        int offset = (pagina * limite) - limite;
        if (campopesquisa.equals("nivel")) {
            if (pesquisa.equals("")) {
                sql = "select * from usuarios where " + campopesquisa + " > 0 order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
            } else {
                sql = "select * from usuarios where " + campopesquisa + " = " + pesquisa + " order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
            }
        } else {
            sql = "select * from usuarios where " + campopesquisa + " like '%" + pesquisa + "%' order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
        }
        //    sql = "select * from usuarios where "+campopesquisa+" = "+pesquisa+" order by "+ordenacao+" LIMIT 5 OFFSET " + offset;
        //else
        //  sql = "select * from usuarios where "+campopesquisa+" like '%"+pesquisa+"%' order by "+ordenacao+" LIMIT 5 OFFSET " + offset;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Usuarios> listaUsuarios = new ArrayList<Usuarios>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuarios usuarios = new Usuarios();
                usuarios.setUsuario(rs.getString("usuario"));
                usuarios.setSenha(rs.getString("senha"));
                usuarios.setNivel(rs.getInt("nivel"));
                usuarios.setNomeCompleto(rs.getString("nomecompleto"));
                usuarios.setFoto(rs.getString("foto"));
                listaUsuarios.add(usuarios);
            }
            return listaUsuarios;
        } catch (SQLException erro) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            //connection.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //metodo para excluir usuario
    public boolean excluiUsuario(Usuarios usuarios) throws SQLException {
        String sql = "delete from usuarios where usuario=?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, usuarios.getUsuario());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
        return false;
    }

    //metodo para atualizar usuario
    public void alteraUsuario(Usuarios usuarios) throws SQLException {
        String sql = "update usuarios set senha=?, nivel=?, nomecompleto=?, foto=? where usuario=?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, usuarios.getSenha());
            ps.setInt(2, usuarios.getNivel());
            ps.setString(3, usuarios.getNomeCompleto());
            ps.setString(4, usuarios.getFoto());
            ps.setString(5, usuarios.getUsuario());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            ps.close();
        }
    }

    //metodo para incluir usuario
    public void novoUsuario(Usuarios usuarios) throws SQLException {
        String sql = "insert into usuarios values (?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, usuarios.getUsuario());
            ps.setString(2, usuarios.getSenha());
            ps.setInt(3, usuarios.getNivel());
            ps.setString(4, usuarios.getNomeCompleto());
            ps.setString(5, usuarios.getFoto());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            if (campopesquisa.equals("nivel")) {
                if (pesquisa.equals("")) {
                    sqlConta = "select count(*) as contaRegistros from usuarios where " + campopesquisa + " > 0";
                } else {
                    sqlConta = "select count(*) as contaRegistros from usuarios where " + campopesquisa + " = " + pesquisa;
                }
            } else {
                sqlConta = "select count(*) as contaRegistros from usuarios where " + campopesquisa + " like '%" + pesquisa + "%'";
            }
            //if (campopesquisa.equals("nivel")) {
            //  sqlConta = "select count(*) as contaRegistros from usuarios where " + campopesquisa + " = " + pesquisa;
            //} else {
            //  sqlConta = "select count(*) as contaRegistros from usuarios where " + campopesquisa + " like '%" + pesquisa + "%'";
            //}
            psConta = connection.prepareStatement(sqlConta);
            rsConta = psConta.executeQuery();
            rsConta.next();
            String qtdTotalRegistros = rsConta.getString("contaRegistros");
            return qtdTotalRegistros;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
            psConta.close();
            rsConta.close();
        }
        return null;
    }
}
