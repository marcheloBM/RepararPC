<%-- 
    Document   : FormAdmi
    Created on : 20-07-2018, 4:33:01
    Author     : march
--%>
<%@page import="Cl.Burgos.RepararPC.Inter.Confi"%>

<%@page import="Cl.Burgos.RepararPC.Enum.TipoLogin"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <div class="container" style="margin-top:50px;" >
        <form class="form-horizontal" method="post" action="ServletLogin">
            <fieldset>
                <div id="edit_farmer" style="display:none"></div>
                <div class="row">
                    <div class="col-md-2 panel panel-heading">Informacion Personal</div>
                    <div class="col-md-4 panel panel-heading" style="display:none; color:red" id="contact_error"></div>
                </div>
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtRut">RUT</label>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txt_rut" maxlength="10" name="txtRut" required oninput="checkRut(this)" placeholder="11.111.111-1" class="form-control input-md" value="17.008.864-6">

                        </div>
                    </div>
                    <label class="col-md-1 control-label" for="txtNombre">Nombre</label>  
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txtnombre" minlength="4" name="txtNombre" placeholder="" required class="form-control input-md" type="text" value="Marcelo">
                        </div>
                    </div> 
                </div>
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtApellido">Apellidos</label>  
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txtapellido" minlength="4" name="txtApellido" placeholder=""  required class="form-control input-md" type="text" value="Burgos">
                        </div>
                    </div>
                    <label class="col-md-1 control-label" for="txtCorreo">Email</label>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-home"></i>
                            </span>

                            <input id="txt_email" name="txtCorreo" placeholder="pepito@mail.com" class="form-control input-md ac_village" required pattern="^[-\w.]+@{1}[-a-z0-9]+[.]{1}[a-z]{2,5}$" type="text" value="marchelo.1989@live.cl">
                        </div> 
                    </div>
                </div>
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtCelular"  >Celular</label>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-home"></i>
                            </span>

                            <input id="txtTelefono" name="txtCelular" placeholder="911111111" class="form-control input-md ac_village" required="" type="number" max="999999999" value="990715586">
                        </div> 
                    </div>
                    <label class="col-md-1 control-label" for="txtPassword"  >Contraseña</label>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-home"></i>
                            </span>

                            <input id="txtPassword" name="txtPassword" placeholder="" class="form-control input-md ac_village" required="" type="password"max="" value="HP2117la">
                        </div> 
                    </div>
                </div>
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtTipoLogin">Tipo Usuario</label>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-list"></i>
                            </span>
                            <select id="txtTipoLogin" name="txtTipoLogin" class="form-control input-md">
                                <option value="Administrador">Administrador</option>
                                <option value="Usuario">Usuario</option>
                            </select>
                        </div>
                    </div>
                </div>
                            
                <div class="form-group row">
                    <div class="col-md-8 text-center">
                        <button id="crear_user" value="REGISTRARLOGIN" type="submit" name="submit" class="btn btn-large btn-success"> Registrar</button>
                        <button class="btn btn-large btn-danger" type="button" onclick=history.go(-1)> Cancelar </button>
                    </div>
                </div>
                            
            </fieldset>
        </form>
        
        
    </div>
        
</html>
