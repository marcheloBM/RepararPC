<%-- 
    Document   : ListarCliente
    Created on : 20-11-2018, 3:11:14
    Author     : march
--%>

<%@page import="Cl.Burgos.RepararPC.ENT.ClCliente"%>
<%@page import="Cl.Burgos.RepararPC.DAO.DAOCliente"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%String idL = (String) ("" + session.getAttribute("id"));%>
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
                 </tr>
                 <%
                     DAOCliente dAOCliente = new DAOCliente();
                     List<ClCliente> list = dAOCliente.leerClienteIdLogin(Integer.parseInt(idL));
                     for (ClCliente elem : list) {
                         String id_cliente=String.valueOf(elem.getId());
                         String rut_cliente =elem.getRut();
                         String nombre_cliente=elem.getNombre();
                         String apellido_cliente=elem.getApellido();
                         String correo_cliente=elem.getCorreo();
                         String celular_cliente=elem.getCelular();
                         String id_loginCliente =String.valueOf(elem.getIdLogin());
                 %>
                 <form action="ServletCliente" method="post">
                     <tr align='center'>
                    <td><%=rut_cliente%></td>
                    <td><input name="txtNombre" type="text" id="txtNombre"  size='1' <%=nombre_cliente%>  class="form-control text-center" value="<%=nombre_cliente%>" /></td>
                    <td><input name="txtApellido" type="text" id="txtApellido"  size='1' <%=apellido_cliente%>  class="form-control text-center" value="<%=apellido_cliente%>" /></td>
                    <td><input name="txtCorreo" type="text" id="txtCorreo"  size='1' <%=correo_cliente%>  class="form-control text-center" value="<%=correo_cliente%>" /></td>
                    <td><input name="txtCelular" type="text" id="txtCelular"  size='1' <%=celular_cliente%>  class="form-control text-center" value="<%=celular_cliente%>" /></td>
                    <td>
                        <input name="txtId" type="hidden" id="txtId" value="<%=id_cliente%>"/>
                        <input name="txtRut" type="hidden" id="txtRut" value="<%=rut_cliente%>"/>
                        <input name="txtIdLogin" type="hidden" id="txtIdLogin" value="<%=id_loginCliente%>"/>
                        <input class="btn btn-warning" type="submit" name="submit" value="ACTUALIZARCLIENTE"> </td>
                     </tr>
                 </form>
                    <% }%>
            </table>
        </div>
    </body>
</html>
