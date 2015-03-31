
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
        autoScenario.fillRequestListFromDB();
        return autoScenario.getRequest();
    } 
     
    public ResponseContents executaNovoCenario(String json) throws NamingException, IOException, URISyntaxException{
        AutoScenario autoScenario  = new AutoScenario();
        return autoScenario.execRequest(json);
    }
    
    public boolean criaNovoCenario(String json) throws NamingException, IOException, URISyntaxException{
        AutoScenario autoScenario  = new AutoScenario();
        return autoScenario.createRequest(json);
    }
    
    public boolean editaNovoCenario(String json) throws NamingException, IOException, URISyntaxException{
        AutoScenario autoScenario  = new AutoScenario();
        return autoScenario.updateRequest(json);
    }
     
    
    /*
    public int countScenario() throws NamingException{
        AutoScenario autoScenario  = new AutoScenario();
        autoScenario.fillRequestListFromDB();
        return autoScenario.getScenarioListSize();
    }
    
    public ResponseContents executeScenario(int index) throws IOException, URISyntaxException, NamingException{
        this.autoScenario  = new AutoScenario();
        this.autoScenario.fillRequestListFromDB();
        return this.autoScenario.executeScenario(index);
    }
    */
    
    
    
   
    
    
    
    
    
}
