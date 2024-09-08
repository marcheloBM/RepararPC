/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.Service;

import Cl.Burgos.RepararPC.DAO.DAOCliente;
import Cl.Burgos.RepararPC.ENT.ClCliente;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Marchelo
 */
@WebService(serviceName = "WebClienteService")
public class WebClienteService {

    DAOCliente dAOCliente = new DAOCliente();
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "insertarCliente")
    public boolean insertarCliente(@WebParam(name = "rut") String rut, @WebParam(name = "nombre") String nombre, @WebParam(name = "apellido") String apellido, 
            @WebParam(name = "correo") String correo, @WebParam(name = "celular") String celular, @WebParam(name = "idLogin") int idLogin) throws Exception{
        ClCliente clCliente = new ClCliente(rut, nombre, apellido, correo, celular, idLogin);
        return dAOCliente.sqlInsert(clCliente);
    }
    
    @WebMethod(operationName = "actualizarCliente")
    public boolean actualizarCliente(@WebParam(name = "id") int id, @WebParam(name = "rut") String rut, @WebParam(name = "nombre") String nombre, @WebParam(name = "apellido") String apellido, 
            @WebParam(name = "correo") String correo, @WebParam(name = "celular") String celular, @WebParam(name = "idLogin") int idLogin) throws Exception{
        ClCliente clCliente = new ClCliente(id, rut, nombre, apellido, correo, celular, idLogin);
        return dAOCliente.sqlUpdate(clCliente);
    }
    
    @WebMethod(operationName = "eliminarCliente")
    public boolean eliminarCliente(@WebParam(name = "id")int id) throws Exception{
        ClCliente clCliente = new ClCliente(id);
        return dAOCliente.sqlDelete(clCliente);
    }
    
    @WebMethod(operationName = "listaClienteAll")
    public List<ClCliente> listarClienteAll(@WebParam(name = "intDesde") long intDesde, @WebParam(name = "intCuantos") long intCuantos, @WebParam(name = "strBusqueda") String strBusqueda){
        return dAOCliente.leerClientes(intDesde, intCuantos, strBusqueda);
    }
    @WebMethod(operationName = "listarClienteRut")
    public List<ClCliente> listarClienteRut(){
        return dAOCliente.leerClientesRut();
    }
    @WebMethod(operationName = "leerClienteIdLogin")
    public List<ClCliente> leerClienteIdLogin(@WebParam(name = "id")int id){
        return dAOCliente.leerClienteIdLogin(id);
    }
    @WebMethod(operationName = "leerClienteId")
    public String[] leerClienteId(@WebParam(name = "id")int id) throws Exception{
        ClCliente clCliente = new ClCliente(id);
        return dAOCliente.leerCliente(clCliente);
    }
    
    @WebMethod(operationName = "buscarRutCliente")
    public ClCliente buscarRutCliente(@WebParam(name = "rut")String rut) throws Exception{
        ClCliente clCliente = new ClCliente(rut);
        return dAOCliente.sqlBuscarRut(clCliente);
    }
    @WebMethod(operationName = "buscarRutClienteT")
    public boolean buscarRutClienteT(@WebParam(name = "rut")String rut) throws Exception{
        ClCliente clCliente = new ClCliente(rut);
        return dAOCliente.sqlSearchRut(clCliente);
    }
}
