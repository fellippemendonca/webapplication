/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SoapConnections;

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
    
}
