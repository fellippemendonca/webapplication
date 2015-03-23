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
        <title>Ajax - Servlets Integration Example</title>

        <!-- Load the scripts needed for the application. -->
        <script type="text/javascript" src="jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="buttonEventsInit.js"></script>
        <script type="text/javascript" src="resultsPrinter.js"></script>
        <script type="text/javascript" src="sendToServlet.js"></script>
        <script src="addInput.js" language="Javascript" type="text/javascript"></script>

        <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
        <script type="text/javascript" src="autoComplete.js"></script>
        <script type="text/javascript" src="autocompleteTests.js"></script>

        <!-- Bootstrap -->
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <h3>Ajax Plus Java Servlets Integration</h3>

        <!-- Buttons that will call the servlet to retrieve the information. -->

        <button id="execute-requests" class="btn btn-primary" type="button">Execute</button>
        <div id="request-list"></div></br></br>
        <div id="bands-albums-results"></div></br></br>

        <h4>Add the request information and press Submit!</h4>


        <div id="section">

            <table class="table">
                <tbody>
                    <tr>
                        <td>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Method</span>
                                <input type="text" id="method" class="form-control" size="5" placeholder="GET" aria-describedby="basic-addon1" autocomplete="on">
                            </div>
                        </td>
                        <td>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Environment</span>
                                <input type="text" id="environment" class="form-control" size="5" placeholder="HLG" aria-describedby="basic-addon1" autocomplete="on">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Scheme</span>
                                <input type="text" id="scheme" class="form-control" size="5" placeholder="http" aria-describedby="basic-addon1" autocomplete="on">
                            </div>
                        </td>
                        <td>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Host</span>
                                <input type="text" id="host" class="form-control" size="5" placeholder="api.extra.com.br" aria-describedby="basic-addon1" autocomplete="on">
                            </div>
                        </td>
                        <td>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Path</span>
                                <input type="text" id="path" class="form-control" size="5" placeholder="api/v1/sellerItems" aria-describedby="basic-addon1" autocomplete="on">
                            </div>
                        </td>   

                    </tr>
                </tbody>
            </table>

            <table class="table">
                <tbody>
                <td>
                    <div id="Template" align="left">
                        <span class="input-group-addon" id="basic-addon1">Template:</span>
                    </div>
                    <input type="button" class="btn btn-primary" value="+" onClick="addElement('Template');">
                </td>
                
                <td>
                    <div id="Texugo" align="left">
                        <span class="input-group-addon" id="basic-addon1">Texugo</span>
                    </div>
                    <input type="button" class="btn btn-primary" value="+" onClick="addElement('Texugo');">
                </td>
                
                <td>
                    <div id="Header" align="left">
                        <span class="input-group-addon" id="basic-addon1">Header:</span>
                    </div>
                    <input type="button" class="btn btn-primary" value="+" onClick="addElement2('Header');">
                </td>
                <td>
                    <div id="Parameter" align="left">
                        <span class="input-group-addon" id="basic-addon1">Parameter:</span>
                    </div>
                    <input type="button"  class="btn btn-primary" value="+" onClick="addElement2('Parameter');">
                </td>
                </tbody>
            </table>
            <br>
            <input type="submit" id="submit-request-info" value="Submit">
            <div id="response-div"></div>
        </div>

        <!--
        <div class="ui-widget">
            <label for="tags">Tags: </label>
            <input id="tags">
        </div>
        -->

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins)  
        <script src="https://code.jquery.com/jquery.js"></script>-->
        <!-- Include all compiled plugins (below), or include individual files as needed  -->
        <script src="bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>

