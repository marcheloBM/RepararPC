/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.Controlador;

import Cl.Burgos.RepararPC.Log4j.Log;
import Cl.Burgos.RepararPC.DAO.DAOLogin;
import Cl.Burgos.RepararPC.ENT.ClLogin;
import Cl.Burgos.RepararPC.Enum.TipoLogin;
import Cl.Burgos.RepararPC.FUN.Metodos;
import Cl.Burgos.RepararPC.WSDL.Exception_Exception;
import Cl.Burgos.RepararPC.WSDL.WebLoginService_Service;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;
//import org.jboss.logging.Logger;

/**
 *
 * @author march
 */
@WebServlet(name = "ServletLogin", urlPatterns = {"/ServletLogin"})
public class ServletLogin extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/WebServiceRepararPC/WebLoginService.wsdl")
    private WebLoginService_Service service;

    //Variables del Log4j
//    static  Logger log =Logger.getLogger(ServletLogin.class);
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //obtengo el submit
        String submit = request.getParameter("submit");
        submit = (submit == null) ? "" : submit;
        
        Map<String, String> data = new HashMap<>();
        //pregunto que tipo de submit es
        switch (submit) {
            case "REGISTRARLOGIN":
                System.out.println("Servlet_Login:Switch.LoginRegistrar");
//                data = LoginRegistrar(request, response);
                break;
            case "INGRESARLOGIN":
                System.out.println("Servlet_Login:Switch.LoginIngresar");
//                data = LoginIngresar(request, response);
                break;
            case "ACTUALIZARLOGIN":
                System.out.println("Servlet_Login:Switch.LoginActualizar");
                data = LoginActualizar(request, response);
                break;
            default:
                
                response.sendRedirect("RegAdmi.jsp");
                return;
        }
        //cargo datos
        request.setAttribute("status", data.get("status"));
        request.setAttribute("message", data.get("message"));
        //devuelvo
        request.getRequestDispatcher(data.get("url")).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private Map<String, String> LoginRegistrar(HttpServletRequest request, HttpServletResponse response) {
        //inicializo sesion
        HttpSession session = request.getSession();
        Map<String, String> options = new LinkedHashMap<>();
        try {
            //declaro estado y mensaje
            String status, message;
            
            //cargar datos en la clase
            ClLogin clLogin = rescataParamInLogin(request,response);
            
            //Crear DAO
            DAOLogin dAOLogin = new DAOLogin();
            if(!dAOLogin.sqlSearchRut(clLogin)){
                if(!dAOLogin.sqlInsert(clLogin)){
                    options.put("message", "[ERROR] No se pudo registrar el login.");
                }else{
//                    options.put("url", "/Login.jsp");
                    options.put("message", "[OK] Login registrado exitosamente.");
//                    request.getRequestDispatcher("/Login.jsp").forward(request, response);
                }
            }else{
                options.put("message", "[ERROR] Usuario ya Registrado");
            }
            
        } //nose que es esto
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            options.put("message", e.getMessage());
        }
        options.put("url", "/RegAdmi.jsp");
        return options;
         
    }

    private ClLogin rescataParamInLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //obtengo los datos del formulario
        String stRut=request.getParameter("txtRut");
        String stNombre=request.getParameter("txtNombre");
        String stApellido=request.getParameter("txtApellido");
        String stCorreo=request.getParameter("txtCorreo");
        String stCelular=request.getParameter("txtCelular");
        String stPassworld=request.getParameter("txtPassword");
        TipoLogin tl = TipoLogin.valueOf(request.getParameter("txtTipoLogin")) ;
        ClLogin clLogin= new ClLogin(stRut, stNombre, stApellido, stCorreo, stCelular, stPassworld, tl);
        return clLogin;
    }

    private Map<String, String> LoginIngresar(HttpServletRequest request, HttpServletResponse response) {
        //inicializo sesion
        HttpSession session = request.getSession();
        Map<String, String> options = new LinkedHashMap<>();
        //declaro estado y mensaje
        String status, message;
        status = "";
        message = "";
//        //obtengo los datos de los text del login
//        String email = request.getParameter("txtRut");
//        String contrasena = request.getParameter("txtPassword");
//        System.out.println("extraccion correcta" + email + contrasena);
//        //creo un objeto usuario solo con los datos rut y contraseña
        try {
            //cargar datos en la clase
            ClLogin clLogin = rescataParamLogin(request,response);
            //Crear DAO
            DAOLogin dAOLogin = new DAOLogin();
            String stRut=request.getParameter("txtRut");
            String stPassworld=request.getParameter("txtPass");
            boolean resp = validarLogin(stRut,stPassworld);
//            if(dAOLogin.sqlSelectValidar(clLogin)){
            if(resp){
                status = "ok";
                message = "Sesion iniciada";
                session.setAttribute("sesion_rut", clLogin.getRut());
                session.setAttribute("session_objetLogin", clLogin);
                options.put("status", status);
                options.put("message", message);
            }
        } catch (Exception ex) {
//            Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
//            Log.log(ex.getMessage());
//            log.warn(ex.getMessage());
            status = "error "+ex;
            message = "RUT y/o Contrasena incorrecta.";
            options.put("status", status);
            options.put("message", message);
        }
        //cargo los datos a option y lo retorno
//        options.put("status", status);
//        options.put("message", message);
        options.put("url", "/Login.jsp");
        return options;
        
    }

    private ClLogin rescataParamLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stRut=request.getParameter("txtRut");
        String stPassworld=request.getParameter("txtPassword");
        ClLogin clLogin= new ClLogin(stRut, stPassworld);
        return clLogin;
    }

    private Map<String, String> LoginActualizar(HttpServletRequest request, HttpServletResponse response) {
        //inicializo sesion
        HttpSession session = request.getSession();
        Map<String, String> options = new LinkedHashMap<>();
        try {
            //declaro estado y mensaje
            String status, message;
            
            //cargar datos en la clase
            ClLogin clLogin = rescataParamAcLogin(request,response);
            
            //Crear DAO
            DAOLogin dAOLogin = new DAOLogin();
            if(!dAOLogin.sqlUpdate(clLogin)){
                options.put("message", "[ERROR] No se pudo actualizar el login.");
            }else{
                options.put("message", "[OK] Login actualizado exitosamente.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            options.put("message", e.getMessage());
        }
        options.put("url", "/RegAdmi.jsp");
        return options;
    }

    private ClLogin rescataParamAcLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int stId=Integer.parseInt(request.getParameter("txtIdLogin"));
        String stRut=request.getParameter("txtRut");
        String stNombre=request.getParameter("txtNombre");
        String stApellido=request.getParameter("txtApellido");
        String stCorreo=request.getParameter("txtCorreo");
        String stCelular=request.getParameter("txtCelular");
        String stPassworld=request.getParameter("txtPass");
        TipoLogin tl = TipoLogin.valueOf(request.getParameter("txtTipoUsuario")) ;
        ClLogin clLogin= new ClLogin(stId, stRut, stNombre, stApellido, stCorreo, stCelular, stPassworld, tl);
        return clLogin;
    }

    private boolean validarLogin(java.lang.String rut, java.lang.String pass) throws Exception_Exception {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        Cl.Burgos.RepararPC.WSDL.WebLoginService port = service.getWebLoginServicePort();
        return port.validarLogin(rut, pass);
    }

}
