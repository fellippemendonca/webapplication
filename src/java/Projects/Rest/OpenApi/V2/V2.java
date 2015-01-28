/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Projects.Rest.OpenApi.V2;

import HttpConnections.RestRequester;
import Projects.Rest.OpenApi.V2.Requests.Orders;
import Projects.Rest.OpenApi.V2.Requests.Products;
import Projects.Rest.OpenApi.V2.Requests.SellerItems;

/**
 *
 * @author fellippe.mendonca
 */
public class V2 {
     RestRequester restRequester;
    
    public V2(RestRequester restRequester){
        this.restRequester = restRequester;
        this.restRequester.setPath("api/v2");
    }
    
    public SellerItems sellerItems(){
        SellerItems sellerItems = new SellerItems(restRequester);
        return sellerItems;
    }
    
    public Products products(){
        Products products = new Products(restRequester);
        return products;
    }
    
    public Orders orders(){
        Orders orders = new Orders(restRequester);
        return orders;
    }
}
