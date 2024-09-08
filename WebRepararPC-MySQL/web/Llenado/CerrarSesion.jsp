<%-- 
    Document   : cerrar_sesion
    Created on : 18-oct-2017, 23:15:33
    Author     : Casa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("sesion_rut") != null) {
        session.invalidate();
        response.sendRedirect("/WebRepararPC/Login.jsp");
        return;
    } 
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>