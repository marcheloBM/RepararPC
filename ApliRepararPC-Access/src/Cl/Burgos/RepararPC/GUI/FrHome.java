/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.GUI;

import Cl.Burgos.RepararPC.FUN.Log;
import Cl.Burgos.RepararPC.DAO.DAOCliente;
import Cl.Burgos.RepararPC.DAO.DAOComputador;
import Cl.Burgos.RepararPC.DAO.DAOLogin;
import Cl.Burgos.RepararPC.ENT.ClCliente;
import Cl.Burgos.RepararPC.ENT.ClComputador;
import Cl.Burgos.RepararPC.ENT.ClLogin;
//import Cl.Burgos.RepararPC.Enum.TipoCapacidad;
//import Cl.Burgos.RepararPC.Enum.TipoReparacion;
import Cl.Burgos.RepararPC.FUN.Archivos;
import Cl.Burgos.RepararPC.FUN.ComandosCMD;
import Cl.Burgos.RepararPC.FUN.FormatoFecha;
import Cl.Burgos.RepararPC.FUN.ImprimirExcel;
import Cl.Burgos.RepararPC.FUN.ImprimirPDF;
import Cl.Burgos.RepararPC.FUN.ImprimirWord;
import Cl.Burgos.RepararPC.Conf.Confi;
import Cl.Burgos.RepararPC.DAO.DAOArchivo;
import Cl.Burgos.RepararPC.ENT.ClArchivo;
import Cl.Burgos.RepararPC.FUN.Render;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.apache.log4j.Logger;

/**
 *
 * @author march
 */
public class FrHome extends javax.swing.JFrame {

    //Variables del Log4j
    static  Logger log =Logger.getLogger(FrHome.class);
    
    DAOCliente dAOCliente = new DAOCliente();
    DAOComputador dAOComputador = new DAOComputador();
    DAOArchivo dAOArchivo = new DAOArchivo();
    ClLogin clLogin = FrLogin.clLogin;
    ClCliente clCliente=null;
    String fechaAct = "Fecha Actual: "+FormatoFecha.mostrarFechaCompletaEditada(new Date());
    int idCliente;
    static int idPc;
    long lngNumPaginas;
    /**
     * Creates new form frHome
     */
    public FrHome() {
        initComponents();
        lblNombreLogin.setText("Usuario de Seccion: "+clLogin.getNombre()+" "+clLogin.getApellido());
        lblFechaAc.setText(fechaAct);
        
        Limpiar();
        //Limpiar y Cargar Lista de Cliente ComboBox
        llenarCombo();
//        jcbRam.setModel(new DefaultComboBoxModel<TipoCapacidad>(TipoCapacidad.values()));
//        jcbDisco.setModel(new DefaultComboBoxModel<>(TipoCapacidad.values()));
//        jcbTipoRaparacion.setModel(new DefaultComboBoxModel(TipoReparacion.values()));
        
        jPanel1.setOpaque(false);
        jPanel2.setOpaque(false);
        jPanel3.setOpaque(false);
        jPanel4.setOpaque(false);
        jPanel6.setOpaque(false);
        jPanel7.setOpaque(false);
        jPanel8.setOpaque(false);
        jPanel10.setOpaque(false);
        
        setLocationRelativeTo(null); 
        setResizable(false); 
        setTitle("Inicio de Seccion");
        String url="/Cl/Burgos/RepararPC/IMG/";
        setIconImage(new ImageIcon(getClass().getResource(url+"Icono.png")).getImage());
        ((JPanel)getContentPane()).setOpaque(false);
        ImageIcon MyImgCustom =new ImageIcon(this.getClass().getResource(url+"fondo1.jpg"));
        JLabel fondo= new JLabel();
        
        fondo.setIcon(MyImgCustom);
        getLayeredPane().add(fondo,JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0,0,MyImgCustom.getIconWidth(),MyImgCustom.getIconHeight());
    }

    public String fechaInstalacion(){
        String fechaInsta = "cmd /c wmic os get installdate";
        String fi = ComandosCMD.cmd(fechaInsta);
        fi=fi.substring(0, fi.indexOf('.'));
        int n1=0;
        int n2=4;
        String fecha="";
        for (int i = 0; i < 6; i++) {
            if(n1==0){
                fecha=fecha+""+fi.substring(n1,n2);
                n1=n1+4;
                n2=n2+2;
            }else{
                if(n1==8){
                    fecha=fecha+" "+fi.substring(n1,n2);
                    n1=n1+2;
                    n2=n2+2;
                }else{
                    if(n1>=8){
                        fecha=fecha+":"+fi.substring(n1,n2);
                        n1=n1+2;
                        n2=n2+2;
                    }else{
                        fecha=fecha+"/"+fi.substring(n1,n2);
                        n1=n1+2;
                        n2=n2+2;
                    }
                }
            }
        }
        return fecha;
    }
    public void llenarCombo(){
        //para Cargar el ComboBox
        this.jcbClientes.setSelectedItem(null);
        this.jcbClientes.removeAllItems();
//        dAOCliente.llenarCombo(this.jcbClientes);
        List<ClCliente> listRut=dAOCliente.leerClienteIdLogin(clLogin.getId());
//for indicamos la variable indice en 0 para recorrer toda la lista, de inicio a fin al final de cada iteracion el indice se incrementa en uno
//        for (int i = 0; i < listRut.size(); i++) {
//            this.jcbClientes.addItem(listRut.get(i).getRut());
//        }
//for each : debemos indicar el dato que almacena la lista , en este caso String , luego debemos declarar una variable pivote (str) finalmente dos puntos (:) y la lista que vamos a recorrer
        for(ClCliente cliente : listRut){
            this.jcbClientes.addItem(cliente.getNombre());
        }
    }
    
    public String Reparacion(){
        String resp;
        String Formateo="",Limpieza="",Instalacion="",Reparacion="";
        if(jRadioButton1.isSelected()){ Formateo="Formateo "; }
        if(jRadioButton2.isSelected()){ Limpieza="Limpieza "; }
        if(jRadioButton3.isSelected()){ Instalacion="Instalacion "; }
        if(jRadioButton4.isSelected()){ Reparacion="Reparacion "; }
        resp = Formateo+""+Limpieza+""+Instalacion+""+Reparacion;
        return resp;
    }
    
    public ClComputador datosPc() throws Exception{
        int valor;
        String tipoRepa;
        if(txtValor.getText().length()!=0){
            valor=Integer.parseInt(txtValor.getText());
        }else{
            valor=0;
        }
        tipoRepa=Reparacion();
        java.util.Date dd = jdcFecha.getDate();
        java.sql.Timestamp d = new java.sql.Timestamp(dd.getTime());
        ClComputador pc = new ClComputador(txtMarca.getText(), txtModelo.getText(),
                txtNumeroSerie.getText(),
                txtOS.getText(),txtArquitectura.getText(),
                tipoRepa,
                txtDescripcion.getText(), valor, d , idCliente,clLogin.getId());
        return pc;

    }
    public ClComputador idPc() throws Exception{
        ClComputador pc = new ClComputador(idPc);
        return pc;
    }
    public ClComputador datosPcAll() throws Exception{
        String tipoRepa=Reparacion();
        java.util.Date dd = jdcFecha.getDate();
            java.sql.Timestamp d = new java.sql.Timestamp(dd.getTime());
        ClComputador pc = new ClComputador(
                idPc, 
                txtMarca.getText(), txtModelo.getText(), txtNumeroSerie.getText(),
                
                txtOS.getText(),txtArquitectura.getText(),
                tipoRepa, 
                txtDescripcion.getText(),Integer.parseInt(txtValor.getText()),d, idCliente,clLogin.getId());
        return pc;
    }
        
    public void defineTablaClientes(String strBusqueda,long DesdeHoja){
        
        long lngRegistros=1;
        long lngDesdeRegistro;
        
        //DEFINIMOS LA TABLA MODELO
        DefaultTableModel tablaClientes = new DefaultTableModel();
        
        //LE AGREGAMOS EL TITULO DE LAS COLUMNAS DE LA TABLA EN UN ARREGLO
        String strTitulos[]={"ID","MARCA","MODELO","Numero Serie","SO","Arquitectura","TIPO REPARACION","DESCRPCION","Valor","FECHA INGRESO"};
        
        //LE ASIGNAMOS LAS COLUMNAS AL MODELO CON LA CADENA DE ARRIBA
        tablaClientes.setColumnIdentifiers(strTitulos);
        
        //LE ASIGNAMOS EL MODELO DE ARRIBA AL JTABLE 
        this.JTabPC.setModel(tablaClientes);
        
                    //AHORA A LEER LOS DATOS
        
        //ASIGNAMOS CUANTOS REGISTROS POR HOJA TRAEREMOS
        lngRegistros=(Long.valueOf(this.txtNumReg.getText()));
        
        //ASIGNAMOS DESDE QUE REGISTRO TRAERA LA CONSULTA SQL
        lngDesdeRegistro=(DesdeHoja*lngRegistros)-lngRegistros;
        
        //INSTANCEAMOS LA CLASE CLIENTE
//        DAOPc classCliente= new DAOPc();
        
        //LEEMOS LA CLASE CLIENTE MANDANDOLE LOS PARAMETROS
        dAOComputador.leerClientesId(lngDesdeRegistro, (Long.valueOf(this.txtNumReg.getText())),tablaClientes,strBusqueda,idCliente);
        
        //LE PONEMOS EL RESULTADO DE LA CONSULA AL JTABLE
        this.JTabPC.setModel(tablaClientes);
        
        //ASIGNAMOS LOS VALORES A LA PAGINACION
        lngRegistros = dAOComputador.leerCuantos("");
        lngNumPaginas= (lngRegistros/ (Long.valueOf( this.txtNumReg.getText())))+1;
        this.jlblTotalPaginas.setText(" De " + ( lngNumPaginas));
        
    }
    public void defineTablaClientesAll(String strBusqueda,long DesdeHoja){
        
        long lngRegistros=1;
        long lngDesdeRegistro;
        
        //DEFINIMOS LA TABLA MODELO
        DefaultTableModel tablaClientes = new DefaultTableModel();
        
        //LE AGREGAMOS EL TITULO DE LAS COLUMNAS DE LA TABLA EN UN ARREGLO
        String strTitulos[]={"ID","MARCA","MODELO","Numero Serie","REPARACION","DESCRPCION","FECHA"};
        
        //LE ASIGNAMOS LAS COLUMNAS AL MODELO CON LA CADENA DE ARRIBA
        tablaClientes.setColumnIdentifiers(strTitulos);
        
        //LE ASIGNAMOS EL MODELO DE ARRIBA AL JTABLE 
        this.JTabPC.setModel(tablaClientes);
        
                    //AHORA A LEER LOS DATOS
        
        //ASIGNAMOS CUANTOS REGISTROS POR HOJA TRAEREMOS
        lngRegistros=(Long.valueOf(this.txtNumReg.getText()));
        
        //ASIGNAMOS DESDE QUE REGISTRO TRAERA LA CONSULTA SQL
        lngDesdeRegistro=(DesdeHoja*lngRegistros)-lngRegistros;
        
        //INSTANCEAMOS LA CLASE CLIENTE
//        DAOPc classCliente= new DAOPc();
        
        //LEEMOS LA CLASE CLIENTE MANDANDOLE LOS PARAMETROS
        dAOComputador.leerPC(lngDesdeRegistro, (Long.valueOf(this.txtNumReg.getText())),tablaClientes,strBusqueda);
        
        //LE PONEMOS EL RESULTADO DE LA CONSULA AL JTABLE
        this.JTabPC.setModel(tablaClientes);
        
        //ASIGNAMOS LOS VALORES A LA PAGINACION
        lngRegistros = dAOComputador.leerCuantos("");
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jcbClientes = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        btnAgregarCliente = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtCorreoCliente = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNumeroSerie = new javax.swing.JTextField();
        txtArquitectura = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtOS = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jdcFecha = new com.toedter.calendar.JDateChooser();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        jlblNumReg = new javax.swing.JLabel();
        txtNumReg = new javax.swing.JTextField();
        jlblNumReg2 = new javax.swing.JLabel();
        txtPagina = new javax.swing.JTextField();
        jlblTotalPaginas = new javax.swing.JLabel();
        cmdAtras = new javax.swing.JButton();
        cmdSiguiente1 = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        lblBuscar = new javax.swing.JLabel();
        btnBuscarAll = new javax.swing.JButton();
        btnImprimirReporte = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTabPC = new javax.swing.JTable();
        lblNombreLogin = new javax.swing.JLabel();
        lblFechaAc = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        btnConsultar = new javax.swing.JButton();
        btnAgregarPC = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnActualizarPC = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnEliminarPC = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Clientes"));

        jcbClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Nombre:");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnAgregarCliente.setText("Agregar");
        btnAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarClienteActionPerformed(evt);
            }
        });

        jLabel3.setText("Correo:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCorreoCliente)
                    .addComponent(jcbClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregarCliente)
                .addGap(32, 32, 32))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCorreoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(btnAgregarCliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("PC - Notebook"));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Info PC"));

        jLabel4.setText("Marca:");

        txtMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMarcaKeyTyped(evt);
            }
        });

        jLabel5.setText("Modelo:");

        jLabel12.setText("Numero Serie:");

        txtNumeroSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroSerieActionPerformed(evt);
            }
        });

        jLabel9.setText("Arquitectura:");

        jLabel8.setText("Sistema Operativo:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtArquitectura, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumeroSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOS, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtMarca)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(txtNumeroSerie)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtArquitectura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Info Reparacion"));

        jLabel13.setText("Tipo Reparacion:");

        jLabel16.setText("Fecha:");

        jLabel14.setText("Descripcion:");

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane2.setViewportView(txtDescripcion);

        jLabel15.setText("Valor:");

        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValorKeyTyped(evt);
            }
        });

        jdcFecha.setDateFormatString("dd/MM/yyyy hh:mm:ss");

        jRadioButton1.setText("Formateo");

        jRadioButton2.setText("Limpieza");

        jRadioButton3.setText("Instalacion");

        jRadioButton4.setText("Reparacion");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton4))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15)
                                .addGap(7, 7, 7)
                                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Comandos Listado"));

        jlblNumReg.setText("Registros");

        txtNumReg.setText("5");
        txtNumReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumRegActionPerformed(evt);
            }
        });

        jlblNumReg2.setText("Pagina ");

        txtPagina.setText("1");
        txtPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaginaActionPerformed(evt);
            }
        });

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

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        lblBuscar.setText("Buscar");

        btnBuscarAll.setText("Buscar All");
        btnBuscarAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarAllActionPerformed(evt);
            }
        });

        btnImprimirReporte.setText("Imprimir ");
        btnImprimirReporte.setActionCommand("");
        btnImprimirReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirReporteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblNumReg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumReg, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdAtras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblNumReg2)
                .addGap(2, 2, 2)
                .addComponent(txtPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jlblTotalPaginas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdSiguiente1)
                .addGap(41, 41, 41)
                .addComponent(btnImprimirReporte)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscarAll)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblNumReg)
                    .addComponent(txtNumReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblNumReg2)
                    .addComponent(txtPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblTotalPaginas)
                    .addComponent(cmdAtras)
                    .addComponent(cmdSiguiente1)
                    .addComponent(lblBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarAll, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimirReporte))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista RepacionesPC"));

        JTabPC.setModel(new javax.swing.table.DefaultTableModel(
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
        JTabPC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTabPCMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTabPC);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
        );

        lblNombreLogin.setText("Nombre");

        lblFechaAc.setText("Fecha");

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("PC"));

        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        btnAgregarPC.setText("Agregar");
        btnAgregarPC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPCActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnActualizarPC.setText("Actualizar");
        btnActualizarPC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarPCActionPerformed(evt);
            }
        });

        jButton1.setText("Archivo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnEliminarPC.setText("Eliminar");
        btnEliminarPC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton1)
                    .addComponent(btnLimpiar)
                    .addComponent(btnConsultar))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnAgregarPC)
                    .addComponent(btnActualizarPC)
                    .addComponent(btnEliminarPC))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConsultar)
                    .addComponent(btnAgregarPC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizarPC)
                    .addComponent(btnLimpiar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarPC)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNombreLogin)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblFechaAc)
                                .addGap(206, 206, 206)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreLogin)
                    .addComponent(lblFechaAc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        try {
        ClCliente cc = new ClCliente(jcbClientes.getSelectedItem().toString());
            if(!dAOCliente.sqlSearchNombre(cc)){
                JOptionPane.showMessageDialog(null, "Cliente no Encontrado");
            }else{
                JOptionPane.showMessageDialog(null, "Cliente Encontrado");
                txtCorreoCliente.setText(cc.getCorreo());
                idCliente=cc.getId();
                clCliente=new ClCliente(cc.getId(), cc.getNombre(), cc.getApellido(), cc.getCorreo(), cc.getCelular());
                
                txtCorreoCliente.setEditable(false);
                btnAgregarCliente.setEnabled(false);
                btnBuscar.setEnabled(false);
                btnConsultar.setEnabled(true);
                btnAgregarPC.setEnabled(true);
                
                jcbClientes.setEnabled(false);
                
                defineTablaClientes("",1);
            }
        } catch (Exception ex) {
//            Logger.getLogger(FrHome.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Log.log(ex.getMessage());
            log.fatal(ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarcaKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if((c<'a'||c>'z') && (c<'A'||c>'Z')) evt.consume();
    }//GEN-LAST:event_txtMarcaKeyTyped

    private void txtNumeroSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroSerieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroSerieActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        // TODO add your handling code here:
        String so = Confi.SO;
         // Comando para Linux
        if (so.equals("Linux")){
            // Comando para Linux
            //https://blog.desdelinux.net/comandos-para-conocer-el-sistema-identificar-hardware-y-algunas-configuraciones-de-software/
            
        }else{
            // Comando para Windows
            //CMD
            String marca= "cmd /c wmic computersystem get name";
            String ma = ComandosCMD.cmd(marca);
            ma = ma.replaceAll("\\s*$","");
            txtMarca.setText(ma);
            
            String modelo= "cmd /c wmic csproduct get name";
            String mo = ComandosCMD.cmd(modelo);
            mo = mo.replaceAll("\\s*$","");
            txtModelo.setText(mo);

            String numSerie = "cmd /c wmic bios get serialnumber";
            String numSerie2 = "wmic path win32_computersystemproduct get uuid";
            String ns = ComandosCMD.cmd(numSerie);
            ns = ns.replaceAll("\\s*$","");
            if(ns.equals("To be filled by O.E.M.")){
                ns = ComandosCMD.cmd(numSerie2);
            }
            txtNumeroSerie.setText(ns);

//            String fechaInsta = "systeminfo|find /i “fecha”";
            String fi = fechaInstalacion();
            Date d=null;
            try {
                d = FormatoFecha.mostrarFechaDMYHMS(fi);
            } catch (ParseException ex) {
//                Logger.getLogger(FrHome.class.getName()).log(Level.SEVERE, null, ex);
                Log.log(ex.getMessage());
                log.fatal(ex.getMessage());
            }
            jdcFecha.setDate(d);
        }
        
        //Java
//        String SO = System.getProperty("os.name");
        txtOS.setText(so);
        
        String arq = System.getProperty("os.arch");
        txtArquitectura.setText(arq);
        
        btnAgregarPC.setEnabled(true);
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void txtNumRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumRegActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumRegActionPerformed

    private void txtPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaginaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaginaActionPerformed

    private void cmdAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdAtrasActionPerformed
        long lngValor=0;
        if(1<Long.valueOf( this.txtPagina.getText())){
            lngValor=Long.valueOf( this.txtPagina.getText())-1;
            this.txtPagina.setText(String.valueOf(lngValor));
            defineTablaClientes(Integer.toString(idCliente),lngValor);
        }
    }//GEN-LAST:event_cmdAtrasActionPerformed

    private void cmdSiguiente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSiguiente1ActionPerformed
        long lngValor=0;
        if(lngNumPaginas>Long.valueOf( this.txtPagina.getText())){
            lngValor=Long.valueOf( this.txtPagina.getText())+1;
            this.txtPagina.setText(String.valueOf(lngValor));
            defineTablaClientes(Integer.toString(idCliente),lngValor);
        }
    }//GEN-LAST:event_cmdSiguiente1ActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        // TODO add your handling code here:
        btnBuscarAll.setEnabled(txtBuscar.getText().length() != 0);
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void btnBuscarAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarAllActionPerformed
                defineTablaClientesAll(this.txtBuscar.getText(),Long.valueOf(this.txtPagina.getText()));
    }//GEN-LAST:event_btnBuscarAllActionPerformed

    private void btnImprimirReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirReporteActionPerformed
//        // TODO add your handling code here:
        int opcion=0;
        do {
            try {

                opcion=Integer.parseInt(JOptionPane.showInputDialog(null,
                    "1 Imprimir en Archivo en Excel\n"
                    + "2 Imprimir en Archivo en PDF\n"
                    + "3 Imprimir en Archivo en Word\n"
//                    + "4 Imprimir en Archivo en Impresora\n"
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
//                case 4:
//                imprimirImpresora();
//                break;
                case 0:
                JOptionPane.showMessageDialog(null,"Programa finalizado");
                break;
                default:
                JOptionPane.showMessageDialog(null, "Opcion incorreta");
            }

        } catch (Exception n) {
            JOptionPane.showMessageDialog(null, "Error :"+n.getMessage());
            Log.log(n.getMessage());
            log.fatal(n.getMessage());
        }
        } while (opcion!=0);
    }//GEN-LAST:event_btnImprimirReporteActionPerformed

    public void imprimirExel(){
        try {
            DAOLogin dAOLogin= new DAOLogin();
            String[] datosLogin=dAOLogin.leerLogin(clLogin);
            String[] datosCliente=dAOCliente.leerCliente(clCliente);
            String[] datosPC=dAOComputador.leerCliente(idPc());
            
            ImprimirExcel e=new ImprimirExcel();
            e.Crear(datosLogin,datosCliente,datosPC);
            JOptionPane.showMessageDialog(null,"Todavia no esta Listo Esta Funcion");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Hubo un error"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            Log.log(ex.getMessage());
            log.fatal(ex.getMessage());
        }
    }
    public void imprimirPDF(){
        ImprimirPDF imprimirPDF = new ImprimirPDF();
        try {
            imprimirPDF.Crear(clLogin,clCliente,datosPcAll());
        } catch (Exception ex) {
//            Logger.getLogger(FrHome.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.fatal(ex.getMessage());
        }
    }
    public void imprimirWord(){
        
        ImprimirWord word = new ImprimirWord();
        try {
            word.crear(clLogin,clCliente,datosPcAll());
        } catch (Exception ex) {
//            Logger.getLogger(FrHome.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.fatal(ex.getMessage());
        }
    }
//    public void imprimirImpresora(){
//        
//    }
    private void JTabPCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTabPCMouseClicked
        // TODO add your handling code here:
        int fila;
//        this.txtIdPC.getText();
        String[] datosCliente =new String[10];
        fila = this.JTabPC.rowAtPoint(evt.getPoint());
//        DAOPc cliente = new DAOPc();
        long lngCliente;
        
        if (fila > -1){
            try {
                idPc=Integer.parseInt(String.valueOf(JTabPC.getValueAt(fila, 0)));

                datosCliente=dAOComputador.leerCliente( idPc());

                idPc=Integer.parseInt(datosCliente[0]);
                this.txtMarca.setText(datosCliente[1]);
                this.txtModelo.setText(datosCliente[2]);
                this.txtNumeroSerie.setText(datosCliente[3]);
                
                this.txtOS.setText(datosCliente[4]);
                this.txtArquitectura.setText(datosCliente[5]);
                repar(datosCliente[6]);
                //                selecionarCheak(datosCliente[6]);
                this.txtDescripcion.setText(datosCliente[7]);
                this.txtValor.setText(datosCliente[8]);
                //                this.txtReparacion.setText(datosCliente[6]);
                Date d = FormatoFecha.mostrarFechaYMDHMS(datosCliente[9]);
                this.jdcFecha.setDate(d);

                

                if(Long.valueOf( datosCliente[0])>0){
                    this.btnAgregarPC.setEnabled(false);
                    this.btnEliminarPC.setEnabled(true);
                    this.btnActualizarPC.setEnabled(true);
                    this.btnImprimirReporte.setEnabled(true);
//                    this.btnActualizarPC.setLabel("Actualizar");
                    }
            } catch (Exception ex) {
//                Logger.getLogger(FrHome.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage());
                Log.log(ex.getMessage());
                log.fatal(ex.getMessage());
            }
            new FrArchivo().setVisible(true);
        }
    }//GEN-LAST:event_JTabPCMouseClicked

    private void btnAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarClienteActionPerformed
        // TODO add your handling code here:
        FrCliente cliente = new FrCliente();
        cliente.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnAgregarClienteActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        idCliente=0;
        txtCorreoCliente.setText("");
        txtCorreoCliente.setEditable(true);
        btnAgregarCliente.setEnabled(true);
        btnBuscar.setEnabled(true);
        jcbClientes.setEnabled(true);
        Limpiar();

        DefaultTableModel modelo = (DefaultTableModel) JTabPC.getModel();
        while(modelo.getRowCount()>0)modelo.removeRow(0);

        TableColumnModel modCol = JTabPC.getColumnModel();
        while(modCol.getColumnCount()>0)modCol.removeColumn(modCol.getColumn(0));

        //        defineTablaClientes(Integer.toString(idCliente),1);
    }//GEN-LAST:event_btnLimpiarActionPerformed

    public void Limpiar(){
        jLabel4.setForeground(Color.WHITE);
        jLabel5.setForeground(Color.WHITE);
        jLabel8.setForeground(Color.WHITE);
        jLabel9.setForeground(Color.WHITE);
        jLabel13.setForeground(Color.WHITE);
        jLabel14.setForeground(Color.WHITE);
        jLabel16.setForeground(Color.WHITE);
        
        btnAgregarPC.setEnabled(false);
        btnActualizarPC.setEnabled(false);
        btnEliminarPC.setEnabled(false);
        btnImprimirReporte.setEnabled(false);
        btnConsultar.setEnabled(false);
        btnBuscarAll.setEnabled(false);
        btnBuscar.setEnabled(true);
        
        txtMarca.setText("");
        txtModelo.setText("");
        txtNumeroSerie.setText("");
        txtOS.setText("");
        txtArquitectura.setText("");
        txtDescripcion.setText("");
//        txtIdPC.setText("");
        txtValor.setText("");
        txtNumReg.setText("8");
        
        jRadioButton1.setSelected(false);
        jRadioButton2.setSelected(false);
        jRadioButton3.setSelected(false);
        jRadioButton4.setSelected(false);
        
        //Colocar la fecha de hoy a calendar jdcFecha
        Calendar c2 = new GregorianCalendar();
        jdcFecha.setCalendar(c2);
        
        //Bloqueo de Jtable
        JTabPC.setEnabled(false);
        
    }
    
    private void btnAgregarPCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPCActionPerformed
        try {
            // TODO add your handling code here:
            if(!dAOComputador.sqlInsert(datosPc())){
                JOptionPane.showMessageDialog(null, "PC No Agregado");
            }else{
                JOptionPane.showMessageDialog(null, "PC Agregado");
                defineTablaClientes(Integer.toString(idCliente),1);
                Limpiar();

            }
        } catch (Exception ex) {
//            Logger.getLogger(FrHome.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Log.log(ex.getMessage());
            log.fatal(ex.getMessage());
        }
    }//GEN-LAST:event_btnAgregarPCActionPerformed

    private void btnActualizarPCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarPCActionPerformed
        try {
            // TODO add your handling code here:
            if(!dAOComputador.sqlUpdate(datosPcAll())){
                JOptionPane.showMessageDialog(null, "PC No Actualizado");
            }else{
                JOptionPane.showMessageDialog(null, "PC Actualizado");
                defineTablaClientes(Integer.toString(idCliente),1);
                Limpiar();
            }
        } catch (Exception ex) {
//            Logger.getLogger(FrHome.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.fatal(ex.getMessage());
        }
    }//GEN-LAST:event_btnActualizarPCActionPerformed

    private void btnEliminarPCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPCActionPerformed
        try {
            // TODO add your handling code here:
            if(!dAOComputador.sqlDelete(idPc())){
                JOptionPane.showMessageDialog(null, "PC No Eliminado");
            }else{
                JOptionPane.showMessageDialog(null, "PC Eliminado");
                defineTablaClientes(Integer.toString(idCliente),1);
                Limpiar();
            }
        } catch (Exception ex) {
//            Logger.getLogger(FrHome.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.fatal(ex.getMessage());
        }
    }//GEN-LAST:event_btnEliminarPCActionPerformed

    private void txtValorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyTyped
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if(!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE){
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo Numeros");
            txtValor.setCursor(null);
        }
    }//GEN-LAST:event_txtValorKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            ClComputador cc = Archivos.leerArchivo();
            txtMarca.setText(cc.getMarca());
            txtModelo.setText(cc.getModelo());
            txtNumeroSerie.setText(cc.getNumSerie());
            txtOS.setText(cc.getSistemaOpe());
            txtArquitectura.setText(cc.getArquitectura());
            jdcFecha.setDate(cc.getFecha());
            
            llenarCombo();
            btnAgregarPC.setEnabled(true);
            
        } catch (Exception ex) {
//            Logger.getLogger(FrHome.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Log.log(ex.getMessage());
            log.fatal(ex.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    
    public void repar(String wind){
        String[] parts = wind.split(" ");
        for (int i = 0; i < parts.length; i++) {
//            System.out.println(parts[i].toString());
            if(parts[i].equals("Formateo")){ jRadioButton1.setSelected(true); }
            if(parts[i].equals("Limpieza")){ jRadioButton2.setSelected(true); }
            if(parts[i].equals("Instalacion")){ jRadioButton3.setSelected(true); }
            if(parts[i].equals("Reparacion")){ jRadioButton4.setSelected(true); }
        }
    }
       
    
    
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
            java.util.logging.Logger.getLogger(FrHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrHome().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTabPC;
    private javax.swing.JButton btnActualizarPC;
    private javax.swing.JButton btnAgregarCliente;
    private javax.swing.JButton btnAgregarPC;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarAll;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnEliminarPC;
    private javax.swing.JButton btnImprimirReporte;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cmdAtras;
    private javax.swing.JButton cmdSiguiente1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> jcbClientes;
    private com.toedter.calendar.JDateChooser jdcFecha;
    private javax.swing.JLabel jlblNumReg;
    private javax.swing.JLabel jlblNumReg2;
    private javax.swing.JLabel jlblTotalPaginas;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblFechaAc;
    private javax.swing.JLabel lblNombreLogin;
    private javax.swing.JTextField txtArquitectura;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCorreoCliente;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNumReg;
    private javax.swing.JTextField txtNumeroSerie;
    private javax.swing.JTextField txtOS;
    private javax.swing.JTextField txtPagina;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
