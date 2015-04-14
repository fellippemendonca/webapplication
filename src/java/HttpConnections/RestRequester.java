/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HttpConnections;

import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

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

    public ResponseContents Request(String method) throws IOException, URISyntaxException {
        RestConnFactory connFactory = new RestConnFactory();
        System.out.println("-------------------------");

        switch (method.toUpperCase()) {
            case "GET":
                System.out.println("GET : " + this.uri.getHost() + this.uri.getPath() + this.uri.getTemplate());
                this.httpget.setURI(this.uri.getFinalURI().build());
                this.RC = connFactory.RestRequest(this.httpget);
                this.RC.setRequest("GET : " + this.uri.getScheme() + "://" + this.uri.getHost() + this.uri.getPath());
                System.out.println("GET : " + this.RC.status);
                break;

            case "PUT":
                System.out.println("PUT : " + this.uri.getHost() + this.uri.getPath() + this.uri.getTemplate());
                this.httpput.setURI(this.uri.getFinalURI().build());
                this.httpput.setEntity(uri.getEntity());
                this.RC = connFactory.RestRequest(this.httpput);
                this.RC.setRequest("PUT : " + this.uri.getScheme() + "://" + this.uri.getHost() + this.uri.getPath());
                System.out.println("PUT : " + this.RC.status);
                break;

            case "POST":
                System.out.println("POST : " + this.uri.getHost() + this.uri.getPath() + this.uri.getTemplate());
                this.httppost.setURI(this.uri.getFinalURI().build());
                this.httppost.setEntity(uri.getEntity());
                this.RC = connFactory.RestRequest(this.httppost);
                this.RC.setRequest("POST : " + this.uri.getScheme() + "://" + this.uri.getHost() + this.uri.getPath());
                System.out.println("POST : " + this.RC.status);
                break;
        }

        System.out.println("-------------------------");
        return this.RC;
    }

    public ResponseContents Request() throws IOException, URISyntaxException {
        RestConnFactory connFactory = new RestConnFactory();
        System.out.println("-------------------------");

        switch (this.method.toUpperCase()) {
            case "GET":
                System.out.println("GET : " + this.uri.getHost() + this.uri.getPath() + this.uri.getTemplate());
                this.httpget.setURI(this.uri.getFinalURI().build());
                this.RC = connFactory.RestRequest(this.httpget);
                this.RC.setRequest("GET : " + this.uri.getScheme() + "://" + this.uri.getHost() + this.uri.getPath());
                System.out.println("GET : " + this.RC.status);
                break;

            case "PUT":
                System.out.println("PUT : " + this.uri.getHost() + this.uri.getPath() + this.uri.getTemplate());
                this.httpput.setURI(this.uri.getFinalURI().build());
                this.httpput.setEntity(uri.getEntity());
                this.RC = connFactory.RestRequest(this.httpput);
                this.RC.setRequest("PUT : " + this.uri.getScheme() + "://" + this.uri.getHost() + this.uri.getPath());
                System.out.println("PUT : " + this.RC.status);
                break;

            case "POST":
                System.out.println("POST : " + this.uri.getHost() + this.uri.getPath() + this.uri.getTemplate());
                this.httppost.setURI(this.uri.getFinalURI().build());
                this.httppost.setEntity(uri.getEntity());
                this.RC = connFactory.RestRequest(this.httppost);
                this.RC.setRequest("POST : " + this.uri.getScheme() + "://" + this.uri.getHost() + this.uri.getPath());
                System.out.println("POST : " + this.RC.status);
                break;
        }

        System.out.println("-------------------------");
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
