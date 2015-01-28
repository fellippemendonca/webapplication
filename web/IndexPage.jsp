<%-- 
    Document   : IndexPage
    Created on : Jan 28, 2015, 2:59:04 PM
    Author     : fellippe.mendonca
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="POST">
            <p><FONT size=+1 color=black>Selecione o Ambiente: </FONT><br>
                <input type="radio" name="envlist" value="PRD" checked>PRD<br>
                <input type="radio" name="envlist" value="HLG"        >HLG<br>
            <p>
            <p><FONT size=+1 color=black>Selecione o Lojista: </FONT><br>
                <input type="radio" name="shoplist" value="3" checked>3-Toystore<br>
                <input type="radio" name="shoplist" value="4"        >4-Mockshop<br>
            <p><input type="submit" name="sub">
        </form>
        <jsp:include page="/servlet" flush="true" />
    </body>
</html>
