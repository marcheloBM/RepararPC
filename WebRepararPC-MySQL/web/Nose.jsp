<%-- 
    Document   : Nose
    Created on : 21-07-2018, 19:46:14
    Author     : march
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Incluye lo anterior en tu etiqueta HEAD ---------->
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <title>Registrar</title>
        
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
                            <li><a href="../CRUD/Inicio.jsp">INICIO</a></li>
                            <li><a href="../CRUD/Misionvision.jsp">MISION Y VISION</a></li>
                            <li><a href="../CRUD/Historia.jsp">HISTORIA</a></li> 
                            <li><a href="../CRUD/Programas.jsp">PROGRAMAS Y CENTROS</a></li> 
                            <li><a href="../CRUD/Vistas/Error.jsp">CONTACTO</a></li> 
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li class="active"><a href="../CRUD/Registrar.jsp"><span class="glyphicon glyphicon-user"></span> Registrar</a></li>
                            <li><a href="login.jsp"><span class="glyphicon glyphicon-log-in"></span> iniciar sesion</a></li>
                        </ul>
                    </div>
                </nav>
            </article>
        </section>
        
        <section class="nav-border container-fluid">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <h1>Registrar</h1>
                        <!--                    <p>[FTL] </p>-->
                    </div>
                    <a href="../CRUD/RegAdmi.jsp">  
                        <img src="Estilo/IMG/Administrador.png" style="width:250px;height:220px;border:0;"/>
                    </a>
                    <!--                    <div class="col-md-2 col-md-offset-4 col-xs-8 col-xs-offset-2">
                                            <a href="registrar_alumno.jsp" class="btn btn-primary btn-block" type="button" role="button">Alumno</a>
                    
                    
                                        </div>-->
                    <a href="registrar_familia.jsp">  
                        <img src="Estilo/IMG/Cliente.png" style="width:250px;height:220px;border:0;"/>
                    </a>
                    <!--                    <div class="col-lg-offset-0 col-md-2 col-md-offset-0 col-sm-offset-2 col-xs-8 col-xs-offset-2">
                                            <a href="registrar_familia.jsp" class="btn btn-primary btn-block" type="button" role="button">Familia</a>
                                        </div>-->

                    <a type="button" value="Regresar" name="Regresar" onclick="history.back()" role="button">  
                        <img src="Estilo/IMG/Flecha.png" style="width:60px;height:60px;border:0;"/>
                    </a>

                </div>
            </div>
<!--            <div class="col-md-4 col-md-offset-4 col-xs-8 col-xs-offset-2">
                <a type="button" value="Regresar" class="btn btn-primary btn-block" name="Regresar" onclick="history.back()" role="button">regresar</a>

            </div>-->
<!--            <div class="container">
                <img src="imagenes/FLECHA.png" alt="Snow">
                <button class="btn">Button</button>
            </div>-->
        </section>
        
        <!-- Pie de Pagina -->
        <%@include file="/Vistas/Footer.jsp" %>
        
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="Estilo/js/bootstrap.min.js"></script>
    </body>
</html>
