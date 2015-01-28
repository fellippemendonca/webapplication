/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Projects.Rest.OpenApi.V1;

import HttpConnections.RestRequester;
import Projects.Rest.OpenApi.V1.Requests.Orders;
import Projects.Rest.OpenApi.V1.Requests.Products;
import Projects.Rest.OpenApi.V1.Requests.SellerItems;

/**
 *
 * @author fellippe.mendonca
 */
public class V1 extends RestRequester{
    RestRequester restRequester;
    
    public V1(RestRequester restRequester){
        this.restRequester = restRequester;
        this.restRequester.setPath("api/v1");
    }
    
    public SellerItems sellerItems(){
        //this.restRequester.setPath("api-front-selleritem/jersey");
        SellerItems sellerItems = new SellerItems(restRequester);
        return sellerItems;
    }
    
    public Products products(){
        //this.restRequester.setPath("api-front-product/jersey");
        Products products = new Products(restRequester);
        return products;
    }
    
    public Orders orders(){
        //this.restRequester.setPath("api-front-order/jersey");
        Orders orders = new Orders(restRequester);
        return orders;
    }
}
