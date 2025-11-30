<%-- 
    Document   : Carousel
    Created on : 20-07-2018, 3:58:28
    Author     : march
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Carousel</title>
    </head>
    <body>
        <!-- CAROUSEL -->
        <section id="_carousel" class="carousel slide" data-ride="carousel">
            <!-- Indicators -->
            <ol class="carousel-indicators">
                <li data-target="#_carousel" data-slide-to="0" class="active"></li>
                <li data-target="#_carousel" data-slide-to="1"></li>
                <li data-target="#_carousel" data-slide-to="2"></li>
            </ol>
            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox" style="background-image: url('https://www.formassembly.com/images/illustrations/robot-msg-error.png');">
                <div class="item active">
                    <div class="carousel-caption">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-3 text-right">
                                    <img class="img-responsive carousel-img" src="Estilo/IMG/Obvius.png">
                                </div>
                                <div class="col-md-8 col-md-offset-1">
                                    <div class="carousel-info text-left">
                                        <p class="h2">CENTRO DE Reparaciones MB .... </p>
                                        <p class="hidden-xs">NUESTROS PROGRAMAS destinados en las mejores compañias para los mejores paises....</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <div class="carousel-caption">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-3 text-right">
                                    <img class="img-responsive carousel-img" src="Estilo/IMG/Obvius.png">
                                </div>
                                <div class="col-md-8 col-md-offset-1">
                                    <div class="carousel-info text-left">
                                        <p class="h2">PROGRAMAS UNIVERSALES</p>
                                        <p class="hidden-xs">Grandes paises de destino... </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <div class="carousel-caption">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-3 text-right">
                                    <img class="img-responsive carousel-img" src="Estilo/IMG/Obvius.png">
                                </div>
                                <div class="col-md-8 col-md-offset-1">
                                    <div class="carousel-info text-left">
                                        <p class="h2">PARA TODO USUARIO.</p>
                                        <p class="hidden-xs">Elije y prueba la experiencia de programasen distintas plataformas...</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
