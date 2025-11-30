<%-- 
    Document   : HomeCliente
    Created on : 17-11-2018, 4:45:54
    Author     : march
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <title>Mantenedor Cliente</title>
        <!-- Bootstrap -->
        <link href="Estilo/css/bootstrap.min.css" rel="stylesheet">
        <link href="Estilo/css/estilos.css">
        
        <!-- Icono de la Pagina -->
        <link rel="shortcut icon" href="Estilo/IMG/fallout.ico">
                
        <!-- Verifica Si Hay Seccion Iniciada -->
        <%@include file="Llenado/LoginLlenado.jsp" %>
        <%
    
        String tipo_usuario = (String) ("" + session.getAttribute("tipo_usuario"));

        String nombre_cel_sesion = (String) ("" + session.getAttribute("nombre"));


        if (tipo_usuario.equals("Usuario"))
        {
            tipo_usuario=tipo_usuario + " - " +"("+nombre_cel_sesion+")";
        }
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
                            <li><a href="HomeLogin.jsp">HOME</a></li>
                            <li class="active"><a href="HomeCliente.jsp">PERFIL CLIENTES</a></li>
                            <li><a href="HomePC.jsp">COMPUTADORES</a></li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="./Llenado/CerrarSesion.jsp"><span class="glyphicon glyphicon-user"></span> Cerrar Sesion</a></li>
                        </ul>
                    </div>
                </nav>
            </article>
        </section>
        
        <!-- Formulario -->
        <%@include file="/Form/FormCliente.jsp" %>
        
        <!-- Listar -->
        <%@include file="/Llenado/ListarCliente.jsp" %>
        
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
