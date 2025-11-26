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
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author march
 */
public class ImprimirPDF {
    
    //Variables del Log4j
    static  org.apache.log4j.Logger log =org.apache.log4j.Logger.getLogger(ImprimirPDF.class);
    
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL, BaseColor.RED);
    private static Font blackFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL, BaseColor.BLACK);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
    
    private Document doc = new Document();
//    static String SO = System.getProperty("os.name");
    //Imprimir las tabla en PDF
    public void ImprimirPDF(JTable table,String nombreArc){
        javax.swing.filechooser.FileNameExtensionFilter filtroWord=new FileNameExtensionFilter("Adobe Acrobat PDF", "pdf");
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
        miArchivo.setSelectedFile(new File("Reporte "+nombreArc));
        int aceptar=miArchivo.showSaveDialog(null);
        miArchivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(aceptar==JFileChooser.APPROVE_OPTION){
            File fileWord=miArchivo.getSelectedFile();
            String nombre=fileWord.getName();
        try {    if(nombre.indexOf('.')==-1){
                nombre+=".pdf";
                PdfWriter.getInstance(doc, new FileOutputStream(fileWord.getParentFile()+"/"+nombre));
            }
            doc.open();
            
            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            //Agregando encabezados de tabla
            for (int i = 0; i < table.getColumnCount(); i++) {
                pdfTable.addCell(table.getColumnName(i));
            }
            //Extraer datos de la JTable e insertarlo en PdfPTable
            for (int rows = 0; rows < table.getRowCount() - 1; rows++) {
                for (int cols = 0; cols < table.getColumnCount(); cols++) {
                    pdfTable.addCell(table.getModel().getValueAt(rows, cols).toString());

                }
            }
            Paragraph preface = new Paragraph();
                // Añadimos una línea vacía
                addEmptyLine(preface, 1);
                // Permite escribir un encabezado grande
                preface.add(new Paragraph("Título del documento", catFont));
                addEmptyLine(preface, 1);
                
                // Creará: Informe generado por: _name, _date
                preface.add(new Paragraph("Informe generado " + new Date(),smallBold));
                addEmptyLine(preface, 1);
                
                preface.add(new Paragraph("Este documento describe algo que es muy importante ",smallBold));
                addEmptyLine(preface, 1);

                preface.add(new Paragraph("Este documento es una versión preliminar y no está sujeto a su contrato de licencia o cualquier otro acuerdo.",redFont));
            addEmptyLine(preface, 1);
            doc.add(preface);
            
            doc.add(pdfTable);
            doc.close();
//            System.out.println("done");
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null,"Hubo un error"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            log.info(ex.getMessage());
            Log.log(ex.getMessage());
           
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Hubo un error"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            log.info(ex.getMessage());
            Log.log(ex.getMessage());
        }finally{
                try {
                    if(System.getProperty("os.name").equals("Linux")){
//                        Runtime.getRuntime().exec("libreoffice"+fileWord.getAbsolutePath());
                        File objetofile = new File (fileWord.getAbsolutePath()+".pdf");
                        Desktop.getDesktop().open(objetofile);
                    }
                    else{
//                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+fileWord.getAbsolutePath());
                        File objetofile = new File (fileWord.getAbsolutePath()+".pdf");
                        Desktop.getDesktop().open(objetofile);
                    }
                }
                catch (IOException ex) {
                    Logger.getLogger(ImprimirPDF.class.getName()).log(Level.SEVERE, null, ex);
                    log.info(ex.getMessage());
                    Log.log(ex.getMessage());
                }
            }

    }
    }
    private static void addEmptyLine(Paragraph paragraph, int number) {
                for (int i = 0; i < number; i++) {
                        paragraph.add(new Paragraph(" "));
                }
        }
    //Imprimir informacion de Clase
    public void Crear(ClLogin login,ClCliente cliente,ClComputador computador){
//        Directorio d = new Directorio();
        javax.swing.filechooser.FileNameExtensionFilter filtroWord=new FileNameExtensionFilter("Adobe Acrobat PDF", "pdf");
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
        try {    
            if(nombre.indexOf('.')==-1){
                nombre+=".pdf";
                PdfWriter.getInstance(doc, new FileOutputStream(fileWord.getParentFile()+"/"+nombre));
            }else{
                nombre+=".pdf";
                PdfWriter.getInstance(doc, new FileOutputStream(fileWord.getParentFile()+"/"+nombre));
            }
            doc.open();
            Paragraph preface = new Paragraph();
                // Añadimos una línea vacía
                addEmptyLine(preface, 1);
                // Permite escribir un encabezado grande
                preface.add(new Paragraph("Reparación", catFont));
                addEmptyLine(preface, 1);
                
                // Creará: Informe generado por: _name, _date
                preface.add(new Paragraph("Informe generado por: Nombre de usuario " +login.getNombre()+" "+login.getApellido()+
                        ", " + new Date(),smallBold));
                addEmptyLine(preface, 1);
                
                preface.add(new Paragraph("El documento es una versión preliminar y describe sobre el servicio prestado al cliente.",smallBold));
                addEmptyLine(preface, 1);

                preface.add(new Paragraph("Por lo cual Documento contiene información sobre la reparación efectuada al dispositivo "
                        + "entregado por el cliente en la cual se definen algunos datos sobre el cliente y dispositivo para "
                        + "demostrar mayor caridad sobre la reparación efectuada.",blackFont));
                addEmptyLine(preface, 1);
                
            doc.add(preface);
            
                // a table with three columns
                PdfPTable table = new PdfPTable(4);
                // the cell object
                PdfPCell cell;
                // we add a cell with colspan 3
                cell = new PdfPCell(new Phrase("Datos del Cliente",redFont));
                cell.setColspan(4);
                table.addCell(cell);
//                // now we add a cell with rowspan 2
//                cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
//                cell.setRowspan(2);
//                table.addCell(cell);
                // we add the four remaining cells with addCell()
                table.addCell("NOMBRE");
                table.addCell("CORREO");
                table.addCell("CELULAR");
                
                table.addCell(cliente.getNombre()+" "+cliente.getApellido());
                table.addCell(cliente.getCorreo());
                table.addCell(cliente.getCelular());
            doc.add(table);
            
            Paragraph preface2 = new Paragraph();
            addEmptyLine(preface2, 1);
            preface2.add(new Paragraph("Descripción del Servicio",redFont));
                addEmptyLine(preface2, 1);
            doc.add(preface2);
            
            // a table with three columns
                PdfPTable table2 = new PdfPTable(5);
                // the cell object
                PdfPCell cell2;
                // we add a cell with colspan 3
                cell2 = new PdfPCell(new Phrase("Datos del PC o Notebook",redFont));
                cell2.setColspan(5);
                table2.addCell(cell2);
                table2.addCell("MARCA");
                table2.addCell("MODELO");
                table2.addCell("NUM DE SERIE");
                
                table2.addCell(computador.getMarca());
                table2.addCell(computador.getModelo());
                table2.addCell(computador.getNumSerie());
            doc.add(table2);
            
            Paragraph preface3 = new Paragraph();
            addEmptyLine(preface3, 1);
            doc.add(preface3);
            
            // a table with three columns
                PdfPTable table3 = new PdfPTable(3);
                // the cell object
                PdfPCell cell3;
                // we add a cell with colspan 3
                cell3 = new PdfPCell(new Phrase("Sistema Operativo",redFont));
                cell3.setColspan(3);
                table3.addCell(cell3);
                table3.addCell("SO");
                table3.addCell("ARQUITECTURA DEL SO");
                
                table3.addCell(computador.getSistemaOpe());
                table3.addCell(computador.getArquitectura());
            doc.add(table3);
            
            Paragraph preface4 = new Paragraph();
            addEmptyLine(preface4, 1);
            preface4.add(new Paragraph("El tipo de reparación fue "+computador.getTipoRepa()+" que describe con lo siguiente "+computador.getDescripcion()+
                    ", efectuado en la fecha de "+FormatoFecha.mostrarFechaHora(computador.getFecha())+" por lo cual se cobró un valor de "+computador.getValor()+" por el servicio prestado.",blackFont));
            addEmptyLine(preface4, 1);
            preface4.add(new Paragraph("Este documento contiene toda la información sobre la reparación efectuada en su dispositivo, "
                    + "por lo cual se le otorgara 1 mes de garantía sobre dicha reparación.",blackFont));
            
            
            doc.add(preface4);
            
            doc.close();
            
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null,"Hubo un error"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            log.info(ex.getMessage());
            Log.log(ex.getMessage());
        }   catch (FileNotFoundException ex) {
            Logger.getLogger(ImprimirPDF.class.getName()).log(Level.SEVERE, null, ex);
            log.info(ex.getMessage());
            Log.log(ex.getMessage());
        }
        finally{
                try {
                    if(System.getProperty("os.name").equals("Linux")){
//                        Runtime.getRuntime().exec("libreoffice"+fileWord.getAbsolutePath());
                        File objetofile = new File (fileWord.getAbsolutePath()+".pdf");
                        Desktop.getDesktop().open(objetofile);
                    }
                    else{
//                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+fileWord.getAbsolutePath());
                        File objetofile = new File (fileWord.getAbsolutePath()+".pdf");
                        Desktop.getDesktop().open(objetofile);
                    }
                }
                catch (IOException ex) {
                    Logger.getLogger(ImprimirPDF.class.getName()).log(Level.SEVERE, null, ex);
                    log.info(ex.getMessage());
                    Log.log(ex.getMessage());
                }
            }

    }
    }
    
}
