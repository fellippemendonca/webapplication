/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoScenarios.openApi.v1.sellerItems;

import DAO.HeaderReferenceObject;
import DAO.ParameterReferenceObject;
import DAO.RequestObjectList;
import DAO.RequestReferenceObject;
import DAO.TemplateReferenceObject;
import HttpConnections.ResponseContents;
import HttpConnections.RestRequester;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
/**
 *
 * @author fellippe.mendonca
 */
public class AutoScenario implements Serializable {

    List<RestRequester> restRequesterList;

    public AutoScenario() {
        this.restRequesterList = new ArrayList<>();

    }

    public List<RestRequester> getScenarioList() {
        return this.restRequesterList;
    }

    public int getScenarioListSize() {
        return this.restRequesterList.size();
    }

    public ResponseContents executeScenario(int index) throws IOException, URISyntaxException {
        return restRequesterList.get(index).Request();
    }

    
   
    
    public String executaNovoCenario() throws NamingException {
        RequestObjectList rob = new RequestObjectList();
        return rob.generateJsonRequestList();
    }
    
    public ResponseContents newScenarioExec(String json) throws NamingException {
            RequestObjectList rob = new RequestObjectList();
            RequestReferenceObject rro = rob.addRequest(json);
            RestRequester restRequester1 = new RestRequester();
            restRequester1.setMethod(rro.getMethod().getMethodValue());
            restRequester1.setScheme(rro.getScheme().getSchemeValue());
            restRequester1.setHost(rro.getHost().getHostAddressValue());
            restRequester1.setPath(rro.getPath().getPathValue());
            if (rro.getHeaderReferenceObjectList().isEmpty() == false) {
                for (HeaderReferenceObject hro : rro.getHeaderReferenceObjectList()) {
                    restRequester1.addHeader(hro.getHeader().getHeaderName(), hro.getHeader().getHeaderValue());
                }
            }
            if (rro.getTemplateReferenceObjectList().isEmpty() == false) {
                for (TemplateReferenceObject tro : rro.getTemplateReferenceObjectList()) {
                    restRequester1.addTemplate(tro.getTemplate().getTemplateValue());
                }
            }
            if (rro.getParameterReferenceObjectList().isEmpty() == false) {
                for (ParameterReferenceObject pro : rro.getParameterReferenceObjectList()) {
                    restRequester1.addParameter(pro.getParameter().getParameterName(), pro.getParameter().getParameterValue());
                }
            }
        try {
            return restRequester1.Request();
        } catch (IOException ex) {
            Logger.getLogger(AutoScenario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AutoScenario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String getRequest() throws NamingException {
        RequestObjectList rob = new RequestObjectList();
        return rob.generateJsonRequestList();
    }
    
    /*

    public boolean insereNovoCenario() {
        RequestObjectList rob;
        try {
            rob = new RequestObjectList();
            rob.createRequestReferenceObject();
            rob.getRequestReferenceObject().setEnvironment("PRD");
            rob.getRequestReferenceObject().setMethod("GET");
            rob.getRequestReferenceObject().setScheme("http");
            rob.getRequestReferenceObject().setHost("api.extra.com.br");
            rob.getRequestReferenceObject().setPath("api/v1");
            rob.getRequestReferenceObject().addTemplateReferenceObject(rob.createTemplateReferenceObject("orders/status/new", 1, 1));
            rob.getRequestReferenceObject().addParameterReferenceObject(rob.createParameterReferenceObject("_offset", "0", 1));
            rob.getRequestReferenceObject().addParameterReferenceObject(rob.createParameterReferenceObject("_limit", "1", 1));
            rob.getRequestReferenceObject().addHeaderReferenceObject(rob.createHeaderReferenceObject("nova-app-token", "abc"));
            rob.getRequestReferenceObject().addHeaderReferenceObject(rob.createHeaderReferenceObject("nova-auth-token", "CIT-3"));
            rob.getRequestReferenceObject().addHeaderReferenceObject(rob.createHeaderReferenceObject("Content-type", "application/json; charset=utf-8"));
            
            //System.out.println("TemplateList[0]: "+rob.getRequestReferenceObject().getTemplateReferenceObjectList().get(0).getTemplate().getTemplateValue());
            rob.getRequestReferenceObject().persistRequestReference();
            
            return true;
        } catch (NamingException ex) {
            Logger.getLogger(AutoScenario.class.getName()).log(Level.SEVERE, "RequestObjectList not initialized", ex);
            return false;
        }
    }*/
/*
    public boolean addRequest(String env, String shop) throws NamingException {
        DynamicMatrix DX = new DatabankConnector().executeQuery("AD" + env, "select * from ac_admin.ecma_sku_mp_related_sku_seller where store_qualifier_id = '" + shop + "' and rownum =1");
        String sku_id = DX.getValueByColumnName("sku_id");
        String sku_id_origin = DX.getValueByColumnName("sku_id_origin");

        RestRequester restRequester = new RestRequester();
        restRequester.setScheme("http");
        restRequester.setHost("api.extra.com.br");
        restRequester.addHeader("nova-app-token", "abc");
        restRequester.addHeader("nova-auth-token", "CIT-3");
        restRequester.addHeader("Content-type", "application/json; charset=utf-8");
        restRequester.setPath("api/v1");
        restRequester.addTemplate("sellerItems");
        restRequester.addTemplate(sku_id);
        restRequester.setMethod("GET");
        this.restRequesterList.add(restRequester);
        
         RestRequester restRequester2 = new RestRequester();
         restRequester2.setScheme("http");
         restRequester2.setHost("api.extra.com.br");
         restRequester2.addHeader("nova-app-token", "abc");
         restRequester2.addHeader("nova-auth-token", "CIT-3");
         restRequester2.addHeader("Content-type", "application/json; charset=utf-8");
         restRequester2.setPath("api/v1");
         restRequester2.addTemplate("sellerItems");
         restRequester2.addTemplate("skuOrigin");
         restRequester2.addTemplate(sku_id_origin);
         restRequester2.setMethod("GET");
         this.restRequesterList.add(restRequester2);
         

        RequestObjectList rob = new RequestObjectList();
        for (RequestReferenceObject rro : rob.getRequestObjectListFromDB()) {
            RestRequester restRequester1 = new RestRequester();
            restRequester1.setMethod(rro.getMethod().getMethodValue());
            restRequester1.setScheme(rro.getScheme().getSchemeValue());
            restRequester1.setHost(rro.getHost().getHostAddressValue());
            restRequester1.setPath(rro.getPath().getPathValue());
            if (rro.getHeaderReferenceObjectList().isEmpty() == false) {
                for (HeaderReferenceObject hro : rro.getHeaderReferenceObjectList()) {
                    restRequester1.addHeader(hro.getHeader().getHeaderName(), hro.getHeader().getHeaderValue());
                }
            }
            if (rro.getTemplateReferenceObjectList().isEmpty() == false) {
                for (TemplateReferenceObject tro : rro.getTemplateReferenceObjectList()) {
                    restRequester1.addTemplate(tro.getTemplate().getTemplateValue());
                }
            }
            if (rro.getParameterReferenceObjectList().isEmpty() == false) {
                for (ParameterReferenceObject pro : rro.getParameterReferenceObjectList()) {
                    restRequester1.addParameter(pro.getParameter().getParameterName(), pro.getParameter().getParameterValue());
                }
            }
            this.restRequesterList.add(restRequester1);
        }
        return true;
    }*/

}
