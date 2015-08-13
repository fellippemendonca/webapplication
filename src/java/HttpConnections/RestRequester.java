/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HttpConnections;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

/**
 *
 * @author fellippe.mendonca
 */
public class RestRequester {

    DiffURIBuilder uri;
    ResponseContents RC;
    HttpGet httpget;
    HttpPut httpput;
    HttpPost httppost;
    String method;

    public RestRequester() {
        this.uri = new DiffURIBuilder();
        this.RC = new ResponseContents();
        this.httpget = new HttpGet();
        this.httpput = new HttpPut();
        this.httppost = new HttpPost();
        this.method = "";
    }

    public String getUriString() {
        return this.uri.getFinalURI().toString();
    }

    public ResponseContents Request() throws IOException, URISyntaxException {
        RestConnFactory connFactory = new RestConnFactory();
        Date startDate = new Date();
        switch (this.method.toUpperCase()) {
            case "GET":
                this.httpget.setURI(this.uri.getFinalURI().build());
                this.RC = connFactory.RestRequest(this.httpget);
                this.RC.setEndDate();
                this.RC.setRequest("GET : " + this.uri.getScheme() + "://" + this.uri.getHost() + this.uri.getPath());
                this.RC.getDiffMilliseconds(startDate);
                break;

            case "PUT":
                this.httpput.setURI(this.uri.getFinalURI().build());
                this.httpput.setEntity(uri.getEntity());
                //System.out.println("Entity Encoding: "+this.httpput.getEntity().getContentEncoding());
                //System.out.println("Entity Contents: "+this.httpput.getEntity().getContentType());
                this.RC = connFactory.RestRequest(this.httpput);
                this.RC.setEndDate();
                this.RC.setRequest("PUT : " + this.uri.getScheme() + "://" + this.uri.getHost() + this.uri.getPath());
                this.RC.getDiffMilliseconds(startDate);
                break;

            case "POST":
                this.httppost.setURI(this.uri.getFinalURI().build());
                this.httppost.setEntity(uri.getEntity());
                //System.out.println("Entity Encoding: "+this.httppost.getEntity().getContentEncoding());
                //System.out.println("Entity Contents: "+this.httppost.getEntity().getContentType());
                this.RC = connFactory.RestRequest(this.httppost);
                this.RC.setEndDate();
                this.RC.setRequest("POST : " + this.uri.getScheme() + "://" + this.uri.getHost() + this.uri.getPath());
                this.RC.getDiffMilliseconds(startDate);
                break;
        }
        return this.RC;
    }

    public ResponseContents getResponse() {
        return this.RC;
    }

    public boolean addHeader(String name, String value) {
        this.httpget.addHeader(name, value);
        this.httpput.addHeader(name, value);
        this.httppost.addHeader(name, value);
        return true;
    }

    public DiffURIBuilder setScheme(String scheme) {
        this.uri.setScheme(scheme);
        return this.uri;
    }

    public DiffURIBuilder setHost(String host) {
        this.uri.setHost(host);
        return this.uri;
    }

    public DiffURIBuilder setPath(String path) {
        this.uri.setPath(path);
        return this.uri;
    }

    public DiffURIBuilder addTemplate(String template) {
        this.uri.addTemplate(template);
        return this.uri;
    }

    public DiffURIBuilder addParameter(String name, String value) {
        this.uri.addParameter(name, value);
        return this.uri;
    }

    public DiffURIBuilder setEntity(String entity) {
        this.uri.setEntity(entity);
        return this.uri;
    }

    public DiffURIBuilder getUri() {
        return this.uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
