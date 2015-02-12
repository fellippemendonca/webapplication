<%-- 
    Document   : IndexPage
    Created on : Jan 28, 2015, 2:59:04 PM
    Author     : fellippe.mendonca
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="addInput.js" language="Javascript" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Bootstrap -->
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="POST">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <FONT size=+1 color=black>Selecione o Ambiente: </FONT>
                </div>
                <div class="panel-body">
                    <div class="radio">
                        <label>
                            <input type="radio" name="envlist" value="PRD" checked>PRD
                        </label>
                    </div>
                    <div class="radio">
                        <label>
                            <input type="radio" name="envlist" value="HLG"        >HLG
                        </label>
                    </div>
                </div>

                <div class="panel-heading">
                    <FONT size=+1 color=black>Selecione o Lojista: </FONT>
                </div>
                <div class="panel-body">
                    <div class="radio">
                        <label>
                            <input type="radio" name="shoplist" value="3" checked>3-Toystore
                        </label>
                    </div>
                    <div class="radio">
                        <label>
                            <input type="radio" name="shoplist" value="4">4-Mockshop
                        </label>
                    </div>
                </div>
                <div>
                    <button class="btn btn-primary" type="submit">Executar</button>
                </div>

                <div id="section">
                    <div id="dynamicInput1">
                        <table class="table"><tbody>
                                <tr><td><input type="button" value="Add Header" onClick="addHeader('dynamicInput1');">
                            </tbody></table>  
                    </div>
                    <div id="dynamicInput2">
                        <table class="table"><tbody>
                                <tr><td><input type="button" value="Add Parameter" onClick="addParameter('dynamicInput2');">
                            </tbody></table>  
                    </div>
                </div>
            </div>
        </form>

        <jsp:include page="/servlet" flush="false" />


        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://code.jquery.com/jquery.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
