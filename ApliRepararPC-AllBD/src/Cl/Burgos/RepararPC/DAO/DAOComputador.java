/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.DAO;

import Cl.Burgos.RepararPC.BD.BD;
import Cl.Burgos.RepararPC.FUN.Log;
import Cl.Burgos.RepararPC.ENT.ClComputador;
//import Cl.Burgos.RepararPC.Enum.TipoReparacion;
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
            String stSql  = "insert into Computador(marca, modelo, numSerie, sistemaOpe, arquitectura, tipoReparacion, descripcion, valor, fecha, Cliente_idCliente,Cliente_Login_IdLogin) values (";
            stSql += "'" + cc.getMarca()+ "'";
            stSql += ",'" + cc.getModelo()+ "'";
            stSql += ",'" + cc.getNumSerie()+ "'";
            stSql += ",'" + cc.getSistemaOpe()+ "'";
            stSql += ",'" + cc.getArquitectura()+ "'";
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
            String stSql =  "update Computador set ";
            stSql += "marca='"+cc.getMarca()+"',";
            stSql += "modelo='"+cc.getModelo()+"',";
            stSql += "numSerie='"+cc.getNumSerie()+"',";
            stSql += "sistemaOpe='"+cc.getSistemaOpe()+"',";
            stSql += "arquitectura='"+cc.getArquitectura()+"',";
            stSql += "tipoReparacion='"+cc.getTipoRepa()+"',";
            //            stSql += "'"+pc.getReparacion().substring(4)+"',";
            stSql += "descripcion='"+cc.getDescripcion()+"',";
            stSql += "valor='"+cc.getValor()+"',";
            stSql += "fecha='"+cc.getFecha()+"',";
            stSql += "Cliente_idCliente='"+cc.getIdCliente()+"',";
            stSql += "Cliente_Login_idLogin='"+cc.getIdLogin()+"'";
            stSql += " WHERE idComputador='"+cc.getIdPC()+"'";
            stSql += ";";
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
           String stSql =  "delete from Computador where idComputador=";
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
        String datos[]=new String [12];
      
        strConsulta="select idComputador, marca, modelo, numSerie, sistemaOpe, arquitectura, tipoReparacion, descripcion, valor, fecha, Cliente_idCliente, Cliente_Login_idLogin from Computador;";
      
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         
         while(rs.next()){
              //System.out.println(res.getString("Nombres"));
               datos[0]=rs.getString("idComputador");
              datos[1]=rs.getString("marca");
              datos[2]=rs.getString("modelo");
              datos[3]=rs.getString("numSerie");
              datos[4]=rs.getString("sistemaOpe");
              datos[5]=rs.getString("arquitectura");
              datos[6]=rs.getString("tipoReparacion");
              datos[7]=rs.getString("descripcion");
              datos[8]=rs.getString("valor");
              datos[9]=rs.getString("fecha");
              datos[10]=rs.getString("Cliente_idCliente");
              datos[11]=rs.getString("Cliente_Login_idLogin");
              
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
      
        strConsulta="select idComputador, marca, modelo, numSerie, sistemaOpe, arquitectura, "
                + "tipoReparacion, descripcion, valor, fecha, Cliente_idCliente, Cliente_Login_idLogin from Computador;";
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         if(rs==null)return null;
         while(rs.next()){
             ClComputador cc = new ClComputador(rs.getInt("idComputador"), rs.getString("marca"), 
                     rs.getString("modelo"), rs.getString("numSerie"), 
                     rs.getString("sistemaOpe"), rs.getString("arquitectura"), 
                     rs.getString("tipoReparacion"),
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
                     rs.getString("modelo"), rs.getString("numSerie"), 
                     rs.getString("sistemaOpe"), rs.getString("arquitectura"), 
                     rs.getString("tipoReparacion"),
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
        String datos[]=new String [10];
      
        strConsulta="select idComputador, marca, modelo, numSerie, sistemaOpe, arquitectura, tipoReparacion, descripcion, valor, fecha, Cliente_idCliente, Cliente_Login_idLogin from Computador where Cliente_idCliente="+id+";";
        
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         
         while(rs.next()){
              //System.out.println(res.getString("Nombres"));
              datos[0]=rs.getString("idComputador");
              datos[1]=rs.getString("marca");
              datos[2]=rs.getString("modelo");
              datos[3]=rs.getString("numSerie");
              datos[4]=rs.getString("sistemaOpe");
              datos[5]=rs.getString("arquitectura");
              datos[6]=rs.getString("tipoReparacion");
              datos[7]=rs.getString("descripcion");
              datos[8]=rs.getString("valor");
              datos[9]=rs.getString("fecha");
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
        strConsulta="select count(*) as cuantos from Computador;";
      
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
        String datos[]=new String [12];
        
        strConsulta="select idComputador,marca, modelo, numSerie, sistemaOpe, arquitectura, "
                + "tipoReparacion, descripcion, valor, fecha, Cliente_idCliente, Cliente_Login_idLogin from Computador where idComputador="+cc.getIdPC()+";";
     
      
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         
         
         while(rs.next()){
              //System.out.println(res.getString("Nombres"));
              datos[0]=rs.getString("idComputador");
              datos[1]=rs.getString("marca");
              datos[2]=rs.getString("modelo");
              datos[3]=rs.getString("numSerie");
              datos[4]=rs.getString("sistemaOpe");
              datos[5]=rs.getString("arquitectura");
              datos[6]=rs.getString("tipoReparacion");
              datos[7]=rs.getString("descripcion");
              datos[8]=rs.getString("valor");
              datos[9]=rs.getString("fecha");
              datos[10]=rs.getString("Cliente_idCliente");
              datos[11]=rs.getString("Cliente_Login_idLogin");
              
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
