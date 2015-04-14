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


                <div id="section">

                    <table class="table">
                        <tbody>
                        <td>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Environment</span>
                                <input type="text" class="form-control" size="5" placeholder="HLG" aria-describedby="basic-addon1">
                            </div>
                        </td>
                        <td>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Scheme</span>
                                <input type="text" class="form-control" size="5" placeholder="http" aria-describedby="basic-addon1">
                            </div>
                        </td>
                        <td>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Host</span>
                                <input type="text" class="form-control" size="5" placeholder="api.extra.com.br" aria-describedby="basic-addon1">
                            </div>
                        </td>
                        <td>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">Path</span>
                                <input type="text" class="form-control" size="5" placeholder="api/v1/sellerItems" aria-describedby="basic-addon1">
                            </div>
                        </td>   
                        <td>
                            <div id="dynamicInput3" align="left">
                                <span class="input-group-addon" id="basic-addon1">Add Template:</span>
                                <input type="button" class="btn btn-primary" value="+" onClick="addTemplate('dynamicInput3');">
                            </div>
                        </td>
                        </tbody>
                    </table>

                    <table class="table"><tbody>
                        <td>
                            <div id="dynamicInput1" align="left">
                                <span class="input-group-addon" id="basic-addon1">Add Header:</span>
                                <input type="button" class="btn btn-primary" value="+" onClick="addHeader('dynamicInput1');">
                            </div>
                        </td>
                        <td>
                            <div id="dynamicInput2" align="left">
                                <span class="input-group-addon" id="basic-addon1">Add Parameter:</span>
                                <input type="button"  class="btn btn-primary" value="+" onClick="addParameter('dynamicInput2');">
                            </div>
                        </td>
                        </tbody></table>
                </div>
                <div>
                    <button class="btn btn-primary" id="execute-requests" type="submit">Executar</button>
                </div>
            </div>
        </form>

        <!--jsp:include page="/servlet" flush="false" /-->

        </br>Ajax Requests
        <div id="requests-results"></div>
        </br>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://code.jquery.com/jquery.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>
