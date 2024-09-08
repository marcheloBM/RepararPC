/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.Inter;

/**
 *
 * @author march
 */
public interface Confi {
    //Configuracion de BD localhot
    //Para mySQL
    String DriverBD="com.mysql.jdbc.Driver";
    //Para Oracle
//    String DriverBD="oracle.jdbc.OracleDriver";
    String ip="localhost";
    String puerto="3306";
    String BaseDatos="RepararPC";
    String userBD="root";
    String passBD="";
    // BD Windows 7
//    String ip="192.168.1.84";
//    String puerto="3306";
//    String BaseDatos="RepararPC";
//    String userBD="marchelo";
//    String passBD="HP2117la";
    // BD Servidor
//    String ip="10.20.30.1";
//    String puerto="3306";
//    String BaseDatos="RepararPC";
//    String userBD="marchelo";
//    String passBD="HP2117la";
//    
    //Configuracion de Log
    String nameLog="LogGeneral.log";
    //Configuracion del Log4j
    // Nombre de Referencia del Archivo Log4j.properties
    String nameLog4jLib="Log4jLib.log";
    String nameLog4jApli="Log4jApli.log";
    // Ubicacion del los Archivos
    static String urlDirec="C:\\Users\\march\\RepararPC\\";
    
    //Configuracion de Directorio
    static String carpeta = "RepararPC";
    static String SO = System.getProperty("os.name");
    static String userDir = System.getProperty("user.home");
        
    //Prueba Login
    String loginUsep="170088646";
    String loginPasp="HP2117la";
//    String loginUsep="111111111";
//    String loginPasp="admin";
    
    //Cliente
    String clienteRut="24021829-1";
    String clienteNombre="Jose";
    String clienteApellido="Bustos";
    String clienteCorreo="j.Bustos@live.cl";
    String clienteCelular="123456789";
    
    //PC
//    String capacidadP="8";
//    String descripcionP="pruebas";
//    String valorP="1000";
    
}
