/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonObjects;

import JsonObjects.Tags.JsonTag;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonRequestObject {
    int dbId;
    String requestName;
    String environment;
    String method;
    String scheme;
    String host;
    String path;
    String payload;
    List<String> templates;
    List<JHeader> headers;
    List<JParameter> parameters;
    List<JsonTag> jsonTags;
    
    public JsonRequestObject(){
        this.dbId = 0;
        this.requestName = "";
        this.environment = "";
        this.method = "";
        this.scheme = "";
        this.host = "";
        this.path = "";
        this.payload = "";
        this.templates = new ArrayList();
        this.headers = new ArrayList();
        this.parameters = new ArrayList();
        this.jsonTags = new ArrayList();
    }
    
    public JsonRequestObject(int dbId, String requestName, String environment, String method, String scheme, String host, String path, String payload, List<String> templates, List<JHeader> headers, List<JParameter> parameters, List<JsonTag> jsonTags) {
        this.dbId = dbId;
        this.requestName = requestName;
        this.environment = environment;
        this.method = method;
        this.scheme = scheme;
        this.host = host;
        this.path = path;
        this.payload = payload;
        this.templates = templates;
        this.headers = headers;
        this.parameters = parameters;
        this.jsonTags = jsonTags;
    }

    public int getRequestReference() {
        return this.dbId;
    }

    public void setRequestReference(int dbId) {
        this.dbId = dbId;
    }
    
    public String getRequestName() {
        return this.requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }
    
    public String getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public String getPayload() {
        return this.payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public List<String> getTemplates() {
        return this.templates;
    }

    public void setTemplates(List<String> templates) {
        this.templates = templates;
    }

    public List<JHeader> getHeaders() {
        return this.headers;
    }

    public void setHeaders(List<JHeader> headers) {
        this.headers = headers;
    }

    public List<JParameter> getParameters() {
        return this.parameters;
    }

    public void setParameters(List<JParameter> parameters) {
        this.parameters = parameters;
    }

    public List<JsonTag> getJsonTags() {
        return jsonTags;
    }

    public void setJsonTags(List<JsonTag> jsonTags) {
        this.jsonTags = jsonTags;
    }
    
}
