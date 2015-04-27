
package servlet.stateless;

import HttpConnections.ResponseContents;
import autoScenarios.openApi.v1.sellerItems.AutoScenario;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import javax.ejb.Stateless;
import javax.naming.NamingException;


@Stateless
public class StatelessSessionBean implements Serializable{
    AutoScenario autoScenario;
    
    public String getRequest() throws NamingException{
        AutoScenario autoScenario = new AutoScenario();
        return autoScenario.getRequest();
    } 
     
    public ResponseContents executaNovoCenario(String json) throws NamingException, IOException, URISyntaxException{
        AutoScenario autoScenario  = new AutoScenario();
        return autoScenario.execRequest(json);
    }
    
    
    /*-------------------------CRUD REQUEST REFERENCES------------------------*/
    public boolean criaNovoCenario(String json) throws NamingException, IOException, URISyntaxException{
        AutoScenario autoScenario  = new AutoScenario();
        return autoScenario.createRequest(json);
    }
    
    public boolean editaNovoCenario(String json) throws NamingException, IOException, URISyntaxException{
        AutoScenario autoScenario  = new AutoScenario();
        return autoScenario.updateRequest(json);
    }
    
    public boolean removeNovoCenario(String json) throws NamingException, IOException, URISyntaxException{
        AutoScenario autoScenario  = new AutoScenario();
        return autoScenario.removeRequest(json);
    }
    /*------------------------------------------------------------------------*/
}