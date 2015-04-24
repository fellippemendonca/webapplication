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
public class JsonTableMatrix {
    List<String> columnNames;
    List<JsonTableRow> rows;

    public JsonTableMatrix(List<String> columnNames, List<JsonTableRow> rows) {
        this.columnNames = columnNames;
        this.rows = rows;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<JsonTableRow> getRows() {
        return rows;
    }

    public void setRows(List<JsonTableRow> rows) {
        this.rows = rows;
    }
    
}
