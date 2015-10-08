/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.stateless;

import HttpConnections.ResponseContents;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author fellippe.mendonca
 */
@WebServlet("/restexecutorservlet")
public class RestExecutorServlet extends HttpServlet {

    @EJB
    private StatelessSessionBean sless;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*    
         for (String name : Collections.<String>list(request.getParameterNames())) {
         String value = request.getParameter(name); 
         System.out.println(name+" : "+value);
         }
         */
        response.setContentType("application/json;charset=UTF-8");
        System.out.println("Request:\n" + request.getParameter("jsonRequestObj") + "\n");
        ResponseContents RC2 = sless.executaNovoCenario(request.getParameter("jsonRequestObj"));
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        String responseContents = gson.toJson(RC2);
        System.out.println("Response Status: "+RC2.getStatus());
        response.setStatus(200);
        response.getWriter().write(responseContents);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
