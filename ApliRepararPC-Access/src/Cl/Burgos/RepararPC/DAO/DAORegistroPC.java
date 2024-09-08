/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.DAO;

import Cl.Burgos.RepararPC.BD.BD;
import Cl.Burgos.RepararPC.ENT.ClRegistroPc;
import Cl.Burgos.RepararPC.FUN.Log;
import Cl.Burgos.RepararPC.Inter.RegistroPCInter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author march
 */
public class DAORegistroPC implements RegistroPCInter{
    //Variables del Log4j
    static  Logger log =Logger.getLogger(DAOLogin.class);

    //Revisar metodo 
    @Override
    public boolean sqlValidarClavePC(String  key) {
        String stSql =  "select IdRegistropc,keypc,keyactivacion,fechaTermino,activo from registropc where ";
            stSql += "keypc='" + key+ "'";
            stSql += ";";
        try {
            ResultSet rs = BD.getInstance().sqlSelect(stSql);
            if(rs==null || !rs.next())return false;
            return true;
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
//            log.info(ex.getMessage());
        } catch (Exception ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
//            log.info(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean sqlValidarActivoPC(String  key) {
        String stSql =  "select IdRegistropc,keypc,keyactivacion,fechaTermino,activo from registropc where ";
            stSql += "keypc='" + key+ "' and activo=true";
            stSql += ";";
        try {
            ResultSet rs = BD.getInstance().sqlSelect(stSql);
            if(rs==null || !rs.next())return false;
            return true;
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
//            log.info(ex.getMessage());
        } catch (Exception ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
//            log.info(ex.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean sqlInsertarPC(ClRegistroPc clRegistroPc) {
        Connection con = BD.getInstance().conectar();
        String stSql  = "insert into registropc(keypc,keyactivacion) values (?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(stSql);
            ps.setString(1, clRegistroPc.getKeyPC());
            ps.setString(2, clRegistroPc.getKeyActivacion());
            
            ps.execute();
            return true;
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean sqlActivarPC(ClRegistroPc clRegistroPc) {
        Connection con = BD.getInstance().conectar();
        java.util.Date date = clRegistroPc.getFechaTermino();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String stSql =  "update registropc set fechaTermino=?, activo=? where keyactivacion=?;";
        PreparedStatement ps = null;
        try {            
            ps = con.prepareStatement(stSql);
            ps.setDate(1, sqlDate);
            ps.setBoolean(2, clRegistroPc.isActivo());
            ps.setString(3, clRegistroPc.getKeyActivacion());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
//            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        }
        return false;
    }
    
    @Override
    public Date sqlValidarFechaPC(String  key) {
        String stSql =  "select IdRegistropc,keypc,keyactivacion,fechaTermino,activo from registropc where ";
            stSql += "keypc='" + key+ "'";
            stSql += ";";
            Date fecha = null;
        try {
            ResultSet rs = BD.getInstance().sqlSelect(stSql);
            if(rs==null || !rs.next())return fecha;
            fecha = rs.getDate("fechaTermino") ;
            return fecha;
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
//            log.info(ex.getMessage());
        } catch (Exception ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
//            log.info(ex.getMessage());
        }
        return fecha;
    }
    
    public boolean sqldesabilitarPC(String keypc,String keyAct) {
        Connection con = BD.getInstance().conectar();
        String stSql =  "update registropc set keyactivacion=?, fechaTermino=null, activo=? where keypc=?;";
        PreparedStatement ps = null;
        try {            
            ps = con.prepareStatement(stSql);
            ps.setString(1, keyAct);
            ps.setBoolean(2, false);
            ps.setString(3, keypc);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
//            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        }
        return false;
    }
}
