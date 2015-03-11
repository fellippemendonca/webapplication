/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Entities.Header;
import Entities.ReferenceEntities.HeaderReference;
import Entities.ReferenceEntities.RequestReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import jpa.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class HeaderReferenceObject {
    HeaderReference headerReference;
    Header header;
    DataAccessObject dao;
    
    public HeaderReferenceObject(DataAccessObject dao, String name, String value){
        this.header = new Header(0, name, value);
        this.headerReference = null;
        this.dao = dao;
    }
    
    public HeaderReferenceObject(HeaderReference headerReference, DataAccessObject dao){
        this.headerReference = headerReference;
        this.dao = dao;
        this.header = this.dao.getHeaderJpaController().findHeader(this.headerReference.getIdHeader());
    }

    public HeaderReference getHeaderReference() {
        return headerReference;
    }

    public void setHeaderReference(HeaderReference headerReference) {
        this.headerReference = headerReference;
    }
    
    public boolean persistHeaderReference(RequestReference requestReference) {
        persistHeader();
        HeaderReference tmp = new HeaderReference(0, requestReference.getIdRequestReference(), this.header.getIdHeader());
        try {
            this.headerReference = dao.getHeaderReferenceJpaController().create(tmp);
            return true;
        } catch (NamingException ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (RollbackFailureException ex) {
            Logger.getLogger(HeaderReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(HeaderReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Header getHeader() {
        return this.header;
    }
    
    public void setHeader(String name, String value) {
        this.header = new Header(0, name, value);
    }

    public void persistHeader() {
        try {
            this.header = this.dao.getHeaderJpaController().findOrAdd(this.header);
        } catch (Exception ex) {
            Logger.getLogger(HeaderReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DataAccessObject getDao() {
        return dao;
    }

    public void setDao(DataAccessObject dao) {
        this.dao = dao;
    }
    
    
}
