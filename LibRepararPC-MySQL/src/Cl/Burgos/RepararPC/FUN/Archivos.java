/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.FUN;

import Cl.Burgos.RepararPC.Log4j.Log;
import Cl.Burgos.RepararPC.DAO.DAOCliente;
import Cl.Burgos.RepararPC.DAO.DAOComputador;
import Cl.Burgos.RepararPC.ENT.ClCliente;
import Cl.Burgos.RepararPC.ENT.ClComputador;
import Cl.Burgos.RepararPC.FUN.Directorio;
import Cl.Burgos.RepararPC.FUN.FormatoFecha;
import static Cl.Burgos.RepararPC.Inter.Confi.SO;
import static Cl.Burgos.RepararPC.Inter.Confi.userDir;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.log4j.Logger;

/**
 *
 * @author march
 */
public class Archivos {
    //Variables del Log4j
    static  Logger log =Logger.getLogger(Archivos.class);
    
    public static ClComputador leerArchivo() throws Exception{
        String archiv=selectArchivo();
        File archivo = new File (archiv);
//        File archivo = new File ("E:/informacion.txt");
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        String cadena = br.readLine();
        int filas=Integer.parseInt(cadena);
        String[] alinea=new String[filas];
        String separador = ";";
        
        String rut,nombre,apellido,correo,celular,marca,modelo,numserie,SO,arquitectura,version,fecha;
        ClComputador computador = null;
        
        for (int i = 0; i < alinea.length; i++) {
            cadena=br.readLine();
            alinea[i]=cadena;
        }
        for (int i = 0; i < alinea.length; i++) {
            String cade=alinea[i];
            StringTokenizer st=new StringTokenizer(cade, separador);
            while (st.hasMoreTokens()) {
                rut=st.nextToken();
                nombre=st.nextToken();
                apellido=st.nextToken();
                correo=st.nextToken();
                celular=st.nextToken();
                marca=st.nextToken();
                modelo=st.nextToken();
                numserie=st.nextToken();
                SO=st.nextToken();
                arquitectura=st.nextToken();
                version=st.nextToken();
                fecha=st.nextToken();
                
                fecha=fecha.substring(0, fecha.indexOf('.'));
                
                int id=0;
                
//              
                ClCliente cliente = new ClCliente(rut, nombre, apellido, correo, celular,1);
                
                DAOCliente dAOCliente = new DAOCliente();
                if(!dAOCliente.sqlSearchRut(cliente)){
                    dAOCliente.sqlInsert(cliente);
                    computador = new ClComputador(marca, modelo, numserie, SO, arquitectura, version, FormatoFecha.mostrarFechaYMDHMS(fecha));
                }
            }     
        }      
        return computador;
    }
    
    public static void crearArchivo(String msg,String nom){
        String url=Directorio.selectDirecPre()+"/"+nom+".txt";
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(url, true)))) {
            out.println("1");
            out.println(msg);
            
            Directorio.abrirDirecPre();
            
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
            JOptionPane.showMessageDialog(null,"Hubo un error"+e);
            Log.log(e.getMessage());
            log.info(e.getMessage());
        }
    }
    
    public static String selectArchivo(){
        String url=null;
        //Creamos el objeto JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        //Indicamos lo que podemos seleccionar
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //Creamos el filtro
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo De Texto", "txt");
        //Le indicamos el filtro
        fileChooser.setFileFilter(filtro);
        //Indicamos que podemos seleccionar varios ficheros
        fileChooser.setMultiSelectionEnabled(false);
        //Indicamos un lugar de busqueda predeterminada
        if(SO.startsWith("Windows")){
            fileChooser.setCurrentDirectory(new File(userDir +"/Desktop"));
        }else{
            fileChooser.setCurrentDirectory(new File(userDir +"/Escritorio"));
        }
        //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
        int aceptar=fileChooser.showOpenDialog(null);
        //Si el usuario, pincha en aceptar
        if(aceptar==JFileChooser.APPROVE_OPTION){
            url = fileChooser.getSelectedFile().toString();
        }else{
            JOptionPane.showMessageDialog(null,"Hubo un error al seleccionar el Archivo");
            Log.log("Hubo un error al seleccionar el Archivo");
            log.info("Hubo un error al seleccionar el Archivo");
        }
        return url;
    }
}
