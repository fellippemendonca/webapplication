/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SoapConnections;

import java.net.URL;

/**
 *
 * @author fellippe.mendonca
 */
public class SoapPost {
    URL uri;
    String xmlRequest;

    public SoapPost() {
        this.uri = null;
        this.xmlRequest = "";
    }

    public URL getUri() {
        return uri;
    }

    public void setUri(URL uri) {
        this.uri = uri;
    }

    public String getXmlRequest() {
        return xmlRequest;
    }

    public void setXmlRequest(String xmlRequest) {
        this.xmlRequest = xmlRequest;
    }
}
