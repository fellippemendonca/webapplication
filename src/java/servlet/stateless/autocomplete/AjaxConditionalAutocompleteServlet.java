/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet.stateless.autocomplete;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "AjaxConditionalAutocompleteServlet", urlPatterns = {"/AjaxConditionalAutocompleteServlet"})
public class AjaxConditionalAutocompleteServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws javax.naming.NamingException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException {

        // Map real data into JSON
        response.setContentType("application/json;charset=UTF-8");
        SearchInDatabank search = new SearchInDatabank();
        
        /*Search and list by field type and criteria*/
        List<String> suggestions = search.selectConditionalStringArrayFrom(request.getParameter("field"), request.getParameter("criteria")); /*NEW*/
        
        Collections.sort(suggestions);
        String param = request.getParameter("term");
        List<AutoCompleteData> result = new ArrayList<>();
        if (param.equals("")) {
            for (String suggestion : suggestions) {
                result.add(new AutoCompleteData(suggestion, suggestion));
            }
        } else {
            for (String suggestion : suggestions) {
                if (suggestion.toLowerCase().startsWith(param.toLowerCase())) {
                    result.add(new AutoCompleteData(suggestion, suggestion));
                }
            }
        }

        response.getWriter().write(new Gson().toJson(result));
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
        } catch (NamingException ex) {
            Logger.getLogger(AjaxConditionalAutocompleteServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (NamingException ex) {
            Logger.getLogger(AjaxConditionalAutocompleteServlet.class.getName()).log(Level.SEVERE, null, ex);
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
