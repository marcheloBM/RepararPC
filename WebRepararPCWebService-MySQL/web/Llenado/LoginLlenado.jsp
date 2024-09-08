<%-- 
    Document   : LoginLlenado
    Created on : 22-10-2018, 2:37:19
    Author     : march
--%>

<%@page import="Cl.Burgos.RepararPC.WSDL.ClLogin"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%--Pregunto si se ha iniciado session --%>
<%
    if (session.getAttribute("sesion_rut") == null) {
            response.sendRedirect("/WebRepararPCWebService/Login.jsp");
            return;
    } else if (session.getAttribute("session_objetLogin")!=null) {
        
        Cl.Burgos.RepararPC.WSDL.ClLogin user = (ClLogin) session.getAttribute("session_objetLogin");
        session.setAttribute("id", user.getId());
        session.setAttribute("rut", user.getRut());
        session.setAttribute("nombre", ""+user.getNombre());
        session.setAttribute("apellido", ""+user.getApellido());
        session.setAttribute("correo", ""+user.getCorreo());
        session.setAttribute("celular", ""+user.getCelular());
        session.setAttribute("tipo_usuario", ""+user.getTipoLogin());
        //especifica el número de segundos que el servidor esperará entre peticiones para invalidar la sesión. Si el valor especificado es negativo, la sesión nunca expirará.
        //session.setMaxInactiveInterval(60*5);
        
    } 
%>
<html>
    
</html>
