/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.GUI.Mant;

import Cl.Burgos.RepararPC.Log4j.Log;
import Cl.Burgos.RepararPC.DAO.DAOCliente;
import Cl.Burgos.RepararPC.DAO.DAOComputador;
import Cl.Burgos.RepararPC.ENT.ClComputador;
import Cl.Burgos.RepararPC.Enum.TipoCapacidad;
import Cl.Burgos.RepararPC.Enum.TipoReparacion;
import Cl.Burgos.RepararPC.FUN.FormatoFecha;
import static Cl.Burgos.RepararPC.GUI.FrHome.jcbDisco;
import static Cl.Burgos.RepararPC.GUI.FrHome.jcbRam;
import cl.burgos.repararpc.service.Exception_Exception;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.log4j.Logger;

/**
 *
 * @author march
 */
public class FrManteComputador extends javax.swing.JInternalFrame {

    //Variables del Log4j
    static  Logger log =Logger.getLogger(FrManteComputador.class);
    
    DAOCliente dAOCliente = new DAOCliente();
    DAOComputador dAOComputador = new DAOComputador();
    
    long lngNumPaginas;
    /**
     * Creates new form FrManteComputador
     */
    public FrManteComputador() {
        initComponents();
        
        //Los paneles sean tranparentes
        jPanel1.setOpaque(false);
        jPanel2.setOpaque(false);
        jPanel3.setOpaque(false);
        jPanel7.setOpaque(false);
        jPanel10.setOpaque(false);
        
        Limpiar();
        //Limpiar y Cargar Lista de Cliente ComboBox
//        jcbRam.setModel(new DefaultComboBoxModel(TipoCapacidad.values()));
//        jcbDisco.setModel(new DefaultComboBoxModel(TipoCapacidad.values()));
//        jcbTipoRaparacion.setModel(new DefaultComboBoxModel(TipoReparacion.values()));
        
        setResizable(false);
        setTitle("Mantendor Computador");
        String url="/Cl/Burgos/RepararPC/IMG/Mant/";
        
        ((JPanel)getContentPane()).setOpaque(false);
        ImageIcon MyImgCustom =new ImageIcon(this.getClass().getResource(url+"fondo1.jpg"));
        JLabel fondo= new JLabel();
        
        fondo.setIcon(MyImgCustom);
        getLayeredPane().add(fondo,JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0,0,MyImgCustom.getIconWidth(),MyImgCustom.getIconHeight());
    }

    public void defineTablaClientesAll(String strBusqueda,long DesdeHoja){
        
        long lngRegistros=1;
        long lngDesdeRegistro;
        
        //DEFINIMOS LA TABLA MODELO
        DefaultTableModel tablaClientes = new DefaultTableModel();
        
        //LE AGREGAMOS EL TITULO DE LAS COLUMNAS DE LA TABLA EN UN ARREGLO
        String strTitulos[]={"ID","MARCA","MODELO","Numero Serie","RAM","HDD","SO","Arquitectura","Version","TIPO REPARACION","DESCRPCION","Valor","FECHA INGRESO","IDCliente","IDLogin"};
        
        //LE ASIGNAMOS LAS COLUMNAS AL MODELO CON LA CADENA DE ARRIBA
        tablaClientes.setColumnIdentifiers(strTitulos);
        
        //LE ASIGNAMOS EL MODELO DE ARRIBA AL JTABLE 
        this.JTabPC2.setModel(tablaClientes);
        
                    //AHORA A LEER LOS DATOS
        
        //ASIGNAMOS CUANTOS REGISTROS POR HOJA TRAEREMOS
        lngRegistros=(Long.valueOf(this.txtNumReg.getText()));
        
        //ASIGNAMOS DESDE QUE REGISTRO TRAERA LA CONSULTA SQL
        lngDesdeRegistro=(DesdeHoja*lngRegistros)-lngRegistros;
        
        //INSTANCEAMOS LA CLASE CLIENTE
//        DAOPc classCliente= new DAOPc();
        
        //LEEMOS LA CLASE CLIENTE MANDANDOLE LOS PARAMETROS
//        dAOComputador.leerPC(lngDesdeRegistro, (Long.valueOf(this.txtNumReg.getText())),tablaClientes,strBusqueda);
        List<cl.burgos.repararpc.service.ClComputador> lista=listaPCAll(lngDesdeRegistro, (Long.valueOf(this.txtNumReg.getText())),strBusqueda);
        String datos[]=new String [15];
        for (int i = 0; i < lista.size(); i++) {
            datos[0]=Integer.toString(lista.get(i).getIdPC());
            datos[1]=lista.get(i).getMarca();
            datos[2]=lista.get(i).getModelo();
            datos[3]=lista.get(i).getNumSerie();
            datos[4]=lista.get(i).getRam();
            datos[5]=lista.get(i).getHdd();
            datos[6]=lista.get(i).getSistemaOpe();
            datos[7]=lista.get(i).getArquitectura();
            datos[8]=lista.get(i).getVersion();
            datos[9]=lista.get(i).getTipoRepa().toString();
            datos[10]=lista.get(i).getDescripcion();
            datos[11]=Integer.toString(lista.get(i).getValor());
            datos[12]=lista.get(i).getFecha().toString();
            datos[13]=Integer.toString(lista.get(i).getIdCliente());
            datos[14]=Integer.toString(lista.get(i).getIdLogin());
            tablaClientes.addRow(datos);
        }
        
        
        //LE PONEMOS EL RESULTADO DE LA CONSULA AL JTABLE
        this.JTabPC2.setModel(tablaClientes);
        
        //ASIGNAMOS LOS VALORES A LA PAGINACION
        lngRegistros = dAOComputador.leerCuantos("");
        lngNumPaginas= (lngRegistros/ (Long.valueOf( this.txtNumReg.getText())))+1;
        this.jlblTotalPaginas.setText(" De " + ( lngNumPaginas));
        
    }
    
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
//        btnAgregarPC.setEnabled(false);
//        btnActualizarPC.setEnabled(false);
//        btnEliminarPC.setEnabled(false);
//        btnImprimirReporte.setEnabled(false);
//        btnConsultar.setEnabled(false);
//        btnBuscarAll.setEnabled(false);
//        btnBuscar.setEnabled(true);
        
        
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
        txtIdCliente.setText("");
        txtIdLogin.setText("");
        txtNumReg.setText("10");
        
        jcbDisco.setSelectedIndex(0);
        jcbRam.setSelectedIndex(0);
        jcbTipoRaparacion.setSelectedIndex(0);
        
        //Colocar la fecha de hoy a calendar jdcFecha
        Calendar c2 = new GregorianCalendar();
        jdcFecha.setCalendar(c2);
        
        //Bloqueo de Jtable
        JTabPC2.setEnabled(false);

        defineTablaClientesAll("",1);
    }
    
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
                txtDescripcion.getText(), valor, d , Integer.parseInt(txtIdCliente.getText()),Integer.parseInt(txtIdLogin.getText()));
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
                txtDescripcion.getText(),Integer.parseInt(txtValor.getText()),d, Integer.parseInt(txtIdCliente.getText()),
                Integer.parseInt(txtIdLogin.getText()));
        return pc;
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
        jLabel11 = new javax.swing.JLabel();
        txtIdPC = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtRam = new javax.swing.JTextField();
        jcbRam = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        txtNumeroSerie = new javax.swing.JTextField();
        txtHDD = new javax.swing.JTextField();
        jcbDisco = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        txtOS = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtArquitectura = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtVersion = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jcbTipoRaparacion = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jdcFecha = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtIdLogin = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnAgregarPC = new javax.swing.JButton();
        btnActualizarPC = new javax.swing.JButton();
        btnEliminarPC = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
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
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        JTabPC2 = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Mantenedor Login"));

        jLabel11.setText("IdPC:");

        txtIdPC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdPCActionPerformed(evt);
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

        jLabel7.setText("HDD C:");

        jLabel12.setText("Numero Serie:");

        jLabel4.setText("Marca:");

        txtMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMarcaKeyTyped(evt);
            }
        });

        txtNumeroSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroSerieActionPerformed(evt);
            }
        });

        txtHDD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHDDKeyTyped(evt);
            }
        });

        jcbDisco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar", "KB", "MB", "GB", "TB" }));

        jLabel8.setText("Sistema Operativo:");

        jLabel9.setText("Arquitectura:");

        jLabel10.setText("Version:");

        jLabel13.setText("Tipo Reparacion:");

        jcbTipoRaparacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Formateo", "Limpieza", "Instalacion", "Reparacion" }));

        jLabel16.setText("Fecha:");

        jdcFecha.setDateFormatString("dd/MM/yyyy hh:mm:ss");

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

        jLabel1.setText("IdCliente");

        jLabel2.setText("IdLogin");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 63, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIdPC, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                            .addComponent(txtRam)
                                            .addGap(18, 18, 18)
                                            .addComponent(jcbRam, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtModelo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtIdLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4)
                            .addComponent(jLabel12)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtHDD, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jcbDisco, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNumeroSerie)
                            .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtOS, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtArquitectura, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel13)
                                .addComponent(jLabel14))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(jLabel15)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jcbTipoRaparacion, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(jdcFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtIdLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtIdPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtNumeroSerie))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtRam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbRam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbDisco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtArquitectura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtVersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jcbTipoRaparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addComponent(jdcFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addContainerGap())
        );

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

        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(btnEliminarPC))
                        .addComponent(btnActualizarPC, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addGap(8, 8, 8)
                            .addComponent(btnAgregarPC))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregarPC)
                .addGap(18, 18, 18)
                .addComponent(btnActualizarPC)
                .addGap(18, 18, 18)
                .addComponent(btnEliminarPC)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(45, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
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

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista RepacionesPC"));

        JTabPC2.setModel(new javax.swing.table.DefaultTableModel(
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
        JTabPC2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTabPC2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(JTabPC2);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void txtIdPCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdPCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdPCActionPerformed

    private void txtRamKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRamKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(c<'0' || c>'9') evt.consume();
    }//GEN-LAST:event_txtRamKeyTyped

    private void txtMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarcaKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if((c<'a'||c>'z') && (c<'A'||c>'Z')) evt.consume();
    }//GEN-LAST:event_txtMarcaKeyTyped

    private void txtNumeroSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroSerieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroSerieActionPerformed

    private void txtHDDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHDDKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(c<'0' || c>'9') evt.consume();
    }//GEN-LAST:event_txtHDDKeyTyped

    private void txtValorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyTyped
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if(!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE){
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo Numeros");
            txtValor.setCursor(null);
        }
    }//GEN-LAST:event_txtValorKeyTyped

    private void btnAgregarPCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPCActionPerformed
        try {
            // TODO add your handling code here:
            if(!dAOComputador.sqlInsert(datosPc())){
                JOptionPane.showMessageDialog(null, "PC No Agregado");
            }else{
                JOptionPane.showMessageDialog(null, "PC Agregado");
                defineTablaClientesAll("",1);
                Limpiar();

            }
        } catch (Exception ex) {
//            Logger.getLogger(FrManteComputador.class.getName()).log(Level.SEVERE, null, ex);
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
                defineTablaClientesAll("",1);
                Limpiar();
            }
        } catch (Exception ex) {
//            Logger.getLogger(FrManteComputador.class.getName()).log(Level.SEVERE, null, ex);
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
                defineTablaClientesAll("",1);
                Limpiar();
            }
        } catch (Exception ex) {
//            Logger.getLogger(FrManteComputador.class.getName()).log(Level.SEVERE, null, ex);
            Log.log(ex.getMessage());
            log.fatal(ex.getMessage());
        }
    }//GEN-LAST:event_btnEliminarPCActionPerformed

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
            defineTablaClientesAll("",lngValor);
        }
    }//GEN-LAST:event_cmdAtrasActionPerformed

    private void cmdSiguiente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSiguiente1ActionPerformed
        long lngValor=0;
        if(lngNumPaginas>Long.valueOf( this.txtPagina.getText())){
            lngValor=Long.valueOf( this.txtPagina.getText())+1;
            this.txtPagina.setText(String.valueOf(lngValor));
            defineTablaClientesAll("",lngValor);
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
//        int opcion=0;
//        do {
//            try {
//
//                opcion=Integer.parseInt(JOptionPane.showInputDialog(null,
//                    "1 Imprimir en Archivo en Excel\n"
//                    + "2 Imprimir en Archivo en PDF\n"
//                    + "3 Imprimir en Archivo en Word\n"
//                    //                    + "4 Imprimir en Archivo en Impresora\n"
//                    + "0 Salir"));
//
//            switch(opcion){
//                case 1:
//                imprimirExel();
//                break;
//                case 2:
//                imprimirPDF();
//                break;
//                case 3:
//                imprimirWord();
//                break;
//                //                case 4:
//                //                imprimirImpresora();
//                //                break;
//                case 0:
//                JOptionPane.showMessageDialog(null,"Programa finalizado");
//                break;
//                default:
//                JOptionPane.showMessageDialog(null, "Opcion incorreta");
//            }
//
//        } catch (Exception n) {
//            JOptionPane.showMessageDialog(null, "Error :"+n.getMessage());
//        }
//        } while (opcion!=0);
    }//GEN-LAST:event_btnImprimirReporteActionPerformed

    private void JTabPC2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTabPC2MouseClicked
        // TODO add your handling code here:
        int fila;
        this.txtIdPC.getText();
        String[] datosCliente =new String[10];
        fila = this.JTabPC2.rowAtPoint(evt.getPoint());
        //        DAOPc cliente = new DAOPc();
        long lngCliente;
        //
        if (fila > -1){
            try {
                this.txtIdPC.setText(String.valueOf(JTabPC2.getValueAt(fila, 0)));

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
                this.txtIdCliente.setText(datosCliente[13]);
                this.txtIdLogin.setText(datosCliente[14]);

                if(Long.valueOf( datosCliente[0])>0){
                    this.btnEliminarPC.setEnabled(true);
                    this.btnActualizarPC.setEnabled(true);
                    this.btnImprimirReporte.setEnabled(true);
                    //                    this.btnActualizarPC.setLabel("Actualizar");
                }
            } catch (Exception ex) {
//                Logger.getLogger(FrManteComputador.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage());
                Log.log(ex.getMessage());
                log.fatal(ex.getMessage());
            }
        }
    }//GEN-LAST:event_JTabPC2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTabPC2;
    private javax.swing.JButton btnActualizarPC;
    private javax.swing.JButton btnAgregarPC;
    private javax.swing.JButton btnBuscarAll;
    private javax.swing.JButton btnEliminarPC;
    private javax.swing.JButton btnImprimirReporte;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    public static javax.swing.JComboBox jcbDisco;
    public static javax.swing.JComboBox jcbRam;
    private javax.swing.JComboBox<String> jcbTipoRaparacion;
    private com.toedter.calendar.JDateChooser jdcFecha;
    private javax.swing.JLabel jlblNumReg;
    private javax.swing.JLabel jlblNumReg2;
    private javax.swing.JLabel jlblTotalPaginas;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JTextField txtArquitectura;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtHDD;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdLogin;
    private javax.swing.JTextField txtIdPC;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNumReg;
    private javax.swing.JTextField txtNumeroSerie;
    private javax.swing.JTextField txtOS;
    private javax.swing.JTextField txtPagina;
    private javax.swing.JTextField txtRam;
    private javax.swing.JTextField txtValor;
    private javax.swing.JTextField txtVersion;
    // End of variables declaration//GEN-END:variables

    private static java.util.List<cl.burgos.repararpc.service.ClComputador> listaPCAll(long intDesde, long intCuantos, java.lang.String strBusqueda) {
        cl.burgos.repararpc.service.WebPCService_Service service = new cl.burgos.repararpc.service.WebPCService_Service();
        cl.burgos.repararpc.service.WebPCService port = service.getWebPCServicePort();
        return port.listaPCAll(intDesde, intCuantos, strBusqueda);
    }
    
}
