/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonObjects.Tags;

import java.util.List;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonTagNameList {
    List<JsonTagNameOnly> jsonTagNameOnly;

    public JsonTagNameList(List<JsonTagNameOnly> jsonTagNameOnly) {
        this.jsonTagNameOnly = jsonTagNameOnly;
    }

    public List<JsonTagNameOnly> getJsonTagNameOnly() {
        return jsonTagNameOnly;
    }

    public void setJsonTagNameOnly(List<JsonTagNameOnly> jsonTagNameOnly) {
        this.jsonTagNameOnly = jsonTagNameOnly;
    }
    
}
