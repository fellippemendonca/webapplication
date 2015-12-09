
package servlet.stateless;

import HttpConnections.ResponseContents;
import JsonObjects.Validation.JsonValidationScenario;
import autoScenarios.openApi.v1.sellerItems.AutoScenario;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.NamingException;


@Stateless
public class StatelessSessionBean implements Serializable{
    AutoScenario autoScenario;
    
    /*Retorna a lista de requests existentes no banco de dados*/
    public String getRequest(){
        this.autoScenario = new AutoScenario();
        try {
            return this.autoScenario.getRequest();
        } catch (NamingException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return null;
    } 
     
    /*Função responsável por Instanciar Popular e Executar o request fornecido pelo site*/
    public ResponseContents executaNovoCenario(String json){
        this.autoScenario  = new AutoScenario();
        try {
            return this.autoScenario.execRequest(json);
        } catch (NamingException | IOException | URISyntaxException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return null;
    }
    
    /*Função responsável por Instanciar Popular e Executar o request fornecido pelo site juntamente com sua validação*/
    public JsonValidationScenario executaNovaValidacao(String json) {
        this.autoScenario = new AutoScenario();
        try {
            return this.autoScenario.execValidation(json);
        } catch (NamingException | IOException | URISyntaxException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return null;
    }
    
    
    /* VVVVVV Funções para manutenção de dados na base VVVVVV */
    
    /*-------------------------CRUD REQUEST REFERENCES------------------------*/
    public boolean criaNovoCenario(String json){
        this.autoScenario  = new AutoScenario();
        try {
            return this.autoScenario.createRequest(json);
        } catch (NamingException | IOException | URISyntaxException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return false;
    }
    
    public boolean editaNovoCenario(String json) {
        this.autoScenario  = new AutoScenario();
        try {
            return this.autoScenario.updateRequest(json);
        } catch (NamingException | IOException | URISyntaxException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return false;
    }
    
    public boolean removeNovoCenario(String json) {
        this.autoScenario  = new AutoScenario();
        try {
            return autoScenario.removeRequest(json);
        } catch (NamingException | IOException | URISyntaxException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return false;
    }
    /*------------------------------------------------------------------------*/
    
    /*-------------------------CRUD VALIDATION SCENARIOS----------------------*/
    public String getRequestValidation(int requestID) {
        this.autoScenario = new AutoScenario();
        try {
            return autoScenario.getRequestValidation(requestID);
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return null;
    }
    
    public String getRequestValidationExecuted(int requestID) {
        this.autoScenario = new AutoScenario();
        try {
            return autoScenario.getRequestValidationExecuted(requestID);
        } catch (IOException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return null;
    }
    
    public boolean criaValidacao(String json) {
        this.autoScenario  = new AutoScenario();
        try {
            return autoScenario.createValidation(json);
        } catch (IOException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return false;
    }
    
    public boolean atualizaValidacao(String json) {
        this.autoScenario  = new AutoScenario();
        try {
            return autoScenario.updateValidation(json);
        } catch (IOException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return false;
    }
    
    public boolean removeValidacao(String json) {
        this.autoScenario  = new AutoScenario();
        try {
            return autoScenario.deleteValidation(json);
        } catch (IOException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return false;
    }
    
    /*-------------------------CRUD SCHEDULED VALIDATION----------------------*/
    public boolean createScheduledRequest(int id) {
        try {
            this.autoScenario  = new AutoScenario();
            return autoScenario.createScheduledRequest(id);
        } catch (IOException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return false;
    }
    
    public boolean removeScheduledRequest(int id) {
        this.autoScenario  = new AutoScenario();
        try {
            return autoScenario.removeScheduledRequest(id);
        } catch (IOException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return false;
    }
    
    public String getScheduledRequest() {
        autoScenario = new AutoScenario();
        try {
            return autoScenario.getScheduledRequest();
        } catch (NamingException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return null;
    } 
    
    public String getDailyRequestValidationLog(int idRequestReference, String executionDate) {
        this.autoScenario = new AutoScenario();
        try {
            return autoScenario.getDailyRequestValidationLog(idRequestReference, executionDate);
        } catch (NamingException | ParseException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.autoScenario = null;
        }
        return null;
    }
    
    public boolean executeScheduledValidations() {
        boolean success = false;
        try {
            this.autoScenario = new AutoScenario();
            success = autoScenario.executeScheduledValidations();
        } catch (URISyntaxException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            autoScenario = null;
        }
        return success;
    }
    
    public boolean createQueryReport(String json) throws NamingException{
        this.autoScenario = new AutoScenario();
        return this.autoScenario.createQueryReport(json);
    }
    
    public boolean updateQueryReport(String json) throws NamingException{
        this.autoScenario = new AutoScenario();
        return this.autoScenario.updateQueryReport(json);
    }
    
    public boolean removeQueryReport(String json) throws NamingException{
        this.autoScenario = new AutoScenario();
        return this.autoScenario.removeQueryReport(json);
    }
    
    public String getJsonQueryReportList(){
        this.autoScenario = new AutoScenario();
        try {
            return this.autoScenario.getJsonQueryReportList();
        } catch (NamingException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            this.autoScenario = null;
        }
        return null;
    }
    
    public String getJsonQueryReportChart(int queryId, String since){
        this.autoScenario = new AutoScenario();
        try {
            return this.autoScenario.getJsonQueryReportChart(queryId,since);
        } catch (NamingException ex) {
            Logger.getLogger(StatelessSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            this.autoScenario = null;
        }
        return null;
    }
}