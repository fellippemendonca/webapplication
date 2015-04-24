/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonObjects.JsonMatrix;

import java.util.List;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonTableRow {
    List<String> columns;

    public JsonTableRow(List<String> columns) {
        this.columns = columns;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }
}
