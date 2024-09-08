/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.Service;

import Cl.Burgos.RepararPC.DAO.DAOLogin;
import Cl.Burgos.RepararPC.ENT.ClLogin;
import Cl.Burgos.RepararPC.Enum.TipoLogin;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Marchelo
 */
@WebService(serviceName = "WebLoginService")
public class WebLoginService {

    DAOLogin dAOLogin = new DAOLogin();
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "validarLogin")
    public boolean validarLogin(@WebParam(name = "rut") String rut, @WebParam(name = "pass") String pass) throws Exception {
        ClLogin login = new ClLogin(rut, pass);
        boolean resp = dAOLogin.sqlSelectValidar(login);
        return resp;
    }
    
    @WebMethod(operationName = "listaLoginAll")
    public List<ClLogin> listarLoginAll(@WebParam(name = "intDesde") long intDesde, @WebParam(name = "intCuantos") long intCuantos, @WebParam(name = "strBusqueda") String strBusqueda){
        return dAOLogin.leerLogin(intDesde, intCuantos, strBusqueda);
    }
    
    @WebMethod(operationName = "buscarRutLogin")
    public ClLogin buscarRutLogin(@WebParam(name = "rut")String rut, @WebParam(name = "pass") String pass) throws Exception{
        ClLogin login = new ClLogin(rut, pass);
        return dAOLogin.sqlbuscarRut(login);
    }
    @WebMethod(operationName = "buscarRutLoginR")
    public boolean buscarRutLoginR(@WebParam(name = "rut")String rut) throws Exception{
        ClLogin login = new ClLogin(rut);
        return dAOLogin.sqlSearchRut(login);
    }
    @WebMethod(operationName = "buscarIdLogin")
    public String[] buscarIdLogin(@WebParam(name = "id")int id) throws Exception{
        ClLogin clLogin = new ClLogin(id);
        return dAOLogin.leerLogin(clLogin);
    }
    
    @WebMethod(operationName = "insertarLogin")
    public boolean insertarLogin(@WebParam(name = "rut") String rut, @WebParam(name = "nombre") String nombre, @WebParam(name = "apellido") String apellido, 
            @WebParam(name = "correo") String correo, @WebParam(name = "celular") String celular, @WebParam(name = "pass") String pass, @WebParam(name = "tipoLogin") TipoLogin tipoLogin) throws Exception{
        ClLogin clLogin = new ClLogin(rut, nombre, apellido, correo, celular, pass, tipoLogin);
        return dAOLogin.sqlInsert(clLogin);
    }
    
    @WebMethod(operationName = "actualizarLogin")
    public boolean actualizarLogin(@WebParam(name = "id")int id, @WebParam(name = "rut") String rut, @WebParam(name = "nombre") String nombre, @WebParam(name = "apellido") String apellido, 
            @WebParam(name = "correo") String correo, @WebParam(name = "celular") String celular, @WebParam(name = "pass") String pass, @WebParam(name = "tipoLogin") TipoLogin tipoLogin) throws Exception{
        ClLogin clLogin = new ClLogin(id, rut, nombre, apellido, correo, celular, pass, tipoLogin);
        return dAOLogin.sqlUpdate(clLogin);
    }
    
    @WebMethod(operationName = "eliminarLogin")
    public boolean eliminarLogin(@WebParam(name = "id")int id) throws Exception{
        ClLogin clLogin = new ClLogin(id);
        return dAOLogin.sqlDelete(clLogin);
    }
}
