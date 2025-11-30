<%-- 
    Document   : HomeLogin
    Created on : 22-10-2018, 3:10:57
    Author     : march
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Cl.Burgos.RepararPC.ENT.ClLogin"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <title>Mantenedor</title>
        <!-- Bootstrap -->
        <link href="Estilo/css/bootstrap.min.css" rel="stylesheet">
        <link href="Estilo/css/estilos.css">
        
        <!-- Icono de la Pagina -->
        <link rel="shortcut icon" href="Estilo/IMG/fallout.ico">
                
        <!-- Verifica Si Hay Seccion Iniciada -->
        <%@include file="Llenado/LoginLlenado.jsp" %>
        <!-- Verifica Si Hay Seccion Cliente -->
        <%@include file="Llenado/ClienteLlenado.jsp" %>
        <%
            //Validar Login
            String id_Login = (String) (""+session.getAttribute("id"));
            
            //Validar Cliente
            String id_Cliente = (String) (""+session.getAttribute("id_cliente"));
            
            String nom_cliente = (String) (""+session.getAttribute("nombre_cliente"));
            String ape_cliente = (String) (""+session.getAttribute("apellido_cliente"));
            String nombreComple =" - " +"("+nom_cliente+" "+ape_cliente+")";
            
        %>
        
        <!-- Encabezado -->
        <%@include file="Vistas/Head.jsp" %>
    </head>
    <body background="Estilo/IMG/Fondo 1.png">
        <!-- BARRA MENU -->
        <section class="nav-border container-fluid">
            <article class="row">
                <nav class="navbar navbar-inverse">
                    <div class="container">
                        <div class="navbar-header">
                            <a role="button" class="navbar-toggle" data-toggle="collapse" data-target="#_navbar" aria-expanded="false">MENU</a>
                        </div>
                        <ul class="nav navbar-nav">
                            <li class="active"><a href="HomeLogin.jsp">HOME</a></li>
                            <li><a href="HomeCliente.jsp">PERFIL CLIENTES</a></li>
                            <li><a href="HomePC.jsp">COMPUTADORES</a></li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="./Llenado/CerrarSesion.jsp"><span class="glyphicon glyphicon-user"></span> Cerrar Sesion</a></li>
                        </ul>
                    </div>
                </nav>
            </article>
        </section>
                
        <%
            if(id_Cliente.equals("0")){%>
            <!-- Formulario Buscar Cliente-->
            <%@include file="/Form/FormBuscarCliente.jsp" %>
        <%
            }else {%>
            <div class="header-left">
                <p class="header-title"><a>Cliente: <%out.println(session.getAttribute("rut_cliente")); %><%=nombreComple%></a></p>
            </div>
            <button class="btn btn-large btn-danger" type="button" onclick="location='./Llenado/CerrarSesionCliente.jsp'"> Cancelar </button>
            <!-- Formulario -->
            <%@include file="/Form/FormPC.jsp" %>
            <%}%>
        <%
            if(!id_Cliente.equals("0")){%>
            <!-- Formulario Buscar Pc x id Cliente-->
            <%@include file="./Llenado/ListarPCCliente.jsp" %>
        <%}%>
                
        <!-- Espacio -->
        <%@include file="/Vistas/Espacio.jsp" %>
        
        <!-- Pie de Pagina -->
        <%@include file="/Vistas/Footer.jsp" %>
        
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="Estilo/js/bootstrap.min.js"></script>
        <!-- Mensaje de Error -->
        <c:if test="${not empty message}">
            <script>
                alert("${message}");
            </script>
        </c:if>
    </body>
</html>
