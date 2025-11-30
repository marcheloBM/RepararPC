<%-- 
    Document   : Head
    Created on : 20-07-2018, 3:49:29
    Author     : march
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Head</title>
        <%
    
        String nombreUser = (String) (session.getAttribute("nombre")+" "+session.getAttribute("apellido"));

        %>
    </head>
    <body>
        <!-- ENCABEZADO -->
        <section class="header-border">
            <header class="header">
                <article class="container">
                    <div class="header-left">
                        <p class="header-title">
                            <a>Reparacione de Notebook</a>
                        </p>
                        <p>Programa de Registros</p>
                    </div>
                    <div class="header-right">
                        <ul class="header-ul">
                            <li><i class="fa fa-phone"></i> +56 9 907 1xx xx</li>
                            <li><i class="fa fa-envelope"></i> <a href="#">marchelo.1989@live.cl</a></li>
                        </ul>
                        <%
                            if (session.getAttribute("sesion_rut") != null) {
                                %>
                                <div class="header-right">
                                    <p class="header-title">
                                        <a><%=nombreUser%></a>
                                    </p>
                                </div>
                                <%
                            }
                        %>
                    </div>                  
                </article>
            </header>
        </section>
    </body>
</html>
