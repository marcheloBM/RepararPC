/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.FUN;

import java.awt.print.PrinterException;
import java.text.MessageFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;


/**
 *
 * @author march
 */
public class ImprimirImpresora {
    
    //Variables del Log4j
    static  org.apache.log4j.Logger log =org.apache.log4j.Logger.getLogger(ImprimirImpresora.class);
    
    //Imprimir las tabla
    public void ImprimirImpresora(JTable jTable, String header, String footer, boolean showPrintDialog){        
    boolean fitWidth = true;        
    boolean interactive = true;
    // We define the print mode (Definimos el modo de impresión)
    JTable.PrintMode mode = fitWidth ? JTable.PrintMode.FIT_WIDTH : JTable.PrintMode.NORMAL;
    try {
        // Print the table (Imprimo la <span id="IL_AD5" class="IL_AD">tabla</span>)             
        boolean complete = jTable.print(mode,
                new MessageFormat(header),
                new MessageFormat(footer),
                showPrintDialog,
                null,
                interactive);                 
        if (complete) {
            // Mostramos el mensaje de impresión existosa
            JOptionPane.showMessageDialog(jTable,
                    "Print complete (Impresión completa)",
                    "Print result (Resultado de la impresión)",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Mostramos un mensaje indicando que la impresión fue cancelada                 
            JOptionPane.showMessageDialog(jTable,
                    "Print canceled (Impresión cancelada)",
                    "Print result (Resultado de la impresión)",
                    JOptionPane.WARNING_MESSAGE);
        }
    } catch (PrinterException pe) {
        JOptionPane.showMessageDialog(jTable, 
                "Print fail (Fallo de impresión): " + pe.getMessage(), 
                "Print result (Resultado de la impresión)", 
                JOptionPane.ERROR_MESSAGE);
        log.info(pe.getMessage());
        Log.log(pe.getMessage());
    }
}
		
}
