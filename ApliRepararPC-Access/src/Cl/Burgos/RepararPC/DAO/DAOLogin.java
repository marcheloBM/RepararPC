/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.DAO;

import Cl.Burgos.RepararPC.BD.BD;
import Cl.Burgos.RepararPC.FUN.Log;
import Cl.Burgos.RepararPC.ENT.ClLogin;
import Cl.Burgos.RepararPC.Enum.TipoLogin;
import Cl.Burgos.RepararPC.Inter.LoginInter;
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
public class DAOLogin implements LoginInter{
    
    //Variables del Log4j
    static  Logger log =Logger.getLogger(DAOLogin.class);
    
    @Override
    public boolean sqlSelectValidar(ClLogin login) {
         String stSql =  "select idLogin,rut,nombre,apellido,correo,celular,password,tipo from Login where ";
            stSql += "rut='" + login.getRut()+ "'";
            stSql += " and password='" + login.getPassworld()+ "'";
            stSql += ";";
        try {
            ResultSet rs = BD.getInstance().sqlSelect(stSql);
            if(rs==null || !rs.next())return false;
            login.setId(rs.getInt("idLogin")) ;
            login.setRut(rs.getString("rut")) ;
            login.setNombre(rs.getString("nombre")) ;
            login.setApellido(rs.getString("apellido")) ;
            login.setCorreo(rs.getString("correo")) ;
            login.setCelular(rs.getString("celular")) ;
            login.setPassworld(rs.getString("password")) ;
            login.setTipoLogin(TipoLogin.valueOf(rs.getString("tipo"))) ;
            return true;
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        } catch (Exception ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        }
        return false;
    }
    @Override
    public boolean sqlInsert(ClLogin login) {
        try {
            String stSql  = "insert into Login(rut,nombre,apellido,correo,celular,password,tipo) values (";
            stSql += "'" + login.getRut()+ "'";
            stSql += ",'" + login.getNombre()+ "'";
            stSql += ",'" + login.getApellido()+ "'";
            stSql += ",'" + login.getCorreo()+ "'";
            stSql += ",'" + login.getCelular()+ "'";
            stSql += ",'" + login.getPassworld()+ "'";
            stSql += ",'" + login.getTipoLogin()+ "'";
            stSql += " )";
            return BD.getInstance().sqlEjecutar(stSql);
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
            return false;
        }
    }
    @Override
    public boolean sqlUpdate(ClLogin login){		
        try {
            String stSql =  "update Login set ";
            stSql += "rut='" + login.getRut()+ "'";
            stSql += ",nombre='" + login.getNombre()+ "'";
            stSql += ",apellido='" + login.getApellido()+ "'";
            stSql += ",correo='" + login.getCorreo()+ "'";
            stSql += ",celular='" + login.getCelular()+ "'";
            stSql += ",password='" + login.getPassworld()+ "'";
            stSql += ",tipo='" + login.getTipoLogin()+ "'";
            stSql += " WHERE ";
            stSql += "idLogin='"+login.getId()+"'";
            stSql += ";";
            return BD.getInstance().sqlEjecutar(stSql);
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        }
        return false;
    }
    @Override
    public boolean sqlDelete(ClLogin login){		
        try {
            String stSql =  "delete from Login where idLogin=";
            stSql += " '" + login.getId()+ "')";
            return BD.getInstance().sqlEjecutar(stSql);
        } catch (SQLException ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        }
        return false;
    }
    @Override
    public List<ClLogin> leerLogin(long intDesde, long intCuantos, String strBusqueda) {
        List<ClLogin> lista=new ArrayList<>();
        String strConsulta;
      
        strConsulta="select idLogin, rut, nombre, apellido, correo, celular, password, tipo from Login;";
        try{
         ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
         if(rs==null)return null;
         while(rs.next()){
              ClLogin l = new ClLogin(rs.getInt("idLogin"), rs.getString("rut"), 
                      rs.getString("nombre"), rs.getString("apellido"), 
                      rs.getString("correo"), rs.getString("celular"), 
                      rs.getString("password"), TipoLogin.valueOf(rs.getString("tipo")));
              lista.add(l);
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
    //Revisar esto Codigo
    @Override
    public void leerLoginTab(long intDesde ,long intCuantos,DefaultTableModel tablaClientes,String strBusqueda ){
        String strConsulta;
        String datos[]=new String [8];
        
        strConsulta="call ProLoginListarAll("+intDesde+","+intCuantos+",'"+strBusqueda+"');";
        try{
            ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
            while(rs.next()){
                datos[0]=rs.getString("idLogin");
                datos[1]=rs.getString("rut");
                datos[2]=rs.getString("nombre");
                datos[3]=rs.getString("apellido");
                datos[4]=rs.getString("correo");
                datos[5]=rs.getString("celular");
                datos[6]=rs.getString("password");
                datos[7]=rs.getString("tipo");
                tablaClientes.addRow(datos);
            }
            rs.close();
        }catch(SQLException e){
            Log.log(e.getMessage());
            log.info(e.getMessage());
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception ex) {
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public long leerCuantos(String strBusqueda){
        String strConsulta;
        long cuantos = 0;
        strConsulta="select count(*) as cuantos from Login;";
      
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
    @Override
    public boolean sqlSearchRut(ClLogin login) {
        String strConsulta;
        strConsulta="select idLogin,rut,nombre,apellido,correo,celular,password,tipo from Login where rut='" +login.getRut() +"';";
        try {
            ResultSet rs = BD.getInstance().sqlSelect(strConsulta);
            if(rs==null || !rs.next())return false;
            login.setId(rs.getInt("idLogin")) ;
            login.setRut(rs.getString("rut")) ;
            login.setNombre(rs.getString("nombre")) ;
            login.setApellido(rs.getString("apellido")) ;
            login.setCorreo(rs.getString("correo")) ;
            login.setCelular(rs.getString("celular")) ;
            login.setPassworld(rs.getString("password")) ;
            login.setTipoLogin(TipoLogin.valueOf(rs.getString("tipo"))) ;
            return true;
        } catch (SQLException ex) {
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.info(ex.getMessage());
        }
        return false;
    }
    //Revisar esto Codigo
    @Override
    public ClLogin sqlbuscarRut(ClLogin login) {
        ClLogin clLogin = new ClLogin();
        String strConsulta;
        strConsulta="call ProLoginBuscarRut('" +login.getRut() +"');";
        try {
            ResultSet rs = BD.getInstance().sqlSelect(strConsulta);
            if(rs==null)return null;
             while(rs.next()){
                  clLogin = new ClLogin(rs.getInt("idLogin"), rs.getString("rut"), 
                          rs.getString("nombre"), rs.getString("apellido"), 
                          rs.getString("correo"), rs.getString("celular"), 
                          rs.getString("password"), TipoLogin.valueOf(rs.getString("tipo")));
                  return clLogin;
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
        return clLogin;
    }
    @Override
    public String[] leerLogin(ClLogin login){
        String strConsulta;
        String datos[]=new String [8];
        
        strConsulta="select idLogin,rut,nombre,apellido,correo,celular,password,tipo from Login where idLogin="+login.getId()+";";
        
        try{
            ResultSet rs=BD.getInstance().sqlSelect(strConsulta);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                datos[7]=rs.getString(8);
                
                rs.close();
                
                return datos;
            }
            rs.close();
        }catch(SQLException e){
//            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, e);
            Log.log(e.getMessage());
            log.info(e.getMessage());
            return datos;
        }
        return datos;
    }
    
    
//******** Sql selecionar
//    public static boolean sqlSelect(ClLogin login) throws Exception, SQLException{
//        String stSql = "Select";
//        stSql += " idLogin";
//        stSql += " ,user";
//        stSql += " ,pass";
//        stSql += " ,primerNombre";
//        stSql += " ,primerApellido";
//        stSql += "  from login";
//        stSql += " Where  user='" + login.getUser()+ "'";
//        stSql += " and  pass='" + login.getPass()+ "'";
//        try {
//            ResultSet rs = BD.getInstance().sqlSelect(stSql);
//            if(rs==null || !rs.next())return false;
//            login.setIdLogin(rs.getInt("idLogin")) ;
//            login.setUser(rs.getString("user")) ;
//            login.setPass(rs.getString("pass")) ;
//            login.setPrimerNombre(rs.getString("primerNombre")) ;
//            login.setPrimerApellido(rs.getString("primerApellido")) ;
//            return true;
//        } catch (SQLException ex) {
//            Log.log(ex.getMessage());
//        }
//        return false;
//    }
    

    
    //******** Sql BuscarAll
//    public static boolean sqlSearchAll(ClLogin login) throws Exception, SQLException{
//        String stSql = "Select";
//        stSql += " idLogin";
//        stSql += " ,user";
//        stSql += " ,pass";
//        stSql += " ,primerNombre";
//        stSql += " from login";
//        stSql += " Where  user='" + login.getUser()+ "'";
//        stSql += " and  primerNombre='" + login.getUser()+ "'";
//        
//        try {
//            ResultSet rs = BD.getInstance().sqlSelect(stSql);
//            if(rs==null || !rs.next())return false;
//            login.setIdLogin(rs.getInt("idLogin")) ;
//            login.setUser(rs.getString("user")) ;
//            login.setPass(rs.getString("pass")) ;
//            login.setPrimerNombre(rs.getString("primerNombre")) ;
//            return true;
//        } catch (SQLException ex) {
//            Log.log(ex.getMessage());
//        }
//        return false;
//    }
    
    //******** Sql listar
//    public List<ClLogin> sqlSelectAll(int id,int nPagina,int cantidad){
//        List<ClLogin> lista=new ArrayList<>();
//        try {
//            String stSql = "Select ";
//            stSql += " idLogin";
//            stSql += " ,user";
//            stSql += " ,pass";
//            stSql += " ,nombre";
//            stSql += " from login ";
//                        stSql += " Where  idLogin='" + id+ "'";
//            stSql += " order by nombre asc";
//            ResultSet rs=BD.getInstance().sqlSelect(stSql);
//            if(rs==null)return null;
//            while (rs.next()) {                
//                ClLogin p = new ClLogin(rs.getInt("idLogin"), rs.getString("rut"), 
//                      rs.getString("nombre"), rs.getString("apellido"), 
//                      rs.getString("correo"), rs.getString("celular"), 
//                      rs.getString("passworld"), TipoLogin.valueOf("tipo"));
//                lista.add(p);
//            }
//            
//        } catch (Exception e) {
//            Log.log(e.getMessage());
//            e.getMessage();
//        }
//        return lista;
//    }

        

    

    
    
}
