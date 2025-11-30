<%-- 
    Document   : Programas
    Created on : 20-07-2018, 4:25:08
    Author     : march
--%>

<% //<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <title>Prorgramas</title>
        <!-- Bootstrap -->
        <link href="../Estilo/css/bootstrap.min.css" rel="stylesheet">
        
        <link rel="shortcut icon" href="../Estilo/IMG/fallout.ico">
        <!-- Encabezado -->
        <%@include file="../Vistas/Head.jsp" %>
    </head>
    <body background="../Estilo/IMG/Fondo 1.png">
        <!-- BARRA MENU -->
        <section class="nav-border container-fluid">
            <article class="row">
                <nav class="navbar navbar-inverse">
                    <div class="container">
                        <div class="navbar-header">
                            <a role="button" class="navbar-toggle" data-toggle="collapse" data-target="#_navbar" aria-expanded="false">MENU</a>
                        </div>
                        <ul class="nav navbar-nav">
                            <li><a href="../Inicio.jsp">Inicio</a></li>
                            <li ><a href="MisionVision.jsp">MISION Y VISION</a></li>
                            <li><a href="Historia.jsp">HISTORIA</a></li> 
                            <li class="active"><a href="Programas.jsp">PROGRAMAS Y CENTROS</a></li> 
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="../RegAdmi.jsp"><span class="glyphicon glyphicon-user"></span> Registrar</a></li>
                            <li><a href="../Login.jsp"><span class="glyphicon glyphicon-log-in"></span> iniciar sesion</a></li>
                        </ul>
                    </div>
                </nav>
            </article>
        </section>
        
        <!-- NOTICIAS -->
        <section class="section">
            <article class="container">
                <div class="row">
                    <div class="col-md-4">
                        <img src="../Estilo/IMG/apliEscri.png" alt="Aplicacion" />
                        <a title="Aplicacion WEB" href="https://www.java.com/es/"><img src="../Estilo/IMG/apliWeb.png" alt="Web" /></a>
                    </div>
                </div>
            </article>
        </section>
        
        <section class="section">
            <article class="container">
                <div class="row">
                    <div class="col-md-4">
                        <img class="img-responsive" src="http://www.tecnivoz.com/tecnivoz/images/soporte3.jpg" alt="Web">
                    </div>
                    <div class="col-md-8">
                        <div class="descrip">
                            <p class="h2">PROGRAMAS:</p>
                            <ul>
                                <li><strong>Los mejores programas.</strong></li>
                                <li>Aplicaciones en JAVA</li>
                                <li>Paginas WEB en JSP</li>
                                <li>Aplicaciones en C# </li>
                                <li>Paginas WEB en PHP</li>

                            </ul>
                            <p>Vive la experiencia de nuestros programas en múltiples plataformas, con los mejores programadores del país…</p>

                            <p><i class="fa fa-calendar"></i> 05-07-2017 / <i class="fa  fa-user-o"></i> MBReparaciones Sl . Todos los derechos reservados</p>
                        </div>
                    </div>
                </div>
            </article>
        </section>
        
        <!-- Pie de Pagina -->
        <%@include file="../Vistas/Footer.jsp" %>
        
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="../Estilo/js/bootstrap.min.js"></script>
    </body>
</html>
