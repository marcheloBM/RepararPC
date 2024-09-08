/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.GUI;

import Cl.Burgos.RepararPC.DAO.DAOArchivo;
import Cl.Burgos.RepararPC.ENT.ClArchivo;
import Cl.Burgos.RepararPC.ENT.ClComputador;
import Cl.Burgos.RepararPC.FUN.Render;
import Cl.Burgos.RepararPC.GUI.Ayuda.FrAIDA64Extreme;
import Cl.Burgos.RepararPC.GUI.Ayuda.FrAyuda;
import Cl.Burgos.RepararPC.GUI.Ayuda.FrCINEBENCHR15;
import Cl.Burgos.RepararPC.GUI.Ayuda.FrCrystalDiskInfo;
import Cl.Burgos.RepararPC.GUI.Ayuda.FrHWMonitorPro;
import Cl.Burgos.RepararPC.GUI.Ayuda.FrPerformanceTest;
import Cl.Burgos.RepararPC.GUI.Ayuda.FrScreenShot;
import Cl.Burgos.RepararPC.GUI.Ayuda.FrSpeccy;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author march
 */
public class FrArchivo extends javax.swing.JFrame {

    DAOArchivo dAOArchivo = new DAOArchivo();
    String rutaArchivo;
    int idAr;
    int idPc = FrHome.idPc;
    /**
     * Creates new form FrArchivo
     */
    public FrArchivo() {
        initComponents();
        LimpiarArchi();
        
        jPanel1.setOpaque(false);
        jPanel2.setOpaque(false);
        jPanel3.setOpaque(false);
        
        setLocationRelativeTo(null); 
        setResizable(false); 
        setTitle("Archivos");
        String url="/Cl/Burgos/RepararPC/IMG/";
        setIconImage(new ImageIcon(getClass().getResource(url+"Icono.png")).getImage());
        ((JPanel)getContentPane()).setOpaque(false);
        ImageIcon MyImgCustom =new ImageIcon(this.getClass().getResource(url+"fondo1.jpg"));
        JLabel fondo= new JLabel();
        
        fondo.setIcon(MyImgCustom);
        getLayeredPane().add(fondo,JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0,0,MyImgCustom.getIconWidth(),MyImgCustom.getIconHeight());
    }

    public void seleccionar_archi() {
        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter fi = new FileNameExtensionFilter("pdf & zip & htm & rar & speccy & png & txt", "pdf","zip","htm","rar","speccy","png","txt");
        j.setFileFilter(fi);
        int se = j.showOpenDialog(this);
        if (se == 0) {
            txtnombreAr.setText("" + j.getSelectedFile().getName());
            rutaArchivo=j.getSelectedFile().getAbsolutePath();
            btnAgregarAr.setEnabled(true);
        } else {
        }
    }
    
    public ClArchivo insert(){
        File ruta = new File(rutaArchivo);
        ClArchivo clArchivo= null;
        try {
            byte[] pdf = new byte[(int) ruta.length()];
            InputStream input = new FileInputStream(ruta);
            input.read(pdf);
            clArchivo = new ClArchivo(txtnombreAr.getText().trim(), pdf,idPc);
        } catch (IOException ex) {
            clArchivo = new ClArchivo(txtnombreAr.getText().trim(), null,idPc);
            //System.out.println("Error al agregar archivo "+ex.getMessage());
        }
        
        return clArchivo;
    }
    public ClArchivo update(){
        ClArchivo clArchivo= null;
        try {
            File ruta = new File(rutaArchivo);
            byte[] pdf = new byte[(int) ruta.length()];
            InputStream input = new FileInputStream(ruta);
            input.read(pdf);
            clArchivo = new ClArchivo(idAr,txtnombreAr.getText().trim(), pdf,idPc);
        } catch (IOException ex) {
            clArchivo = new ClArchivo(idAr,txtnombreAr.getText().trim(), null,idPc);
            //System.out.println("Error al agregar archivo "+ex.getMessage());
        }
        
        return clArchivo;
    }
    public ClArchivo delete(){
        ClArchivo clArchivo = new ClArchivo(idAr);
        return clArchivo;
    }
    
    public void defineTablaArchivo(){
        long lngRegistros=1;
        long lngDesdeRegistro;
        
        //DEFINIMOS LA TABLA MODELO
        DefaultTableModel tablaClientes = new DefaultTableModel();
//        DefaultTableModel tablaClientes = new DefaultTableModel(){
//            @Override
//            public boolean isCellEditable(int row, int column){
//                return false;
//            }
//        };
        jTableArchivo.setDefaultRenderer(Object.class, new Render());
        
        //LE AGREGAMOS EL TITULO DE LAS COLUMNAS DE LA TABLA EN UN ARREGLO
        String strTitulos[]={"ID","Nombre","Archivo"};
        
        //LE ASIGNAMOS LAS COLUMNAS AL MODELO CON LA CADENA DE ARRIBA
        tablaClientes.setColumnIdentifiers(strTitulos);
        
        //LE ASIGNAMOS EL MODELO DE ARRIBA AL JTABLE 
        this.jTableArchivo.setModel(tablaClientes);
        
                    //AHORA A LEER LOS DATOS
        
        //ASIGNAMOS CUANTOS REGISTROS POR HOJA TRAEREMOS
//        lngRegistros=(Long.valueOf(this.txtNumReg.getText()));
        
        //ASIGNAMOS DESDE QUE REGISTRO TRAERA LA CONSULTA SQL
//        lngDesdeRegistro=(DesdeHoja*lngRegistros)-lngRegistros;
        
        //INSTANCEAMOS LA CLASE CLIENTE
//        DAOPc classCliente= new DAOPc();
        
        //LEEMOS LA CLASE CLIENTE MANDANDOLE LOS PARAMETROS
        //dAOClaves.leerClientesId(lngDesdeRegistro, (Long.valueOf(this.txtNumReg.getText())),tablaClientes,strBusqueda,idCliente);
        List<ClArchivo> lista=dAOArchivo.listarArchivo(idPc);
        Object fila[] = new Object[4];
//        String datos[]=new String [3];
        for (int i = 0; i < lista.size(); i++) {
            fila[0]=Integer.toString(lista.get(i).getId());
            fila[1]=lista.get(i).getNombre();
            fila[2] = new JButton(selec(lista.get(i).getNombre()));
            tablaClientes.addRow(fila);
        }
        
        //LE PONEMOS EL RESULTADO DE LA CONSULA AL JTABLE
        this.jTableArchivo.setModel(tablaClientes);
        this.jTableArchivo.setRowHeight(60);
        
        //ASIGNAMOS LOS VALORES A LA PAGINACION
//        lngRegistros = dAOClaves.leerCuantos("");
//        lngNumPaginas= (lngRegistros/ (Long.valueOf( this.txtNumReg.getText())))+1;
//        this.jlblTotalPaginas.setText(" De " + ( lngNumPaginas));
        
    }
    public ImageIcon selec(String ext){
        String disco = ext;
        int pos = disco.indexOf(".");
        String dato =disco.substring(pos+1);
//        System.out.println(dato+" "+pos);
        String urlImagen="";
        File carpeta = new File("./IMG/");
        String[] listado = carpeta.list();
        if (listado == null || listado.length == 0) { 
            System.out.println("No hay elementos dentro de la carpeta actual"); 
        }else { 
            for (int i=0; i< listado.length; i++) { 
                if(listado[i].equals(dato+".png")){
                    urlImagen="./IMG/"+listado[i];
                }
            }
        }
        if(urlImagen==""){
            urlImagen="./IMG/desco.png";
        }
            ImageIcon icon = new ImageIcon(urlImagen);
            ImageIcon imgi = new ImageIcon(icon.getImage().getScaledInstance(60,60,Image.SCALE_DEFAULT)); 
            return imgi;
    }
    
    public void LimpiarArchi(){
        defineTablaArchivo();
        txtnombreAr.setForeground(Color.WHITE);
        txtnombreAr.setEditable(false);
        btnAgregarAr.setEnabled(false);
        btnEliminarAr.setEnabled(false);
        btnActualizarAr.setVisible(false);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtnombreAr = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnAgregarAr = new javax.swing.JButton();
        btnEliminarAr = new javax.swing.JButton();
        btnLimiarAr = new javax.swing.JButton();
        btnActualizarAr = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableArchivo = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        ScreenShot = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        AIDA64Extreme = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        CINEBENCHR15 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        HWMonitorPro = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        PerformanceTest = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        Speccy = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Nombre Archivo:");

        jButton1.setText("Buscar Archivo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnombreAr)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(65, 65, 65))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1)
                    .addComponent(txtnombreAr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAgregarAr.setText("Agregar");
        btnAgregarAr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarArActionPerformed(evt);
            }
        });

        btnEliminarAr.setText("Eliminar");
        btnEliminarAr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarArActionPerformed(evt);
            }
        });

        btnLimiarAr.setText("Limpiar");
        btnLimiarAr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimiarArActionPerformed(evt);
            }
        });

        btnActualizarAr.setText("Actualizar");
        btnActualizarAr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarArActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(btnAgregarAr)
                .addGap(18, 18, 18)
                .addComponent(btnEliminarAr)
                .addGap(18, 18, 18)
                .addComponent(btnLimiarAr)
                .addGap(18, 18, 18)
                .addComponent(btnActualizarAr)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarAr)
                    .addComponent(btnEliminarAr)
                    .addComponent(btnLimiarAr)
                    .addComponent(btnActualizarAr))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista Archivos"));

        jTableArchivo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableArchivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableArchivoMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTableArchivo);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenu1.setText("Reportes");

        ScreenShot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cl/Burgos/RepararPC/IMG/ICO/ScreenShot25x25.png"))); // NOI18N
        ScreenShot.setText("Capturador de Pantalla");

        jMenuItem2.setText("Capturador");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        ScreenShot.add(jMenuItem2);

        jMenuItem1.setText("Informacion");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        ScreenShot.add(jMenuItem1);

        jMenu1.add(ScreenShot);

        AIDA64Extreme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cl/Burgos/RepararPC/IMG/ICO/AIDA64Extreme25x25.png"))); // NOI18N
        AIDA64Extreme.setText("AIDA64 Extreme");

        jMenuItem3.setText("AIDA64 Extreme");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        AIDA64Extreme.add(jMenuItem3);

        jMenuItem4.setText("Key Activacion");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        AIDA64Extreme.add(jMenuItem4);

        jMenuItem5.setText("Informacion");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        AIDA64Extreme.add(jMenuItem5);

        jMenu1.add(AIDA64Extreme);

        CINEBENCHR15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cl/Burgos/RepararPC/IMG/ICO/CINEBENCHR1525x25.png"))); // NOI18N
        CINEBENCHR15.setText("CINEBENCH R15");

        jMenuItem6.setText("CINEBENCH R15");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        CINEBENCHR15.add(jMenuItem6);

        jMenuItem7.setText("Informacion");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        CINEBENCHR15.add(jMenuItem7);

        jMenu1.add(CINEBENCHR15);

        HWMonitorPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cl/Burgos/RepararPC/IMG/ICO/HWMonitorPro25x25.png"))); // NOI18N
        HWMonitorPro.setText("HWMonitorPro");

        jMenuItem8.setText("HWMonitorPro");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        HWMonitorPro.add(jMenuItem8);

        jMenuItem9.setText("Key");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        HWMonitorPro.add(jMenuItem9);

        jMenuItem10.setText("Informacion");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        HWMonitorPro.add(jMenuItem10);

        jMenu1.add(HWMonitorPro);

        PerformanceTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cl/Burgos/RepararPC/IMG/ICO/PerformanceTest25x25.png"))); // NOI18N
        PerformanceTest.setText("PerformanceTest");

        jMenuItem11.setText("PerformanceTest");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        PerformanceTest.add(jMenuItem11);

        jMenuItem12.setText("Informacion");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        PerformanceTest.add(jMenuItem12);

        jMenu1.add(PerformanceTest);

        Speccy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cl/Burgos/RepararPC/IMG/ICO/Speccy25x25.png"))); // NOI18N
        Speccy.setText("Speccy");

        jMenuItem13.setText("Speccy");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        Speccy.add(jMenuItem13);

        jMenuItem14.setText("Informacion");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        Speccy.add(jMenuItem14);

        jMenu1.add(Speccy);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cl/Burgos/RepararPC/IMG/ICO/crystaldiskinfo25x25.png"))); // NOI18N
        jMenu3.setText("CrystalDiskInfo 8.4.0");

        jMenuItem16.setText("CrystalDiskInfo 8.4.0");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem16);

        jMenuItem17.setText("Informacion");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem17);

        jMenu1.add(jMenu3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        jMenuItem15.setText("Acerca de ...");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem15);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        seleccionar_archi();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAgregarArActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarArActionPerformed
        // TODO add your handling code here:
        if(!dAOArchivo.sqlInsert(insert())){
            JOptionPane.showMessageDialog(null, "Archivo No Agregado");
        }else{
            JOptionPane.showMessageDialog(null, "Archivo Agregado");
            LimpiarArchi();
        }
    }//GEN-LAST:event_btnAgregarArActionPerformed

    private void btnEliminarArActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarArActionPerformed
        // TODO add your handling code here:
        if(!dAOArchivo.sqlDelete(delete())){
            JOptionPane.showMessageDialog(null, "Archivo No Eliminado");
        }else{
            JOptionPane.showMessageDialog(null, "Archivo Eliminado");
            LimpiarArchi();
        }
    }//GEN-LAST:event_btnEliminarArActionPerformed

    private void btnLimiarArActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimiarArActionPerformed
        // TODO add your handling code here:
        LimpiarArchi();
    }//GEN-LAST:event_btnLimiarArActionPerformed

    private void btnActualizarArActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarArActionPerformed
        // TODO add your handling code here:
        if(!dAOArchivo.sqlUpdate(update())){
            JOptionPane.showMessageDialog(null, "Archivo No Actualizado");
        }else{
            JOptionPane.showMessageDialog(null, "Archivo Actualizado");
            LimpiarArchi();
        }
    }//GEN-LAST:event_btnActualizarArActionPerformed

    private void jTableArchivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableArchivoMouseClicked
        // TODO add your handling code here:
        int fila;
        fila = this.jTableArchivo.rowAtPoint(evt.getPoint());
        if (fila > -1){
            try {
                idAr=Integer.parseInt(String.valueOf(jTableArchivo.getValueAt(fila, 0)));

                List<ClArchivo> datosCliente=dAOArchivo.ejecutar_archivoPDF(idAr);
                for (int i = 0; i < datosCliente.size(); i++) {
                    idAr=Integer.parseInt(String.valueOf(datosCliente.get(i).getId()));
                    txtnombreAr.setText(datosCliente.get(i).getNombre());

                    InputStream bos = new ByteArrayInputStream(datosCliente.get(i).getArchivo());

                    int tamanoInput = bos.available();
                    byte[] datosPDF = new byte[tamanoInput];
                    bos.read(datosPDF, 0, tamanoInput);

                    OutputStream out = new FileOutputStream(datosCliente.get(i).getNombre());
                    out.write(datosPDF);
                    //abrir archivo
                    out.close();
                    bos.close();
                    try {
                        Desktop.getDesktop().open(new File(datosCliente.get(i).getNombre()));
                    } catch (Exception ex) {

                    }
                    btnActualizarAr.setEnabled(true);
                    btnEliminarAr.setEnabled(true);
                }
            } catch (Exception ex) {
                //                Logger.getLogger(FrManteLogin.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Sin Archivo");
                //                Log.log(ex.getMessage());
                //                log.fatal(ex.getMessage());
            }
        }
    }//GEN-LAST:event_jTableArchivoMouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        new FrScreenShot(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Programa Eliminado");
//        String ruta;
//        ruta="./PRO/Monitor 11/ScreenShot.exe";
//        try {
//            Desktop.getDesktop().open(new File(ruta));
//        } catch (Exception ex) {
//        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Programa Eliminado");
//        String ruta;
//        ruta="./PRO/AIDA64 Extreme/aida64.exe";
//        try {
//            Desktop.getDesktop().open(new File(ruta));
//        } catch (Exception ex) {
//        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Programa Eliminado");
//        String ruta;
//        ruta="./PRO/AIDA64 Extreme/key";
//        try {
//            Desktop.getDesktop().open(new File(ruta));
//        } catch (Exception ex) {
//        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        new FrAIDA64Extreme(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Programa Eliminado");
//        String ruta;
//        ruta="./PRO/CINEBENCH R15/CINEBENCH Windows 64 Bit.exe";
//        try {
//            Desktop.getDesktop().open(new File(ruta));
//        } catch (Exception ex) {
//        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        new FrCINEBENCHR15(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Programa Eliminado");
//        String ruta;
//        ruta="./PRO/HWMonitorPro/HWMonitorPro.exe";
//        try {
//            Desktop.getDesktop().open(new File(ruta));
//        } catch (Exception ex) {
//        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Programa Eliminado");
//        String ruta;
//        ruta="./PRO/HWMonitorPro/key";
//        try {
//            Desktop.getDesktop().open(new File(ruta));
//        } catch (Exception ex) {
//        }
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        new FrHWMonitorPro(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Programa Eliminado");
//        String ruta;
//        ruta="./PRO/PerformanceTest/PerformanceTest64.exe";
//        try {
//            Desktop.getDesktop().open(new File(ruta));
//        } catch (Exception ex) {
//        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        new FrPerformanceTest(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Programa Eliminado");
//        String ruta;
//        ruta="./PRO/Speccy/Speccy64.exe";
//        try {
//            Desktop.getDesktop().open(new File(ruta));
//        } catch (Exception ex) {
//        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        new FrSpeccy(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        new FrAyuda(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Programa Eliminado");
//        String ruta;
//        ruta="./PRO/CrystalDiskInfo 8.4.0/DiskInfo64.exe";
//        try {
//            Desktop.getDesktop().open(new File(ruta));
//        } catch (Exception ex) {
//        }
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        new FrCrystalDiskInfo(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrArchivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrArchivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrArchivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrArchivo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrArchivo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu AIDA64Extreme;
    private javax.swing.JMenu CINEBENCHR15;
    private javax.swing.JMenu HWMonitorPro;
    private javax.swing.JMenu PerformanceTest;
    private javax.swing.JMenu ScreenShot;
    private javax.swing.JMenu Speccy;
    private javax.swing.JButton btnActualizarAr;
    private javax.swing.JButton btnAgregarAr;
    private javax.swing.JButton btnEliminarAr;
    private javax.swing.JButton btnLimiarAr;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable jTableArchivo;
    private javax.swing.JTextField txtnombreAr;
    // End of variables declaration//GEN-END:variables
}
