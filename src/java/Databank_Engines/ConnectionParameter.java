/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Databank_Engines;

/**
 *
 * @author fellippe.mendonca
 */
public class ConnectionParameter {

    ConnData conndata;

    public ConnectionParameter() {

        this.conndata = new ConnData();
    }

 
    public ConnData getConnParams(String type) {
        switch (type) {
            case "EXPRD":
                this.conndata.setUrl("jdbc:sqlserver://10.128.65.31:1108;databaseName=db_prd_ExtracaoParceiro");
                this.conndata.setType("MSSQL");
                this.conndata.setUser("usr_extracaoparceiro");
                this.conndata.setPassword("u$r3xTr@c@0PaRc3!r0");
                break;
            case "ADPRD":
                this.conndata.setUrl("jdbc:oracle:thin:@cnv01-scan.dc.nova:1521/ADPRD");
                this.conndata.setType("ORACLE");
                this.conndata.setUser("fellippe_mendonca");
                this.conndata.setPassword("Convergys123");
                break;
            case "SLPRD":
                this.conndata.setUrl("jdbc:oracle:thin:@cnv01-scan.dc.nova:1521/SLPRD");
                this.conndata.setType("ORACLE");
                this.conndata.setUser("fellippe_mendonca");
                this.conndata.setPassword("Convergys123");
                break;
            case "ADHLG":
                this.conndata.setUrl("jdbc:oracle:thin:@10.128.135.13:1521/ADHLG");
                this.conndata.setType("ORACLE");
                this.conndata.setUser("fellippe_mendonca");
                this.conndata.setPassword("BF685b83");
                break;
            case "SLHLG":
                this.conndata.setUrl("jdbc:oracle:thin:@10.128.135.13:1521/SLHLG");
                this.conndata.setType("ORACLE");
                this.conndata.setUser("fellippe_mendonca");
                this.conndata.setPassword("BF685b83");
                break;
        }
        return this.conndata;
    }
}
