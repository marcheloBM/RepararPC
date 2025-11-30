<%-- 
    Document   : FormBuscarCliente
    Created on : 02-12-2018, 19:00:46
    Author     : march
--%>

<%@page import="java.util.List"%>
<%@page import="Cl.Burgos.RepararPC.ENT.ClCliente"%>
<%@page import="Cl.Burgos.RepararPC.DAO.DAOCliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
            //Validar Login
            String id_Login1 = (String) (""+session.getAttribute("id"));
%>
<html>
    <div class="container" style="margin-top:50px;" >
        <form class="form-horizontal" method="post" action="ServletCliente">
            <fieldset>
                <div id="edit_farmer" style="display:none"></div>
                <div class="row">
                    <div class="col-md-2 panel panel-heading">Clientes</div>
                    <div class="col-md-4 panel panel-heading" style="display:none; color:red" id="contact_error"></div>
                </div>
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtRut">RUT</label>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <select name="cbo_tipo_cliente" id="cbo_tipo_programa" class="form-control" style="width: 150px;">
                                <% DAOCliente  dAOCliente = new DAOCliente();
                            List<ClCliente> lista1 = dAOCliente.leerClienteIdLogin(Integer.parseInt(id_Login1));
                                                    for (ClCliente cat : lista1)
                                                    {
                                                        String rut_cliente = ""+cat.getRut();
                                                        String nombre_cliente = ""+cat.getNombre();
                            %>
                             <option value="<%=rut_cliente%>"><%=rut_cliente%></option>
                              <% }%>
                             </select>
                        </div>
                    </div><div class="col-md-4">
                        <button id="crear_user" value="BUSCARCLIENTE" type="submit" name="submit" class="btn btn-large btn-success">Buscar</button>
                        <button class="btn btn-large btn-danger" type="button" onclick=history.go(-1)> Cancelar </button>
                    </div>
                </div>
            </fieldset>
        </form>
        
        
    </div>
</html>
