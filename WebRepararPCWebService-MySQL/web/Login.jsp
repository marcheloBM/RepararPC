<%-- 
    Document   : Login
    Created on : 26-07-2018, 0:32:11
    Author     : march
--%>
<!-- Verifica Si Hay Seccion Iniciada -->
<% if (session.getAttribute("sesion_rut") != null) {
        response.sendRedirect("/WebRepararPCWebService/HomeLogin.jsp");
        return;
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <title>Login</title>
        <!-- Bootstrap -->
        <link href="Estilo/css/bootstrap.min.css" rel="stylesheet">
                
        <link rel="shortcut icon" href="Estilo/IMG/fallout.ico">        
        
    <body background="Estilo/IMG/Fondo 1.png" oncontextmenu="return false">
        <!-- Encabezado -->
        <%@include file="Vistas/Head.jsp" %>
        
        <!-- BARRA MENU -->
        <section class="nav-border container-fluid">
            <article class="row">
                <nav class="navbar navbar-inverse">
                    <div class="container">
                        <div class="navbar-header">
                            <a role="button" class="navbar-toggle" data-toggle="collapse" data-target="#_navbar" aria-expanded="false">MENU</a>
                        </div>
                        <ul class="nav navbar-nav">
                            <li><a href="Inicio.jsp">INICIO</a></li>
                            <li><a href="Info/MisionVision.jsp">MISION Y VISION</a></li>
                            <li><a href="Info/Historia.jsp">HISTORIA</a></li> 
                            <li><a href="Info/Programas.jsp">PROGRAMAS Y CENTROS</a></li> 

                        </ul>

                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="RegAdmi.jsp"><span class="glyphicon glyphicon-user"></span> Registrar</a></li>
                            <li class="active"><a href="Login.jsp"><span class="glyphicon glyphicon-log-in"></span> iniciar sesion</a></li>
                        </ul>
                    </div>
                </nav>
            </article>
        </section>
        
        <!-- FORMULARIO -->
        <%@include file="Form/FormLogin.jsp" %>
        <!-- Espacio -->
        <%@include file="/Vistas/Espacio.jsp" %>
        <!-- Pie de Pagina -->
        <%@include file="/Vistas/Footer.jsp" %>
        
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="Estilo/js/bootstrap.min.js"></script>
        <script src="Estilo/js/validarRUT.js"></script>
        
        <!-- Mensaje de Error -->
        <c:if test="${not empty message}">
            <script>
                alert("${message}");
            </script>
        </c:if>
    </body>
</html>
