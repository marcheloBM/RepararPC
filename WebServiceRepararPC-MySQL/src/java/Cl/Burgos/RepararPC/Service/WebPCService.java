/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.Service;

import Cl.Burgos.RepararPC.DAO.DAOComputador;
import Cl.Burgos.RepararPC.ENT.ClComputador;
import Cl.Burgos.RepararPC.Enum.TipoReparacion;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Marchelo
 */
@WebService(serviceName = "WebPCService")
public class WebPCService {

    DAOComputador dAOComputador = new DAOComputador();
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "listaPCAll")
    public List<ClComputador> listaPCAll(@WebParam(name = "intDesde") long intDesde, @WebParam(name = "intCuantos") long intCuantos, @WebParam(name = "strBusqueda") String strBusqueda){
        return dAOComputador.leerPC(intDesde, intCuantos, strBusqueda);
    }
    
    @WebMethod(operationName = "insertarPC")
    public boolean insertarPC(@WebParam(name = "marca") String marca, @WebParam(name = "modelo") String modelo, @WebParam(name = "numSerie") String numSerie, 
            @WebParam(name = "ram") String ram, @WebParam(name = "hdd") String hdd, @WebParam(name = "SO") String so, @WebParam(name = "arquitectura") String arquitectura, 
            @WebParam(name = "version") String version, @WebParam(name = "tipoRaparacion") TipoReparacion tipoReparacion, @WebParam(name = "descripcion") String descripcion, 
            @WebParam(name = "valor") int valor, @WebParam(name = "fecha") Date fecha, @WebParam(name = "idCliente") int idCliente,
            @WebParam(name = "idLogin") int idLogin) throws Exception{
        ClComputador clComputador = new ClComputador(marca, modelo, numSerie, ram, hdd, so, arquitectura, version, tipoReparacion, descripcion, valor, fecha, idCliente, idLogin);
        return dAOComputador.sqlInsert(clComputador);
    }
}
