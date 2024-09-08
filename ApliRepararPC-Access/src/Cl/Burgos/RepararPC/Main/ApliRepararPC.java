/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.Main;

import Cl.Burgos.RepararPC.FUN.Directorio;
import Cl.Burgos.RepararPC.GUI.FrLogin;
import Cl.Burgos.RepararPC.Conf.Confi;
import Cl.Burgos.RepararPC.FUN.ValidarPC;
import Cl.Burgos.RepararPC.GUI.PopupDatos;
import java.io.File;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Marchelo
 */
public class ApliRepararPC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        File log4jfile = new File(Confi.userProgra+"/Log4j.properties");
        PropertyConfigurator.configure(log4jfile.getAbsolutePath());
        
        Directorio.crearDirecPre();
        Directorio.crearDirecSec();
        
//        new ValidarPC().validarRegistropc();

        new FrLogin().setVisible(true);
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                PopupDatos dialog = new PopupDatos(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });               
//            }
//        });
    }
    
}
