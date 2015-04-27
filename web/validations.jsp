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
        <title>Validações das APIs</title>

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
                    <li><a href="http://10.116.45.34:8080/servlet-stateless/insertions.jsp">Cadastro</a></li>
                    <li class="active"><a href="http://10.116.45.34:8080/servlet-stateless/validations.jsp">Validações</a></li>
                    <li><a href="http://10.116.45.34:8080/servlet-stateless/About.html">Sobre</a></li>
                    <li><a href="https://mktplace.atlassian.net/secure/Dashboard.jspa">Contato</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </nav>
    <br><br><br>
    </body>
</html>
