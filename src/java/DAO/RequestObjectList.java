/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Entities.ReferenceEntities.RequestReference;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author fellippe.mendonca
 */
public class RequestObjectList {
    
    List<RequestReferenceObject> requestObjectList;
    RequestReferenceObject requestReferenceObject;
    DataAccessObject dao;
    
    
    public RequestObjectList() throws NamingException{
        this.requestObjectList = new ArrayList<>();
        this.dao = new DataAccessObject();
    }

    public List<RequestReferenceObject> getRequestObjectList() throws NamingException {
        for (RequestReference rr : this.dao.getRequestReferece()) {
            this.requestObjectList.add(new RequestReferenceObject(rr,this.dao));
        }
        return this.requestObjectList;
    }
    
    public void setRequestObjectList(List<RequestReferenceObject> requestObjectList) {
        this.requestObjectList = requestObjectList;
    }
    
    public List<RequestReferenceObject> getRequestObjectListFromDB() throws NamingException {
        List<RequestReferenceObject> requestObjectListDB = new ArrayList<>();
        for (RequestReference rr : this.dao.getRequestReferece()) {
            requestObjectListDB.add(new RequestReferenceObject(rr,this.dao));
        }
        return requestObjectListDB;
    }

    public RequestReferenceObject getRequestReferenceObject() {
        return requestReferenceObject;
    }
    
    
    public RequestReferenceObject createRequestReferenceObject(){
        this.requestReferenceObject = new RequestReferenceObject(this.dao);
        return requestReferenceObject;
    }
    
    
    public TemplateReferenceObject createTemplateReferenceObject(String name, int seq,int fix){
        TemplateReferenceObject templateReferenceObject = new TemplateReferenceObject(this.dao, name, seq, fix);
        return templateReferenceObject;
    }
    
    public HeaderReferenceObject createHeaderReferenceObject(String name, String value){
        HeaderReferenceObject headerReferenceObject = new HeaderReferenceObject(this.dao, name, value);
        return headerReferenceObject;
    }
    
    public ParameterReferenceObject createParameterReferenceObject(String name, String value, int fix){
        ParameterReferenceObject parameterReferenceObject = new ParameterReferenceObject(this.dao, name, value, fix);
        return parameterReferenceObject;
    }

}
