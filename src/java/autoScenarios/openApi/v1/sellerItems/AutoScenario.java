/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package autoScenarios.openApi.v1.sellerItems;

import Databank_Engines.DatabankConnector;
import Databank_Engines.Matrix.DynamicMatrix;
import HttpConnections.ResponseContents;
import Projects.Rest.RestRequests;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author fellippe.mendonca
 */
public class AutoScenario {

    public ResponseContents searchSkuV1(String env, String shop) throws IOException, URISyntaxException {

        DynamicMatrix DX = new DatabankConnector().executeQuery("AD"+env, "select * from ac_admin.ecma_sku_mp_related_sku_seller where store_qualifier_id = '"+shop+"' and rownum =1");

        String sku_id = DX.getValueByColumnName("sku_id");

        ResponseContents RC1 = new RestRequests().openApi(env, shop).v1().sellerItems().getSku(sku_id);

        return RC1;
    }
}