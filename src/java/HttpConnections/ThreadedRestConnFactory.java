/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package HttpConnections;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author fellippe.mendonca
 */
public class ThreadedRestConnFactory extends Thread {
    CloseableHttpClient httpclient;
    HttpResponse httpResponse;
    ResponseContents responseObj;
    HttpRequestBase httprequest;

    public ThreadedRestConnFactory(HttpRequestBase httprequest) {
        this.httpclient = null;
        this.httpResponse = null;
        this.responseObj = new ResponseContents("", "", "");
        this.httprequest = httprequest;
    }
    
    @Override
    public void run() {
        try {
            HttpResponse httpResponseTemp = null;
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
            HttpConnectionParams.setSoTimeout(httpParams, 30000);
            this.httpclient = new DefaultHttpClient(httpParams);
            Date startDate = new Date();
            try {
                httpResponseTemp = this.httpclient.execute(this.httprequest);
            } catch (IOException ex) {
                System.out.println("An error occured while executing the request. Message: " + ex);
                this.responseObj.setStatus(ex.toString());
            }
            this.responseObj.setEndDate();
            this.responseObj.getDiffMilliseconds(startDate);
            if (httpResponseTemp == null) {
                this.httpclient.close();
            } else {
                this.httpResponse = httpResponseTemp;
                this.responseObj.setStatus(this.httpResponse.getStatusLine().toString());
                //System.out.println("Header:"+httpResponseTemp.getFirstHeader("x-request-id")+", ExecTime:"+this.responseObj.getExecTime());
                this.httpclient.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(RestConnFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CloseableHttpClient getHttpclient() {
        return httpclient;
    }

    public void setHttpclient(CloseableHttpClient httpclient) {
        this.httpclient = httpclient;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public ResponseContents getResponseObj() {
        return responseObj;
    }

    public void setResponseObj(ResponseContents responseObj) {
        this.responseObj = responseObj;
    }
}
