/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.dao.ContaDAO;
import br.com.jairo.dao.DcontaDAO;
import br.com.jairo.modelo.Contas;
import br.com.jairo.modelo.Dcontas;
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
public class ContaCRUD extends HttpServlet {

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

        String conCodigo = request.getParameter("concodigo");
        String conDescricao = request.getParameter("condescricao");
        String tipoCodigo = request.getParameter("tipocodigo");
        String conSaldo = request.getParameter("consaldo");
        String dconCodigo = request.getParameter("concodigo");
        String dconDescricao = request.getParameter("dcondescricao");
        String dtipoCodigo = request.getParameter("tipocodigo");
        String dconSaldo = request.getParameter("dconsaldo");

        Contas contas = new Contas();
        if (conCodigo != null) {
            contas.setConCodigo(Integer.parseInt(conCodigo));
        }
        contas.setConDescricao(conDescricao);
        if (tipoCodigo != null) {
            contas.setTipoCodigo(Integer.parseInt(tipoCodigo));
        }
        if (conSaldo != null) {
            contas.setConSaldo(Double.parseDouble(conSaldo));
        }

        Dcontas dcontas = new Dcontas();
        if(dconCodigo != null){
            dcontas.setConCodigo(Integer.parseInt(dconCodigo));
        }
        dcontas.setdConDescricao(dconDescricao);
        if(dtipoCodigo != null){
            dcontas.setTipoCodigo(Integer.parseInt(tipoCodigo));
        }
        if(dconSaldo != null){
            dcontas.setdConSaldo(Double.parseDouble(dconSaldo));
        }

        ContaDAO contaDAO = new ContaDAO();
        DcontaDAO dcontaDAO = new DcontaDAO();

        //Verificar qual é a ação
        String acao = request.getParameter("acao");

        if (acao == null) {
            acao = "listarConta";
        }

        if (acao.equals("alterar")) {
            contaDAO.alteraConta(contas);
            rd = request.getRequestDispatcher("/ContaCRUD?acao=listarConta");
        } else if (acao.equals("excluir")) {
            contaDAO.excluiConta(contas);
            rd = request.getRequestDispatcher("/ContaCRUD?acao=listarConta");
        } else if (acao.equals("listarConta")) {
            int numPagina = 1;
            if (request.getParameter("numpagina") != null) {
                numPagina = Integer.parseInt(request.getParameter("numpagina"));
            }
            try {

                String ordenacao = request.getParameter("ordenacao");
                if (ordenacao == null) {
                    ordenacao = "condescricao";
                }

                String pesquisa = request.getParameter("pesquisa");
                if (pesquisa == null) {
                    pesquisa = "";
                }

                String campopesquisa = request.getParameter("campopesquisa");
                if (campopesquisa == null) {
                    campopesquisa = "condescricao";
                }

                List listaContas = contaDAO.getListaContaPaginado(numPagina, ordenacao, pesquisa, campopesquisa);
                String qtdTotalRegistros = contaDAO.totalRegistros(pesquisa, campopesquisa);
                request.setAttribute("sessaoListaContas", listaContas);
                request.setAttribute("sessaoQtdTotalDeRegistros", qtdTotalRegistros);
                rd = request.getRequestDispatcher("/listacontas.jsp");
            } catch (SQLException ex) {
                Logger.getLogger(ContaCRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (acao.equals("novo")) {
            contaDAO.novaConta(contas);
            //contaDAO.getListaContaSaldo();
            rd = request.getRequestDispatcher("/dconta.jsp");
        } else if (acao.equals("gravar")) {
            dcontaDAO.novaDconta(dcontas);
            rd = request.getRequestDispatcher("/ContaCRUD?acao=listarConta");
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
            Logger.getLogger(ContaCRUD.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ContaCRUD.class.getName()).log(Level.SEVERE, null, ex);
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
