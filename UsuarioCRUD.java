/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.dao.UsuarioDAO;
import br.com.jairo.modelo.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jairo
 */
public class UsuarioCRUD extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        RequestDispatcher rd = null;

        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        String nivel = request.getParameter("nivel");
        String nomeCompleto = request.getParameter("nomecompleto");
        String foto = request.getParameter("foto");

        Usuarios usuarios = new Usuarios();
        usuarios.setUsuario(usuario);
        usuarios.setSenha(senha);
        if (nivel != null) {
            usuarios.setNivel(Integer.parseInt(nivel));
        }
        usuarios.setNomeCompleto(nomeCompleto);
        usuarios.setFoto(foto);

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        //Verificar qual é a ação
        String acao = request.getParameter("acao");

        if (acao == null) {
            acao = "listarUsuario";
        }

        if (acao.equals("alterar")) {
            usuarioDAO.alteraUsuario(usuarios);
            rd = request.getRequestDispatcher("/UsuarioCRUD?acao=listarUsuario");
        } else if (acao.equals("excluir")) {
            usuarioDAO.excluiUsuario(usuarios);
            rd = request.getRequestDispatcher("/UsuarioCRUD?acao=listarUsuario");
        } else if (acao.equals("listarUsuario")) {
            int numPagina = 1;
            if (request.getParameter("numpagina") != null) {
                numPagina = Integer.parseInt(request.getParameter("numpagina"));
            }
            try {

                String ordenacao = request.getParameter("ordenacao");
                if (ordenacao == null) {
                    ordenacao = "usuario";
                }

                String pesquisa = request.getParameter("pesquisa");
                if (pesquisa == null) {
                    pesquisa = "";
                }

                String campopesquisa = request.getParameter("campopesquisa");
                if (campopesquisa == null) {
                    campopesquisa = "usuario";
                }

                List listaUsuarios = usuarioDAO.getListaUsuarioPaginado(numPagina, ordenacao, pesquisa, campopesquisa);
                String qtdTotalRegistros = usuarioDAO.totalRegistros(pesquisa, campopesquisa);
                request.setAttribute("sessaoListaUsuarios", listaUsuarios);
                request.setAttribute("sessaoQtdTotalDeRegistros", qtdTotalRegistros);
                rd = request.getRequestDispatcher("/listausuarios.jsp");
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioCRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.equals("novo")) {
            usuarioDAO.novoUsuario(usuarios);
            rd = request.getRequestDispatcher("/UsuarioCRUD?acao=listarUsuario");
        }

        rd.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
