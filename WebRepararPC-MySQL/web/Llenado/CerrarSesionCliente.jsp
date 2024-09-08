<%-- 
    Document   : CerrarSesionCliente
    Created on : 14-12-2018, 3:59:06
    Author     : march
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("rut_cliente") != null) {
        //remueve el objeto asociado a la sesión.
        session.removeAttribute("session_objetCliente");
        response.sendRedirect("/WebRepararPC/HomeLogin.jsp");
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
