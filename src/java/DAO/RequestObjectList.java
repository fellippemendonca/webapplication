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
    
    List<RequestReferenceObject> requestObjectList;
    DataAccessObject dao;
    
    
    public RequestObjectList() throws NamingException{
        this.requestObjectList = new ArrayList<>();
        this.dao = new DataAccessObject();
    }
    
    public RequestReferenceObject addRequest(String json){
        RequestReferenceObject rro = new RequestReferenceObject(this.dao,json);
        this.requestObjectList.add(rro);
        return rro;
    }

    /*RETORNA LISTA APENAS COM REQUESTS DA MEMORIA*/
    public List<RequestReferenceObject> getRequestObjectList() {
        return this.requestObjectList;
    }
    
    /*RETORNA LISTA APENAS COM REQUESTS DA BASE DE DADOS*/
    public List<RequestReferenceObject> getRequestObjectListFromDB() throws NamingException {
        List<RequestReferenceObject> requestObjectListDB = new ArrayList<>();
        for (RequestReference rr : this.dao.getRequestReferece()) {
            requestObjectListDB.add(new RequestReferenceObject(rr,this.dao));
        }
        return requestObjectListDB;
    }
    
    public String generateJsonRequestList() throws NamingException{
        List<JsonRequestObject> requestList = new ArrayList();
        for (RequestReferenceObject rro : getRequestObjectListFromDB()) {
            requestList.add(rro.getRequestObject());
        }
        return "{\"requestList\":"+(new Gson().toJson(requestList))+"}";
    }

}
