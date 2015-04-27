/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Entities.ReferenceEntities.RequestReference;
import JsonObjects.JsonRequestObject;
import JsonObjects.Tags.JsonTag;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author fellippe.mendonca
 */
public class RequestObjectList {
    DataAccessObject dao;
    RequestReferenceObject requestRefObj;
    
    public RequestObjectList() throws NamingException{
        this.dao = new DataAccessObject();
        this.requestRefObj = null;
    }
    
    
    
    public RequestReferenceObject setRequest(String json){
        this.requestRefObj = new RequestReferenceObject(this.dao,json);
        return this.requestRefObj;
    }
    
    
    /*--------------------------CRUD REQUESTS---------------------------------*/
    public boolean createRequest(String json){
        this.requestRefObj = new RequestReferenceObject(this.dao,json);
        return this.requestRefObj.persistRequestReference();
    }
    public boolean updateRequest(String json){
        this.requestRefObj = new RequestReferenceObject(this.dao,json);
        return this.requestRefObj.updateRequestReference();
    }
    public boolean deleteRequest(String json){
        this.requestRefObj = new RequestReferenceObject(this.dao,json);
        return this.requestRefObj.deleteRequestReference();
    }
    /*------------------------------------------------------------------------*/
    
    
    /*-----------RETORNA LISTA COM REQUESTS DA BASE DE DADOS------------------*/
    public String getJsonRequestList() throws NamingException{
        List<JsonRequestObject> requestList = new ArrayList();
        for (RequestReferenceObject rro : getObjectRequestList()) {
            requestList.add(rro.getRequestObject());
        }
        return "{\"requestList\":"+(new Gson().toJson(requestList))+"}";
    }
    
    public List<RequestReferenceObject> getObjectRequestList() throws NamingException {
        List<RequestReferenceObject> requestObjectListDB = new ArrayList<>();
        for (RequestReference rr : this.dao.getRequestReferece()) {
            requestObjectListDB.add(new RequestReferenceObject(this.dao,rr));
        }
        return requestObjectListDB;
    }
    /*------------------------------------------------------------------------*/
    
    
    /*-------------------METODOS DE LISTAGEM DE PARAMETROS--------------------*/
    //Methods
    public List<String> listMethodsFromDB(){
        return this.dao.getMethodJpaController().listMethodEntities();
    }
    
    //Environments
    public List<String> listEnvironmentsFromDB(){
        return this.dao.getEnvironmentJpaController().listEnvironmentEntities();
    }
    
    //Schemes
    public List<String> listSchemesFromDB(){
        return this.dao.getSchemeJpaController().listSchemeEntities();
    }
    
    //Hosts
    public List<String> listHostsFromDB(){
        return this.dao.getHostAddressJpaController().listHostAddressEntities();
    }
    
    //Paths
    public List<String> listPathsFromDB(){
        return this.dao.getPathJpaController().listPathEntities();
    }
    
    //Payloads
    public List<String> listPayloadsFromDB(){
        return this.dao.getPayloadJpaController().listPayloadEntities();
    }
    
    //Templates
    public List<String> listTemplatesFromDB(){
        return this.dao.getTemplateJpaController().listTemplateEntities();
    }
    
    //Parameters
    public List<String> listParameterNamesFromDB(){
        return this.dao.getParameterJpaController().listParameterNameEntities();
    }
    public List<String> listParameterValuesFromDB(){
        return this.dao.getParameterJpaController().listParameterValueEntities();
    }
    
    //Headers
    public List<String> listHeaderNamesFromDB(){
        return this.dao.getHeaderJpaController().listHeaderNameEntities();
    }
    public List<String> listHeaderValuesFromDB(){
        return this.dao.getHeaderJpaController().listHeaderValueEntities();
    }
    
    //Tags
    public List<JsonTag> listJsonTagValuesFromDB(){
        return this.dao.getRequestTagJpaController().listJsonTagEntities();
    }
    
    public List<String> listTagValuesFromDB(){
        return this.dao.getRequestTagJpaController().listRequestTagEntities();
    }
    /*------------------------------------------------------------------------*/
}
