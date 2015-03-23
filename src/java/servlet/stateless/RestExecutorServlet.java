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

/**
 *
 * @author fellippe.mendonca
 */
@WebServlet("/restexecutorservlet")
public class RestExecutorServlet extends HttpServlet {
    @EJB
    private StatelessSessionBean sless;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*    
        for (String name : Collections.<String>list(request.getParameterNames())) {
            String value = request.getParameter(name); 
            System.out.println(name+" : "+value);
        }     
        */
        
        try {
            ResponseContents RC2 = sless.executaNovoCenario(request.getParameter("jsonRequestObj"));
            System.out.println("Request Enviado ao Servlet:\n"+request.getParameter("jsonRequestObj")+"\n");
            response.setStatus(200);
            response.getWriter().write("Request: "+RC2.getRequest()+" , Status: "+RC2.getStatus()+"<br>");
        } catch (NamingException | URISyntaxException ex) {
            Logger.getLogger(RestExecutorServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
