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
        //Configuracion Nuevo Update
    static String repositorio = "https://github.com/marcheloBM/RepararPC";
    static String versionActual = "1.2";
    
    //Configuracion de BD localhot
    //Para mySQL
    String DriverBD="com.mysql.cj.jdbc.Driver";
    //Para Oracle
//    String DriverBD="oracle.jdbc.OracleDriver";
    String ip="localhost";
    String puerto="3306";
    String BaseDatos="RepararPC";
    String userBD="root";
    String passBD="";
    
    
    //Configuracion de Log
    String nameLog="LogGeneral.log";
    //Configuracion del Log4j
    // Nombre de Referencia del Archivo Log4j.properties
    String nameLog4jLib="Log4jLib.log";
    String nameLog4jApli="Log4jApli.log";
    // Ubicacion del los Archivos
    static String urlDirec="D:\\RepararPC\\";
    
    //Configuracion de Directorio
    static String carpeta = "RepararPC";
    static String SO = System.getProperty("os.name");
//    static String userDir = System.getProperty("user.home");
    static String userDir = "D:\\";
        
    //Prueba Login
    //final
    String loginUsep="";
    String loginPasp="";
    //Administrador
//    String loginUsep="111111111";
//    String loginPasp="prueba";
    //Usuario
//    String loginUsep="222222222";
//    String loginPasp="prueba";
    
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
