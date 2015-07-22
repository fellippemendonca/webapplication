/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet.stateless.validation;

import JsonObjects.Validation.JsonValidationScenario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
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
import servlet.stateless.RestExecutorServlet;
import servlet.stateless.StatelessSessionBean;

/**
 *
 * @author fellippe.mendonca
 */
@WebServlet(name = "ValidationScenarioExecutorServlet", urlPatterns = {"/ValidationScenarioExecutorServlet"})
public class ValidationScenarioExecutorServlet extends HttpServlet {
    @EJB
    private StatelessSessionBean sless;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        System.out.println("Request:\n" + request.getParameter("jsonValidationObj") + "\n");
        JsonValidationScenario jvsc = sless.executaNovaValidacao(request.getParameter("jsonValidationObj"));
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        String responseContents = gson.toJson(jvsc);
        System.out.println("Response:\n"+responseContents);
        response.setStatus(200);
        response.getWriter().write(responseContents);
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
