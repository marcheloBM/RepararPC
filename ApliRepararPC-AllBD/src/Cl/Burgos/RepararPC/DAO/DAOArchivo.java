/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.DAO;

import Cl.Burgos.RepararPC.BD.BD;
import Cl.Burgos.RepararPC.ENT.ClArchivo;
import Cl.Burgos.RepararPC.FUN.Log;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author march
 */
public class DAOArchivo {
    /*Metodo listar*/
    public List<ClArchivo> listarArchivo(int id) {
        List<ClArchivo> lista=new ArrayList<>();
        String strConsulta;
        
        strConsulta="select * from documento where Computador_idComputador="+id;
        
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         if(rs==null)return null;
         while(rs.next()){
             ClArchivo c = new ClArchivo(rs.getInt("IdDocumento"), rs.getString("extencion"), rs.getBytes("archivo"),rs.getInt("computador_idComputador"));
              lista.add(c);
         }
         
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
        } catch (Exception ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
        }
        return lista;
    }
    /*Metodo Agregar*/
    public boolean sqlInsert(ClArchivo clArchivo) {
        Connection con = BD.getInstance().conectar();
        String insert = "insert into documento(extencion,archivo,computador_idComputador) values (?,?,?)";
        FileInputStream fi = null;
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement(insert);
            ps.setString(1, clArchivo.getNombre());
            ps.setBytes(2, clArchivo.getArchivo());
            ps.setInt(3, clArchivo.getIdComputador());
            
            ps.execute();
            return true;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    /*Metodo Actualizar*/
    public boolean sqlUpdate(ClArchivo clArchivo){	
        Connection con = BD.getInstance().conectar();
        String insert = "update documento set extencion=?, archivo=? where IdDocumento=?;";
        FileInputStream fi = null;
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement(insert);
            ps.setString(1, clArchivo.getNombre());
            ps.setBytes(2, clArchivo.getArchivo());
            ps.setInt(3, clArchivo.getId());
            
            
            ps.executeUpdate();
            return true;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    /*Metodo Eliminar*/
    public boolean sqlDelete(ClArchivo clArchivo){
        Connection con = BD.getInstance().conectar();
        PreparedStatement ps = null;
        String stSql =  "delete from documento where IdDocumento=?;";
        try {
            
             ps = con.prepareStatement(stSql);
            ps.setInt(1, clArchivo.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
//            Log.log(ex.getMessage());
//            log.info(ex.getMessage());
        }
        return false;
    }
    //Permite mostrar PDF contenido en la base de datos
    public List<ClArchivo> ejecutar_archivoPDF(int id) {
        List<ClArchivo> lista=new ArrayList<>();
        String strConsulta;
        strConsulta="select * from documento where IdDocumento="+id;
        
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         if(rs==null)return null;
         while(rs.next()){
              ClArchivo c = new ClArchivo(rs.getInt("IdDocumento"), rs.getString("extencion"), rs.getBytes("archivo"),rs.getInt("computador_idComputador"));
              lista.add(c);
         }
         
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
//            Log.log(ex.getMessage());
        } catch (Exception ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
//            Log.log(ex.getMessage());
        }
        return lista;
    }
}
