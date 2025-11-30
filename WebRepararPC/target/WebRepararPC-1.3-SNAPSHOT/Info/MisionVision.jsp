<%-- 
    Document   : MisionVision
    Created on : 20-07-2018, 4:14:47
    Author     : march
--%>

<% //<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Misión y Visión</title>
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
                            <li class="active"><a href="MisionVision.jsp">MISION Y VISION</a></li>
                            <li><a href="Historia.jsp">HISTORIA</a></li> 
                            <li><a href="Programas.jsp">PROGRAMAS Y CENTROS</a></li> 
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="../RegAdmi.jsp"><span class="glyphicon glyphicon-user"></span> Registrar</a></li>
                            <li><a href="../Login.jsp"><span class="glyphicon glyphicon-log-in"></span> Iniciar Sesion</a></li>
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
                        <img class="img-responsive" src="https://www.formassembly.com/images/illustrations/robot-msg-error.png" alt="">
                    </div>

                    <!-- INFORMACION -->
                    <section class="wp-separator">
                        <article class="section">
                            <div class="container">
                                <div class="row text-center">
                                    <p class="h2">Nuestra Misión...</p>
                                    <p class="lead">Nuestro equipo está dedicado a ofrecer la mejor aplicación posible para nuestros usuarios. Al ser una empresa de calidad de servicio, creemos en la importancia de ofrecer a nuestros clientes un servicio de calidad y certificado para nuestras aplicaciones.</p>
                                    <p class="h2">Nuestra Visión...</p>
                                    <p class="lead">Llegar a ser una gran Empresa de software, reconocido por su prestigio y compromiso para generar optimas aplicaciones, resolver necesidades de su entorno y fortalecer productos de calidad...</p>
                                </div>
                            </div>
                        </article>
                    </section>
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
    </body>
</html>
