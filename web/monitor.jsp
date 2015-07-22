<%-- 
    Document   : monitor
    Created on : Jun 3, 2015, 8:04:06 AM
    Author     : fellippe.mendonca
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Monitoring</title>

        <!-- Load the scripts needed for the application.-->
        <!--script type="text/javascript" src="http://code.jquery.com/jquery-2.1.3.min.js" language="Javascript"></script--> 
        <!--script type="text/javascript" src="http://code.jquery.com/ui/1.11.4/jquery-ui.js" language="Javascript"></script-->
        <script type="text/javascript" src="Jquery/jquery-2.1.3.min.js" language="Javascript"></script> 
        <script type="text/javascript" src="Jquery/jquery-ui.min.js" language="Javascript"></script>
        
        <!-- Load the scripts needed for the autocomplete function. -->
        <!--link rel="stylesheet" href="http://cdn.datatables.net/1.10.5/css/jquery.dataTables.css"/-->
        <!--link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css"/-->
        <link rel="stylesheet" href="Jquery/jquery.dataTables.min.css">
        <link rel="stylesheet" href="Jquery/jquery-ui.css">

        <!-- Bootstrap -->
        <script type="text/javascript" src="bootstrap-3.3.4-dist/js/bootstrap.min.js" language="Javascript"></script>
        <link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap-theme.min.css" >

        <!-- Bootstrap DatePicker-->
        <!--script type="text/javascript" src="datepicker/js/bootstrap-datepicker.js" language="Javascript"></script>
        <link rel="stylesheet" href="datepicker/css/datepicker.css">
        <link rel="stylesheet" href="datepicker/less/datepicker.less"-->

        <!--  TagIt -->
        <link href="tagit/css/jquery.tagit.css" rel="stylesheet" type="text/css">
        <link href="tagit/css/tagit.ui-zendesk.css" rel="stylesheet" type="text/css">
        <script src="tagit/js/tag-it.js" type="text/javascript" charset="utf-8"></script>

        <!-- Load the scripts needed for page functions. -->
        <script type='text/javascript' >
            var idRequestReference;
            var jsonRequestValidation;
            var jsonValidationScenario;
            var jsonDailyExecutionRequest;
        </script>

        <script type="text/javascript" src="javascript-files/monitorPage/requestTableList-Monitor.js" language="Javascript" ></script>
        <script type="text/javascript" src="javascript-files/monitorPage/addInput-Monitor.js" language="Javascript" ></script>
        <script type="text/javascript" src="javascript-files/monitorPage/sendToServlet-Monitor.js" language="Javascript" ></script>

        <link rel="stylesheet" type="text/css" href="custom_css/customStyleValidations.css">

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
                        <li class="active"><a href="http://10.116.45.34:8080/servlet-stateless/monitor.jsp">Monitoring</a></li>
                        <li><a href="http://10.116.45.34:8080/servlet-stateless/About.html">About</a></li>
                        <li><a href="https://mktplace.atlassian.net/secure/Dashboard.jspa">Contact</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <br><br><br>

        <div class="container">
            <div class="panel panel-info">
                <div class="panel-heading">How does it work?</div>
                <div class="panel-body">
                    Select a request from list below to view the execution log from pre-selected date.
                </div>
            </div>
        </div>

        <br>
        <div class="container">
            <h4>Labels:</h4>
            <input type="text" id="tag-array" class="form-control" placeholder="Rótulos..." size="5">
            <div id="monitor-view-div"></div>
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
        
        <div id="section">
            <div class="container">
                <!--div id="createNewValidation"></div-->
                <!--button id="create-new-validation-button" type="button" class="btn btn-primary btn-lg disabled" onClick="validationCreate();">Criar Validação</button-->
            </div>
            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <!--div class="modal-dialog modal-lg"-->
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">Scenario</h4>
                        </div>
                        <div class="modal-body">
                            <div class="ui-front">
                                <!-- NOVO CONTEUDO -->
                                <div class="Table">
                                    <div class="row"><div class="col-lg-12">
                                            <div class="input-group">
                                                <span class="input-group-addon" id="basic-addon1">Name</span>
                                                <input type="text" id="requestName" class="form-control" size="5" placeholder="Request Name..." aria-describedby="basic-addon1" maxlength="100" autocomplete="on">
                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <fieldset disabled>
                                                <div class="input-group">
                                                    <span class="input-group-addon" id="basic-addon1">ID</span>
                                                    <input type="text" id="request-id" class="form-control" size="1" placeholder="#" aria-describedby="basic-addon1" autocomplete="on">
                                                </div>
                                            </fieldset>
                                        </div>
                                        <div class="col-lg-4">
                                            <div class="input-group">
                                                <span class="input-group-addon" id="basic-addon1">Method</span>
                                                <input type="text" id="method" class="form-control" size="5" placeholder="GET" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                                            </div>
                                        </div>
                                        <div class="col-lg-4">
                                            <div class="input-group">
                                                <span class="input-group-addon" id="basic-addon1">Environment</span>
                                                <input type="text" id="environment" class="form-control" size="5" placeholder="HLG" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <div class="input-group">
                                                <span class="input-group-addon" id="basic-addon1">Scheme</span>
                                                <input type="text" id="scheme" class="form-control" size="5" placeholder="http" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                                            </div>
                                        </div>
                                        <div class="col-lg-8">
                                            <div class="input-group">
                                                <span class="input-group-addon" id="basic-addon1">Host</span>
                                                <input type="text" id="host" class="form-control" size="5" placeholder="api.extra.com.br" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                                            </div>
                                        </div>

                                    </div>
                                    <br>
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="input-group">
                                                <span class="input-group-addon" id="basic-addon1">Path</span>
                                                <input type="text" id="path" class="form-control" size="5" placeholder="api/v1/sellerItems" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br> 
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">Template</div>
                                            <div class="panel-body">
                                                <div id="Template"></div>
                                                <input type="button" class="btn btn-primary btn-xs" value="+" onClick="addElement('Template', '');">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">Header</div>
                                            <div class="panel-body">
                                                <div id="Header"></div>
                                                <input type="button" class="btn btn-primary btn-xs" value="+" onClick="addElement2('Header', '', '');">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">Parameter</div>
                                            <div class="panel-body">
                                                <div id="Parameter"></div>
                                                <input type="button"  class="btn btn-primary btn-xs" value="+" onClick="addElement2('Parameter', '', '');">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="container">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <h4>Json Object:</h4>
                                            <textarea id="Payload" rows="1" cols="30"></textarea>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <h4>Labels:</h4>
                                            <ul id="request-tags"></ul>
                                        </div>
                                    </div>  
                                    <div class="row">
                                        <div class="col-md-1">
                                            <button type="button" class="btn btn-default" data-toggle="collapse" data-target="#demo">Dynamic Data</button>
                                        </div>
                                        <div class="col-md-9">
                                            <div id="demo" class="collapse">
                                                <select id="databank-selector">
                                                    <option>ADHLG</option>
                                                    <option>SLHLG</option>
                                                    <option>ADPRD</option>
                                                    <option>SLPRD</option>
                                                    <option>EXPRD</option>
                                                </select>
                                                <textarea id="dynamic-data-query" rows="5" cols="10"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-md-5"><span class="input-group-addon" id="basic-addon1">Validations:</span></div>
                                    </div>
                                    <form>
                                        <div class="input-group">
                                            <span class="input-group-addon" id="basic-addon1">Description</span>
                                            <input type="text" id="scenario-description" class="form-control" size="5" placeholder="Scenario Description..." aria-describedby="basic-addon1" maxlength="200" autocomplete="on">
                                        </div>
                                        <br><br>
                                        <div id="Validation"></div>
                                    </form>
                                    <br>
                                    <div class="btn-group">
                                        <button type="button" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">Insert<span class="caret"></span></button>
                                        <ul class="dropdown-menu">
                                            <li><a onClick="addValidationElement('Validation', 'Find Http Status', '', 'Http Status', 'red');">Http Status</a></li>
                                            <li><a onClick="addValidationElement('Validation', 'Response Time', '', 'Response Time(ms)', 'red');">Response Time(ms)</a></li>
                                            <li><a onClick="addValidationElement('Validation', 'Find String', '', 'Find String', 'red');">Find String</a></li>
                                            <li><a onClick="addValidationElement('Validation', 'Find Json Element', '', 'Find Json Element', 'red');">Find Json Element</a></li>
                                            <li><a onClick="addValidationElement('Validation', 'Compare Json Structure', '', 'Match Json Structure', 'red');">Match Json Structure</a></li>
                                        </ul>
                                    </div>
                                    <br><br><br><br>
                                    <div class="row">
                                        <div class="col-md-12"><div id="response-scenario-div"></div></div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <div class="row">
                                        <div class="col-lg-2"><button type="button" class="btn btn-default" data-dismiss="modal">Close</button></div>
                                        <div class="col-lg-2"><button type="button" id="execute-validation-button" class="btn btn-primary btn-lg">Re-Execute</button></div>
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
                    <div class="col-lg-3">
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-addon1">Execution Date</span>
                            <input type="text" id="datepicker-field" class="form-control" aria-describedby="basic-addon1" maxlength="10" data-date-format="yyyy-mm-dd">
                        </div>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-lg-6">
                        <fieldset disabled>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Name</span>
                                <input type="text" id="request-name-indicator" class="form-control" size="1" placeholder="..." aria-describedby="basic-addon1" autocomplete="on">
                            </div>
                        </fieldset>
                    </div>
                    <div class="col-lg-2">
                        <fieldset disabled>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Environment</span>
                                <input type="text" id="request-env-indicator" class="form-control" size="1" placeholder="..." aria-describedby="basic-addon1" autocomplete="on">
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
            <br>
            <div class="container">
                <div id="daily-execution-log" class="Table"></div>

                <br><br><br>
            </div>
        </div>
        <br>      
    </body>
</html>

