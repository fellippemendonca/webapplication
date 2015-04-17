/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HttpConnections;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author fellippe.mendonca
 */
public class RestConnFactory {

    CloseableHttpClient httpclient;
    HttpResponse httpResponse;
    ResponseContents responseObj;

    public RestConnFactory() {
        this.httpclient = HttpClients.createDefault();
        this.httpResponse = null;
        this.responseObj = new ResponseContents("","","");
    }
    public ResponseContents RestRequest(HttpRequestBase httprequest) throws IOException {
        try {
            this.httpResponse = this.httpclient.execute(httprequest);
            this.responseObj.setStatus(this.httpResponse.getStatusLine().toString());
            
            if (this.httpResponse.getEntity() == null || this.httpResponse.getStatusLine().toString().contains("500")) {
                this.responseObj.setContents("{\"no\":\"value\"}");
            } else {
                this.responseObj.setContents(EntityUtils.toString(this.httpResponse.getEntity()));
            }
            this.httpclient.close();
        } catch (IOException ex) {
            Logger.getLogger(RestConnFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.responseObj;
    }
}
