/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.GUI;

import Cl.Burgos.RepararPC.Log4j.Log;
import Cl.Burgos.RepararPC.DAO.DAOCliente;
import Cl.Burgos.RepararPC.DAO.DAOComputador;
import Cl.Burgos.RepararPC.DAO.DAOLogin;
import Cl.Burgos.RepararPC.ENT.ClCliente;
import Cl.Burgos.RepararPC.ENT.ClComputador;
import Cl.Burgos.RepararPC.ENT.ClLogin;
import Cl.Burgos.RepararPC.Enum.TipoCapacidad;
import Cl.Burgos.RepararPC.Enum.TipoReparacion;
import Cl.Burgos.RepararPC.FUN.Archivos;
import Cl.Burgos.RepararPC.FUN.ComandosCMD;
import Cl.Burgos.RepararPC.FUN.FormatoFecha;
import Cl.Burgos.RepararPC.FUN.ImprimirExcel;
import Cl.Burgos.RepararPC.FUN.ImprimirPDF;
import Cl.Burgos.RepararPC.FUN.ImprimirWord;
import Cl.Burgos.RepararPC.Inter.Confi;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
    ClLogin clLogin = FrLogin.clLogin;
    ClCliente clCliente=null;
    String fechaAct = "Fecha Actual: "+FormatoFecha.mostrarFechaCompletaEditada(new Date());
    
    int idCliente;
    long lngNumPaginas;
    /**
     * Creates new form frHome
     */
    public FrHome() {
        initComponents();
        lblNombreLogin.setText("Login: "+clLogin.getNombre()+" "+clLogin.getApellido());
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
        jPanel5.setOpaque(false);
        jPanel6.setOpaque(false);
        jPanel7.setOpaque(false);
        jPanel8.setOpaque(false);
        
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
            this.jcbClientes.addItem(cliente.getRut());
        }
    }
    public ClComputador datosPc() throws Exception{
        String ram = jcbRam.getSelectedItem().toString();
        String disco = jcbDisco.getSelectedItem().toString();
        int valor;
        if(ram.equals("Seleccionar")){
            ram="0";
        }else{
            ram = jcbRam.getSelectedItem().toString();
        }
        if(disco.equals("Seleccionar")){
            disco="0";
        }else{
            disco = jcbDisco.getSelectedItem().toString();
        }
        if(txtValor.getText().length()!=0){
            valor=Integer.parseInt(txtValor.getText());
        }else{
            valor=0;
        }
        java.util.Date dd = jdcFecha.getDate();
        java.sql.Timestamp d = new java.sql.Timestamp(dd.getTime());
        ClComputador pc = new ClComputador(txtMarca.getText(), txtModelo.getText(),
                txtNumeroSerie.getText(),txtRam.getText()+" "+ram,txtHDD.getText()+" "+disco,
                txtOS.getText(),txtArquitectura.getText(),txtVersion.getText(),
                TipoReparacion.valueOf(jcbTipoRaparacion.getSelectedItem().toString()),
                txtDescripcion.getText(), valor, d , idCliente,clLogin.getId());
        return pc;

    }
    public ClComputador idPc() throws Exception{
        ClComputador pc = new ClComputador(Integer.parseInt(txtIdPC.getText()));
        return pc;
    }
    public ClComputador datosPcAll() throws Exception{
        java.util.Date dd = jdcFecha.getDate();
            java.sql.Timestamp d = new java.sql.Timestamp(dd.getTime());
        ClComputador pc = new ClComputador(
                Integer.parseInt(txtIdPC.getText()), 
                txtMarca.getText(), txtModelo.getText(), txtNumeroSerie.getText(),
                txtRam.getText()+" "+jcbRam.getSelectedItem().toString(), 
                txtHDD.getText()+" "+jcbDisco.getSelectedItem().toString(),
                txtOS.getText(),txtArquitectura.getText(),txtVersion.getText(),
                TipoReparacion.valueOf(jcbTipoRaparacion.getSelectedItem().toString()), 
                txtDescripcion.getText(),Integer.parseInt(txtValor.getText()),d, idCliente,clLogin.getId());
        return pc;
    }
    
    public void defineTablaClientes(String strBusqueda,long DesdeHoja){
        
        long lngRegistros=1;
        long lngDesdeRegistro;
        
        //DEFINIMOS LA TABLA MODELO
        DefaultTableModel tablaClientes = new DefaultTableModel();
        
        //LE AGREGAMOS EL TITULO DE LAS COLUMNAS DE LA TABLA EN UN ARREGLO
        String strTitulos[]={"ID","MARCA","MODELO","Numero Serie","RAM","HDD","SO","Arquitectura","Version","TIPO REPARACION","DESCRPCION","Valor","FECHA INGRESO"};
        
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
        String strTitulos[]={"ID","MARCA","MODELO","Numero Serie","RAM","DISCO","REPARACION","DESCRPCION","FECHA"};
        
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jcbClientes = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnAgregarCliente = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtApellidoCliente = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtIdPC = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        txtRam = new javax.swing.JTextField();
        jcbRam = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        txtNumeroSerie = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtHDD = new javax.swing.JTextField();
        jcbDisco = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtOS = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtArquitectura = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtVersion = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jcbTipoRaparacion = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jdcFecha = new com.toedter.calendar.JDateChooser();
        btnConsultar = new javax.swing.JButton();
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
        btnAgregarPC = new javax.swing.JButton();
        btnActualizarPC = new javax.swing.JButton();
        btnEliminarPC = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Clientes"));

        jLabel1.setText("Rut:");

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

        jLabel3.setText("Apellido:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAgregarCliente))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jcbClientes, 0, 115, Short.MAX_VALUE)
                                    .addComponent(txtNombreCliente))))
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtApellidoCliente)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jcbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtApellidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(btnAgregarCliente))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("PC - Notebook"));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Info PC"));

        jLabel11.setText("IdPC:");

        txtIdPC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdPCActionPerformed(evt);
            }
        });

        jLabel4.setText("Marca:");

        txtMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMarcaKeyTyped(evt);
            }
        });

        jLabel5.setText("Modelo:");

        jLabel6.setText("Ram:");

        txtRam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRamKeyTyped(evt);
            }
        });

        jcbRam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar", "KB", "MB", "GB", "TB" }));

        jLabel12.setText("Numero Serie:");

        txtNumeroSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroSerieActionPerformed(evt);
            }
        });

        jLabel7.setText("HDD C:");

        txtHDD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHDDKeyTyped(evt);
            }
        });

        jcbDisco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar", "KB", "MB", "GB", "TB" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIdPC, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(txtRam)
                            .addGap(18, 18, 18)
                            .addComponent(jcbRam, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtModelo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4)
                    .addComponent(jLabel12)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtHDD, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcbDisco, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNumeroSerie)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtMarca))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtIdPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtNumeroSerie))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtRam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbRam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbDisco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Info Sistema Operativo"));

        jLabel8.setText("Sistema Operativo:");

        jLabel9.setText("Arquitectura:");

        jLabel10.setText("Version:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtOS, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(txtArquitectura, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtArquitectura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtVersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Info Reparacion"));

        jLabel13.setText("Tipo Reparacion:");

        jcbTipoRaparacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Formateo", "Limpieza", "Instalacion", "Reparacion" }));

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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel15)))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jcbTipoRaparacion, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(jdcFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jcbTipoRaparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
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
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
        );

        lblNombreLogin.setText("Nombre");

        lblFechaAc.setText("Fecha");

        btnAgregarPC.setText("Agregar");
        btnAgregarPC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPCActionPerformed(evt);
            }
        });

        btnActualizarPC.setText("Actualizar");
        btnActualizarPC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarPCActionPerformed(evt);
            }
        });

        btnEliminarPC.setText("Eliminar");
        btnEliminarPC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPCActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jButton1.setText("Archivo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnEliminarPC))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnLimpiar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnActualizarPC))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnConsultar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnAgregarPC)))
                                .addGap(16, 16, 16)))
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblNombreLogin)
                .addGap(189, 189, 189)
                .addComponent(lblFechaAc)
                .addGap(115, 115, 115))
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
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnConsultar)
                            .addComponent(btnAgregarPC))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnActualizarPC)
                            .addComponent(btnLimpiar))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminarPC)
                            .addComponent(jButton1)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            if(!dAOCliente.sqlSearchRut(cc)){
                JOptionPane.showMessageDialog(null, "Cliente no Encontrado");
            }else{
                JOptionPane.showMessageDialog(null, "Cliente Encontrado");
                txtNombreCliente.setText(cc.getNombre());
                txtApellidoCliente.setText(cc.getApellido());
                idCliente=cc.getId();
                clCliente=new ClCliente(cc.getId(), cc.getRut(), cc.getNombre(), cc.getApellido(), cc.getCorreo(), cc.getCelular());
                
                txtNombreCliente.setEditable(false);
                txtApellidoCliente.setEditable(false);
                btnAgregarCliente.setEnabled(false);
                btnBuscar.setEnabled(false);
                btnConsultar.setEnabled(true);
                
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

    private void txtIdPCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdPCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdPCActionPerformed

    private void txtRamKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRamKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(c<'0' || c>'9') evt.consume();
    }//GEN-LAST:event_txtRamKeyTyped

    private void txtHDDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHDDKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(c<'0' || c>'9') evt.consume();
    }//GEN-LAST:event_txtHDDKeyTyped

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
            String ns = ComandosCMD.cmd(numSerie);
            ns = ns.replaceAll("\\s*$","");
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
        
        String versionSO = System.getProperty("os.version");
        txtVersion.setText(versionSO);
        
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
        this.txtIdPC.getText();
        String[] datosCliente =new String[10];
        fila = this.JTabPC.rowAtPoint(evt.getPoint());
//        DAOPc cliente = new DAOPc();
        long lngCliente;
//
        if (fila > -1){
            try {
                this.txtIdPC.setText(String.valueOf(JTabPC.getValueAt(fila, 0)));

                datosCliente=dAOComputador.leerCliente( idPc());

                this.txtIdPC.setText(datosCliente[0]);
                this.txtMarca.setText(datosCliente[1]);
                this.txtModelo.setText(datosCliente[2]);
                this.txtNumeroSerie.setText(datosCliente[3]);
                this.txtRam.setText(datosCliente[4].substring(0,datosCliente[4].length()-2));
                this.txtHDD.setText(datosCliente[5].substring(0,datosCliente[5].length()-2));
                String ram = datosCliente[4].substring(datosCliente[4].length()-2,datosCliente[4].length());
                String disco = datosCliente[5].substring(datosCliente[5].length()-2,datosCliente[5].length());
                cargarDatosRam(ram);
                cargarDatosDisco(disco);
                this.txtOS.setText(datosCliente[6]);
                this.txtArquitectura.setText(datosCliente[7]);
                this.txtVersion.setText(datosCliente[8]);
                cargaDatosReparacion(datosCliente[9]);
                //                selecionarCheak(datosCliente[6]);
                this.txtDescripcion.setText(datosCliente[10]);
                this.txtValor.setText(datosCliente[11]);
                //                this.txtReparacion.setText(datosCliente[6]);
                Date d = FormatoFecha.mostrarFechaYMDHMS(datosCliente[12]);
                this.jdcFecha.setDate(d);

                

                if(Long.valueOf( datosCliente[0])>0){
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
        txtNombreCliente.setText("");
        txtApellidoCliente.setText("");
        txtNombreCliente.setEditable(true);
        txtApellidoCliente.setEditable(true);
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
        jLabel1.setForeground(Color.WHITE);
        jLabel4.setForeground(Color.WHITE);
        jLabel5.setForeground(Color.WHITE);
        jLabel6.setForeground(Color.WHITE);
        jLabel7.setForeground(Color.WHITE);
        jLabel8.setForeground(Color.WHITE);
        jLabel9.setForeground(Color.WHITE);
        jLabel10.setForeground(Color.WHITE);
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
        txtRam.setText("");
        txtHDD.setText("");
        txtOS.setText("");
        txtArquitectura.setText("");
        txtVersion.setText("");
        txtDescripcion.setText("");
        txtIdPC.setText("");
        txtValor.setText("");
        txtNumReg.setText("8");
        
        jcbDisco.setSelectedIndex(0);
        jcbRam.setSelectedIndex(0);
        jcbTipoRaparacion.setSelectedIndex(0);
        
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
            txtVersion.setText(cc.getVersion());
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

    public void cargarDatosRam(String ram){
        if(ram.equals("KB")){jcbRam.setSelectedIndex(1);}
        if(ram.equals("MB")){jcbRam.setSelectedIndex(2);}
        if(ram.equals("GB")){jcbRam.setSelectedIndex(3);}
        if(ram.equals("TB")){jcbRam.setSelectedIndex(4);}
    }
    public void cargarDatosDisco(String disco){
        if(disco.equals("KB")){jcbDisco.setSelectedIndex(1);}
        if(disco.equals("MB")){jcbDisco.setSelectedIndex(2);}
        if(disco.equals("GB")){jcbDisco.setSelectedIndex(3);}
        if(disco.equals("TB")){jcbDisco.setSelectedIndex(4);}
    }
    public void cargaDatosReparacion(String tipo){
        if(tipo.equals("Formateo")){jcbTipoRaparacion.setSelectedIndex(1);}
        if(tipo.equals("Limpieza")){jcbTipoRaparacion.setSelectedIndex(2);}
        if(tipo.equals("Instalacion")){jcbTipoRaparacion.setSelectedIndex(3);}
        if(tipo.equals("Reparacion")){jcbTipoRaparacion.setSelectedIndex(4);}
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
    private javax.swing.JButton cmdAtras;
    private javax.swing.JButton cmdSiguiente1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> jcbClientes;
    public static javax.swing.JComboBox jcbDisco;
    public static javax.swing.JComboBox jcbRam;
    private javax.swing.JComboBox<String> jcbTipoRaparacion;
    private com.toedter.calendar.JDateChooser jdcFecha;
    private javax.swing.JLabel jlblNumReg;
    private javax.swing.JLabel jlblNumReg2;
    private javax.swing.JLabel jlblTotalPaginas;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblFechaAc;
    private javax.swing.JLabel lblNombreLogin;
    private javax.swing.JTextField txtApellidoCliente;
    private javax.swing.JTextField txtArquitectura;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtHDD;
    private javax.swing.JTextField txtIdPC;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNumReg;
    private javax.swing.JTextField txtNumeroSerie;
    private javax.swing.JTextField txtOS;
    private javax.swing.JTextField txtPagina;
    private javax.swing.JTextField txtRam;
    private javax.swing.JTextField txtValor;
    private javax.swing.JTextField txtVersion;
    // End of variables declaration//GEN-END:variables
}
