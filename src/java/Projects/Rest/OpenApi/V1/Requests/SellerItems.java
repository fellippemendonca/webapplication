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
public class SellerItems extends RestRequester{
    RestRequester restRequester;
    
    public SellerItems(RestRequester restRequester){
        this.restRequester = restRequester;
        this.restRequester.addTemplate("sellerItems");
    }
    
    public ResponseContents getSku(String sku_id) throws IOException, URISyntaxException{
        this.restRequester.addTemplate(sku_id);
        return this.restRequester.Request("GET");
    }
    
    public ResponseContents getSkuOrigin(String sku_origin) throws IOException, URISyntaxException{
        this.restRequester.addTemplate("skuOrigin");
        this.restRequester.addTemplate(sku_origin);
        return this.restRequester.Request("GET");
    }
    
    
    public ResponseContents setPrice(String sku_id, String json) throws IOException, URISyntaxException{
        this.restRequester.addTemplate(sku_id);
        this.restRequester.addTemplate("prices");
        this.restRequester.setEntity(json);
        return this.restRequester.Request("PUT");
    }
    
    public ResponseContents setStock(String sku_id, String json) throws IOException, URISyntaxException{
        this.restRequester.addTemplate(sku_id);
        this.restRequester.addTemplate("stock");
        this.restRequester.setEntity(json);
        return this.restRequester.Request("PUT");
    }
}
