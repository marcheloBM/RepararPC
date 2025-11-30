<%-- 
    Document   : FormPC
    Created on : 18-11-2018, 4:36:30
    Author     : march
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
        //Validar Login
        String idLogin = (String) ("" + session.getAttribute("id"));
        //Validar Cliente
        String idCliente = (String) (""+session.getAttribute("id_cliente"));
        
        %>
<html>
    
    <div class="container" style="margin-top:50px;" >
        <form class="form-horizontal" method="post" action="ServletPC">
            <fieldset>
                <div id="edit_farmer" style="display:none"></div>
                <div class="row">
                    <div class="col-md-2 panel panel-heading">Informacion PC</div>
                    <div class="col-md-4 panel panel-heading" style="display:none; color:red" id="contact_error"></div>
                </div>
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtIdPC">ID</label>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txtIdPC" maxlength="10" name="txtIdPC" class="form-control input-md ac_village" type="number" value="">
                        </div>
                    </div>
                    <label class="col-md-1 control-label" for="txtMarca">Marca</label>  
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txtMarca" minlength="4" name="txtMarca" placeholder="" required class="form-control input-md" type="text" value="<%= request.getAttribute("marca") %>">
                        </div>
                    </div> 
                </div>
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtModelo">Modelo</label>  
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txtModelo" minlength="4" name="txtModelo" placeholder="" required class="form-control input-md" type="text" value="<%= request.getAttribute("modelo") %>">
                        </div>
                    </div>
                    <label class="col-md-1 control-label" for="txtNumeroSerie">N°Serie</label>  
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txtNumeroSerie" minlength="4" name="txtNumeroSerie" placeholder=""  required class="form-control input-md" type="text" value="<%= request.getAttribute("numSerie") %>">
                        </div>
                    </div>
                </div>
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtRam">RAM</label>  
                    <div class="col-md-2">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txtRam" minlength="1" name="txtRam" placeholder="" required class="form-control input-md" type="text" value="null">
                            <select name="cbo_capacidad_ram" id="cbo_alumnos_maximo" class="form-control" >
                                    <option value="0">Seleccionar</option>
                                    <option value="MB">MB</option>
                                    <option value="GB">GB</option>
                                    <option value="TB">TB</option>
                              </select>
                        </div>
                    </div>
                    <label class="col-md-2 control-label" for="txtHdd">HDDC:</label>
                    <div class="col-md-2">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-home"></i>
                            </span>
                            <input id="txtHdd" name="txtHdd" minlength="2" placeholder="" required class="form-control input-md" type="text" value="null">
                            <select name="cbo_capcidad_hdd" id="cbo_alumnos_maximo" class="form-control" >
                                    <option value="0">Seleccionar</option>
                                    <option value="MB">MB</option>
                                    <option value="GB">GB</option>
                                    <option value="TB">TB</option>
                              </select>
                        </div> 
                    </div>
                </div>
                
                <div id="edit_farmer" style="display:none"></div>
                    <div class="row">
                        <div class="col-md-2 panel panel-heading">Sistema Operarivo</div>
                        <div class="col-md-4 panel panel-heading" style="display:none; color:red" id="contact_error"></div>
                    </div>
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtSO"  >Sistema Operarivo</label>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-home"></i>
                            </span>
                            <input id="txtSO" name="txtSO" placeholder="Windows XX" class="form-control input-md ac_village" type="text" value="<%= request.getAttribute("OS") %>">
                        </div> 
                    </div>
                    <label class="col-md-1 control-label" for="txtArqui">Arquitectura</label>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txtArqui" maxlength="10" name="txtArqui" class="form-control input-md ac_village" required="" type="text" value="<%= request.getAttribute("arqui") %>">

                        </div>
                    </div>
                </div>                
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtVersion">Version</label>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txtVersion" maxlength="10" name="txtVersion" class="form-control input-md ac_village" type="text" value="<%= request.getAttribute("vercion") %>" required>

                        </div>
                    </div>
                </div>
                
                <div id="edit_farmer" style="display:none"></div>
                    <div class="row">
                        <div class="col-md-2 panel panel-heading">Info Reparacion</div>
                        <div class="col-md-4 panel panel-heading" style="display:none; color:red" id="contact_error"></div>
                    </div>
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtTipoReparacion">Tipo Reparcion</label>  
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <select name="cbo_tipo_rapracion" id="cbo_alumnos_maximo" class="form-control" >
                                    <option value="0">Seleccionar</option>
                                    <option value="Formateo">Formateo</option>
                                    <option value="Limpieza">Limpieza</option>
                                    <option value="Instalacion">Instalacion</option>
                                    <option value="Reparacion">Reparacion</option>
                              </select>
                        </div>
                    </div>
                    <%java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
                    Date fec=null;
                        if(request.getAttribute("fechaIns")!=null){
                            fec = (Date)request.getAttribute("fechaIns");
                        }else{
                            fec = new java.util.Date();
                        }
                    %>
                    <label class="col-md-1 control-label" for="txtFecha">Fecha</label>  
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txtFecha" name="txtFecha" required class="form-control input-md" type="date" value="<%= df.format(fec) %>">
                        </div>
                    </div>
                </div>
                
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtDescripcion">Descripcion</label>  
                    <div class="col-md-8">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txtDescripcion" minlength="4" name="txtDescripcion" placeholder="" class="form-control input-md" type="text" value="null" required>
                        </div>
                    </div>
                </div>
                <div class="row form-group">
                    <label class="col-md-1 control-label" for="txtValor">Valor</label>  
                    <div class="col-md-2">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                            <input id="txtValor" minlength="4" name="txtValor" placeholder=""  class="form-control input-md" type="number" value="">
                        </div>
                    </div>
                </div>
                
                <div class="form-group row">
                    <div class="col-md-8 text-center">
                        <input name="txtIdLogin" type="hidden" id="txtIdLogin" value="<%=idLogin%>"/>
                        <input name="txtIdCliente" type="hidden" id="txtIdCliente" value="<%=idCliente%>"/>
                        <button id="crear_user" value="REGISTRARPC" type="submit" name="submit" class="btn btn-large btn-success"> Registrar</button>
                        <button id="consultar" value="CONSULTARPC" type="submit" name="submit" class="btn btn-large btn-success"> Consultar</button>
                        <button class="btn btn-large btn-danger" type="button" onclick=history.go(-1)> Cancelar </button>
                    </div>
                </div>
                            
            </fieldset>
        </form>
        
        
    </div>
</html>
