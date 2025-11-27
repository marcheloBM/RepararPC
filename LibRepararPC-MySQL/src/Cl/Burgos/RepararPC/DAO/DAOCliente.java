/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.DAO;

import Cl.Burgos.RepararPC.BD.BD;
import Cl.Burgos.RepararPC.Log4j.Log;
import Cl.Burgos.RepararPC.ENT.ClCliente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
//import java.util.logging.Logger;
import Cl.Burgos.RepararPC.Inter.ClienteInter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSetMetaData;
import org.apache.log4j.Logger;

/**
 *
 * @author march
 */
public class DAOCliente implements ClienteInter{

    //Variables del Log4j
    static  Logger log =Logger.getLogger(DAOCliente.class);
    
    @Override
    public boolean sqlInsert(ClCliente cliente) {
        try {
            String stSql =  "Call ProClienteInsertar (";
            stSql += "'" + cliente.getRut()+ "'";
            stSql += ",'" + cliente.getNombre()+ "'";
            stSql += ",'" + cliente.getApellido()+ "'";
            stSql += ",'" + cliente.getCorreo()+ "'";
            stSql += ",'" + cliente.getCelular()+ "'";
            stSql += ",'" + cliente.getIdLogin()+ "'";
            stSql += ")";
            return BD.getInstance().sqlEjecutar(stSql);
        } catch (SQLException ex) {
//            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            log.info(ex.getMessage());
            Log.log(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean sqlUpdate(ClCliente cliente) {
        try {
            String stSql =  "Call ProClienteModificar (";
            stSql += "'"+cliente.getRut()+"',";
            stSql += "'"+cliente.getNombre()+"',";
            stSql += "'"+cliente.getApellido()+"',";
            stSql += "'"+cliente.getCorreo()+"',";
//            stSql += "'"+cliente.getCelular().substring(4)+"',";
            stSql += "'"+cliente.getCelular()+"',";
            stSql += "'"+cliente.getIdLogin()+"',";
            stSql += "'"+cliente.getId()+"'";
            stSql += ")";
            return BD.getInstance().sqlEjecutar(stSql);
        } catch (SQLException ex) {
//            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            log.info(ex.getMessage());
            Log.log(ex.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean sqlDelete(ClCliente cliente){		
        try {
            String stSql =  "call ProClienteEliminar(";
            stSql += " '" + cliente.getId()+ "')";
            return BD.getInstance().sqlEjecutar(stSql);
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            log.info(ex.getMessage());
            Log.log(ex.getMessage());
        }
        return false;
    }

    @Override
    public List<ClCliente> leerClientes(long intDesde, long intCuantos, String strBusqueda) {
        List<ClCliente> lista=new ArrayList<>();
        String strConsulta;
      
        strConsulta="call ProClienteListarAll("+intDesde+","+intCuantos+",'"+strBusqueda+"');";
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         if(rs==null)return null;
         while(rs.next()){
             ClCliente c = new ClCliente(rs.getInt("idCliente"), rs.getString("rut"), 
                     rs.getString("nombre"), rs.getString("apellido"), rs.getString("correo"), 
                     rs.getString("celular"), rs.getInt("Login_idLogin"));
              lista.add(c);
         }
         
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        } catch (Exception ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        }
        return lista;
    }

    @Override
    public long leerCuantos(String strBusqueda) {
        String strConsulta;
        long cuantos = 0;
        strConsulta="call ProClienteCuantos('" +strBusqueda +"');";
      
        try{
             ResultSet rs=BD.getInstance().sqlSelect(strConsulta);

                 while(rs.next()){
//                      System.out.println(rs.getString("cuantos"));
                      cuantos=Long.valueOf(rs.getString("cuantos"));

                      return cuantos;

                 }
             rs.close();
          }catch(SQLException e){
              Log.log(e.getMessage());
              log.info(e.getMessage());
         return cuantos;
          }
        return cuantos;
    }

//    @Override
    //Metodo Sin Funcionar
    public void llenarCombo(JComboBox<String> combo) {
        String strConsulta;
        int intDesde = 0;
        int intCuantos = 1000;
        String strBusqueda = "";
        strConsulta = "call ProClienteListarAll(" + intDesde + "," + intCuantos + ",'" + strBusqueda + "');";

        try {
            ResultSet rs = BD.getInstance().sqlSelect(strConsulta);

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                combo.addItem(nombre);  // ahora sin warning
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
}
    
    @Override
    public List<ClCliente> leerClientesRut() {
        List<ClCliente> lista=new ArrayList<>();
        String strConsulta;
        
        int intDesde=0;
        int intCuantos=1000;
        String strBusqueda="";
        strConsulta="call ProClienteListarAll("+intDesde+","+intCuantos+",'"+strBusqueda+"');";
        
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         if(rs==null)return null;
         while(rs.next()){
             ClCliente c = new ClCliente(rs.getString("rut"));
              lista.add(c);
         }
         
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        } catch (Exception ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        }
        return lista;
    }
    
    @Override
    public List<ClCliente> leerClienteIdLogin(int id) {
        List<ClCliente> lista=new ArrayList<>();
        String strConsulta;
        
        strConsulta="call ProClienteBuscarIdLogin("+id+");";
        
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         if(rs==null)return null;
         while(rs.next()){
             ClCliente c = new ClCliente(rs.getInt("idCliente"), rs.getString("rut"), 
                     rs.getString("nombre"), rs.getString("apellido"), rs.getString("correo"), 
                     rs.getString("celular"), rs.getInt("Login_idLogin"));
              lista.add(c);
         }
         
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        } catch (Exception ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        }
        return lista;
    }
    
    @Override
    public boolean sqlSearchRut(ClCliente cliente){
//        String stSql  = "Call ProClienteBuscarRUT('"+cliente.getRut()+"');";
        String stSql  = "select idCliente, rut, nombre, apellido, correo, celular";
            stSql += " from Cliente ";
            stSql += " where rut=";
            stSql += "'"+cliente.getRut()+"';";
        try {
            ResultSet rs = BD.getInstance().sqlSelect(stSql);
            if(rs==null || !rs.next())return false;
            cliente.setId(rs.getInt("idCliente")) ;
            cliente.setRut(rs.getString("rut")) ;
            cliente.setNombre(rs.getString("nombre")) ;
            cliente.setApellido(rs.getString("apellido")) ;
            cliente.setCorreo(rs.getString("correo")) ;
            cliente.setCelular(rs.getString("celular")) ;
            return true;
        } catch (SQLException ex) {
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        } catch (Exception ex) {
//            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        }
        return false;
    }
    
    @Override
    public void leerClientes(long intDesde, long intCuantos, DefaultTableModel tablaClientes, String strBusqueda) {
        String strConsulta = "call ProClienteListarAll(" + intDesde + "," + intCuantos + ",'" + strBusqueda + "');";

        try {
            ResultSet rs = BD.getInstance().sqlSelect(strConsulta);
            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            while (rs.next()) {
                String[] datos = new String[columnas];
                for (int i = 0; i < columnas; i++) {
                    datos[i] = rs.getString(i + 1); // ResultSet es 1-based
                }
                tablaClientes.addRow(datos);
            }

            rs.close();
        } catch (SQLException e) {
            Log.log(e.getMessage());
            log.info(e.getMessage());
        }
    }

    @Override
    public String[] leerCliente(ClCliente cliente) {
        String strConsulta;
        String datos[]=new String [7];
        
        strConsulta="call ProClienteBuscarID("+cliente.getId()+");";
     
      
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         
         
         while(rs.next()){
              //System.out.println(res.getString("Nombres"));
              datos[0]=rs.getString("idCliente");
              datos[1]=rs.getString("rut");
              datos[2]=rs.getString("nombre");
              datos[3]=rs.getString("apellido");
              datos[4]=rs.getString("correo");
              datos[5]=rs.getString("celular");
              datos[6]=rs.getString("Login_idLogin");
                      
              rs.close();
              return datos;
              
         }
         rs.close();
          }catch(SQLException e){
         Log.log(e.getMessage());
         log.info(e.getMessage());
 
         return datos;
          }
      
        return datos;
    }

    @Override
    public ClCliente sqlBuscarRut(ClCliente cliente) {
        ClCliente clCliente = null;
        //        String stSql  = "Call ProClienteBuscarRUT('"+cliente.getRut()+"');";
        String stSql  = "select idCliente, rut, nombre, apellido, correo, celular";
            stSql += " from Cliente ";
            stSql += " where rut=";
            stSql += "'"+cliente.getRut()+"';";
        try {
            ResultSet rs = BD.getInstance().sqlSelect(stSql);
            if(rs==null)return null;
             while(rs.next()){
                clCliente = new ClCliente(rs.getInt("idCliente"), rs.getString("rut"), 
                        rs.getString("nombre"), rs.getString("apellido"),
                        rs.getString("correo"), rs.getString("celular"));
                  return clCliente;
             }
        } catch (SQLException ex) {
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        }
        return clCliente;
    }

    

    
    
}
