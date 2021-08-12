/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.dao.TipoDAO;
import br.com.jairo.modelo.Tipos;
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
public class TipoCRUD extends HttpServlet {

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

        String tipoCodigo = request.getParameter("tipocodigo");
        String tipoDescricao = request.getParameter("tipodescricao");

        Tipos tipos = new Tipos();
        if(tipoCodigo != null)
            tipos.setTipoCodigo(Integer.parseInt(tipoCodigo));
        tipos.setTipoDescricao(tipoDescricao);

        TipoDAO tipoDAO = new TipoDAO();

        //Verificar qual é a ação
        String acao = request.getParameter("acao");

        if (acao == null) {
            acao = "listarTipo";
        }

        if (acao.equals("alterar")) {
            tipoDAO.alteraTipo(tipos);
            rd = request.getRequestDispatcher("/TipoCRUD?acao=listarTipo");
        } else if (acao.equals("excluir")) {
            tipoDAO.excluiTipo(tipos);
            rd = request.getRequestDispatcher("/TipoCRUD?acao=listarTipo");
        } else if (acao.equals("listarTipo")) {
            int numPagina = 1;
            if (request.getParameter("numpagina") != null) {
                numPagina = Integer.parseInt(request.getParameter("numpagina"));
            }
            try {

                String ordenacao = request.getParameter("ordenacao");
                if (ordenacao == null) {
                    ordenacao = "tipodescricao";
                }

                String pesquisa = request.getParameter("pesquisa");
                if (pesquisa == null) {
                    pesquisa = "";
                }

                String campopesquisa = request.getParameter("campopesquisa");
                if (campopesquisa == null) {
                    campopesquisa = "tipodescricao";
                }

                List listaTipos = tipoDAO.getListaTipoPaginado(numPagina, ordenacao, pesquisa, campopesquisa);
                String qtdTotalRegistros = tipoDAO.totalRegistros(pesquisa, campopesquisa);
                request.setAttribute("sessaoListaTipos", listaTipos);
                request.setAttribute("sessaoQtdTotalDeRegistros", qtdTotalRegistros);
                rd = request.getRequestDispatcher("/listatipos.jsp");
            } catch (SQLException ex) {
                Logger.getLogger(TipoCRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.equals("novo")) {
            tipoDAO.novoTipo(tipos);
            rd = request.getRequestDispatcher("/TipoCRUD?acao=listarTipo");
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
            Logger.getLogger(TipoCRUD.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TipoCRUD.class.getName()).log(Level.SEVERE, null, ex);
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
