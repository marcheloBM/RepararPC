/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.FUN;

import Cl.Burgos.RepararPC.ENT.ClCliente;
import Cl.Burgos.RepararPC.ENT.ClComputador;
import Cl.Burgos.RepararPC.ENT.ClLogin;
import static Cl.Burgos.RepararPC.Conf.Confi.*;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

/**
 *
 * @author march
 */
public class ImprimirWord {
    
    //Variables del Log4j
    static  org.apache.log4j.Logger log =org.apache.log4j.Logger.getLogger(ImprimirWord.class);
    
    private XWPFDocument document=new XWPFDocument();
//    static String SO = System.getProperty("os.name");
    //Imprimir las tabla
    public void ImprimirWord(JTable jTable,String nombreArchivo) {
        javax.swing.filechooser.FileNameExtensionFilter filtroWord=new FileNameExtensionFilter("Microsoft Word 2016", "docx");
        final JFileChooser miArchivo=new JFileChooser();
        miArchivo.setFileFilter(filtroWord);
        miArchivo.setDialogTitle("Guardar archivo");
        miArchivo.setMultiSelectionEnabled(false);
        miArchivo.setAcceptAllFileFilterUsed(false);
        String userDir = System.getProperty("user.home");
        //preferencia de ubicacion
        if(SO.startsWith("Windows")){
            miArchivo.setCurrentDirectory(new File(userDir +"/Desktop"));
        }else{
            miArchivo.setCurrentDirectory(new File(userDir +"/Escritorio"));
        }
        //El nombre del Archivo
        String nomArchivo="Reporte "+nombreArchivo;
        miArchivo.setSelectedFile(new File(nomArchivo));
        int aceptar=miArchivo.showSaveDialog(null);
        miArchivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(aceptar==JFileChooser.APPROVE_OPTION){
            File fileWord=miArchivo.getSelectedFile();
            String nombre=fileWord.getName();
            if(nombre.indexOf('.')==-1){
                nombre+=".docx";
                fileWord=new File(fileWord.getParentFile(), nombre);
            }else{
                nombre+=".docx";
                fileWord=new File(fileWord.getParentFile(), nombre);
            }
           FileOutputStream fos = null; 
           XWPFDocument document = null; 
           XWPFParagraph para = null; 
           XWPFRun run = null;
           try { 
               // Crear el primer párrafo y establecer su texto. 
               document = new XWPFDocument(); 
               para = document.createParagraph(); 

               para.setAlignment(ParagraphAlignment.CENTER); 

               para.setSpacingAfter(100); 

               para.setSpacingAfterLines(10);
               run = para.createRun(); 
//               for(int i=1; i<=5; i++)
//               run.setText("Test Name Marchelo Value 12345678 Normal Ranges09384"); 
//               run.addBreak();    // similar a la nueva línea
//               run.addBreak();

               XWPFTable table = document.createTable(jTable.getRowCount(),jTable.getColumnCount());
               
               
                for (int i = 0; i < jTable.getColumnCount(); i++) {
//                        System.out.println(jTable.getColumnName(i)+"-");
                        table.getRow(0).getCell(i).setText(jTable.getColumnName(i));
                }
                for (int rows = 0; rows < jTable.getRowCount();rows++) {
                    for (int cols = 0; cols < jTable.getColumnCount(); cols++) {
//                        System.out.print(jTable.getValueAt(rows, cols)+"-");
                        table.getRow(rows).getCell(cols).setText(jTable.getValueAt(rows, cols).toString());
                    }
//                    System.out.println();
                }
                
               table.setRowBandSize(1);
               table.setWidth(1);
               table.setColBandSize(1);
               table.setCellMargins(1, 1, 100, 30);

               if(fileWord.exists())
                   fileWord.delete();


               FileOutputStream out = new FileOutputStream(fileWord);
               document.write(out);
               out.close();
           } catch (Exception e){
               log.info(e.getMessage());
               Log.log(e.getMessage());
           }
           finally{
                try {
                    if(System.getProperty("os.name").equals("Linux")){
//                        Runtime.getRuntime().exec("libreoffice"+fileWord.getAbsolutePath());
                        File objetofile = new File (fileWord.getAbsolutePath());
                        Desktop.getDesktop().open(objetofile);
                    }
                    else{
//                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+fileWord.getAbsolutePath());
                        File objetofile = new File (fileWord.getAbsolutePath());
                        Desktop.getDesktop().open(objetofile);
                    }
                }
                catch (IOException ex) {
                    Logger.getLogger(ImprimirWord.class.getName()).log(Level.SEVERE, null, ex);
                    log.info(ex.getMessage());
                    Log.log(ex.getMessage());
                }
            }
        }
    }
    //Imprimir informacion de Clase
    public void crear(ClLogin login,ClCliente cliente,ClComputador computador){
        javax.swing.filechooser.FileNameExtensionFilter filtroWord=new FileNameExtensionFilter("Microsoft Word 2016", "docx");
        final JFileChooser miArchivo=new JFileChooser();
        miArchivo.setFileFilter(filtroWord);
        miArchivo.setDialogTitle("Guardar archivo");
        miArchivo.setMultiSelectionEnabled(false);
        miArchivo.setAcceptAllFileFilterUsed(false);
        String userDir = System.getProperty("user.home");
        //preferencia de ubicacion
        if(SO.startsWith("Windows")){
            miArchivo.setCurrentDirectory(new File(userDir +"/Desktop"));
        }else{
            miArchivo.setCurrentDirectory(new File(userDir +"/Escritorio"));
        }
        //El nombre del Archivo
        String nomArchivo=cliente.getNombre()+" "+computador.getMarca()+" "+FormatoFecha.fDateS(new Date());
        miArchivo.setSelectedFile(new File(nomArchivo));
        int aceptar=miArchivo.showSaveDialog(null);
        miArchivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(aceptar==JFileChooser.APPROVE_OPTION){
            File fileWord=miArchivo.getSelectedFile();
            String nombre=fileWord.getName();
            if(nombre.indexOf('.')==-1){
                nombre+=".docx";
                fileWord=new File(fileWord.getParentFile(), nombre);
            }else{
                nombre+=".docx";
                fileWord=new File(fileWord.getParentFile(), nombre);
            }
            try {
                FileOutputStream output=new FileOutputStream(fileWord);
                //FileOutputStream output=new FileOutputStream("documento de prueba.docx");

                XWPFParagraph paragraphTitulo=document.createParagraph();
                XWPFRun runTitulo=paragraphTitulo.createRun();

                paragraphTitulo.setAlignment(ParagraphAlignment.CENTER);
                runTitulo.setBold(true);
                runTitulo.setFontSize(15);
                runTitulo.setUnderline(UnderlinePatterns.WORDS);
                runTitulo.setText("Reparación");
                runTitulo.setColor("0026ff");
//                runTitulo.setColor("2f66f2");
                runTitulo.addBreak();

                XWPFParagraph paragraph=document.createParagraph();
                XWPFRun run=paragraph.createRun();
                run.setText("Informe generado por: "+login.getNombre()+" "+login.getApellido()+", "+FormatoFecha.mostrarFechaCompletaEditada(new Date()));
                run.addBreak();
                run.addBreak();
                run.setText("El documento es una versión preliminar y describe sobre el servicio prestado al cliente.");
                run.addBreak();
                run.setText("Por lo cual Documento contiene información sobre la reparación efectuada al dispositivo ");
                run.setText("entregado por el cliente en la cual se definen algunos datos sobre el cliente y dispositivo para ");
                run.setText("demostrar mayor caridad sobre la reparación efectuada.");
                run.addBreak();
                run.addBreak();
                run.setText("Datos del Cliente");
                run.addBreak();
                run.setText("Nombre: "+cliente.getNombre()+" "+cliente.getApellido()+""
                        + ", Correo: "+cliente.getCorreo()+", Celular: "+cliente.getCelular());
                                               
                run.addBreak();
                run.addBreak();
                run.setText("Descripción del Servicio");
                run.addBreak();
                run.setText("Modelo del computador a Reparar.");
                run.addBreak();
                run.setText("Marca: "+computador.getMarca()+", Modelo: "+computador.getModelo()+""
                        + ", Número de serie: "+computador.getNumSerie());
                run.addBreak();
                run.addBreak();
                run.setText("Sistema Operativo Instalado en el Dispositivo");
                run.addBreak();
                run.setText("Sistema operativo: "+computador.getSistemaOpe()+""
                        + ", Arquitectura del SO: "+computador.getArquitectura());
                run.addBreak();
                run.addBreak();
                run.setText("Descripción del Servicio Prestado");
                run.addBreak();
                run.setText("Tipo Reparación: "+computador.getTipoRepa()+", Descripción: "+computador.getDescripcion()+""
                        + ", valor: "+computador.getValor()+", Fecha: "+FormatoFecha.mostrarFechaHora(computador.getFecha()));
                run.addBreak();
                run.addBreak();
                run.setText("El tipo de reparación fue "+computador.getTipoRepa()+" que describe con lo siguiente "+computador.getDescripcion());
                run.setText(", efectuado en la fecha de "+FormatoFecha.mostrarFechaHora(computador.getFecha())+" por lo cual se cobró un valor de "+computador.getValor()+" por el servicio prestado.");
                run.addBreak();
                run.addBreak();
                run.setText("Este documento contiene toda la información sobre la reparación efectuada en su dispositivo, ");
                run.setText("por lo cual se le otorgara 1 mes de garantía sobre dicha reparación.");
                run.addBreak();
                
//                for(int i=0;i<10;i++){
//                    XWPFParagraph paragraphLista=document.createParagraph();
//                    XWPFRun runLista=paragraphLista.createRun();
//                    runLista.setText("Item "+i);
//                    paragraphLista.setNumID(BigInteger.ONE);
//                }
//                XWPFTable table=document.createTable(2,4);
//                table.getRow(0).getCell(0).setText("RUT");
//                table.getRow(0).getCell(1).setText("Nombre");
//                table.getRow(0).getCell(2).setText("Correo");
//                table.getRow(0).getCell(3).setText("Celular");
//
//                table.getRow(1).getCell(0).setText(cliente.getRut());
//                table.getRow(1).getCell(1).setText(cliente.getNombre()+" "+cliente.getApellido());
//                table.getRow(1).getCell(2).setText(cliente.getCorreo());
//                table.getRow(1).getCell(3).setText(cliente.getCelular());
//                
//                table.setRowBandSize(1);
//                table.setWidth(1);
//                table.setColBandSize(1);
//                table.setCellMargins(1, 1, 100, 30);
                
                document.write(output);
                output.close();
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage().toString());
                log.info(e.getMessage());
                Log.log(e.getMessage());
            }
            
            finally{
                try {
                    if(System.getProperty("os.name").equals("Linux")){
//                        Runtime.getRuntime().exec("libreoffice"+fileWord.getAbsolutePath());
                        File objetofile = new File (fileWord.getAbsolutePath());
                        Desktop.getDesktop().open(objetofile);
                    }
                    else{
//                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+fileWord.getAbsolutePath());
                        File objetofile = new File (fileWord.getAbsolutePath());
                        Desktop.getDesktop().open(objetofile);
                    }
                }
                catch (IOException ex) {
                    Logger.getLogger(ImprimirWord.class.getName()).log(Level.SEVERE, null, ex);
                    log.info(ex.getMessage());
                    Log.log(ex.getMessage());
                }
            }
        }
    }
}
