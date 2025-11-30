<%-- 
    Document   : RegAdmi
    Created on : 20-07-2018, 4:33:50
    Author     : march
--%>

<% //<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <title>Registro Administrador</title>

        <!-- Bootstrap -->
        <link href="Estilo/css/bootstrap.min.css" rel="stylesheet">
        <link href="Estilo/css/estilos.css" rel="stylesheet">
        
        <link rel="shortcut icon" href="Estilo/IMG/fallout.ico">
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
                            <li><a href="Inicio.jsp">INICIO</a></li>
                            <li><a href="Info/MisionVision.jsp">MISION Y VISION</a></li>
                            <li><a href="Info/Historia.jsp">HISTORIA</a></li> 
                            <li><a href="Info/Programas.jsp">PROGRAMAS Y CENTROS</a></li> 
                            <li><a href="Info/Contacto.jsp">CONTACTO</a></li> 
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li class="active"><a href="RegAdmi.jsp"><span class="glyphicon glyphicon-user"></span> Registrar</a></li>
                            <li><a href="Login.jsp"><span class="glyphicon glyphicon-log-in"></span> iniciar sesion</a></li>
                        </ul>
                    </div>
                </nav>
            </article>
        </section>
        
        <!--FOMULARIO-->
        <%@include file="Form/FormAdmi.jsp" %>
        <!-- Listar -->
        <%@include file="/Llenado/ListarLogin.jsp" %>
        <!-- Espacio -->
        <%@include file="/Vistas/Espacio.jsp" %>
        <!-- Pie de Pagina -->
        <%@include file="/Vistas/Footer.jsp" %>
         
        
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="Estilo/js/bootstrap.min.js"></script>
        
        <script src="Estilo/js/validarRUT.js"></script>
        <script src="Estilo/js/datepicker.js"></script>
        <script src="Estilo/js/selectactive.js" type="text/javascript"></script>
        
                            <!-- Mensaje de Error -->
        <c:if test="${not empty message}">
            <script>
                alert("${message}");
            </script>
        </c:if>
    
    </body>
</html>
