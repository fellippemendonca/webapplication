/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet.stateless.validation;

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
import servlet.stateless.RequestHandlerServlet;
import servlet.stateless.StatelessSessionBean;

/**
 *
 * @author fellippe.mendonca
 */
@WebServlet(name = "ValidationScenarioHandlerServlet", urlPatterns = {"/ValidationScenarioHandlerServlet"})
public class ValidationScenarioHandlerServlet extends HttpServlet {

    @EJB
    private StatelessSessionBean sless;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        boolean status;
        switch (request.getParameter("operation")) {
            case "insert":
                System.out.println("Validação enviada para ser criada:\n" + request.getParameter("jsonValidationObj") + "\n");
                status = sless.criaValidacao(request.getParameter("jsonValidationObj"));
                if(status == true){
                    response.setStatus(200);
                    response.getWriter().write("Status: Success.");
                }else{
                    response.setStatus(500);
                    response.getWriter().write("Status: Fail.");
                }
                break;
            case "update":
                System.out.println("Validação enviada para ser alterada:\n" + request.getParameter("jsonValidationObj") + "\n");
                status = sless.atualizaValidacao(request.getParameter("jsonValidationObj"));
                if(status == true){
                    response.setStatus(200);
                    response.getWriter().write("Status: Success.");
                }else{
                    response.setStatus(500);
                    response.getWriter().write("Status: Fail.");
                }
                break;
            case "remove":
                System.out.println("Validação enviada para ser removida:\n" + request.getParameter("jsonValidationObj") + "\n");
                status = sless.removeValidacao(request.getParameter("jsonValidationObj"));
                if(status == true){
                    response.setStatus(200);
                    response.getWriter().write("Status: Success.");
                }else{
                    response.setStatus(500);
                    response.getWriter().write("Status: Fail.");
                }
                break;
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ValidationScenarioHandlerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {
            Logger.getLogger(ValidationScenarioHandlerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
