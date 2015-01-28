/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Projects.Rest.OpenApi.V1.Requests;

import HttpConnections.ResponseContents;
import HttpConnections.RestRequester;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author fellippe.mendonca
 */
public class Orders extends RestRequester{
    RestRequester restRequester;
    
    public Orders(RestRequester restRequester){
        this.restRequester = restRequester;
        this.restRequester.addTemplate("orders");
    }
    
    public ResponseContents getOrderId(String orderID) throws IOException, URISyntaxException{
        this.restRequester.addTemplate(orderID);
        return this.restRequester.Request("GET");
    }
    
    public ResponseContents setOrderTracking(String orderID, String json) throws IOException, URISyntaxException{
        this.restRequester.addTemplate(orderID);
        this.restRequester.setEntity(json);
        return this.restRequester.Request("POST");
    }
}
