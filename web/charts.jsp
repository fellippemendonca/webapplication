<%-- 
    Document   : charts
    Created on : Dec 1, 2015, 6:37:46 PM
    Author     : fellippe.mendonca
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Charts</title>

        <!-- Load the scripts needed for Google Charts.-->
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>

        <!-- Load the scripts needed for the application.-->
        <script type="text/javascript" src="Jquery/jquery-2.1.3.min.js" language="Javascript"></script>
        <script type="text/javascript" src="Jquery/jquery-ui.min.js" language="Javascript"></script>

        <!-- Load the scripts needed for the autocomplete function. -->
        <link rel="stylesheet" href="Jquery/jquery.dataTables.min.css">
        <link rel="stylesheet" href="Jquery/jquery-ui.css">

        <!-- Bootstrap -->
        <script type="text/javascript" src="bootstrap-3.3.4-dist/js/bootstrap.min.js" language="Javascript"></script>
        <link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap-theme.min.css" >

        <!--  TagIt -->
        <link href="tagit/css/jquery.tagit.css" rel="stylesheet" type="text/css">
        <link href="tagit/css/tagit.ui-zendesk.css" rel="stylesheet" type="text/css">
        <script src="tagit/js/tag-it.js" type="text/javascript" charset="utf-8"></script>

        <!-- Load the scripts needed for page functions. -->
        <script type='text/javascript' >
            var IdQueryReport;
            var JsonQueryReport;
        </script>

        <script type="text/javascript" src="javascript-files/chartsPage/requestTableList-Chart.js" language="Javascript" ></script>
        <script type="text/javascript" src="javascript-files/chartsPage/addInput-Chart.js" language="Javascript" ></script>
        <script type="text/javascript" src="javascript-files/chartsPage/sendToServlet-Chart.js" language="Javascript" ></script>

        <link rel="stylesheet" type="text/css" href="custom_css/customStyleCharts.css">

    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Api's Warehouse</a>
                </div>
                <div id="navbar" class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="http://10.116.45.34:8080/servlet-stateless/insertions.jsp">APIs</a></li>
                        <li><a href="http://10.116.45.34:8080/servlet-stateless/validations.jsp">Validations</a></li>
                        <li><a href="http://10.116.45.34:8080/servlet-stateless/monitor.jsp">Monitoring</a></li>
                        <li class="active"><a href="http://10.116.45.34:8080/servlet-stateless/charts.jsp">Charts</a></li>
                        <li><a href="http://10.116.45.34:8080/servlet-stateless/About.html">About</a></li>
                        <li><a href="https://mktplace.atlassian.net/secure/CreateIssue!default.jspa">Contact</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <br><br><br>

        <div class="container">
            <div class="panel panel-info">
                <div class="panel-heading">How does it work?</div>
                <div class="panel-body">
                    Select an item from the list below to view the execution log-chart since the pre-selected date.
                    <br>The Charts have a limit of 10 visible variables, so do not forget to sort the generated output by relevance.
                </div>
            </div>
        </div>

        <br>
        <div class="container">
            <h4>Labels:</h4>
            <input type="text" id="tag-array" class="form-control" placeholder="Rótulos..." size="5">
            <input type="submit" class="btn btn-primary" id="submit-tag-filter" value="Filter" align="left"/>
        </div>
        <br>

        <div class="container">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Query Report List</h3>
                </div>
                <div id="query-list"></div>
            </div><div id="filter-view-div"></div>
        </div>

        <div id="section">
            <div class="container">
                <button id="create-new-query-button" type="button" class="btn btn-primary btn-lg" onClick="queryCreate();">Create Chart</button>
                <button id="edit-query-button" type="button" class="btn btn-default disabled" onClick="queryEdit();">Edit</button>
            </div>
            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <!--div class="modal-dialog modal-lg"-->
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">Chart</h4>
                        </div>
                        <div class="modal-body">
                            <div class="ui-front">
                                <!-- NOVO CONTEUDO -->
                                <div class="Table">
                                    <div class="row">
                                        <div class="col-lg-3">
                                            <fieldset disabled>
                                                <div class="input-group">
                                                    <span class="input-group-addon" id="basic-addon1">ID</span>
                                                    <input type="text" id="query-id" class="form-control" size="1" placeholder="#" aria-describedby="basic-addon1" autocomplete="on">
                                                </div>
                                            </fieldset>
                                        </div>
                                        <div class="col-lg-9">
                                            <div class="input-group">
                                                <span class="input-group-addon" id="basic-addon1">Name</span>
                                                <input type="text" id="query-name" class="form-control" size="5" placeholder="Query Name..." aria-describedby="basic-addon1" maxlength="200" autocomplete="on">
                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="input-group">
                                                <span class="input-group-addon" id="basic-addon1">Description</span>
                                                <input type="text" id="query-description" class="form-control" size="5" placeholder="Query Description..." aria-describedby="basic-addon1" maxlength="200" autocomplete="on">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-md-5">
                                            <h4>Labels:</h4>
                                            <ul id="query-tags"></ul>
                                        </div>
                                    </div>  
                                    <div class="row">
                                        <div class="col-md-10">
                                            <select id="databank-name">
                                                <option>ADHLG</option>
                                                <option>SLHLG</option>
                                                <option>ADPRD</option>
                                                <option>SLPRD</option>
                                                <option>EXPRD</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-10">
                                            <textarea id="query-select" rows="5" cols="10"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div id="myTableDiv"></div>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <div class="modal-footer">
                                    <div class="row">
                                        <div class="col-lg-2"><button type="button" class="btn btn-default" data-dismiss="modal">Close</button></div>
                                        <div class="col-lg-2"><button type="button" id="insert-query-button" onClick="insertQueryOnDatabase()" class="btn btn-success" data-dismiss="modal">Create</button></div>
                                        <div class="col-lg-2"><button type="button" id="delete-query-button" onClick="deleteQueryFromDatabase()" class="btn btn-danger" data-dismiss="modal">Delete</button></div>
                                        <div class="col-lg-2"><button type="button" id="update-query-button" onClick="updateQueryFromDatabase()" class="btn btn-warning" data-dismiss="modal">Save</button></div>
                                        <div class="col-lg-2"><button type="button" id="execute-query-button" class="btn btn-primary btn-lg">Execute</button></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="container">
                <div class="row">
                    <div class="col-lg-2">
                        <fieldset disabled>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Database</span>
                                <input type="text" id="database-name-indicator" class="form-control" size="1" placeholder="..." aria-describedby="basic-addon1" autocomplete="on">
                            </div>
                        </fieldset>
                    </div>
                    <div class="col-lg-6">
                        <fieldset disabled>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Name</span>
                                <input type="text" id="query-name-indicator" class="form-control" size="1" placeholder="..." aria-describedby="basic-addon1" autocomplete="on">
                            </div>
                        </fieldset>
                    </div>
                    <div class="col-lg-3">
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-addon1">Since</span>
                            <input type="text" id="datepicker-field" class="form-control" aria-describedby="basic-addon1" maxlength="10" data-date-format="yyyy-mm-dd">
                        </div>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-lg-10">
                        <div id="chart_div" style="width: 1200px; height: 600px;"></div>
                    </div>
                    <div class="col-lg-2">   </div>
                </div>
            </div>
        </div>
        <br>      
    </body>
</html>
