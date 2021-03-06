package Databank_Engines;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import Databank_Engines.Matrix.DynamicMatrix;

/**
 *
 * @author fellippe.mendonca
 */
public class DatabankConnector {

    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DatabankConnector() {

    }

    public DynamicMatrix executeQuery(String databank, String query) {
        DynamicMatrix DX = new DynamicMatrix();
        ConnectionParameter params = new ConnectionParameter();
        ConnData connData = params.getConnParams(databank);
        Connection con = null;
        Statement stmt;
        try {
            switch (connData.getType().toUpperCase()) {
                case "ORACLE":
                    con = DriverManager.getConnection(connData.getUrl(), connData.getUser(), connData.getPassword());
                    break;
                case "MSSQL":
                    String MSCON = connData.getUrl() + ";user=" + connData.getUser() + ";password=" + connData.getPassword() + ";";
                    con = DriverManager.getConnection(MSCON);
                    break;
            }
            if (con != null) {
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                System.out.print("-------------------------\nExecuting Select: " + query + " ... ");
                ResultSet rs = stmt.executeQuery(query);
                System.out.println("Done.");
                ResultSetMetaData rsmd = rs.getMetaData();
                int maxCol = rsmd.getColumnCount();
                System.out.print("Retrieving Matrix... ");
                for (int i = 1; i <= maxCol; i++) {
                    DX.setColumnName(i - 1, rsmd.getColumnName(i));
                }
                int i = 1;
                while (rs.next()) {
                    for (int j = 1; j <= maxCol; j++) {
                        String columnValue = rs.getString(j);
                        if (columnValue == null) {
                            DX.setValue((i - 1), (j - 1), "NULL");
                        } else {
                            DX.setValue((i - 1), (j - 1), columnValue);
                        }
                    }
                    i++;
                }
                System.out.println("Done.");
                stmt.close();
                con.close();
                System.out.println("Connection Closed.");
            } else {
                System.out.println("Connection not Started");
            }
        } catch (SQLException ex) {
            System.err.print("SQLException: ");
            System.err.println(ex.getMessage());
            DX.setColumnName(0, "Problem");
            DX.setValue(0, 0, ex.toString());
        } finally {
            System.out.println("-------------------------");
            return DX;
        }
    }
}
