<%-- 
    Document   : ListarPC
    Created on : 28-11-2018, 3:35:28
    Author     : march
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@page import="Cl.Burgos.RepararPC.Enum.TipoReparacion"%>
<%@page import="java.util.List"%>
<%@page import="Cl.Burgos.RepararPC.ENT.ClComputador"%>
<%@page import="Cl.Burgos.RepararPC.DAO.DAOComputador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                     <td><strong>Marca</strong></td>
                     <td><strong>Modelo</strong></td>
                     <td><strong>Num Serie</strong></td>
                     <td><strong>RAM</strong></td>
                     <td><strong>HDD</strong></td>
                     <td><strong>Sistema Operativo</strong></td>
                     <td><strong>Arquitectura</strong></td>
                     <td><strong>Version</strong></td>
                     <td><strong>TipoRepa</strong></td>
                     <td><strong>Descripcion</strong></td>
                     <td><strong>Valor</strong></td>
                     <td><strong>Fecha</strong></td>
                 </tr>
                 <%
                     DAOComputador dAOComputador = new DAOComputador();
                     long registro = dAOComputador.leerCuantos("");
                     int reg = (int)registro;
                     List<ClComputador> list = dAOComputador.leerPC(0, reg, "");
                     for (ClComputador elem : list) {
                         String id_pc =String.valueOf(elem.getIdPC());
                         String marca_pc =elem.getMarca();
                         String modelo_pc =elem.getModelo();
                         String numSerie_pc =elem.getNumSerie();
                         String ram_pc =elem.getRam();
                         String hdd_pc =elem.getHdd();
                         String SO_pc =elem.getSistemaOpe();
                         String arquitec_pc =elem.getArquitectura();
                         String version_pc =elem.getVersion();
                         TipoReparacion tipoPC_pc =elem.getTipoRepa();
                         String descripcion_pc =elem.getDescripcion();
                         String valor_pc =String.valueOf(elem.getValor());
                         java.util.Date fecha_pc =elem.getFecha();
                         String idCliente_pc =String.valueOf(elem.getIdCliente());
                         String idLogin_pc =String.valueOf(elem.getIdLogin());
                 %>
                 <form action="ServletPC" method="post">
                     <tr align='center'>
                         <td><input name="txtMarca" type="text" id="txtMarca"  size='1' <%=marca_pc%>  class="form-control text-center" value="<%=marca_pc%>" /></td>
                         <td><input name="txtModelo" type="text" id="txtModelo"  size='1' <%=modelo_pc%>  class="form-control text-center" value="<%=modelo_pc%>" /></td>
                         <td><input name="txtNumSerie" type="text" id="txtNumSerie"  size='1' <%=numSerie_pc%>  class="form-control text-center" value="<%=numSerie_pc%>" /></td>
                         <td><input name="txtRam" type="text" id="txtRam"  size='1' <%=ram_pc%>  class="form-control text-center" value="<%=ram_pc%>" /></td>
                         <td><input name="txtHdd" type="text" id="txtHdd"  size='1' <%=hdd_pc%>  class="form-control text-center" value="<%=hdd_pc%>" /></td>
                         <td><input name="txtSO" type="text" id="txtSO"  size='1' <%=SO_pc%>  class="form-control text-center" value="<%=SO_pc%>" /></td>
                         <td><input name="txtArqui" type="text" id="txtArqui"  size='1' <%=arquitec_pc%>  class="form-control text-center" value="<%=arquitec_pc%>" /></td>
                         <td><input name="txtVersion" type="text" id="txtVersion"  size='1' <%=version_pc%>  class="form-control text-center" value="<%=version_pc%>" /></td>
                         <td><input name="txtTipoRep" type="text" id="txtTipoRep"  size='1' <%=tipoPC_pc%>  class="form-control text-center" value="<%=tipoPC_pc%>" /></td>
                         <td><input name="txtDescripcion" type="text" id="txtDescripcion"  size='1' <%=descripcion_pc%>  class="form-control text-center" value="<%=descripcion_pc%>" /></td>
                         <td><input name="txtValor" type="text" id="txtValor"  size='1' <%=valor_pc%>  class="form-control text-center" value="<%=valor_pc%>" /></td>

                         <%java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd"); %>
                         <td><input id="txtFecha" name="txtFecha" type="date" class="form-control"  value="<%= df.format(fecha_pc) %>"></td>
                         
                    <td>
                        <input name="txtIdPc" type="hidden" id="txtIdPc" value="<%=id_pc%>"/>
                        <input name="txtIdCliente" type="hidden" id="txtIdCliente" value="<%=idCliente_pc%>"/>
                        <input name="txtIdLogin" type="hidden" id="txtIdLogin" value="<%=idLogin_pc%>"/>
                        <input class="btn btn-warning" type="submit" name="submit" value="ACTUALIZARPC" ></td>
                    <td>
                        <input name="txtIdPc" type="hidden" id="txtIdPc" value="<%=id_pc%>"/>
                        <input class="btn btn-warning" type="submit" name="submit" value="ELIMINARPC" > </td>
                     </tr>
                 </form>
                    <% }%>
            </table>
        </div>
    </body>
</html>
