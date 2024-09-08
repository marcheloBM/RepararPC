/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.Main;

import Cl.Burgos.RepararPC.FUN.Directorio;
import Cl.Burgos.RepararPC.GUI.FrLogin;
import java.io.File;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author march
 */
public class ApliRepararPC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Variables para Ejecutar el Archivo Log4j
        File log4jfile = new File("./src/Cl/Burgos/RepararPC/Log4j/Log4j.properties");
        PropertyConfigurator.configure(log4jfile.getAbsolutePath());
        
        Directorio.crearDirecPre();
        FrLogin frLogin = new FrLogin();
        frLogin.setVisible(true);
    }
    
}
