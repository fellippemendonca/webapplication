/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.stateless;

import HttpConnections.ResponseContents;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fellippe.mendonca
 */
@WebServlet(name = "RequestHandlerServlet", urlPatterns = {"/requesthandlerservlet"})
public class RequestHandlerServlet extends HttpServlet {

    @EJB
    private StatelessSessionBean sless;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        boolean status;
        try {
            switch (request.getParameter("operation")) {
                case "insert":
                    status = sless.criaNovoCenario(request.getParameter("jsonRequestObj"));
                    System.out.println("Request enviado para ser criado:\n" + request.getParameter("jsonRequestObj") + "\n");
                    if(status == true){
                        response.setStatus(200);
                        response.getWriter().write("Status: Success.");
                    }else{
                        response.setStatus(500);
                        response.getWriter().write("Status: Fail.");
                    }
                    break;
                case "update":
                    status = sless.editaNovoCenario(request.getParameter("jsonRequestObj"));
                    System.out.println("Request enviado para ser editado:\n" + request.getParameter("jsonRequestObj") + "\n");
                    if(status == true){
                        response.setStatus(200);
                        response.getWriter().write("Status: Success.");
                    }else{
                        response.setStatus(500);
                        response.getWriter().write("Status: Fail.");
                    }
                    break;
                case "remove":
                    System.out.println("Request enviado para ser removido:\n" + request.getParameter("jsonRequestObj") + "\n");
                    status = sless.removeNovoCenario(request.getParameter("jsonRequestObj"));
                    if(status == true){
                        response.setStatus(200);
                        response.getWriter().write("Status: Success.");
                    }else{
                        response.setStatus(500);
                        response.getWriter().write("Status: Fail.");
                    }
                    break;
            }

        } catch (NamingException | URISyntaxException ex) {
            Logger.getLogger(RequestHandlerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
