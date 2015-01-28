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
public class Products extends RestRequester{
    RestRequester restRequester;
    
    public Products(RestRequester restRequester){
        this.restRequester = restRequester;
        this.restRequester.addTemplate("products");
    }
    
    public ResponseContents searchProduct(String search) throws IOException, URISyntaxException{
        this.restRequester.addParameter("searchText", search);
        this.restRequester.addParameter("_limit", "2");
        this.restRequester.addParameter("_offset", "0");
        return this.restRequester.Request("GET");
    }
    
    public ResponseContents getProduct(String product_id) throws IOException, URISyntaxException{
        this.restRequester.addTemplate(product_id);
        return this.restRequester.Request("GET");
    }
    
    public ResponseContents getSku(String sku_id) throws IOException, URISyntaxException{
        this.restRequester.addTemplate("sku");
        this.restRequester.addTemplate(sku_id);
        return this.restRequester.Request("GET");
    }
    
    
}
