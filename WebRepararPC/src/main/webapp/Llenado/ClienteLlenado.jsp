<%-- 
    Document   : ClienteLlenado
    Created on : 14-12-2018, 3:06:59
    Author     : march
--%>

<%@page import="Cl.Burgos.RepararPC.ENT.ClCliente"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%--Pregunto si se ha iniciado session --%>
<%
    if (session.getAttribute("session_objetCliente")!=null) {
        
        ClCliente cliente = (ClCliente) session.getAttribute("session_objetCliente");
        session.setAttribute("id_cliente", cliente.getId());
        session.setAttribute("rut_cliente", cliente.getRut());
        session.setAttribute("nombre_cliente", ""+cliente.getNombre());
        session.setAttribute("apellido_cliente", ""+cliente.getApellido());
        session.setAttribute("correo_cliente", ""+cliente.getCorreo());
        session.setAttribute("celular_cliente", ""+cliente.getCelular());
        
        
        
    } else{
        session.setAttribute("id_cliente", "0");
    }
%>
<html>
    
</html>
