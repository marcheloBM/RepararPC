/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.Inter;

import Cl.Burgos.RepararPC.ENT.ClLogin;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author march
 */
public interface LoginInter {
    //******** Sql Validar Login ***
    public boolean sqlSelectValidar(ClLogin login);
    //******** Sql insetar *****
    public boolean sqlInsert(ClLogin login);
    //******** Sql actualizar ****
    public boolean sqlUpdate(ClLogin login);
    //******** SQL Eliminar ****
    public boolean sqlDelete(ClLogin login);
    //Lista Login Todos ***
    public List<ClLogin> leerLogin(long intDesde, long intCuantos, String strBusqueda);
    public void leerLoginTab(long intDesde ,long intCuantos,DefaultTableModel tablaClientes,String strBusqueda );
    //Cuanta Cuantos Login Hay ***
    public long leerCuantos(String strBusqueda);
    //Buscar el RUT ***
    public boolean sqlSearchRut(ClLogin login);
    public ClLogin sqlbuscarRut(ClLogin login);
    //Buscar por ID
    public String[] leerLogin(ClLogin login);
    
    
//    public List<ClLogin> leerClientes(long intDesde ,long intCuantos,String strBusqueda );
//    public void leerClientes(long intDesde ,long intCuantos,ClLogin[] logins,String strBusqueda );

    
}
