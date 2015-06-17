<%-- 
    Document   : validations
    Created on : Apr 27, 2015, 9:24:43 AM
    Author     : fellippe.mendonca
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Validations</title>

        <!-- Load the scripts needed for the application.-->
        <script type="text/javascript" src="http://code.jquery.com/jquery-2.1.3.min.js" language="Javascript"></script> 
        <script type="text/javascript" src="http://code.jquery.com/ui/1.11.4/jquery-ui.js" language="Javascript"></script>

        <!-- Load the scripts needed for the autocomplete function. -->
        <link rel="stylesheet" href="http://cdn.datatables.net/1.10.5/css/jquery.dataTables.css"/>
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css"/>

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
            var jsonRequestValidation;
            var jsonValidationScenario;
        </script>
        <script type="text/javascript" src="javascript-files/validationPage/requestTableList-Validation.js" language="Javascript" ></script>
        <script type="text/javascript" src="javascript-files/validationPage/addInput-Validation.js" language="Javascript" ></script>
        <script type="text/javascript" src="javascript-files/validationPage/sendToServlet-Validation.js" language="Javascript" ></script>

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
                    <a class="navbar-brand" href="#">API's Warehouse</a>
                </div>
                <div id="navbar" class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="http://10.116.45.34:8080/servlet-stateless/insertions.jsp">APIs</a></li>
                        <li class="active"><a href="http://10.116.45.34:8080/servlet-stateless/validations.jsp">Validations</a></li>
                        <li><a href="http://10.116.45.34:8080/servlet-stateless/monitor.jsp">Monitoring</a></li>
                        <li><a href="http://10.116.45.34:8080/servlet-stateless/About.html">About</a></li>
                        <li><a href="https://mktplace.atlassian.net/secure/Dashboard.jspa">Contact</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <br><br><br>

        <div class="container">
            <div class="panel panel-info">
                <div class="panel-heading">How does it works?</div>
                <div class="panel-body">
                    Select a request from list below and then click on Create Scenario.
                    <br>If there already exists some scenarios remember that is possible to edit or include new ones.
                </div>
            </div>
        </div>

        <div class="container">
            <h4>Labels:</h4>
            <input type="text" id="tag-array" class="form-control" placeholder="Rótulos..." size="5">
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

            <div class="container">

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
                    <div class="col-lg-2">
                        <button id="create-new-validation-button" type="button" class="btn btn-primary btn-lg disabled" onClick="validationCreate();">Create Scenario</button>
                    </div>
                </div>
                <br><br>
                <div id="schedule-validation" class="btn-group">
                    <button id="schedule-button" type="button" class="btn btn-default dropdown-toggle disabled" data-toggle="dropdown" aria-expanded="false">
                        Monitoring <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a onClick="insertSchedule()">Schedule</a></li>
                        <li><a onClick="removeSchedule()">Remove</a></li>
                    </ul>
                </div>
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
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="input-group">
                                                <span class="input-group-addon" id="basic-addon1">Name</span>
                                                <input type="text" id="requestName" class="form-control" size="5" placeholder="Nome do Request..." aria-describedby="basic-addon1" maxlength="100" autocomplete="on">
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
                            </div>
                        </div>


                        <div class="container">
                            <h4>Json Object:</h4>
                            <textarea id="Payload" rows="1" cols="10"></textarea>
                            <h4>Labels:</h4>
                            <div class="col-md-5"><ul id="request-tags"></ul></div>
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
                                    <input type="text" id="scenario-description" class="form-control" size="5" placeholder="Descrição do cenário..." aria-describedby="basic-addon1" maxlength="200" autocomplete="on">
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
                                    <li><a onClick="addValidationElement('Validation', 'Compare Json Structure', '', 'Compare Json Structure', 'red');">Compare Json Structure</a></li>
                                </ul>
                            </div>
                            <br><br><br><br>
                            <div id="response-scenario-div"></div>
                        </div>
                        <div class="modal-footer">
                            <div class="row">
                                <div class="col-lg-2"><button type="button" class="btn btn-default" data-dismiss="modal">Close</button></div>
                                <div class="col-lg-2"><button type="button" id="insert-validation-button" class="btn btn-success" data-dismiss="modal">Create</button></div>
                                <div class="col-lg-2"><button type="button" id="delete-validation-button" class="btn btn-danger disabled" data-dismiss="modal">Delete</button></div>
                                <div class="col-lg-2"><button type="button" id="update-validation-button" class="btn btn-warning disabled" data-dismiss="modal">Save</button></div>
                                <div class="col-lg-2"><button type="button" id="execute-validation-button" class="btn btn-primary btn-lg">Execute</button></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <div class="container">
            <div id="validation-list-div" class="Table">

            </div>
            <br><br><br>
            <button type="button" id="execute-massive-validation-button" class="btn btn-primary disabled">Execute List</button>

        </div>
    </div>
    <br>        
</body>
</html>
