/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.Inter;

import Cl.Burgos.RepararPC.ENT.ClComputador;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author march
 */
public interface ComputardorInter {
    //******** Sql Insertar Computador ***
    public boolean sqlInsert(ClComputador cc);
    //******** Sql Actulizar Computador ***
    public boolean sqlUpdate(ClComputador cc);
    //******** Sql Eliminar Computador ***
    public boolean sqlDelete(ClComputador cc);
    //******** Sql Lista Completa Computador ***
    public void leerPC(long intDesde ,long intCuantos,DefaultTableModel tablaClientes,String strBusqueda );
    //******** Sql Lista Completa Computador id ***/
    public void leerClientesId(long intDesde ,long intCuantos,DefaultTableModel tablaClientes,String strBusqueda,int id );
    //******** Sql Cuanta Cuantos Computador Hay ***
    public long leerCuantos(String strBusqueda);
    //******** Sql Busca por id *****
    public String[] leerCliente(ClComputador cc);
    
    //******** Sql BuscarAll
//    public boolean sqlSearchAll(ClPc pc);
    
}
