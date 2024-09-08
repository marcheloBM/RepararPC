<%-- 
    Document   : Contacto
    Created on : 20-07-2018, 4:41:05
    Author     : march
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <%-- <link rel="icon" href="../../../../favicon.ico"> --%>
        
        <title>Centro de Estudios Montreal</title>
        
        <!-- Bootstrap core CSS -->
        <link href="../Estilo/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link href="pricing.css" rel="stylesheet">
        
        <link rel="shortcut icon" href="../Estilo/IMG/fallout.ico">
        
    </head>
    <body>
        <%-- <%@include file="../Vistas/menu.jsp" %> --%>
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
                            <li><a href="MisionVision.jsp">MISION Y VISION</a></li>
                            <li class="active"><a href="Historia.jsp">HISTORIA</a></li> 
                            <li><a href="Programas.jsp">PROGRAMAS Y CENTROS</a></li> 
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="../RegAdmi.jsp"><span class="glyphicon glyphicon-user"></span> Registrar</a></li>
                            <li><a href="../Login.jsp"><span class="glyphicon glyphicon-log-in"></span> iniciar sesion</a></li>
                        </ul>
                    </div>
                </nav>
            </article>
        </section>
        
        <section>
            <div class="container" align="center">
                <center><h2>Contacto</h2></center>
                <br>
                <div>
                    <p class="lead" align="justify">
                Si posees dudas de todo tipo, no dudes en contactarte con nosotros para aclarar todo tipo de 
                incertidumbres. Serás notificado a la brevedad.
                    </p>
                </div>
                <div>
                    <form action="">
                        <table border="0">
                            <tr>
                                <td><strong>Nombre:</strong></td>
                                <td>&nbsp<input id="email" class="input" name="email" type="text" value="" size="30" /></td>
                            </tr>
                            <tr>
                                <td><strong>Correo:</strong></td>
                                <td>&nbsp<input id="email" class="input" name="email" type="text" value="" size="30" /></td>
                            </tr>
                            <tr>
                                <td><strong>Telefono:</strong></td>
                                <td>&nbsp<input id="email" class="input" name="email" type="text" value="" size="30" /></td>
                            </tr>
                            <tr>
                                <td><strong>Mensaje</strong></td>
                                <td>&nbsp<textarea id="message" class="input" name="message" rows="8" cols="40"></textarea></td>
                            </tr>
                            <tr>
                                <td  colspan="2" align="center"> <input class="btn btn-warning" type="submit" name="submit" value="ENVIAR"></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </section>
    </body>
</html>
