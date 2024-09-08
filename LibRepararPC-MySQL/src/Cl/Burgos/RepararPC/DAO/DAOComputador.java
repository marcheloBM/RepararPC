/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.DAO;

import Cl.Burgos.RepararPC.BD.BD;
import Cl.Burgos.RepararPC.Log4j.Log;
import Cl.Burgos.RepararPC.ENT.ClComputador;
import Cl.Burgos.RepararPC.Enum.TipoReparacion;
import Cl.Burgos.RepararPC.Inter.ComputardorInter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;

/**
 *
 * @author march
 */
public class DAOComputador implements ComputardorInter{

    //Variables del Log4j
    static  Logger log =Logger.getLogger(DAOComputador.class);
    
    @Override
    public boolean sqlInsert(ClComputador cc) {
        try {
            String stSql  = "CALL ProComputadorInsertar(";
            stSql += "'" + cc.getMarca()+ "'";
            stSql += ",'" + cc.getModelo()+ "'";
            stSql += ",'" + cc.getNumSerie()+ "'";
            stSql += ",'" + cc.getRam()+ "'";
            stSql += ",'" + cc.getHdd()+ "'";
            stSql += ",'" + cc.getSistemaOpe()+ "'";
            stSql += ",'" + cc.getArquitectura()+ "'";
            stSql += ",'" + cc.getVersion()+ "'";
            stSql += ",'" + cc.getTipoRepa()+ "'";
            stSql += ",'" + cc.getDescripcion()+ "'";
            stSql += ",'" + cc.getValor()+ "'";
            stSql += ",'" + cc.getFecha()+ "'";
            stSql += ",'" + cc.getIdCliente()+ "'";
            stSql += ",'" + cc.getIdLogin()+ "'";
            stSql += " )";
            return BD.getInstance().sqlEjecutar(stSql);
        } catch (SQLException ex) {
//            Logger.getLogger(DAOComputador.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean sqlUpdate(ClComputador cc) {
        try {
            String stSql =  "Call ProComputadorModificar (";
            stSql += "'"+cc.getMarca()+"',";
            stSql += "'"+cc.getModelo()+"',";
            stSql += "'"+cc.getNumSerie()+"',";
            stSql += "'"+cc.getRam()+"',";
            stSql += "'"+cc.getHdd()+"',";
            stSql += "'"+cc.getSistemaOpe()+"',";
            stSql += "'"+cc.getArquitectura()+"',";
            stSql += "'"+cc.getVersion()+"',";
            stSql += "'"+cc.getTipoRepa()+"',";
            //            stSql += "'"+pc.getReparacion().substring(4)+"',";
            stSql += "'"+cc.getDescripcion()+"',";
            stSql += "'"+cc.getValor()+"',";
            stSql += "'"+cc.getFecha()+"',";
            stSql += "'"+cc.getIdCliente()+"',";
            stSql += "'"+cc.getIdLogin()+"',";
            stSql += "'"+cc.getIdPC()+"'";
            stSql += ")";
            return BD.getInstance().sqlEjecutar(stSql);
        } catch (SQLException ex) {
//            Logger.getLogger(DAOComputador.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean sqlDelete(ClComputador cc) {
        try {
            String stSql =  "call ProComputadorEliminar(";
            stSql += " '" + cc.getIdPC()+ "')";
            return BD.getInstance().sqlEjecutar(stSql);
        } catch (SQLException ex) {
//            Logger.getLogger(DAOComputador.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        }
        return false;
    }

    @Override
    public void leerPC(long intDesde, long intCuantos, DefaultTableModel tablaClientes, String strBusqueda) {
        String strConsulta;
        String datos[]=new String [15];
      
        strConsulta="call ProComputadorListarAll("+intDesde+","+intCuantos+",'"+strBusqueda+"');";
      
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         
         while(rs.next()){
              //System.out.println(res.getString("Nombres"));
               datos[0]=rs.getString("idComputador");
              datos[1]=rs.getString("marca");
              datos[2]=rs.getString("modelo");
              datos[3]=rs.getString("numSerie");
              datos[4]=rs.getString("ram");
              datos[5]=rs.getString("hdd");
              datos[6]=rs.getString("sistemaOpe");
              datos[7]=rs.getString("arquitectura");
              datos[8]=rs.getString("version");
              datos[9]=rs.getString("tipoReparacion");
              datos[10]=rs.getString("descripcion");
              datos[11]=rs.getString("valor");
              datos[12]=rs.getString("fecha");
              datos[13]=rs.getString("Cliente_idCliente");
              datos[14]=rs.getString("Cliente_Login_idLogin");
              
              tablaClientes.addRow(datos);
         }
         rs.close();
          }catch(SQLException e){
              Log.log(e.getMessage());
              log.info(e.getMessage());
          }
    }

    public List<ClComputador> leerPC(long intDesde, long intCuantos, String strBusqueda) {
        List<ClComputador> lista=new ArrayList<>();
        String strConsulta;
      
        strConsulta="call ProComputadorListarAll("+intDesde+","+intCuantos+",'"+strBusqueda+"');";
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         if(rs==null)return null;
         while(rs.next()){
             ClComputador cc = new ClComputador(rs.getInt("idComputador"), rs.getString("marca"), 
                     rs.getString("modelo"), rs.getString("numSerie"), rs.getString("ram"), 
                     rs.getString("hdd"), rs.getString("sistemaOpe"), rs.getString("arquitectura"), 
                     rs.getString("version"), TipoReparacion.valueOf(rs.getString("tipoReparacion")),
                     rs.getString("descripcion"),rs.getInt("valor"), rs.getDate("fecha"), 
                     rs.getInt("Cliente_idCliente"), rs.getInt("Cliente_Login_idLogin"));
              lista.add(cc);
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
    
    public List<ClComputador> leerPCIdCliente(long intDesde, long intCuantos, String strBusqueda, int id) {
        List<ClComputador> lista=new ArrayList<>();
        String strConsulta;
      
        strConsulta="call ProComputadorListarId("+intDesde+","+intCuantos+",'"+strBusqueda+"',"+id+");";
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         if(rs==null)return null;
         while(rs.next()){
             ClComputador cc = new ClComputador(rs.getInt("idComputador"), rs.getString("marca"), 
                     rs.getString("modelo"), rs.getString("numSerie"), rs.getString("ram"), 
                     rs.getString("hdd"), rs.getString("sistemaOpe"), rs.getString("arquitectura"), 
                     rs.getString("version"), TipoReparacion.valueOf(rs.getString("tipoReparacion")),
                     rs.getString("descripcion"),rs.getInt("valor"), rs.getDate("fecha"), 
                     rs.getInt("Cliente_idCliente"), rs.getInt("Cliente_Login_idLogin"));
              lista.add(cc);
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
    public void leerClientesId(long intDesde, long intCuantos, DefaultTableModel tablaClientes, String strBusqueda, int id) {
        String strConsulta;
        String datos[]=new String [13];
      
        strConsulta="call ProComputadorListarId("+intDesde+","+intCuantos+",'"+strBusqueda+"',"+id+");";
        
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         
         while(rs.next()){
              //System.out.println(res.getString("Nombres"));
              datos[0]=rs.getString("idComputador");
              datos[1]=rs.getString("marca");
              datos[2]=rs.getString("modelo");
              datos[3]=rs.getString("numSerie");
              datos[4]=rs.getString("ram");
              datos[5]=rs.getString("hdd");
              datos[6]=rs.getString("sistemaOpe");
              datos[7]=rs.getString("arquitectura");
              datos[8]=rs.getString("version");
              datos[9]=rs.getString("tipoReparacion");
              datos[10]=rs.getString("descripcion");
              datos[11]=rs.getString("valor");
              datos[12]=rs.getString("fecha");
              //datos[8]=rs.getString("idCliente");
              
              tablaClientes.addRow(datos);
         }
         rs.close();
          }catch(SQLException e){
              Log.log(e.getMessage());
              log.info(e.getMessage());
          }
    }

    @Override
    public long leerCuantos(String strBusqueda) {
        String strConsulta;
        long cuantos = 0;
        strConsulta="call ProComputadorCuantos('" +strBusqueda +"');";
      
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         
         while(rs.next()){
              //System.out.println(res.getString("Nombres"));
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

    @Override
    public String[] leerCliente(ClComputador cc) {
        String strConsulta;
        String datos[]=new String [15];
        
        strConsulta="call ProComputadorListarIdPC("+cc.getIdPC()+");";
     
      
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         
         
         while(rs.next()){
              //System.out.println(res.getString("Nombres"));
              datos[0]=rs.getString("idComputador");
              datos[1]=rs.getString("marca");
              datos[2]=rs.getString("modelo");
              datos[3]=rs.getString("numSerie");
              datos[4]=rs.getString("ram");
              datos[5]=rs.getString("hdd");
              datos[6]=rs.getString("sistemaOpe");
              datos[7]=rs.getString("arquitectura");
              datos[8]=rs.getString("version");
              datos[9]=rs.getString("tipoReparacion");
              datos[10]=rs.getString("descripcion");
              datos[11]=rs.getString("valor");
              datos[12]=rs.getString("fecha");
              datos[13]=rs.getString("Cliente_idCliente");
              datos[14]=rs.getString("Cliente_Login_idLogin");
              
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
    
}
