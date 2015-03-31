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
        <title>Ajax - Integrado a Servlets</title>

        <!-- Load the scripts needed for the application. -->
        <script type="text/javascript" src="jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="buttonEventsInit.js"></script>
        <script type="text/javascript" src="resultsPrinter.js"></script>
        <script type="text/javascript" src="sendToServlet.js"></script>
        <script type="text/javascript" src="requestTableList.js"></script>
        <script src="addInput.js" language="Javascript" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="custom_css/customStyle.css">

        <!-- Load the scripts needed for the autocomplete function. -->
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
        <link rel="stylesheet" href="http://cdn.datatables.net/1.10.5/css/jquery.dataTables.css"/>
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css"/>
        
        <!-- Bootstrap -->
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

       
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
                <a class="navbar-brand" href="#">Repositório de APIs</a>
            </div>
            <div id="navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Cadastro</a></li>
                    <li><a href="#about">Sobre</a></li>
                    <li><a href="#contact">Contato</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </nav>
    <br><br><br>



    <!-- Buttons that will call the servlet to retrieve the information. -->

    <!--button id="execute-requests" class="btn btn-primary" type="button">Execute</button-->
    <div class="container">
        <div id="request-list"></div></br></br>
        <div id="bands-albums-results"></div></br></br>
    </div>
    <h4>Preencha com as informações da requisição e pressione Executar.</h4>


    <div id="section">

        <table class="table">
            <tbody>
                <tr>
                    <td>
                        <fieldset disabled>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">ID</span>
                                <input type="text" id="request-id" class="form-control" size="1" placeholder="#" aria-describedby="basic-addon1" autocomplete="on">
                            </div>
                        </fieldset>
                    </td>
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
                <span class="input-group-addon" id="basic-addon1">Template:</span>
                <div id="Template" align="left">

                </div>
                <input type="button" class="btn btn-primary" value="+" onClick="addElement('Template', '');">
                <input type="button"  class="btn btn-danger" value="-" onClick="removeLastElement('Template');">
            </td>
            <td>
                <span class="input-group-addon" id="basic-addon1">Header:</span>
                <div id="Header" align="left">

                </div>
                <input type="button" class="btn btn-primary" value="+" onClick="addElement2('Header', '', '');">
                <input type="button"  class="btn btn-danger" value="-" onClick="removeLastElement('Header');">
            </td>
            <td>
                <span class="input-group-addon" id="basic-addon1">Parameter:</span>
                <div id="Parameter" align="left">

                </div>
                <input type="button"  class="btn btn-primary" value="+" onClick="addElement2('Parameter', '', '');">
                <input type="button"  class="btn btn-danger" value="-" onClick="removeLastElement('Parameter');">
            </td>
            </tbody>
        </table>
        <br>

        <div id="update-button-div">
            <input type="submit" class="btn btn-primary" id="submit-request-info" value="Executar" align="left">
            <input type="submit" class="btn btn-success" id="insert-request-info" value="Criar" align="center">
            <input type="submit" class="btn btn-warning" id="update-request-info" value="Atualizar" align="right">
        </div>
        <!--input type="submit" class="btn btn-warning" id="update-request-info" value="Update"-->

        <br><br>
        <div class="container">
            <div id="response-div"></div>
        </div>
    </div>


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins)  
    <script src="https://code.jquery.com/jquery.js"></script>-->
    <!-- Include all compiled plugins (below), or include individual files as needed  -->
    <script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>

