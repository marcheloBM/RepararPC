/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.Conf;

/**
 *
 * @author Marchelo
 */
public interface Confi { 
/*
*   Configuracion de BD localhost 
*   Para mySQL - Oracle - Access - SqlServer 
*/
//    String DriverBD="com.mysql.jdbc.Driver";
//    String DriverBD="oracle.jdbc.OracleDriver";
    String DriverBD ="net.ucanaccess.jdbc.UcanaccessDriver";
//    String DriverBD ="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    
    /* Configuraciones BD Windows 7*/
//    String ipBD="localhost";
//    String puertoBD="3306";
//    String BaseDatosBD="RepararPC";
//    String userBD="root";
//    String passBD="";
    
    /* Configuraciones BD Access*/
    String BaseDatosBD="./RepararPC.accdb";
    String userBD="sa";
    String passBD="mbm31";
    
    /* Configuraciones Sql Server*/
//    String ipBD="localhost";
//    String puertoBD="1433";
//    String BaseDatosBD="RepararPC";
//    String userBD="root";
//    String passBD="";
    
    /* Configuraciones Oracle*/
//    String ipBD="localhost";
//    String puertoBD="1521";
//    String BaseDatosBD="RepararPC";
//    String userBD="HALCONE";
//    String passBD="123";
    
     //Configuracion de Log
    String nameLog="LogGeneral.log";
    //Configuracion del Log4j
    // Nombre de Referencia del Archivo Log4j.properties
    String nameLog4jLib="Log4jLib.log";
    String nameLog4jApli="Log4jApli.log";
    // Ubicacion del los Archivos
    static String urlDirec="C:\\RepararPC\\";
    
    //Configuracion de Directorio
    static String Url = "C:\\";
    static String carpeta1 = "Aplicaciones";
    static String carpeta2 = "RepararPC";
    static String SO = System.getProperty("os.name");
    static String userDir = System.getProperty("user.home");
    static String userProgra = System.getProperty("user.dir");
        
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
    
    //Comandos
    String versionSO = System.getProperty("os.version");
    
}
