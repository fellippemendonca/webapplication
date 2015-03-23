/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Entities.ReferenceEntities.RequestReference;
import JsonObjects.JsonRequestObject;
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
    
    /*RETORNA LISTA COM REQUESTS DA BASE DE DADOS*/
    public List<RequestReferenceObject> getObjectRequestList() throws NamingException {
        List<RequestReferenceObject> requestObjectListDB = new ArrayList<>();
        for (RequestReference rr : this.dao.getRequestReferece()) {
            requestObjectListDB.add(new RequestReferenceObject(rr,this.dao));
        }
        return requestObjectListDB;
    }
    
    public String getJsonRequestList() throws NamingException{
        List<JsonRequestObject> requestList = new ArrayList();
        for (RequestReferenceObject rro : getObjectRequestList()) {
            requestList.add(rro.getRequestObject());
        }
        return "{\"requestList\":"+(new Gson().toJson(requestList))+"}";
    }
    
    
    /*METODOS DE LISTAGEM DE PARAMETROS*/
    public List<String> listMethodsFromDB(){
        return this.dao.getMethodJpaController().listMethodEntities();
    }
    public List<String> listEnvironmentsFromDB(){
        return this.dao.getEnvironmentJpaController().listEnvironmentEntities();
    }
    public List<String> listSchemesFromDB(){
        return this.dao.getSchemeJpaController().listSchemeEntities();
    }
    public List<String> listHostsFromDB(){
        return this.dao.getHostAddressJpaController().listHostAddressEntities();
    }
    public List<String> listPathsFromDB(){
        return this.dao.getPathJpaController().listPathEntities();
    }
    public List<String> listTemplatesFromDB(){
        return this.dao.getTemplateJpaController().listTemplateEntities();
    }
    
    public List<String> listParameterNamesFromDB(){
        return this.dao.getParameterJpaController().listParameterNameEntities();
    }
    public List<String> listParameterValuesFromDB(){
        return this.dao.getParameterJpaController().listParameterValueEntities();
    }
    
    public List<String> listHeaderNamesFromDB(){
        return this.dao.getHeaderJpaController().listHeaderNameEntities();
    }
    public List<String> listHeaderValuesFromDB(){
        return this.dao.getHeaderJpaController().listHeaderValueEntities();
    }
}
