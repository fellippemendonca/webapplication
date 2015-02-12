/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoScenarios.openApi.v1.sellerItems;

import DAO.DataAccessObject;
import DAO.HeaderReferenceObject;
import DAO.ParameterReferenceObject;
import DAO.RequestObjectList;
import DAO.RequestReferenceObject;
import DAO.TemplateReferenceObject;
import Databank_Engines.DatabankConnector;
import Databank_Engines.Matrix.DynamicMatrix;
import Entities.Parameter;
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
    //DataAccessObject dao;  //DataAccessObject dao = new DataAccessObject();

    public AutoScenario() {
        this.restRequesterList = new ArrayList<>();
        //this.dao = new DataAccessObject();
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

    public int parameterInsert(String name, String value, int fix) throws NamingException {
        DataAccessObject dao = new DataAccessObject();
        Parameter parameter = new Parameter(0, name, value, fix);
        try {
            dao.getParameterJpaController().create(parameter);
        } catch (Exception ex) {
            Logger.getLogger(AutoScenario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parameter.getIdParameter();
    }

    public String getRequest() throws NamingException {
        String MassiveRequest = "";

        RequestObjectList rob = new RequestObjectList();

        for (RequestReferenceObject rro : rob.getRequestObjectListFromDB()) {
            MassiveRequest += "<br>Environment: " + rro.getEnvironment().getEnvironmentName();
            MassiveRequest += "<br>Method: " + rro.getMethod().getMethodValue();
            MassiveRequest += "<br>Scheme: " + rro.getScheme().getSchemeValue();
            MassiveRequest += "<br>HostAddress: " + rro.getHost().getHostAddressValue();
            MassiveRequest += "<br>Path: " + rro.getPath().getPathValue();

            for (HeaderReferenceObject hro : rro.getHeaderReferenceObjectList()) {
                MassiveRequest += "<br>Header name: " + hro.getHeader().getHeaderName() + ", Header value: " + hro.getHeader().getHeaderValue();
            }
            for (TemplateReferenceObject tro : rro.getTemplateReferenceObjectList()) {
                MassiveRequest += "<br>Template: " + tro.getTemplate().getTemplateValue();
            }
            for (ParameterReferenceObject pro : rro.getParameterReferenceObjectList()) {
                MassiveRequest += "<br>Parameter name: " + pro.getParameter().getParameterName() + ", Parameter value: " + pro.getParameter().getParameterValue();
            }
        }
        return MassiveRequest;
    }

    public boolean insereNovoCenario() {
        RequestObjectList rob = null;
        try {

            rob = new RequestObjectList();
            rob.createRequestReferenceObject();
            rob.getRequestReferenceObject().setEnvironment("HLG");
            rob.getRequestReferenceObject().setMethod("GET");
            rob.getRequestReferenceObject().setScheme("http");
            rob.getRequestReferenceObject().setHost("adserv.mp.hlg.dc.nova");
            rob.getRequestReferenceObject().setPath("AdminMpServicesWeb/resources/orders");
            rob.getRequestReferenceObject().addTemplateReferenceObject(rob.createTemplateReferenceObject("v3", 1, 1));
            rob.getRequestReferenceObject().addParameterReferenceObject(rob.createParameterReferenceObject("startRow", "0", 1));
            rob.getRequestReferenceObject().addParameterReferenceObject(rob.createParameterReferenceObject("pageRows", "10", 1));
            rob.getRequestReferenceObject().addParameterReferenceObject(rob.createParameterReferenceObject("storeQualifierId", "4", 1));
            rob.getRequestReferenceObject().addParameterReferenceObject(rob.createParameterReferenceObject("orderId", "9999859901", 1));

            //rob.getRequestReferenceObject().addHeaderReferenceObject(rob.createHeaderReferenceObject("TesteHeader", "TesteHeader"));
            rob.getRequestReferenceObject().persistRequestReference();

            /*System.out.println(
             "\n "+ rob.getRequestReferenceObject().getEnvironment().getEnvironmentName()
             + "\n "+ rob.getRequestReferenceObject().getMethod().getMethodValue()
             + "\n "+ rob.getRequestReferenceObject().getScheme().getSchemeValue()
             + "\n "+ rob.getRequestReferenceObject().getHost().getHostAddressValue()
             + "\n "+ rob.getRequestReferenceObject().getPath().getPathValue()
             + "\n "+ rob.getRequestReferenceObject().getTemplateReferenceObjectList().get(0).getTemplate().getTemplateValue()
             + "\n "+ rob.getRequestReferenceObject().getParameterReferenceObjectList().get(0).getParameter().getParameterName()
             + "\n "+ rob.getRequestReferenceObject().getParameterReferenceObjectList().get(1).getParameter().getParameterName()
             + "\n "+ rob.getRequestReferenceObject().getParameterReferenceObjectList().get(2).getParameter().getParameterName()
             );*/
            return true;
        } catch (NamingException ex) {
            Logger.getLogger(AutoScenario.class.getName()).log(Level.SEVERE, "RequestObjectList not initialized", ex);
            return false;
        }

    }

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
        /*
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

         RestRequester restRequester3 = new RestRequester();
         restRequester3.setScheme("http");
         restRequester3.setHost("api.extra.com.br");
         restRequester3.addHeader("nova-app-token", "abc");
         restRequester3.addHeader("nova-auth-token", "CIT-3");
         restRequester3.addHeader("Content-type", "application/json; charset=utf-8");
         restRequester3.setPath("api/v1");
         restRequester3.addTemplate("orders");
         restRequester3.addTemplate("9999631401");
         restRequester3.setMethod("GET");
         this.restRequesterList.add(restRequester3);

         RestRequester restRequester7 = new RestRequester();
         restRequester7.setScheme("http");
         restRequester7.setHost("api.extra.com.br");
         restRequester7.addHeader("nova-app-token", "abc");
         restRequester7.addHeader("nova-auth-token", "CIT-3");
         restRequester7.addHeader("Content-type", "application/json; charset=utf-8");
         restRequester7.setPath("api/v1");
         restRequester7.addTemplate("orders/status/new");
         restRequester7.addParameter("_limit", "1");
         restRequester7.addParameter("_offset", "0");
         restRequester7.setMethod("GET");
         this.restRequesterList.add(restRequester7);

         RestRequester restRequester8 = new RestRequester();
         restRequester8.setScheme("http");
         restRequester8.setHost("api.extra.com.br");
         restRequester8.addHeader("nova-app-token", "abc");
         restRequester8.addHeader("nova-auth-token", "CIT-3");
         restRequester8.addHeader("Content-type", "application/json; charset=utf-8");
         restRequester8.setPath("api/v1");
         restRequester8.addTemplate("orders/status/approved");
         restRequester8.addParameter("_limit", "1");
         restRequester8.addParameter("_offset", "0");
         restRequester8.setMethod("GET");
         this.restRequesterList.add(restRequester8);

         RestRequester restRequester9 = new RestRequester();
         restRequester9.setScheme("http");
         restRequester9.setHost("api.extra.com.br");
         restRequester9.addHeader("nova-app-token", "abc");
         restRequester9.addHeader("nova-auth-token", "CIT-3");
         restRequester9.addHeader("Content-type", "application/json; charset=utf-8");
         restRequester9.setPath("api/v1");
         restRequester9.addTemplate("orders/status/sent");
         restRequester9.addParameter("_limit", "1");
         restRequester9.addParameter("_offset", "0");
         restRequester9.setMethod("GET");
         this.restRequesterList.add(restRequester9);

         RestRequester restRequester10 = new RestRequester();
         restRequester10.setMethod("GET");
         restRequester10.setScheme("http");
         restRequester10.setHost("api.extra.com.br");
         restRequester10.setPath("api/v1");
         restRequester10.addTemplate("orders/status/delivered");
         restRequester10.addHeader("nova-app-token", "abc");
         restRequester10.addHeader("nova-auth-token", "CIT-3");
         restRequester10.addHeader("Content-type", "application/json; charset=utf-8");
         restRequester10.addParameter("_offset", "0");
         restRequester10.addParameter("_limit", "1");

         this.restRequesterList.add(restRequester10);
         */

        RequestObjectList rob = new RequestObjectList();
        for (RequestReferenceObject rro : rob.getRequestObjectListFromDB()) {
            RestRequester restRequester1 = new RestRequester();
            //MassiveRequest += "<br>Environment: " + rro.getEnvironment().getEnvironmentName();
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
    }

}
