/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.GUI;

import Cl.Burgos.RepararPC.Log4j.Log;
import Cl.Burgos.RepararPC.DAO.DAOLogin;
import Cl.Burgos.RepararPC.ENT.ClLogin;
import Cl.Burgos.RepararPC.Enum.TipoLogin;
import Cl.Burgos.RepararPC.GUI.Mant.FrMantenedor;
import Cl.Burgos.RepararPC.Inter.Confi;
import Cl.Burgos.RepararPC.WSDL.Exception_Exception;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
 *
 * @author march
 */
public class FrLogin extends javax.swing.JFrame {

    //Variables del Log4j
    static  Logger log =Logger.getLogger(FrLogin.class);
    
    static ClLogin clLogin = null;
    
    DAOLogin dAOLogin=new DAOLogin();
    /**
     * Creates new form FrLogin
     */
    public FrLogin() {
        initComponents();
        
        txtRut.setText(Confi.loginUsep);
        txtPass.setText(Confi.loginPasp);
        
        jPanel1.setOpaque(false);
        
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

    public ClLogin Validar() throws Exception{
        Cl.Burgos.RepararPC.WSDL.ClLogin l = buscarRutLogin(txtRut.getText(), String.valueOf(txtPass.getPassword()));
        int id=l.getId();
        String rut = l.getRut();
        String nombre = l.getNombre();
        String apellido = l.getApellido();
        String correo = l.getCorreo();
        String celular = l.getCelular();
        String pass = l.getPassworld();
        String tipoLogin =l.getTipoLogin().toString();
        TipoLogin tl = null;
        if(tipoLogin.equals("USUARIO")){
            tl=TipoLogin.Usuario;
        }else if(tipoLogin.equals("ADMINISTRADOR")){
            tl=TipoLogin.Administrador;
        }
        clLogin = new ClLogin(id, rut, nombre, apellido, correo, celular, pass, tl);
        return clLogin;
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
        imgLogin = new javax.swing.JLabel();
        lblRut = new javax.swing.JLabel();
        lblPassworld = new javax.swing.JLabel();
        txtRut = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        btnIniciar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        imgLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cl/Burgos/RepararPC/IMG/Login.png"))); // NOI18N

        lblRut.setText("RUT:");

        lblPassworld.setText("Password:");

        txtRut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRutKeyPressed(evt);
            }
        });

        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(imgLogin)
                .addGap(78, 78, 78))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnIniciar)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalir)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblRut)
                            .addComponent(lblPassworld))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPass)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(61, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgLogin)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRut)
                    .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassworld)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIniciar)
                    .addComponent(btnSalir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void Mantenedor(){
        FrMantenedor frMantenedor= new FrMantenedor();
        frMantenedor.setVisible(true);
    }
    public void Home(){
        FrHome home = new FrHome();
        home.setVisible(true);
    }
    public void ConsultaS(){
        FrConsultaS consultaS=new FrConsultaS();
        consultaS.setVisible(true);
    }
    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        try {
//            clLogin = Validar();
            boolean resp = validarLogin(txtRut.getText(), String.valueOf(txtPass.getPassword()));
//            if(!dAOLogin.sqlSelectValidar(clLogin)){
            if(!resp){
                JOptionPane.showMessageDialog(null, "Error Usuario no encontrado");
                JOptionPane.showMessageDialog(null, "Solicitar ayuda Al Administrador");
                ConsultaS();
            }else{
                Validar();
                JOptionPane.showMessageDialog(null, "Usuario Encontrado");
                String nomCompleto=clLogin.getNombre()+" "+clLogin.getApellido();
                JOptionPane.showMessageDialog(null, "Usuario: "+nomCompleto);
                
                if(clLogin.getTipoLogin().equals(TipoLogin.Administrador)){
                    Mantenedor();
                    this.dispose();
                }
                else{
                    Home();
                    this.dispose();
                }
            }
        } catch (Exception ex) {
//            Logger.getLogger(FrLogin.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Log.log(ex.getMessage());
            log.fatal(ex.getMessage());
        }
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtRutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutKeyPressed
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if(!Character.isDigit(c) && c != KeyEvent.VK_PERIOD && c != KeyEvent.VK_MINUS && c != KeyEvent.VK_BACK_SPACE){
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo Numeros, Puntos y Guion");
            txtRut.setCursor(null);
        }
    }//GEN-LAST:event_txtRutKeyPressed

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
            java.util.logging.Logger.getLogger(FrLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel imgLogin;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblPassworld;
    private javax.swing.JLabel lblRut;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtRut;
    // End of variables declaration//GEN-END:variables


    private static boolean validarLogin(java.lang.String rut, java.lang.String pass) throws Exception_Exception {
        Cl.Burgos.RepararPC.WSDL.WebLoginService_Service service = new Cl.Burgos.RepararPC.WSDL.WebLoginService_Service();
        Cl.Burgos.RepararPC.WSDL.WebLoginService port = service.getWebLoginServicePort();
        return port.validarLogin(rut, pass);
    }

    private static Cl.Burgos.RepararPC.WSDL.ClLogin buscarRutLogin(java.lang.String rut, java.lang.String pass) throws Exception_Exception {
        Cl.Burgos.RepararPC.WSDL.WebLoginService_Service service = new Cl.Burgos.RepararPC.WSDL.WebLoginService_Service();
        Cl.Burgos.RepararPC.WSDL.WebLoginService port = service.getWebLoginServicePort();
        return port.buscarRutLogin(rut, pass);
    }

}
