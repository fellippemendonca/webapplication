/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonObjects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonRequestObject {
    String environment;
    String method;
    String scheme;
    String host;
    String path;
    List<String> templates;
    List<JHeader> headers;
    List<JParameter> parameters;

    
    public JsonRequestObject(){
        this.environment = "";
        this.method = "";
        this.scheme = "";
        this.host = "";
        this.path = "";
        this.templates = new ArrayList();
        this.headers = new ArrayList();
        this.parameters = new ArrayList();
    }
    
    public JsonRequestObject(String environment, String method, String scheme, String host, String path, List<String> templates, List<JHeader> headers, List<JParameter> parameters) {
        this.environment = environment;
        this.method = method;
        this.scheme = scheme;
        this.host = host;
        this.path = path;
        this.templates = templates;
        this.headers = headers;
        this.parameters = parameters;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getTemplates() {
        return templates;
    }

    public void setTemplates(List<String> templates) {
        this.templates = templates;
    }

    public List<JHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<JHeader> headers) {
        this.headers = headers;
    }

    public List<JParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<JParameter> parameters) {
        this.parameters = parameters;
    }
    
}
