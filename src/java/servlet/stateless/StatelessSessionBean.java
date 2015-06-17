
package servlet.stateless;

import HttpConnections.ResponseContents;
import JsonObjects.Validation.JsonValidationScenario;
import autoScenarios.openApi.v1.sellerItems.AutoScenario;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.ParseException;
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
    
    public JsonValidationScenario executaNovaValidacao(String json) throws NamingException, IOException, URISyntaxException{
        AutoScenario autoScenario  = new AutoScenario();
        return autoScenario.execValidation(json);
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
    
    /*-------------------------CRUD VALIDATION SCENARIOS----------------------*/
    public String getRequestValidation(int requestID) throws NamingException, URISyntaxException, Exception{
        AutoScenario autoScenario = new AutoScenario();
        return autoScenario.getRequestValidation(requestID);
    }
    
    public String getRequestValidationExecuted(int requestID) throws NamingException, URISyntaxException, Exception{
        AutoScenario autoScenario = new AutoScenario();
        return autoScenario.getRequestValidationExecuted(requestID);
    }
    
    public boolean criaValidacao(String json) throws NamingException, IOException, URISyntaxException, Exception{
        AutoScenario autoScenario  = new AutoScenario();
        return autoScenario.createValidation(json);
    }
    
    public boolean atualizaValidacao(String json) throws NamingException, IOException, URISyntaxException, Exception{
        AutoScenario autoScenario  = new AutoScenario();
        return autoScenario.updateValidation(json);
    }
    
    public boolean removeValidacao(String json) throws NamingException, IOException, URISyntaxException, Exception{
        AutoScenario autoScenario  = new AutoScenario();
        return autoScenario.deleteValidation(json);
    }
    
    /*-------------------------CRUD SCHEDULED VALIDATION----------------------*/
    public boolean createScheduledRequest(int id) throws NamingException, IOException, URISyntaxException, Exception{
        AutoScenario autoScenario  = new AutoScenario();
        return autoScenario.createScheduledRequest(id);
    }
    
    public boolean removeScheduledRequest(int id) throws NamingException, IOException, URISyntaxException, Exception{
        AutoScenario autoScenario  = new AutoScenario();
        return autoScenario.removeScheduledRequest(id);
    }
    
    public String getScheduledRequest() throws NamingException{
        AutoScenario autoScenario = new AutoScenario();
        return autoScenario.getScheduledRequest();
    } 
    
    public String getDailyRequestValidationLog(int idRequestReference, String executionDate) throws NamingException, ParseException{
        AutoScenario autoScenario = new AutoScenario();
        return autoScenario.getDailyRequestValidationLog(idRequestReference, executionDate);
    }
    
}