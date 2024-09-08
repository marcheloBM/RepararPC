<%-- 
    Document   : FormCliente
    Created on : 17-11-2018, 4:40:11
    Author     : march
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
        String idLogin = (String) ("" + session.getAttribute("id"));

        
        %>
<html>
    <div class="container" style="margin-top:50px;" >
        <form class="form-horizontal" method="post" action="ServletCliente">
            <fieldset>
                <div id="edit_farmer" style="display:none"></div>
                <div class="row">
                    <div class="col-md-2 panel panel-heading">Informacion Cliente</div>
                    <div class="col-md-4 panel panel-heading" style="display:none; color:red" id="contact_error"></div>
                </div>
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtId">ID</label>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txt_id" maxlength="10" name="txtId" class="form-control input-md ac_village" required="" type="number" value="1">

                        </div>
                    </div>
                    <label class="col-md-1 control-label" for="txtRut">RUT</label>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txt_rut" maxlength="10" name="txtRut" required oninput="checkRut(this)" placeholder="11.111.111-1" class="form-control input-md" value="17.008.864-6">

                        </div>
                    </div>
                </div>
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtNombre">Nombre</label>  
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txtnombre" minlength="4" name="txtNombre" placeholder="" required class="form-control input-md" type="text" value="Marcelo">
                        </div>
                    </div> 
                    <label class="col-md-1 control-label" for="txtApellido">Apellidos</label>  
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txtapellido" minlength="4" name="txtApellido" placeholder="" class="form-control input-md" type="text" value="Burgos">
                        </div>
                    </div>
                </div>
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtCorreo">Email</label>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-home"></i>
                            </span>

                            <input id="txt_email" name="txtCorreo" placeholder="pepito@mail.com" class="form-control input-md ac_village" pattern="^[-\w.]+@{1}[-a-z0-9]+[.]{1}[a-z]{2,5}$" type="text" value="marchelo.1989@live.cl">
                        </div> 
                    </div>
                    <label class="col-md-1 control-label" for="txtCelular"  >Celular</label>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-home"></i>
                            </span>

                            <input id="txtTelefono" name="txtCelular" placeholder="911111111" class="form-control input-md ac_village" type="number" max="999999999" value="990715586">
                        </div> 
                    </div>
                </div>              
                
                <div class="form-group row">
                    <div class="col-md-8 text-center">
                        <input name="txtIdLogin" type="hidden" id="txtIdLogin" value="<%=idLogin%>"/>
                        <button id="registrar_user" value="REGISTRARCLIENTE" type="submit" name="submit" class="btn btn-large btn-success"> Registrar</button>
                        <button class="btn btn-large btn-danger" type="button" onclick=history.go(-1)> Cancelar </button>
                    </div>
                </div>
                            
            </fieldset>
        </form>
        
        
    </div>
</html>
