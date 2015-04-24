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
        <title>Cadastro de APIs</title>

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
        <script type="text/javascript" src="javascript-files/sendToServlet.js" language="Javascript" ></script>
        <script type="text/javascript" src="javascript-files/requestTableList.js" language="Javascript" ></script>
        <script type="text/javascript" src="javascript-files/addInput.js" language="Javascript" ></script>
        <link rel="stylesheet" type="text/css" href="custom_css/customStyle.css">

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
                    <li class="active"><a href="http://10.116.45.34:8080/servlet-stateless/insertions.jsp">Cadastro</a></li>
                    <li><a href="http://10.116.45.34:8080/servlet-stateless/About.html">Sobre</a></li>
                    <li><a href="https://mktplace.atlassian.net/secure/Dashboard.jspa">Contato</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </nav>
    <br><br><br>


    <div class="container">
        <h4>Labels:</h4>
        <input type="text" id="tag-array" class="form-control" placeholder="Rótulos..." size="5">
        <input type="submit" class="btn btn-primary" id="submit-tag-filter" value="Filtrar" align="left"/>  
    </div>
    <br>
    <div class="container">
        <div id="request-list"></div><br>
    </div>
    <br>
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
                            <input type="text" id="method" class="form-control" size="5" placeholder="GET" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                        </div>
                    </td>
                    <td>
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-addon1">Environment</span>
                            <input type="text" id="environment" class="form-control" size="5" placeholder="HLG" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-addon1">Scheme</span>
                            <input type="text" id="scheme" class="form-control" size="5" placeholder="http" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                        </div>
                    </td>
                    <td>
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-addon1">Host</span>
                            <input type="text" id="host" class="form-control" size="5" placeholder="api.extra.com.br" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                        </div>
                    </td>
                    <td>
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-addon1">Path</span>
                            <input type="text" id="path" class="form-control" size="5" placeholder="api/v1/sellerItems" aria-describedby="basic-addon1" maxlength="45" autocomplete="on">
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>

        <table class="table">
            <tbody>
                <tr>
                    <td>
                        <span class="input-group-addon" id="basic-addon1">Template:</span>
                        <div id="Template" align="left">

                        </div>
                        <input type="button" class="btn btn-primary" value="+" onClick="addElement('Template', '');">
                        <input type="button"  class="btn btn-danger" value="clear" onClick="removeLastElement('Template');">
                    </td>
                    <td>
                        <span class="input-group-addon" id="basic-addon1">Header:</span>
                        <div id="Header" align="left">

                        </div>
                        <input type="button" class="btn btn-primary" value="+" onClick="addElement2('Header', '', '');">
                        <input type="button"  class="btn btn-danger" value="clear" onClick="removeLastElement('Header');">
                    </td>
                    <td>
                        <span class="input-group-addon" id="basic-addon1">Parameter:</span>
                        <div id="Parameter" align="left">

                        </div>
                        <input type="button"  class="btn btn-primary" value="+" onClick="addElement2('Parameter', '', '');">
                        <input type="button"  class="btn btn-danger" value="clear" onClick="removeLastElement('Parameter');">
                    </td>
                </tr>  
            </tbody>
        </table>

        <div class="container">
            <h4>Json:</h4>
            <textarea id="Payload" rows="1" cols="10"></textarea>
            <h4>Labels:</h4>
            <ul id="request-tags">
            </ul>
        </div>
        <br><br><br>

        <div id="update-button-div">
            <table class="table">
                <tbody>
                    <tr>
                        <td>
                            <input type="submit" class="btn btn-primary" id="submit-request-info" value="Executar" align="left">
                        </td>
                        <td>
                            <input type="submit" class="btn btn-success" id="insert-request-info" value="Criar" align="center">
                            <input type="submit" class="btn btn-warning" id="update-request-info" value="Atualizar" align="right">
                            <input type="submit" class="btn btn-danger disabled" id="remove-request-info" value="Remover" align="right">
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!--input type="submit" class="btn btn-warning" id="update-request-info" value="Update"-->

        <br><br>
        <div class="container">
            <div id="response-div"></div>
        </div>
    </div>
    <br>   
</body>
</html>

