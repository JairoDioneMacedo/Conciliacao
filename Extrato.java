/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.dao.ContaDAO;
import br.com.jairo.dao.ExtratoDAO;
import br.com.jairo.modelo.Contas;
import br.com.jairo.modelo.ExtratoTransiente;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Jairo
 */
public class Extrato extends HttpServlet {

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
        //PrintWriter out = response.getWriter();
        RequestDispatcher rd = null;

        String dataInicial = request.getParameter("dataI");
        String dataFinal = request.getParameter("dataF");
        String conCodigo = request.getParameter("concodigo");
        String conSaldo = request.getParameter("consaldo");

        ExtratoDAO extratoDAO = new ExtratoDAO();
        ContaDAO contaDAO = new ContaDAO();

        List<ExtratoTransiente> extrato;
        Contas conta = null;
        try {
            extrato = extratoDAO.exibeExtrato(dataInicial, dataFinal, conCodigo, conSaldo);
            conta = contaDAO.buscarConta(Integer.parseInt(conCodigo));
        } catch (ParseException ex) {
            throw new ServletException(ex);
        }

        JasperReport report;
        byte[] byteStream = null;
        try {
            URL resourceUrl = request.getSession().getServletContext().getResource("/WEB-INF/report/extrato.jasper");
            report = (JasperReport) JRLoader.loadObject(resourceUrl);
            HashMap<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("conta", conta.getConDescricao());
            parametros.put("saldo", conta.getConSaldo());

            byteStream = JasperRunManager.runReportToPdf(report, parametros, new JRBeanCollectionDataSource(extrato));
        } catch (JRException ex) {
            Logger.getLogger(Extrato.class.getName()).log(Level.SEVERE, null, ex);
        }

        OutputStream outStream = response.getOutputStream();
        response.setHeader("Content-Disposition", "inline, filename=extrato.pdf");
        response.setContentType("application/pdf");
        response.setContentLength(byteStream.length);
        outStream.write(byteStream, 0, byteStream.length);
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
            Logger.getLogger(Extrato.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Extrato.class.getName()).log(Level.SEVERE, null, ex);
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
