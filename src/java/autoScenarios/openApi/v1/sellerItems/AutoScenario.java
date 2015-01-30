/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoScenarios.openApi.v1.sellerItems;

import DAO.DataAccessObject;
import Databank_Engines.DatabankConnector;
import Databank_Engines.Matrix.DynamicMatrix;
import Entities.Store;
import HttpConnections.ResponseContents;
import HttpConnections.RestRequester;
import Projects.Rest.RestRequests;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import jpa.StoreJpaController;

/**
 *
 * @author fellippe.mendonca
 */
public class AutoScenario {
    
    @PersistenceContext protected EntityManager   em;
    @Resource           private   UserTransaction utx;    

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

    public ResponseContents searchSkuV1(String env, String shop) throws IOException, URISyntaxException {
        DynamicMatrix DX = new DatabankConnector().executeQuery("AD" + env, "select * from ac_admin.ecma_sku_mp_related_sku_seller where store_qualifier_id = '" + shop + "' and rownum =1");
        String sku_id = DX.getValueByColumnName("sku_id");
        String sku_id_origin = DX.getValueByColumnName("sku_id_origin");
        ResponseContents RC1 = new RestRequests().openApi(env, shop).v1().sellerItems().getSku(sku_id);
        return RC1;
    }
    
    public String jpaController (){
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("ServletStatelessPU");
        StoreJpaController jpa = new StoreJpaController(utx,emf);
        return jpa.findStore(1).getStoreName();
    }
    
    public String findStore (int i){
        DataAccessObject dao = new DataAccessObject();
        return dao.getStore(i);
    }
    
    public List<Store> findStoreByID (String i){
        DataAccessObject dao = new DataAccessObject();
        return dao.findStoreByID(i);
    }
    
    

    public boolean addRequest(String env, String shop) {
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
        //restRequester.addParameter("1");
        //restRequester.Request("GET");
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

        RestRequester restRequester4 = new RestRequester();
        restRequester4.setScheme("http");
        restRequester4.setHost("api.extra.com.br");
        restRequester4.addHeader("nova-app-token", "abc");
        restRequester4.addHeader("nova-auth-token", "CIT-3");
        restRequester4.addHeader("Content-type", "application/json; charset=utf-8");
        restRequester4.setPath("api/v1");
        restRequester4.addTemplate("sellerItems");
        restRequester4.addTemplate(sku_id);
        restRequester4.setMethod("GET");
        this.restRequesterList.add(restRequester4);

        RestRequester restRequester5 = new RestRequester();
        restRequester5.setScheme("http");
        restRequester5.setHost("api.extra.com.br");
        restRequester5.addHeader("nova-app-token", "abc");
        restRequester5.addHeader("nova-auth-token", "CIT-3");
        restRequester5.addHeader("Content-type", "application/json; charset=utf-8");
        restRequester5.setPath("api/v1");
        restRequester5.addTemplate("sellerItems");
        restRequester5.addTemplate("skuOrigin");
        restRequester5.addTemplate(sku_id_origin);
        restRequester5.setMethod("GET");
        this.restRequesterList.add(restRequester5);

        RestRequester restRequester6 = new RestRequester();
        restRequester6.setScheme("http");
        restRequester6.setHost("api.extra.com.br");
        restRequester6.addHeader("nova-app-token", "abc");
        restRequester6.addHeader("nova-auth-token", "CIT-3");
        restRequester6.addHeader("Content-type", "application/json; charset=utf-8");
        restRequester6.setPath("api/v1");
        restRequester6.addTemplate("orders");
        restRequester6.addTemplate("9999631401");
        restRequester6.setMethod("GET");
        this.restRequesterList.add(restRequester6);

        return true;
    }

}
