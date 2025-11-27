/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.Main;

import Cl.Burgos.RepararPC.FUN.Directorio;
import Cl.Burgos.RepararPC.GUI.FrLogin;
import Cl.Burgos.RepararPC.GUI.GitHubReleaseGUI;
import Cl.Burgos.RepararPC.Inter.Confi;
import java.io.File;
import javax.swing.JOptionPane;
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
        Inicio();
    }
    public static void interzas(){
        //Variables para Ejecutar el Archivo Log4j
        File log4jfile = new File("./src/Cl/Burgos/RepararPC/Log4j/Log4j.properties");
        PropertyConfigurator.configure(log4jfile.getAbsolutePath());
        
        Directorio.crearDirecPre();
        FrLogin frLogin = new FrLogin();
        frLogin.setVisible(true);
    }
    public static void Inicio(){
        String repo = Confi.repositorio;
        String versionActual = Confi.versionActual;

        String ultimaVersion = GitHubReleaseGUI.obtenerUltimaVersion(repo);

        if (ultimaVersion == null) {
            JOptionPane.showMessageDialog(null, "⚠️ No se pudo verificar la versión.");
            //Inicia el programa si no se puede verificar
            interzas();
        } else if (ultimaVersion.equals(versionActual)) {
            // Estás usando la última versión publicada
            interzas();
        } else if (compararVersiones(versionActual, ultimaVersion) > 0) {
            // Estás usando una versión más nueva que la publicada
            JOptionPane.showMessageDialog(null, "🧪 Estás usando una versión en desarrollo (" + versionActual + ").");
            interzas();
        } else {
            // Hay una versión más nueva publicada
            JOptionPane.showMessageDialog(null, "🟢 Hay una nueva versión disponible: " + ultimaVersion);
            int respu = JOptionPane.showConfirmDialog(null, "¿Desea descargar la nueva versión?");
            if (respu == JOptionPane.YES_OPTION) {
                //Abrimos para descargar la nueva version
                GitHubReleaseGUI.main(new String[]{});
            } else {
                JOptionPane.showMessageDialog(null, "Intente mantener el programa actualizado.");
                //Si no queremos actualizar a la ultima Version
                interzas();
            }
        }
    }
    public static int compararVersiones(String v1, String v2) {
        String[] a = v1.split("\\.");
        String[] b = v2.split("\\.");
        int len = Math.max(a.length, b.length);
        for (int i = 0; i < len; i++) {
            int n1 = i < a.length ? Integer.parseInt(a[i]) : 0;
            int n2 = i < b.length ? Integer.parseInt(b[i]) : 0;
            if (n1 != n2) return Integer.compare(n1, n2);
        }
        return 0;
    }
}
