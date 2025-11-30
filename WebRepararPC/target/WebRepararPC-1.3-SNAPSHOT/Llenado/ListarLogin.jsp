<%-- 
    Document   : ListarLogin
    Created on : 24-11-2018, 2:53:26
    Author     : march
--%>

<%@page import="Cl.Burgos.RepararPC.Enum.TipoLogin"%>
<%@page import="java.util.List"%>
<%@page import="Cl.Burgos.RepararPC.ENT.ClLogin"%>
<%@page import="Cl.Burgos.RepararPC.DAO.DAOLogin"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listar PDE | CEM</title>
    </head>
    <body>
        <div class="float-center">
            <table class="table table-hover">
        <tr align="center">
                     <td><strong>Rut</strong></td>
                     <td><strong>Nombre</strong></td>
                     <td><strong>Apellido</strong></td>
                     <td><strong>Correo</strong></td>
                     <td><strong>Celular</strong></td>
                     <td><strong>Passworld</strong></td>
                     <td><strong>Tipo Usuario</strong></td>
                 </tr>
                 <%
                     DAOLogin dAOLogin = new DAOLogin();
                     long registro = dAOLogin.leerCuantos("");
                     int reg = (int)registro;
                     List<ClLogin> list = dAOLogin.leerLogin(0, reg, "");
                     for (ClLogin elem : list) {
                         String id_login =String.valueOf(elem.getId());
                         String rut_login =elem.getRut();
                         String nombre_login=elem.getNombre();
                         String apellido_login=elem.getApellido();
                         String correo_login=elem.getCorreo();
                         String celular_login=elem.getCelular();
                         String pass_login=elem.getPassworld();
                         TipoLogin tipo_logi=elem.getTipoLogin();
                 %>
                 <form action="ServletLogin" method="post">
                     <tr align='center'>
                         <td><%=rut_login%></td>
                         <td><input name="txtNombre" type="text" id="txtNombre"  size='1' <%=nombre_login%>  class="form-control text-center" value="<%=nombre_login%>" /></td>
                         <td><input name="txtApellido" type="text" id="txtApellido"  size='1' <%=apellido_login%>  class="form-control text-center" value="<%=apellido_login%>" /></td>
                         <td><input name="txtCorreo" type="text" id="txtCorreo"  size='1' <%=correo_login%>  class="form-control text-center" value="<%=correo_login%>" /></td>
                         <td><input name="txtCelular" type="text" id="txtCelular"  size='1' <%=celular_login%>  class="form-control text-center" value="<%=celular_login%>" /></td>
                         <td><input name="txtPass" type="text" id="txtPass"  size='1' <%=pass_login%>  class="form-control text-center" value="<%=pass_login%>" /></td>
                         <td><%=tipo_logi%></td>
                    <td>
                        <input name="txtIdLogin" type="hidden" id="txtIdLogin" value="<%=id_login%>"/>
                        <input name="txtRut" type="hidden" id="txtRut" value="<%=rut_login%>"/>
                        <input name="txtTipoUsuario" type="hidden" id="txtPass" value="<%=tipo_logi%>"/>
                        <input class="btn btn-warning" type="submit" name="submit" value="ACTUALIZARLOGIN"> </td>
                     </tr>
                 </form>
                    <% }%>
            </table>
        </div>
    </body>
</html>

