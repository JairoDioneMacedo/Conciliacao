/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.dao.ContaDAO;
import br.com.jairo.dao.DebitoDAO;
import br.com.jairo.dao.MovimentoDAO;
import br.com.jairo.modelo.Contas;
import br.com.jairo.modelo.Debitos;
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
public class DebitoCRUD1 extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        RequestDispatcher rd = null;

        String debCodigo = request.getParameter("debcodigo");
        String debData = request.getParameter("debdata");
        String conCodigo = request.getParameter("concodigo");
        String debValor = request.getParameter("debvalor");
        String debHistorico = request.getParameter("debhistorico");

        Debitos debitos = new Debitos();
        Contas contas = new Contas();
        Movimentos movimentos = new Movimentos();

        if (debCodigo != null) {
            debitos.setDebCodigo(Integer.parseInt(debCodigo));
        }
        DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        if (debData != null) {
            Date debDataFormatada = formatoData.parse(debData);
            debitos.setDebData(debDataFormatada);
        }
        if (conCodigo != null) {
            debitos.setConCodigo(Integer.parseInt(conCodigo));
        }
        if (debValor != null) {
            debitos.setDebValor(Double.parseDouble(debValor));
        }
        debitos.setDebHistorico(debHistorico);

        DebitoDAO debitoDAO = new DebitoDAO();
        MovimentoDAO movimentoDAO = new MovimentoDAO();
        ContaDAO contaDAO = new ContaDAO();

        contas = contaDAO.buscarConta(Integer.parseInt(conCodigo));
        // Testar se existe conta, mostrar mensagem de erro caso não exista

        //Verificar qual é a ação
        String acao = request.getParameter("acao");

        if (acao == null) {
            acao = "listarDebito";
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
            debitoDAO.novoDebito(debitos,conCodigo,contaComSaldo);
            movimentoDAO.novoMovimentoDebito(movimentos,debitos, contas,contaComSaldo);
            double novo_saldo = contaDAO.atualizaSaldo(contas, debitos.getDebValor() * -1);
            //movimentoDAO.Extrato(movimentos);

            rd = request.getRequestDispatcher("/index1.jsp");
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
            Logger.getLogger(DebitoCRUD1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(DebitoCRUD1.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DebitoCRUD1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(DebitoCRUD1.class.getName()).log(Level.SEVERE, null, ex);
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
