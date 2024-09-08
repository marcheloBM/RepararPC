/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.GUI.Mant;


import Cl.Burgos.RepararPC.Log4j.Log;
import Cl.Burgos.RepararPC.DAO.DAOLogin;
import Cl.Burgos.RepararPC.ENT.ClLogin;
import Cl.Burgos.RepararPC.Enum.TipoLogin;
import Cl.Burgos.RepararPC.FUN.ImprimirExcel;
import Cl.Burgos.RepararPC.FUN.ImprimirImpresora;
import Cl.Burgos.RepararPC.FUN.ImprimirPDF;
import Cl.Burgos.RepararPC.FUN.ImprimirWord;
import Cl.Burgos.RepararPC.GUI.*;
import Cl.Burgos.RepararPC.WSDL.Exception_Exception;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;

/**
 *
 * @author march
 */
public class FrManteLogin extends javax.swing.JInternalFrame {

    //Variables del Log4j
    static  Logger log =Logger.getLogger(FrManteLogin.class);
    
//    String nombre1=FrLogin.clLogin.getNombre();
//    String apellido1=frLogin.clLogin.getApellido();
//    String nombreLogin = nombre1+" "+apellido1;
    long lngNumPaginas;
    DAOLogin dAOLogin =new DAOLogin();
    /**
     * Creates new form FrManteLogin1
     */
    public FrManteLogin() {
        initComponents();
        this.txtNumReg.setText("10");
        defineTablaClientes("",1);
        Bloquear();
        Limpiar();
        //Cargar el comboBox
//        jcbTipoCliente.setModel(new DefaultComboBoxModel(TipoLogin.values()));
        
        //Los paneles sean tranparentes
        jPanel1.setOpaque(false);
        jPanel2.setOpaque(false);
        jPanel3.setOpaque(false);
        jPanel4.setOpaque(false);
        
        setResizable(false);
        setTitle("Mantendor Login");
        String url="/Cl/Burgos/RepararPC/IMG/Mant/";
        
        ((JPanel)getContentPane()).setOpaque(false);
        ImageIcon MyImgCustom =new ImageIcon(this.getClass().getResource(url+"fondo1.jpg"));
        JLabel fondo= new JLabel();
        
        fondo.setIcon(MyImgCustom);
        getLayeredPane().add(fondo,JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0,0,MyImgCustom.getIconWidth(),MyImgCustom.getIconHeight());
    }
    public ClLogin allClLogin() throws Exception{
        ClLogin clLogin = new ClLogin(Integer.parseInt(txtIdLogin.getText()), 
                txtRut.getText(), txtNombre.getText(), 
                txtApellido.getText(), txtCorreo.getText(), txtCelular.getText(), 
                txtPass.getText(), TipoLogin.valueOf(jcbTipoCliente.getSelectedItem().toString()));
        return clLogin;
    }
    public ClLogin idClLogin() throws Exception{
        ClLogin clLogin= new ClLogin(Integer.parseInt(txtIdLogin.getText()));
        return clLogin;
    }
    public ClLogin datosClLogin() throws Exception{
        ClLogin clLogin=new ClLogin(txtRut.getText(), txtNombre.getText(), 
                txtApellido.getText(), txtCorreo.getText(), txtCelular.getText(), 
                txtPass.getText(), TipoLogin.valueOf(jcbTipoCliente.getSelectedItem().toString()));
        return clLogin;
    }
    public void Bloquear(){
        txtIdLogin.setEditable(false);
        btnEliminar.setEnabled(false);
        btnActualizar.setEnabled(false);
        //btnImprimirReporte.setEnabled(false);
    }
    public void Limpiar(){
        txtIdLogin.setText("");
        txtRut.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtCelular.setText("");
        txtPass.setText("");
        jcbTipoCliente.setSelectedItem(TipoLogin.Administrador);
        defineTablaClientes("",1);
        //Bloqueo de Jtable
        JTabLogin.setEnabled(false);
    }
    public void defineTablaClientes(String strBusqueda,long DesdeHoja){
        
        long lngRegistros=1;
        long lngDesdeRegistro;
        
        //DEFINIMOS LA TABLA MODELO
        DefaultTableModel tablaClientes = new DefaultTableModel();
        
        //LE AGREGAMOS EL TITULO DE LAS COLUMNAS DE LA TABLA EN UN ARREGLO
        String strTitulos[]={"ID","RUT","NOMBRE","APELLIDO","CORREO","CELULAR","PASSWORLD","TIPO"};
        
        //LE ASIGNAMOS LAS COLUMNAS AL MODELO CON LA CADENA DE ARRIBA
        tablaClientes.setColumnIdentifiers(strTitulos);
        
        //LE ASIGNAMOS EL MODELO DE ARRIBA AL JTABLE 
        this.JTabLogin.setModel(tablaClientes);
        
                    //AHORA A LEER LOS DATOS
        
        //ASIGNAMOS CUANTOS REGISTROS POR HOJA TRAEREMOS
        lngRegistros=(Long.valueOf(this.txtNumReg.getText()));
        
        //ASIGNAMOS DESDE QUE REGISTRO TRAERA LA CONSULTA SQL
        lngDesdeRegistro=(DesdeHoja*lngRegistros)-lngRegistros;
        
        //INSTANCEAMOS LA CLASE CLIENTE
//        DAOLogin dAOLogin= new DAOLogin();
        
        //LEEMOS LA CLASE CLIENTE MANDANDOLE LOS PARAMETROS
//        dAOLogin.leerLoginTab(lngDesdeRegistro, (Long.valueOf(this.txtNumReg.getText())),tablaClientes,strBusqueda);
//        List<ClLogin> lista=dAOLogin.leerLogin(lngDesdeRegistro, (Long.valueOf(this.txtNumReg.getText())),strBusqueda);
        List<Cl.Burgos.RepararPC.WSDL.ClLogin> lista=listaLoginAll(lngDesdeRegistro, (Long.valueOf(this.txtNumReg.getText())),strBusqueda);
        String datos[]=new String [8];
        for (int i = 0; i < lista.size(); i++) {
            datos[0]=Integer.toString(lista.get(i).getId());
            datos[1]=lista.get(i).getRut();
            datos[2]=lista.get(i).getNombre();
            datos[3]=lista.get(i).getApellido();
            datos[4]=lista.get(i).getCorreo();
            datos[5]=lista.get(i).getCelular();
            datos[6]=lista.get(i).getPassworld();
            datos[7]=lista.get(i).getTipoLogin().toString();
            tablaClientes.addRow(datos);
        }
        
        //LE PONEMOS EL RESULTADO DE LA CONSULA AL JTABLE
        this.JTabLogin.setModel(tablaClientes);
        
        //ASIGNAMOS LOS VALORES A LA PAGINACION
        lngRegistros = dAOLogin.leerCuantos("");
        lngNumPaginas= (lngRegistros/ (Long.valueOf( this.txtNumReg.getText())))+1;
        this.jlblTotalPaginas.setText(" De " + ( lngNumPaginas));
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jlblNumReg = new javax.swing.JLabel();
        txtNumReg = new javax.swing.JTextField();
        jlblNumReg2 = new javax.swing.JLabel();
        txtPagina = new javax.swing.JTextField();
        jlblTotalPaginas = new javax.swing.JLabel();
        cmdAtras = new javax.swing.JButton();
        cmdSiguiente1 = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        lblBuscar = new javax.swing.JLabel();
        cmdBuscar = new javax.swing.JButton();
        btnImprimirReporte = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTabLogin = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnActualizar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        salirclijButton3 = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtPass = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtIdLogin = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        txtRut = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtCelular = new javax.swing.JTextField();
        jcbTipoCliente = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(51, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mantenedor Login", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jlblNumReg.setText("Registros");

        txtNumReg.setText("5");

        jlblNumReg2.setText("Pagina ");

        txtPagina.setText("1");

        jlblTotalPaginas.setText("Pagina ");

        cmdAtras.setText("<<");
        cmdAtras.setActionCommand("");
        cmdAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAtrasActionPerformed(evt);
            }
        });

        cmdSiguiente1.setText(">>");
        cmdSiguiente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSiguiente1ActionPerformed(evt);
            }
        });

        lblBuscar.setText("Buscar");

        cmdBuscar.setText("Buscar All");
        cmdBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBuscarActionPerformed(evt);
            }
        });

        btnImprimirReporte.setText("Imprimir ");
        btnImprimirReporte.setActionCommand("");
        btnImprimirReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirReporteActionPerformed(evt);
            }
        });

        JTabLogin.setModel(new javax.swing.table.DefaultTableModel(
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
        JTabLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTabLoginMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTabLogin);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnImprimirReporte)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jlblNumReg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumReg, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdAtras)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlblNumReg2)
                        .addGap(2, 2, 2)
                        .addComponent(txtPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlblTotalPaginas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdSiguiente1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblBuscar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmdBuscar)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblNumReg)
                    .addComponent(txtNumReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblNumReg2)
                    .addComponent(txtPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblTotalPaginas)
                    .addComponent(cmdAtras)
                    .addComponent(cmdSiguiente1)
                    .addComponent(lblBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImprimirReporte)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        salirclijButton3.setText("Salir");
        salirclijButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirclijButton3ActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnAgregar)
                    .addComponent(btnActualizar)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salirclijButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salirclijButton3)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jLabel3.setText("Pass:");

        jLabel12.setText("Nombre:");

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        jLabel11.setText("ID:");

        jLabel4.setText("Apellido:");

        jLabel6.setText("Rut:");

        jLabel7.setText("Correo:");

        jLabel8.setText("Celular:");

        jcbTipoCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Usuario" }));

        jLabel1.setText("Tipo:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre)
                    .addComponent(txtApellido)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtIdLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtRut))
                .addGap(57, 57, 57)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbTipoCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPass)
                    .addComponent(txtCorreo)
                    .addComponent(txtCelular))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtIdLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jcbTipoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAtrasActionPerformed
        long lngValor=0;
        if(1<Long.valueOf( this.txtPagina.getText())){
            lngValor=Long.valueOf( this.txtPagina.getText())-1;
            this.txtPagina.setText(String.valueOf(lngValor));
            defineTablaClientes("",lngValor);
        }
    }//GEN-LAST:event_cmdAtrasActionPerformed

    private void cmdSiguiente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSiguiente1ActionPerformed
        long lngValor=0;
        if(lngNumPaginas>Long.valueOf( this.txtPagina.getText())){
            lngValor=Long.valueOf( this.txtPagina.getText())+1;
            this.txtPagina.setText(String.valueOf(lngValor));
            defineTablaClientes("",lngValor);
        }
    }//GEN-LAST:event_cmdSiguiente1ActionPerformed

    private void cmdBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBuscarActionPerformed
        defineTablaClientes(this.txtBuscar.getText(),Long.valueOf(this.txtPagina.getText()));
    }//GEN-LAST:event_cmdBuscarActionPerformed

    private void btnImprimirReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirReporteActionPerformed
        // TODO add your handling code here:
        int opcion=0;
        do {
            try {
                opcion=Integer.parseInt(JOptionPane.showInputDialog(null,
                    "1 Imprimir en Archivo en Excel\n"
                    + "2 Imprimir en Archivo en PDF\n"
                    + "3 Imprimir en Archivo en Word\n"
                    + "4 Imprimir en Archivo en Impresora\n"
                    + "0 Salir"));

            switch(opcion){
                case 1:
                imprimirExel();
                break;
                case 2:
                imprimirPDF();
                break;
                case 3:
                imprimirWord();
                break;
                case 4:
                imprimirImpresora();
                break;
                case 0:
                JOptionPane.showMessageDialog(null,"Programa finalizado");
                break;
                default:
                JOptionPane.showMessageDialog(null, "Opcion incorreta");
            }

        } catch (Exception n) {
            JOptionPane.showMessageDialog(null, "Error"+n.getMessage());
            Log.log(n.getMessage());
            log.fatal(n.getMessage());
        }
        } while (opcion!=0);
    }//GEN-LAST:event_btnImprimirReporteActionPerformed


    public void imprimirExel(){
        if (this.JTabLogin.getRowCount()==0) {
            JOptionPane.showMessageDialog (null, "No hay datos en la tabla para exportar.","BCO",
                JOptionPane.INFORMATION_MESSAGE);
//            this.cmbConsorcio.grabFocus();
            return;
        }
        List<JTable> tb=new ArrayList<>();
        List<String> nom=new ArrayList<>();
        String nombreArc = "Login";
        tb.add(JTabLogin);
        nom.add("Detalles");
        try {
            ImprimirExcel e=new ImprimirExcel();
            e.ImprimirExcel(tb, nom,nombreArc);
            if (e.export()) {
                JOptionPane.showMessageDialog(null, "Los datos fueron exportados a excel.","BCO",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Hubo un error"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            Log.log(ex.getMessage());
            log.fatal(ex.getMessage());
        }
        
    }
    public void imprimirPDF(){
        ImprimirPDF imprimirPDF = new ImprimirPDF();
        imprimirPDF.ImprimirPDF(JTabLogin,"Login");
    }
    public void imprimirWord(){
        ImprimirWord word = new ImprimirWord();
        word.ImprimirWord(JTabLogin,"Login");
    }
    public void imprimirImpresora(){
        ImprimirImpresora impresora = new ImprimirImpresora();
        impresora.ImprimirImpresora(JTabLogin, "nombreLogin", "NombreLogin", rootPaneCheckingEnabled);
    }
    
    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
                try {
                    boolean resp = false;
                    int id = Integer.parseInt(txtIdLogin.getText());
                    String rut = txtRut.getText();
                    String nombre = txtNombre.getText();
                    String apellido = txtApellido.getText();
                    String correo = txtCorreo.getText();
                    String celuilar = txtCelular.getText();
                    String pass = txtPass.getText();
                    TipoLogin tipoLogin = TipoLogin.valueOf(jcbTipoCliente.getSelectedItem().toString());
                    if(tipoLogin.equals(TipoLogin.Usuario)){
                        resp = actualizarLogin(id,rut,nombre,apellido,correo,celuilar,pass,Cl.Burgos.RepararPC.WSDL.TipoLogin.USUARIO);
                    }
                    if(tipoLogin.equals(TipoLogin.Administrador)){
                        resp = actualizarLogin(id,rut,nombre,apellido,correo,celuilar,pass,Cl.Burgos.RepararPC.WSDL.TipoLogin.ADMINISTRADOR);
                    }
                    if(!resp){
                                JOptionPane.showMessageDialog(null, "Usuario no Actualizado");
                            }else{
                                JOptionPane.showMessageDialog(null, "Usuario Actualizado");
                                Limpiar();
                            }
                    }catch (Exception ex) {
//                        Logger.getLogger(FrManteLogin.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        Log.log(ex.getMessage());
                        log.fatal(ex.getMessage());
                    }
//                        Logger.getLogger(FrManteLogin.class.getName()).log(Level.SEVERE, null, ex);
        
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        try {
            boolean resp = buscarRutLoginR(txtRut.getText());
            boolean resp2 = false;
                    String rut = txtRut.getText();
                    String nombre = txtNombre.getText();
                    String apellido = txtApellido.getText();
                    String correo = txtCorreo.getText();
                    String celuilar = txtCelular.getText();
                    String pass = txtPass.getText();
                    TipoLogin tipoLogin = TipoLogin.valueOf(jcbTipoCliente.getSelectedItem().toString());
                    if(tipoLogin.equals(TipoLogin.Usuario)){
                        resp2 = insertarLogin(rut,nombre,apellido,correo,celuilar,pass,Cl.Burgos.RepararPC.WSDL.TipoLogin.USUARIO);
                    }
                    if(tipoLogin.equals(TipoLogin.Administrador)){
                        resp2 = insertarLogin(rut,nombre,apellido,correo,celuilar,pass,Cl.Burgos.RepararPC.WSDL.TipoLogin.ADMINISTRADOR);
                    }
            if(!resp){
                if(!resp2){
                    JOptionPane.showMessageDialog(null, "Usuario no Agregado");
                }else{
                    JOptionPane.showMessageDialog(null, "Usuario Agregado");
                    Limpiar();
                    defineTablaClientes("",1);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Usuario Ya Existe");
            }
        }catch (Exception ex) {
//            Logger.getLogger(FrManteLogin.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Log.log(ex.getMessage());
            log.fatal(ex.getMessage());
        }
//            Logger.getLogger(FrManteLogin.class.getName()).log(Level.SEVERE, null, ex);
       
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void salirclijButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirclijButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_salirclijButton3ActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
                try {
                    boolean resp = eliminarLogin(Integer.parseInt(txtIdLogin.getText()));
                        if(!resp){
                                JOptionPane.showMessageDialog(null, "Usuario no Eliminado");
                            }else{
                                JOptionPane.showMessageDialog(null, "Usuario Eliminado");
                                Limpiar();
                                defineTablaClientes("",1);
                            }
                    }catch (Exception ex) {
//                        Logger.getLogger(FrManteLogin.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        Log.log(ex.getMessage());
                        log.fatal(ex.getMessage());
                    }
//                        Logger.getLogger(FrManteLogin.class.getName()).log(Level.SEVERE, null, ex);
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void JTabLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTabLoginMouseClicked
        int fila;
        this.txtIdLogin.getText();
        List<String> datosCliente;
        fila = this.JTabLogin.rowAtPoint(evt.getPoint());
        DAOLogin login = new DAOLogin();
        long lngCliente;

        if (fila > -1){
            try {
                this.txtIdLogin.setText(String.valueOf(JTabLogin.getValueAt(fila, 0)));

                datosCliente=buscarIdLogin(Integer.parseInt(txtIdLogin.getText()));
//                datosCliente=login.leerLogin( idClLogin());
                    this.txtIdLogin.setText(datosCliente.get(0));
                    this.txtRut.setText(datosCliente.get(1));
                this.txtNombre.setText(datosCliente.get(2));
                this.txtApellido.setText(datosCliente.get(3));
                this.txtCorreo.setText(datosCliente.get(4));
                this.txtCelular.setText(datosCliente.get(5));
                this.txtPass.setText(datosCliente.get(6));
                
//                this.jcbTipoCliente.setSelectedItem(TipoLogin.valueOf(datosCliente.get(7)));
                
                cargaDatosLogin(datosCliente.get(7));
                this.btnEliminar.setEnabled(true);
                this.btnActualizar.setEnabled(true);

//                if(Long.valueOf( datosCliente[0])>0){
                    //                    this.btnActualizar.setLabel("Actualizar");
//                }
            } catch (Exception ex) {
//                Logger.getLogger(FrManteLogin.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage());
                Log.log(ex.getMessage());
                log.fatal(ex.getMessage());
            }
        }
    }//GEN-LAST:event_JTabLoginMouseClicked

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if((c<'a'||c>'z') && (c<'A'||c>'Z') && c!= KeyEvent.VK_SPACE) evt.consume();
    }//GEN-LAST:event_txtNombreKeyTyped


    public void cargaDatosLogin(String tipo){
        if(tipo.equals("Administrador")){jcbTipoCliente.setSelectedIndex(0);}
        if(tipo.equals("Usuario")){jcbTipoCliente.setSelectedIndex(1);}
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTabLogin;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnImprimirReporte;
    private javax.swing.JButton cmdAtras;
    private javax.swing.JButton cmdBuscar;
    private javax.swing.JButton cmdSiguiente1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jcbTipoCliente;
    private javax.swing.JLabel jlblNumReg;
    private javax.swing.JLabel jlblNumReg2;
    private javax.swing.JLabel jlblTotalPaginas;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JButton salirclijButton3;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtIdLogin;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumReg;
    private javax.swing.JTextField txtPagina;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtRut;
    // End of variables declaration//GEN-END:variables


    private static java.util.List<Cl.Burgos.RepararPC.WSDL.ClLogin> listaLoginAll(long intDesde, long intCuantos, java.lang.String strBusqueda) {
        Cl.Burgos.RepararPC.WSDL.WebLoginService_Service service = new Cl.Burgos.RepararPC.WSDL.WebLoginService_Service();
        Cl.Burgos.RepararPC.WSDL.WebLoginService port = service.getWebLoginServicePort();
        return port.listaLoginAll(intDesde, intCuantos, strBusqueda);
    }

    private static boolean actualizarLogin(int id, java.lang.String rut, java.lang.String nombre, java.lang.String apellido, java.lang.String correo, java.lang.String celular, java.lang.String pass, Cl.Burgos.RepararPC.WSDL.TipoLogin tipoLogin) throws Exception_Exception {
        Cl.Burgos.RepararPC.WSDL.WebLoginService_Service service = new Cl.Burgos.RepararPC.WSDL.WebLoginService_Service();
        Cl.Burgos.RepararPC.WSDL.WebLoginService port = service.getWebLoginServicePort();
        return port.actualizarLogin(id, rut, nombre, apellido, correo, celular, pass, tipoLogin);
    }

    private static boolean buscarRutLoginR(java.lang.String rut) throws Exception_Exception {
        Cl.Burgos.RepararPC.WSDL.WebLoginService_Service service = new Cl.Burgos.RepararPC.WSDL.WebLoginService_Service();
        Cl.Burgos.RepararPC.WSDL.WebLoginService port = service.getWebLoginServicePort();
        return port.buscarRutLoginR(rut);
    }

    private static boolean insertarLogin(java.lang.String rut, java.lang.String nombre, java.lang.String apellido, java.lang.String correo, java.lang.String celular, java.lang.String pass, Cl.Burgos.RepararPC.WSDL.TipoLogin tipoLogin) throws Exception_Exception {
        Cl.Burgos.RepararPC.WSDL.WebLoginService_Service service = new Cl.Burgos.RepararPC.WSDL.WebLoginService_Service();
        Cl.Burgos.RepararPC.WSDL.WebLoginService port = service.getWebLoginServicePort();
        return port.insertarLogin(rut, nombre, apellido, correo, celular, pass, tipoLogin);
    }

    private static boolean eliminarLogin(int id) throws Exception_Exception {
        Cl.Burgos.RepararPC.WSDL.WebLoginService_Service service = new Cl.Burgos.RepararPC.WSDL.WebLoginService_Service();
        Cl.Burgos.RepararPC.WSDL.WebLoginService port = service.getWebLoginServicePort();
        return port.eliminarLogin(id);
    }

    private static java.util.List<java.lang.String> buscarIdLogin(int id) throws Exception_Exception {
        Cl.Burgos.RepararPC.WSDL.WebLoginService_Service service = new Cl.Burgos.RepararPC.WSDL.WebLoginService_Service();
        Cl.Burgos.RepararPC.WSDL.WebLoginService port = service.getWebLoginServicePort();
        return port.buscarIdLogin(id);
    }



    
}
