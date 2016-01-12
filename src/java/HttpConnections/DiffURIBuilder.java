/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HttpConnections;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

/**
 *
 * @author fellippe.mendonca
 */
public class DiffURIBuilder extends URIBuilder {

    String template;
    String entity;

    public DiffURIBuilder() {
        super();
        this.template = "";
        this.entity = null;
    }

    @Override
    public URIBuilder setPath(String path) {
        return super.setPath("/" + path);
    }

    public boolean addTemplate(String template) {
        this.template = this.template + "/" + template;
        return true;
    }

    public String getTemplate() {
        return this.template;
    }

    public boolean setEntity(String ent) {
        this.entity = ent; //NEW 
        return true;
    }

    public HttpEntity getEntity() {
        if (this.entity == null) {
            return new StringEntity("", "UTF-8");
        } else {
            return new StringEntity(this.entity, "UTF-8");
        }
    }

    public URIBuilder getFinalURI() {
        return super.setPath(super.getPath() + this.template);
    }

    public String getStringEntity() {
        return this.entity;
    }

    public URL getFinalURIString() {
        URL endpoint = null;
        try {
            endpoint = new URL(new URL(getFinalURI().build().toString()), "", new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL url) throws IOException {
                    URL target = new URL(url.toString());
                    URLConnection connection = target.openConnection();
                    // Connection settings
                    connection.setConnectTimeout(30000); // 20 sec
                    connection.setReadTimeout(60000); // 30 sec
                    return (connection);
                }
            });
        } catch (MalformedURLException ex) {
            Logger.getLogger(DiffURIBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(DiffURIBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Soap URL:"+endpoint);
        return endpoint;
    }

}
