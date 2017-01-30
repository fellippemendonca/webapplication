/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.QueryReportObject;

import DAO.DataAccessObject;
import Databank_Engines.DatabankConnector;
import Databank_Engines.Matrix.DynamicMatrix;
import Entities.QueryReportEntities.QueryReport;
import Entities.QueryReportEntities.QueryReportLog;
import Entities.QueryReportEntities.QueryTag;
import Entities.QueryReportEntities.QueryTagReference;
import JsonObjects.QueryReport.JsonQueryReport;
import JsonObjects.Tags.JsonTag;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fellippe.mendonca
 */
public class QueryReportObject {

    DataAccessObject dao;
    JsonQueryReport jsonQueryReport;
    QueryReport queryReport;
    List<QueryTagObject> queryTagObjectList;

    public QueryReportObject(DataAccessObject dao) {
        this.dao = dao;
    }

    public QueryReportObject(DataAccessObject dao, String jsonQueryReport) {
        this.dao = dao;
        boolean success = setJsonQueryReportObjectFromString(jsonQueryReport);
        if (success == false) {
            System.out.println("A problem occured while Parsing Json Query Report Object from JsonString.");
        }
    }

    public QueryReportObject(DataAccessObject dao, int queryReportId) {
        this.dao = dao;
        boolean success = setJsonQueryReportObjectFromDataBank(queryReportId);
        if (success == false) {
            System.out.println("A problem occured while Retrieving Json Query Report Object from Database.");
        }
    }

    public JsonQueryReport getJsonQueryReport() {
        return this.jsonQueryReport;
    }

    public boolean setJsonQueryReportObjectFromString(String json) {
        boolean success = false;
        this.queryReport = new QueryReport();
        try {
            this.jsonQueryReport = new Gson().fromJson(json, JsonQueryReport.class);
            success = true;
        } catch (JsonSyntaxException ex) {
            System.out.println("A problem occured while Parsing Json Query Report Object from String." + ex);
        }

        if (success == true) {
            this.queryReport.setId(this.jsonQueryReport.getId());
            this.queryReport.setName(this.jsonQueryReport.getName());
            this.queryReport.setDescription(this.jsonQueryReport.getDescription());
            this.queryReport.setDbName(this.jsonQueryReport.getDbName());
            this.queryReport.setSelectString(this.jsonQueryReport.getSelectQuery());
            this.queryTagObjectList = new ArrayList<>();
            for (JsonTag tag : this.jsonQueryReport.getJsonTags()) {
                if (tag.getName().isEmpty()) {
                    System.out.println("Empty tag value not inserted.");
                } else {
                    this.queryTagObjectList.add(new QueryTagObject(this.dao, tag.getName().toUpperCase()));
                }
            }

        } else {
            System.out.println("A problem occured while retrieving Query Report Object.");
        }
        return success;
    }

    public boolean setJsonQueryReportObjectFromDataBank(int queryReportId) {
        boolean success = false;
        this.jsonQueryReport = new JsonQueryReport();
        try {
            this.queryReport = this.dao.getQueryReportJpaController().findQueryReport(queryReportId);
            success = true;
        } catch (Exception e) {
            System.out.println("A problem occured while retrieving Query Report Object. " + e);
        }
        this.jsonQueryReport.setId(this.queryReport.getId());
        this.jsonQueryReport.setName(this.queryReport.getName());
        this.jsonQueryReport.setDescription(this.queryReport.getDescription());
        this.jsonQueryReport.setDbName(this.queryReport.getDbName());
        this.jsonQueryReport.setSelectQuery(this.queryReport.getSelectString());
        this.queryTagObjectList = new ArrayList<>();
        for (QueryTagReference tagRef : this.dao.getQueryTagReferenceJpaController().findByIdQueryReport(queryReportId)) {
            QueryTagObject queryTagObject = new QueryTagObject(tagRef, this.dao);
            this.queryTagObjectList.add(queryTagObject);
            this.jsonQueryReport.getJsonTags().add(queryTagObject.getJsonTag());
        }
        return success;
    }

    public boolean createQueryReport() {
        boolean success;
        try {
            this.queryReport = this.dao.getQueryReportJpaController().create(this.queryReport);
            success = true;
        } catch (Exception ex) {
            success = false;
            Logger.getLogger(QueryReportObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (success == true) {
            try {
                for (QueryTagObject tagObj : this.queryTagObjectList) {
                    tagObj.persistQueryTagReference(this.queryReport);
                }
                success = true;
            } catch (Exception ex) {
                success = false;
                Logger.getLogger(QueryReportObject.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return success;
    }

    public boolean updateQueryReport() {
        boolean success;
        try {
            this.dao.getQueryReportJpaController().edit(this.queryReport);
            success = true;
        } catch (Exception ex) {
            success = false;
            Logger.getLogger(QueryReportObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (success == true) {
            try {
                dao.getQueryTagReferenceJpaController().destroyByIdQueryReport(this.queryReport.getId());
                for (QueryTagObject tagObj : this.queryTagObjectList) {
                    tagObj.persistQueryTagReference(this.queryReport);
                }
                success = true;
            } catch (Exception ex) {
                success = false;
                Logger.getLogger(QueryReportObject.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return success;
    }

    public boolean removeQueryReport() {
        boolean success;
        try {
            this.dao.getQueryReportJpaController().destroy(this.queryReport.getId());
            success = true;
        } catch (Exception ex) {
            success = false;
            Logger.getLogger(QueryReportObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (success == true) {
            try {
                dao.getQueryTagReferenceJpaController().destroyByIdQueryReport(this.queryReport.getId());
                success = true;
            } catch (Exception ex) {
                success = false;
                Logger.getLogger(QueryReportObject.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return success;
    }

    public String retrieveGoogleChartMatrixFromLog(int queryId, String since) {
        List<QueryReportLog> logList = this.dao.getQueryReportLogJpaController().findQueryReportId(queryId, since);
        List list = new ArrayList();
        if (logList.size() > 0) {
            List<String> chartVariables = new ArrayList();
            chartVariables.add("Execution");
            String lastExec = logList.get(logList.size() - 1).getQueryResultJsonString();
            DynamicMatrix dx1 = new Gson().fromJson(lastExec, DynamicMatrix.class);

            if (dx1.getMaxRow() <= dx1.getMaxCol(0)) {
                for (int i = 0; i < dx1.getMaxCol(0); i++) {
                    chartVariables.add(dx1.getColumnName(i));
                }
                list.add(chartVariables);
                for (QueryReportLog log : logList) {
                    DynamicMatrix dx = new Gson().fromJson(log.getQueryResultJsonString(), DynamicMatrix.class);
                    List<String> chartValues = new ArrayList();

                    if (dx.getMaxRow() > 0) {
                        for (String chartVariable : chartVariables) {
                            if (chartVariable.equals("Execution")) {
                                //chartValues.add(new SimpleDateFormat("dd/MMM HH:mm").format(log.getExecutionDateTime()));
                                chartValues.add(new SimpleDateFormat("MM/dd/yyyy HH:mm").format(log.getExecutionDateTime()));
                            } else {
                                String currentValue = "0";
                                for (int i = 0; i < dx.getMaxCol(0); i++) {
                                    if (dx.getColumnName(i).equals(chartVariable)) {
                                        currentValue = dx.getValue(0, i);
                                    }
                                }
                                chartValues.add(currentValue);
                            }
                        }
                        list.add(chartValues);
                    }
                }
            } else {
                int maxVariables = dx1.getMaxRow();
                if (maxVariables > 15) {
                    maxVariables = 15;
                }
                for (int i = 0; i < maxVariables; i++) {
                    chartVariables.add(dx1.getValue(i, 0));
                }
                list.add(chartVariables);

                for (QueryReportLog log : logList) {
                    DynamicMatrix dx = new Gson().fromJson(log.getQueryResultJsonString(), DynamicMatrix.class);
                    List<String> chartValues = new ArrayList();
                    int count = 0;
                    for (String chartVariable : chartVariables) {
                        count++;
                        if (chartVariable.equals("Execution")) {
                            //chartValues.add(new SimpleDateFormat("dd/MMM HH:mm").format(log.getExecutionDateTime()));
                            chartValues.add(new SimpleDateFormat("MM/dd/yyyy HH:mm").format(log.getExecutionDateTime()));
                        } else {
                            String tempValue = "0";
                            for (int i = 0; i < dx.getMaxRow(); i++) {
                                if (chartVariable.equals(dx.getValue(i, 0))) {
                                    tempValue = dx.getValue(i, 1);
                                }
                            }
                            if (count > 16) {
                                System.out.println("exceeded 16 rows, breaking operation...");
                                break;
                            }
                            chartValues.add(tempValue);
                        }
                    }
                    list.add(chartValues);
                }
            }
        }
        return new Gson().toJson(list);
    }

    public boolean executeQueriesReport() {
        System.out.println("Scheduled Query Report Starting...");
        DatabankConnector databankConnector = new DatabankConnector();
        boolean success = true;
        for (QueryReport queryReport : dao.getQueryReportJpaController().findQueryReportEntities()) {
            String query = queryReport.getSelectString().replaceAll(";", " ");
            String dbName = queryReport.getDbName();
            DynamicMatrix DX = databankConnector.executeQuery(dbName, query);
            String JsonResponse = new Gson().toJson(DX);
            Date date = new Date();
            try {
                QueryReportLog queryReportLog = new QueryReportLog(0, queryReport.getId(), date, JsonResponse);
                dao.getQueryReportLogJpaController().create(queryReportLog);
            } catch (Exception ex) {
                Logger.getLogger(QueryReportObject.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Scheduled Query Report Failed!");
                success = false;
                return success;
            }
        }
        System.out.println("Scheduled Query Report Done!");
        return success;
    }
}
