/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.dao.ContaDAO;
import br.com.jairo.dao.CreditoDAO;
import br.com.jairo.dao.MovimentoDAO;
import br.com.jairo.modelo.Contas;
import br.com.jairo.modelo.Creditos;
import br.com.jairo.modelo.Movimentos;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class LancamentoCRUD1 extends HttpServlet {

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
            throws ServletException, IOException, ParseException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        RequestDispatcher rd = null;

        String creCodigo = request.getParameter("crecodigo");
        String creData = request.getParameter("credata");
        String conCodigo = request.getParameter("concodigo");
        String creValor = request.getParameter("crevalor");
        String creHistorico = request.getParameter("crehistorico");
        String conSaldo = request.getParameter("consaldo");

        Creditos creditos = new Creditos();
        Contas contas = new Contas();
        Movimentos movimentos = new Movimentos();

        if (creCodigo != null) {
            creditos.setCreCodigo(Integer.parseInt(creCodigo));
        }
        DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        if (creData != null) {
            Date creDataFormatada = formatoData.parse(creData);
            creditos.setCreData(creDataFormatada);
        }
        if (conCodigo != null) {
            creditos.setConCodigo(Integer.parseInt(conCodigo));
        }
        if (creValor != null) {
            creditos.setCreValor(Double.parseDouble(creValor));
        }
        creditos.setCreHistorico(creHistorico);
        if(conSaldo != null){
            contas.setConSaldo(Double.parseDouble(conSaldo));
        }

        CreditoDAO creditoDAO = new CreditoDAO();
        MovimentoDAO movimentoDAO = new MovimentoDAO();
        ContaDAO contaDAO = new ContaDAO();

        contas = contaDAO.buscarConta(Integer.parseInt(conCodigo));
        // Testar se existe conta, mostrar mensagem de erro caso não exista

        //Verificar qual é a ação
        String acao = request.getParameter("acao");

        if (acao == null) {
            acao = "listarCredito";
        }

        if (acao.equals("alterar")) {
            //contaDAO.alteraConta(contas);
            //rd = request.getRequestDispatcher("/ContaCRUD?acao=listarConta");
        } else if (acao.equals("excluir")) {
            //contaDAO.excluiConta(contas);
            //rd = request.getRequestDispatcher("/ContaCRUD?acao=listarConta");
        } //else if (acao.equals("listarConta")) {
        //int numPagina = 1;
        //if (request.getParameter("numpagina") != null) {
        //numPagina = Integer.parseInt(request.getParameter("numpagina"));
        //}
        else if (acao.equals("novo")) {
            Contas contaComSaldo = new Contas();
            creditoDAO.novaCredito(creditos,conCodigo,contaComSaldo);
            movimentoDAO.novoMovimentoCredito(movimentos, creditos,contas,contaComSaldo);
            double novo_saldo = contaDAO.atualizaSaldo(contas, creditos.getCreValor());
            //movimentoDAO.Extrato(movimentos);
            //contaDAO.getContaSaldo();
            rd = request.getRequestDispatcher("/debito1.jsp");
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
        } catch (ParseException ex) {
            Logger.getLogger(LancamentoCRUD1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LancamentoCRUD1.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ParseException ex) {
            Logger.getLogger(LancamentoCRUD1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LancamentoCRUD1.class.getName()).log(Level.SEVERE, null, ex);
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
