/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SoapConnections;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fellippe.mendonca
 */
public class SoapPost {
    String uri;
    String xmlRequest;

    public SoapPost() {
        this.uri = "";
        this.xmlRequest = "";
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getXmlRequest() {
        return xmlRequest;
    }

    public void setXmlRequest(String xmlRequest) {
        this.xmlRequest = xmlRequest;
    }
    
    public URL getConvertedURI() {
        //new URL();
        try {
            URL endpoint =
                    new URL(new URL(this.uri),
                            "/path/to/webservice",
                            new URLStreamHandler() {
                                @Override
                                protected URLConnection openConnection(URL url) throws IOException {
                                    URL target = new URL(url.toString());
                                    URLConnection connection = target.openConnection();
                                    // Connection settings
                                    connection.setConnectTimeout(10000); // 10 sec
                                    connection.setReadTimeout(60000); // 1 min
                                    return(connection);
                                }
                            });
            return endpoint;
        } catch (MalformedURLException ex) {
            Logger.getLogger(SoapPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
