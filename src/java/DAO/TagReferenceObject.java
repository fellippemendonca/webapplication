/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Entities.ReferenceEntities.RequestReference;
import Entities.ReferenceEntities.RequestTagReference;
import Entities.RequestTag;
import JsonObjects.Tags.JsonTag;
import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import jpa.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class TagReferenceObject {
    RequestTag requestTag;
    RequestTagReference requestTagReference;
    DataAccessObject dao;
    JsonTag jsonTag;

    
    public TagReferenceObject(){
        this.requestTag = new RequestTag(0, "");
    }
    
    public TagReferenceObject(DataAccessObject dao, String name) {
        this.dao = dao;
        this.requestTag = new RequestTag(0, name);
        this.jsonTag = new JsonTag(0,name);
        this.requestTagReference = null;
    }
    
    public TagReferenceObject(RequestTagReference requestTagReference, DataAccessObject dao) {
        this.requestTagReference = requestTagReference;
        this.dao = dao;
        this.requestTag = dao.getRequestTagJpaController().findRequestTag(this.requestTagReference.getIdRequestTag());
        this.jsonTag = new JsonTag(this.requestTag.getIdRequestTag(), this.requestTag.getTagValue());
    }

    public void persistRequestTag() {
        try {
            this.requestTag = this.dao.getRequestTagJpaController().findOrAdd(this.requestTag);
        } catch (Exception ex) {
            Logger.getLogger(HeaderReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean persistTagReference(RequestReference requestReference) {
        persistRequestTag();
        RequestTagReference tmp = new RequestTagReference(0, requestReference.getIdRequestReference(), this.requestTag.getIdRequestTag());
        try {
            this.requestTagReference = this.dao.getRequestTagReferenceJpaController().create(tmp);
            return true;
        } catch (NamingException ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(HeaderReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HeaderReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteRequestTagReference(){
        try {
             this.dao.getRequestTagReferenceJpaController().destroy(this.requestTagReference.getIdrequestTagReference());
            return true;
        } catch (NamingException ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(HeaderReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HeaderReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public JsonTag getJsonTag() {
        return this.jsonTag;
    }
    
    public void setRequestTag(String tagValue) {
        this.requestTag = new RequestTag(0, tagValue);
    }
    public RequestTag getRequestTag() {
        return requestTag;
    }
    public RequestTagReference getRequestTagReference() {
        return requestTagReference;
    }
    public void setRequestTagReference(RequestTagReference requestTagReference) {
        this.requestTagReference = requestTagReference;
    }
    
}
