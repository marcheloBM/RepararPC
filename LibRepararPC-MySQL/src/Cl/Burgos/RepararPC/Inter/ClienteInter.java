/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.Inter;

import Cl.Burgos.RepararPC.ENT.ClCliente;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author march
 */
public interface ClienteInter {
    //******** Sql Lista Completa Cliente ***
    public List<ClCliente> leerClientes(long intDesde, long intCuantos, String strBusqueda);
    public void leerClientes(long intDesde ,long intCuantos,DefaultTableModel tablaClientes,String strBusqueda );
    //******** Cuenta Cuantos Cliente Hay ***
    public long leerCuantos(String strBusqueda);
    //******** Sql Insertar Cliente ***
    public boolean sqlInsert(ClCliente cliente);
    //******** Sql actualizar Cliente ***
    public boolean sqlUpdate(ClCliente cliente);
    //******** Sql Eliminafr Cliente ***
    public boolean sqlDelete(ClCliente cliente);
    //******** Sql Cargar Datos de Cliente en comboBox***
//    public void llenarCombo(JComboBox Combo);
    //******** Sql Buscar x Rut ********
    public List<ClCliente> leerClientesRut();
    public boolean sqlSearchRut(ClCliente cliente);
    public ClCliente sqlBuscarRut(ClCliente cliente);
    //******** Sql Buscar x Id Login ********
    public List<ClCliente> leerClienteIdLogin(int id);
    //******** Sql Busca por id *****
    public String[] leerCliente(ClCliente cliente);
}
