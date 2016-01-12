<%-- 
    Document   : index
    Created on : Feb 12, 2015, 1:58:58 PM
    Author     : fellippe.mendonca
--%>

<%@ page language="java"
         contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>API's Insertions</title>

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
        <script type="text/javascript" src="javascript-files/insertionPage/sendToServlet.js" language="Javascript" ></script>
        <script type="text/javascript" src="javascript-files/insertionPage/requestTableList.js" language="Javascript" ></script>
        <script type="text/javascript" src="javascript-files/insertionPage/addInput.js" language="Javascript" ></script>
        <link rel="stylesheet" type="text/css" href="custom_css/customStyleInsertions.css">
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
                <a class="navbar-brand" href="#">API's Warehouse</a>
            </div>
            <div id="navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="http://10.116.45.34:8080/servlet-stateless/insertions.jsp">APIs</a></li>
                    <li><a href="http://10.116.45.34:8080/servlet-stateless/validations.jsp">Validations</a></li>
                    <li><a href="http://10.116.45.34:8080/servlet-stateless/monitor.jsp">Monitoring</a></li>
                    <li><a href="http://10.116.45.34:8080/servlet-stateless/charts.jsp">Charts</a></li>
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
                Select a request from list below to automatically populate the required fields, then click on Execute on the end of the page to get the response.
                <br><br>If the request that you want is not on the list remember that you can create a new one. Fill the required fields and click Execute, if it works then click Action and choose Create.
                <br>(Do not forget to set some Labels on your request to find it easily later).
            </div>
        </div>
    </div>

    <div class="container">
        <h4>Labels:</h4>
        <input type="text" id="tag-array" class="form-control" placeholder="Tag List..." size="5"><div id="filter-view-div"></div>
        <input type="submit" class="btn btn-primary" id="submit-tag-filter" value="Filter" align="left"/>  
    </div>
    <br>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Request List</h3>
            </div>
            <div id="request-list"></div>
        </div>
    </div>
    <br>
    <div id="section">
        <!--REQUEST BODY-->
        <div class="container">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Request Data</h3>
                </div>
                <div class="container">
                    <br>
                    <!--h4>Request Data:</h4><br-->
                    <div class="Table">
                        <div class="row">
                            <div class="col-lg-2">
                                <fieldset disabled>
                                    <div class="input-group">
                                        <span class="input-group-addon" id="basic-addon1">ID</span>
                                        <input type="text" id="request-id" class="form-control" size="1" placeholder="#" aria-describedby="basic-addon1" autocomplete="on">
                                    </div>
                                </fieldset>
                            </div>
                            <div class="col-lg-2">
                                <div class="input-group">
                                    <span class="input-group-addon" id="basic-addon1">Environment</span>
                                    <input type="text" id="environment" class="form-control" size="5" placeholder="HLG" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="input-group">
                                    <span class="input-group-addon" id="basic-addon1">Name</span>
                                    <input type="text" id="requestName" class="form-control" size="5" placeholder="Request Name..." aria-describedby="basic-addon1" maxlength="100" autocomplete="on">
                                </div>
                            </div>
                            <div class="col-lg-1">
                                <button id="show_description" type="button" class="btn btn-default" onClick="$('#descriptionModal').modal('show');">Description</button>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-lg-2">
                                <div class="input-group">
                                    <span class="input-group-addon" id="basic-addon1">Method</span>
                                    <input type="text" id="method" class="form-control" size="5" placeholder="GET" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                                </div>
                            </div>
                            <div class="col-lg-2">
                                <div class="input-group">
                                    <span class="input-group-addon" id="basic-addon1">Scheme</span>
                                    <input type="text" id="scheme" class="form-control" size="5" placeholder="http" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                                </div>
                            </div>
                            <div class="col-lg-3">
                                <div class="input-group">
                                    <span class="input-group-addon" id="basic-addon1">Host</span>
                                    <input type="text" id="host" class="form-control" size="5" placeholder="api.extra.com.br" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="input-group">
                                    <span class="input-group-addon" id="basic-addon1">Path</span>
                                    <input type="text" id="path" class="form-control" size="5" placeholder="api/v1/sellerItems" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div class="container">
                    <div class="row">
                        <div class="col-lg-3">
                            <div class="panel panel-default">
                                <div class="panel-heading">Template</div>
                                <div class="panel-body">
                                    <div id="Template"></div>
                                    <input type="button" class="btn btn-primary btn-xs" value="+" onClick="addElement('Template', '');">
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="panel panel-default">
                                <div class="panel-heading">Header</div>
                                <div class="panel-body">
                                    <div id="Header"></div>
                                    <input type="button" class="btn btn-primary btn-xs" value="+" onClick="addElement2('Header', '', '');">
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="panel panel-default">
                                <div class="panel-heading">Parameter</div>
                                <div class="panel-body">
                                    <div id="Parameter"></div>
                                    <input type="button"  class="btn btn-primary btn-xs" value="+" onClick="addElement2('Parameter', '', '');">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div class="container">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Json Object</h3>
                                </div>
                                <textarea id="Payload" rows="5" cols="50"></textarea>
                            </div>
                        </div>
                        <div class="col-md-5">
                            <button type="button" class="btn btn-default" data-toggle="collapse" data-target="#demo">Dynamic Data</button>
                            <div id="demo" class="collapse">
                                <select id="databank-selector">
                                    <option>ADHLG</option>
                                    <option>SLHLG</option>
                                    <option>ADPRD</option>
                                    <option>SLPRD</option>
                                    <option>EXPRD</option>
                                </select>
                                <textarea id="dynamic-data-query" rows="5" cols="70"></textarea>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-10"><div id="response-view-div"></div>
                            <h4>Labels:</h4>
                            <ul id="request-tags"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!---->
        <br><br>

        <div class="container">
            <div id="update-button-div">
                <div class="row">
                    <div class="col-md-2">
                        <input type="submit" class="btn btn-primary btn-lg" id="submit-request-info" value="Execute" align="right">
                    </div>
                    <div class="col-md-1">
                        <div id="schedule-validation" class="btn-group">
                            <button id="schedule-button" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                Action <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu">
                                <li><a onClick="insertRequestOnDatabase()"><b>Create</b> populated fields as a <b>New Request</b></a></li>
                                <li><a onClick="updateRequestFromDatabase()"><b>Save</b> any changed data on <b>Selected Request</b> </a></li>
                                <li><a onClick="deleteRequestFromDatabase()"><b>Remove</b> the <b>Selected Request</b>  from database</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br><br>
        <div class="container">
            <div id="response-div"></div>
        </div>
    </div>
    <br>
    <!-- Modal -->
    <div class="modal fade" id="descriptionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <!--div class="modal-dialog modal-lg"-->
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Description</h4>
                </div>
                <div class="modal-body">
                    <div class="ui-front">
                        <div class="container">
                            <textarea id="requestDescription" rows="25" cols="80">Nenhuma descrição foi definida para este request.</textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

