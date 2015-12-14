/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HttpConnections;

import SoapConnections.SoapConnFactory;
import SoapConnections.SoapPost;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.soap.SOAPException;
import org.apache.http.NameValuePair;
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
    SoapPost soapPost;
    String method;

    public RestRequester() {
        this.uri = new DiffURIBuilder();
        this.RC = new ResponseContents();
        this.httpget = new HttpGet();
        this.httpput = new HttpPut();
        this.httppost = new HttpPost();
        this.soapPost = new SoapPost();
        this.method = "";
    }

    public String getUriString() {
        return this.uri.getFinalURI().toString();
    }

    public ResponseContents Request() throws IOException, URISyntaxException {
        RestConnFactory connFactory = new RestConnFactory();
        SoapConnFactory soapConnFactory = new SoapConnFactory();
        Date startDate = new Date();
        switch (this.method.toUpperCase()) {
            case "GET":
                this.httpget.setURI(this.uri.getFinalURI().build());
                this.RC = connFactory.RestRequest(this.httpget);
                this.RC.setEndDate();
                this.RC.setRequest(RequestStringBuilder("GET"));
                this.RC.getDiffMilliseconds(startDate);
                break;

            case "PUT":
                this.httpput.setURI(this.uri.getFinalURI().build());
                this.httpput.setEntity(this.uri.getEntity());
                this.RC = connFactory.RestRequest(this.httpput);
                this.RC.setEndDate();
                this.RC.setRequest(RequestStringBuilder("PUT"));
                this.RC.getDiffMilliseconds(startDate);
                break;

            case "POST":
                this.httppost.setURI(this.uri.getFinalURI().build());
                this.httppost.setEntity(this.uri.getEntity());
                this.RC = connFactory.RestRequest(this.httppost);
                this.RC.setEndDate();
                this.RC.setRequest(RequestStringBuilder("POST"));
                this.RC.getDiffMilliseconds(startDate);
                break;
            case "SOAP":
                this.soapPost.setUri(this.uri.getFinalURIString());
                this.soapPost.setXmlRequest(this.uri.getStringEntity());
                /*ADD REQUEST SOAP CONN FACTORY*/
                this.RC = soapConnFactory.SoapRequest(this.soapPost);
                this.RC.setEndDate();
                this.RC.setRequest(RequestStringBuilder("SOAP"));
                this.RC.getDiffMilliseconds(startDate);
                break;
        }
        return this.RC;
    }

    public ResponseContents ThreadedRequest(int threadNumber) throws IOException, URISyntaxException {
        String threadMessages = "The threadNumber parameter detected. This request executed in Parallel mode.";
        String threadResults = "";
        int sum = 0, avg = 0;
        List<ThreadedRestConnFactory> connFactoryList = new ArrayList();
        Date startDate = new Date();

        switch (this.method.toUpperCase()) {
            case "GET":
                this.httpget.setURI(this.uri.getFinalURI().build());
                /*------------------------THREADS NODE------------------------*/
                for (int i = 0; i < threadNumber; i++) {
                    connFactoryList.add(new ThreadedRestConnFactory(this.httpget));
                }

                for (ThreadedRestConnFactory thread : connFactoryList) {
                    thread.start();
                }

                for (ThreadedRestConnFactory thread : connFactoryList) {
                    try {
                        thread.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RestRequester.class.getName()).log(Level.SEVERE, null, ex);
                        threadResults = "A problem occurred while joining the Threads: "+ex;
                    }
                    threadResults += "\nThread ID:" + thread.getId()
                                + ", Response Status:" + thread.getResponseObj().getStatus()
                                + ", Elapsed Time:" + thread.getResponseObj().getExecTime();
                    sum+=thread.getResponseObj().getExecTime();
                }
                avg = sum/threadNumber;
                threadMessages+=" Average response time: "+avg+"ms";
                this.RC.setContents(threadMessages+threadResults);
                /*------------------------------------------------------------*/
                this.RC.setEndDate();
                this.RC.setRequest(RequestStringBuilder("GET"));
                this.RC.getDiffMilliseconds(startDate);
                break;

            case "PUT":
                this.httpput.setURI(this.uri.getFinalURI().build());
                this.httpput.setEntity(uri.getEntity());
                /*------------------------THREADS NODE------------------------*/
                for (int i = 0; i < threadNumber; i++) {
                    connFactoryList.add(new ThreadedRestConnFactory(this.httpput));
                }

                for (ThreadedRestConnFactory thread : connFactoryList) {
                    thread.start();
                }

                for (ThreadedRestConnFactory thread : connFactoryList) {
                    try {
                        thread.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RestRequester.class.getName()).log(Level.SEVERE, null, ex);
                        threadResults = "A problem occurred while joining the Threads: "+ex;
                    }
                    threadResults += "\nThread ID:" + thread.getId()
                                + ", Response Status:" + thread.getResponseObj().getStatus()
                                + ", Elapsed Time:" + thread.getResponseObj().getExecTime();
                    sum+=thread.getResponseObj().getExecTime();
                }
                avg = sum/threadNumber;
                threadMessages+=" Average response time: "+avg+"ms";
                this.RC.setContents(threadMessages+threadResults);
                /*------------------------------------------------------------*/
                this.RC.setEndDate();
                this.RC.setRequest(RequestStringBuilder("PUT"));
                this.RC.getDiffMilliseconds(startDate);
                break;

            case "POST":
                this.httppost.setURI(this.uri.getFinalURI().build());
                this.httppost.setEntity(uri.getEntity());
                /*------------------------THREADS NODE------------------------*/
                for (int i = 0; i < threadNumber; i++) {
                    connFactoryList.add(new ThreadedRestConnFactory(this.httppost));
                }

                for (ThreadedRestConnFactory thread : connFactoryList) {
                    thread.start();
                }

                for (ThreadedRestConnFactory thread : connFactoryList) {
                    try {
                        thread.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RestRequester.class.getName()).log(Level.SEVERE, null, ex);
                        threadResults = "A problem occurred while joining the Threads: "+ex;
                    }
                    threadResults += "\nThread ID:" + thread.getId()
                                + ", Response Status:" + thread.getResponseObj().getStatus()
                                + ", Elapsed Time:" + thread.getResponseObj().getExecTime();
                    sum+=thread.getResponseObj().getExecTime();
                }
                avg = sum/threadNumber;
                threadMessages+=" Average response time: "+avg+"ms";
                this.RC.setContents(threadMessages+threadResults);
                /*------------------------------------------------------------*/
                this.RC.setEndDate();
                this.RC.setRequest(RequestStringBuilder("POST"));
                this.RC.getDiffMilliseconds(startDate);
                break;
        }
                connFactoryList.clear();
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

    public String RequestStringBuilder(String operation) {
        String requestString = (operation + " : " + this.uri.getScheme() + "://" + this.uri.getHost() + this.uri.getPath());
        int i = 0;
        for (NameValuePair param : this.uri.getQueryParams()) {
            if (i == 0) {
                requestString += "?";
            } else {
                requestString += "&";
            }
            requestString += param.getName() + "=" + param.getValue();
            i++;
        }
        return requestString;
    }
}
