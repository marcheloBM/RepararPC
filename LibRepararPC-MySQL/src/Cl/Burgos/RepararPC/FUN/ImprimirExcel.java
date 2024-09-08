/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.FUN;

import Cl.Burgos.RepararPC.Log4j.Log;
import Cl.Burgos.RepararPC.ENT.ClCliente;
import Cl.Burgos.RepararPC.ENT.ClComputador;
import Cl.Burgos.RepararPC.ENT.ClLogin;
import static Cl.Burgos.RepararPC.Inter.Confi.*;
import java.awt.Desktop;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
import jxl.write.WriteException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author march
 */
public class ImprimirExcel {
    private File file;
    private List<JTable> tabla;
    private List<String> nom_files;

    private org.apache.poi.ss.usermodel.Workbook libro=new HSSFWorkbook();
//    static String SO = System.getProperty("os.name");
    
    //Variables del Log4j
    static  org.apache.log4j.Logger log =org.apache.log4j.Logger.getLogger(ImprimirExcel.class);
    
    //Imprimir las tabla
    public void ImprimirExcel(List<JTable> tabla, List<String> nom_files,String nombreArc) {
        javax.swing.filechooser.FileNameExtensionFilter filtroWord=new FileNameExtensionFilter("Microsoft Excel 2016", "xls");
        final JFileChooser miArchivo=new JFileChooser();
        miArchivo.setFileFilter(filtroWord);
        miArchivo.setDialogTitle("Guardar archivo");
        miArchivo.setMultiSelectionEnabled(false);
        miArchivo.setAcceptAllFileFilterUsed(false);
//        String userDir = System.getProperty("user.home");
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
            if(nombre.indexOf('.')==-1){
                nombre+=".xls";
                fileWord=new File(fileWord.getParentFile(), nombre);
            }else{
                nombre+=".xls";
                fileWord=new File(fileWord.getParentFile(), nombre);
            }
            this.file = fileWord;
            this.tabla = tabla;
            this.nom_files = nom_files;
            if (nom_files.size()!=tabla.size()) {
                try {
                    throw new Exception ("Error");
                } catch (Exception ex) {
                    Logger.getLogger(ImprimirExcel.class.getName()).log(Level.SEVERE, null, ex);
                    log.info(ex.getMessage());
                    Log.log(ex.getMessage());
                } 
            }
        } 
    }
    public boolean export() {
        
        try {
            DataOutputStream out=new DataOutputStream (new FileOutputStream(file));
            WritableWorkbook w = Workbook.createWorkbook(out);
            for (int index=0; index<tabla.size(); index++){
                JTable table=tabla.get(index);
                WritableSheet s=w.createSheet(nom_files.get(index),0);
               //Para que salga el titulo de las columnas
                for (int i = 0; i < table.getColumnCount(); i++) {
                for (int j = 0; j < table.getRowCount(); j++) {
                    Object titulo = table.getColumnName(i);
                    s.addCell(new Label(i+1, j+1, String.valueOf(titulo)));
                }
                }
            for (int i = 0; i < table.getColumnCount(); i++) {
                  for (int j = 0; j < table.getRowCount(); j++) {
                        Object object = table.getValueAt(j, i);
                        s.addCell(new Label(i+1, j+2, String.valueOf(object)));
                  }
                }
             /*
            *    for sin titulo de columnas:
            *
            *  for (int i=0; i<table.getColumnCount(); i++){
            *     for (int j=0; j<table.getRowCount();j++){
            *         Object object=table.getValueAt(j,i);
            *         s.addCell (new Label(i,j,String.valueOf(object)));
            *        
            *     }   
            * }
            **/

            }
            w.write();
            w.close();
            return true;
        }catch (IOException | WriteException e) {
            log.info(e.getMessage());
            Log.log(e.getMessage());
            return false;
        }finally{
                try {
                    if(System.getProperty("os.name").equals("Linux")){
//                        Runtime.getRuntime().exec("libreoffice"+fileWord.getAbsolutePath());
                        File objetofile = new File (file.getAbsolutePath());
                        Desktop.getDesktop().open(objetofile);
                    }
                    else{
//                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+fileWord.getAbsolutePath());
                        File objetofile = new File (file.getAbsolutePath());
                        Desktop.getDesktop().open(objetofile);
                    }
                }
                catch (IOException ex) {
                    Logger.getLogger(ImprimirExcel.class.getName()).log(Level.SEVERE, null, ex);
                    log.info(ex.getMessage());
                    Log.log(ex.getMessage());
                }
            }
    }
    //Imprimir informacion de ClString[]ase
    public void Crear(String[] login,String[] cliente,String[] computador){
        javax.swing.filechooser.FileNameExtensionFilter filtroWord=new FileNameExtensionFilter("Microsoft Excel 2016", "xls");
        final JFileChooser miArchivo=new JFileChooser();
        miArchivo.setFileFilter(filtroWord);
        miArchivo.setDialogTitle("Guardar archivo");
        miArchivo.setMultiSelectionEnabled(false);
        miArchivo.setAcceptAllFileFilterUsed(false);
//        String userDir = System.getProperty("user.home");
        //preferencia de ubicacion
        if(SO.startsWith("Windows")){
            miArchivo.setCurrentDirectory(new File(userDir +"/Desktop"));
        }else{
            miArchivo.setCurrentDirectory(new File(userDir +"/Escritorio"));
        }
        //El nombre del Archivo
        String nomArchivo=cliente[1]+" "+computador[1]+" "+FormatoFecha.fDateS(new Date());
        miArchivo.setSelectedFile(new File(nomArchivo));
        int aceptar=miArchivo.showSaveDialog(null);
        miArchivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(aceptar==JFileChooser.APPROVE_OPTION){
            File fileWord=miArchivo.getSelectedFile();
            String nombre=fileWord.getName();
            if(nombre.indexOf('.')==-1){
                nombre+=".xls";
                fileWord=new File(fileWord.getParentFile(), nombre);
            }else{
                nombre+=".xls";
                fileWord=new File(fileWord.getParentFile(), nombre);
            }
            try {
                FileOutputStream output = new FileOutputStream(fileWord);
                //Creamos un nuevo libro
                
                //Creamos una nueva hoja
//                Sheet hoja = libro.createSheet("Hoja1");

//                //Creamos una Fila
//                Row fila = hoja.createRow(0);
//                //Creamos una celda
//                Cell celda = fila.createCell(0);
//                //Añadimos el texto
//                celda.setCellValue("Informacion de "+login[1]);
                                
//                for(int i=1;i<10;i++){
//                    for (int j=1; j < 10; j++) {
//                        Row filaa = hoja.createRow(1);
//                        //Creamos una celda
//                        Cell celdaa = filaa.createCell(j);
//                        //Añadimos el texto
//                        celdaa.setCellValue(1);
//                    }
//                }
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.createSheet();
                workbook.setSheetName(0, "Hoja excel");

                String[] headers = new String[]{
                    "ID",
                    "RUT",
                    "Nombre",
                    "Apellido",
                    "Correo",
                    "Celular",
                    "Passworld",
                    "Tipo"
                };
                String[] headers2 = new String[]{
                    "ID",
                    "RUT",
                    "Nombre",
                    "Apellido",
                    "Correo",
                    "Celular",
                    "IDLogin"
                };
                String[] headers3 = new String[]{
                    "ID",
                    "Marca",
                    "Modelo",
                    "Numero Serie",
                    "RAM",
                    "HDD",
                    "Sistema Operativo",
                    "Arquitectura",
                    "Version",
                    "Tipo Reparacion",
                    "Descripcion",
                    "Valor",
                    "Fecha"
                };

                Object[][] data = new Object[][] {
                    login
                };
                Object[][] dato2 = new Object[][] {
                    cliente
                };
                Object[][] dato3 = new Object[][] {
                    computador
                };

                CellStyle headerStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
//                font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                headerStyle.setFont(font);
        //
                CellStyle style = workbook.createCellStyle();
                style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        //        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

                Row fila = sheet.createRow(0);
                //Creamos una celda
                Cell celda = fila.createCell(0);
                //Añadimos el texto
                celda.setCellValue("Tecnico");
                
                HSSFRow headerRow = sheet.createRow(1);
                for (int i = 0; i < headers.length; ++i) {
                    String header = headers[i];
                    HSSFCell cell = headerRow.createCell(i);
                    cell.setCellStyle(headerStyle);
                    cell.setCellValue(header);
                }

                for (int i = 0; i < data.length; ++i) {
                    HSSFRow dataRow = sheet.createRow(i + 2);

                    Object[] d = data[i];
                    String id = (String) d[0];
                    String rut = (String) d[1];
                    String nombr = (String) d[2];
                    String apellido = (String) d[3];
                    String correo = (String) d[4];
                    String celular = (String) d[5];
                    String passworld = (String) d[6];
                    String tipo = (String) d[7];

                    dataRow.createCell(0).setCellValue(Integer.parseInt(id));
                    dataRow.createCell(1).setCellValue(rut);
                    dataRow.createCell(2).setCellValue(nombr);
                    dataRow.createCell(3).setCellValue(apellido);
                    dataRow.createCell(4).setCellValue(correo);
                    dataRow.createCell(5).setCellValue(Integer.parseInt(celular));
                    dataRow.createCell(6).setCellValue(passworld);
                    dataRow.createCell(7).setCellValue(tipo);
                }
                
                Row fila2 = sheet.createRow(3);
                //Creamos una celda
                Cell celda2 = fila2.createCell(0);
                //Añadimos el texto
                celda2.setCellValue("Cliente");
                
                HSSFRow headerRow2 = sheet.createRow(4);
                for (int i = 0; i < headers2.length; ++i) {
                    String header = headers2[i];
                    HSSFCell cell = headerRow2.createCell(i);
                    cell.setCellStyle(headerStyle);
                    cell.setCellValue(header);
                }
                for (int i = 0; i < dato2.length; ++i) {
                    HSSFRow dataRow = sheet.createRow(i + 5);

                    Object[] d = dato2[i];
                    String id = (String) d[0];
                    String rut = (String) d[1];
                    String nombr = (String) d[2];
                    String apellido = (String) d[3];
                    String correo = (String) d[4];
                    String celular = (String) d[5];
                    String idLogin = (String) d[6];

                    dataRow.createCell(0).setCellValue(Integer.parseInt(id));
                    dataRow.createCell(1).setCellValue(rut);
                    dataRow.createCell(2).setCellValue(nombr);
                    dataRow.createCell(3).setCellValue(apellido);
                    dataRow.createCell(4).setCellValue(correo);
                    dataRow.createCell(5).setCellValue(Integer.parseInt(celular));
                    dataRow.createCell(6).setCellValue(Integer.parseInt(idLogin));
                }
                
                Row fila3 = sheet.createRow(6);
                //Creamos una celda
                Cell celda3 = fila3.createCell(0);
                //Añadimos el texto
                celda3.setCellValue("Computador");
                
                HSSFRow headerRow3 = sheet.createRow(7);
                for (int i = 0; i < headers3.length; ++i) {
                    String header = headers3[i];
                    HSSFCell cell = headerRow3.createCell(i);
                    cell.setCellStyle(headerStyle);
                    cell.setCellValue(header);
                }
                for (int i = 0; i < dato3.length; ++i) {
                    HSSFRow dataRow = sheet.createRow(i + 8);

                    Object[] d = dato3[i];
                    String id = (String) d[0];
                    String marca = (String) d[1];
                    String modelo = (String) d[2];
                    String numSerie = (String) d[3];
                    String ram = (String) d[4];
                    String hdd = (String) d[5];
                    String sistemaOpe = (String) d[6];
                    String arquitectura = (String) d[7];
                    String version = (String) d[8];
                    String tipoReparacion = (String) d[9];
                    String descripcion = (String) d[10];
                    String valor = (String) d[11];
                    String fecha = (String) d[12];

                    dataRow.createCell(0).setCellValue(Integer.parseInt(id));
                    dataRow.createCell(1).setCellValue(marca);
                    dataRow.createCell(2).setCellValue(modelo);
                    dataRow.createCell(3).setCellValue(numSerie);
                    dataRow.createCell(4).setCellValue(ram);
                    dataRow.createCell(5).setCellValue(hdd);
                    dataRow.createCell(6).setCellValue(sistemaOpe);
                    dataRow.createCell(7).setCellValue(arquitectura);
                    dataRow.createCell(8).setCellValue(version);
                    dataRow.createCell(9).setCellValue(tipoReparacion);
                    dataRow.createCell(10).setCellValue(descripcion);
                    dataRow.createCell(11).setCellValue(Integer.parseInt(valor));
                    dataRow.createCell(12).setCellValue(fecha);
                }

//                HSSFRow dataRow = sheet.createRow(1 + data.length);
//                HSSFCell total = dataRow.createCell(1);
//                total.setCellType(Cell.CELL_TYPE_FORMULA);
//                total.setCellStyle(style);
//                total.setCellFormula(String.format("SUM(B2:B%d)", 1 + data.length));

                workbook.write(output);
                output.close();

//                libro.write(output);
//                output.close();
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
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
                    Logger.getLogger(ImprimirExcel.class.getName()).log(Level.SEVERE, null, ex);
                    log.info(ex.getMessage());
                    Log.log(ex.getMessage());
                }
            }
        }
    }

    
}
