package servlet.stateless;

import java.io.IOException;
import java.io.PrintWriter;
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
import java.net.URISyntaxException;

/**
 *
 * @author fellippe.mendonca
 */
@WebServlet("/jsonrequestservlet")
public class JsonRequestServlet extends HttpServlet {

    @EJB
    private StatelessSessionBean sless;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String buttonID = request.getParameter("button-id");
            switch (buttonID) {
                case "execute-requests":
                    response.getWriter().write(new Gson().toJson(sless.getRequest()));
                    break;

                case "bands-albums":
                    response.getWriter().write(new Gson().toJson(sless.getRequest()));
                    break;
            }
        } catch (NamingException ex) {
            Logger.getLogger(JsonRequestServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
