/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package HttpConnections;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

/**
 *
 * @author fellippe.mendonca
 */
public class DiffURIBuilder extends URIBuilder{
    
    String template;
    HttpEntity entity;
    
    public DiffURIBuilder() {
       super();
       this.template = "";
       this.entity = null;
    }

    @Override
    public URIBuilder setPath(String path){
        return super.setPath("/"+path);
    }
    
    public boolean addTemplate(String template){
        this.template = this.template+"/"+template;
        return true;
    }
    public String getTemplate(){
        return this.template;
    }
    
    public boolean setEntity(String ent) {
        try {
            this.entity = new StringEntity(ent);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DiffURIBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
            return true;
    }
    
    public HttpEntity getEntity(){
        return this.entity;
    }

    public URIBuilder getFinalURI(){
        return super.setPath(super.getPath()+this.template);
    }

}
