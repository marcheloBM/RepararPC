/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.Controlador;

import Cl.Burgos.RepararPC.DAO.DAOCliente;
import Cl.Burgos.RepararPC.ENT.ClCliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author march
 */
@WebServlet(name = "ServletCliente", urlPatterns = {"/ServletCliente"})
public class ServletCliente extends HttpServlet {

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
            case "REGISTRARCLIENTE":
                System.out.println("Servlet_Cliente:Switch.ClienteIngresar");
                data = ClienteIngresar(request, response);
                break;
            case "ACTUALIZARCLIENTE":
                System.out.println("Servlet_Cliente:Switch.ClienteActualizar");
                data = ClienteActualizar(request, response);
                break;
            case "BUSCARCLIENTE":
                System.out.println("Servlet_Cliente:Switch.ClienteBuscar");
                data = ClienteBuscar(request,response);
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

    private Map<String, String> ClienteIngresar(HttpServletRequest request, HttpServletResponse response) {
        //inicializo sesion
        HttpSession session = request.getSession();
        Map<String, String> options = new LinkedHashMap<>();
        try {
            //declaro estado y mensaje
            String status, message;
            //cargar datos en la clase
            ClCliente clCliente = rescataParamInCliente(request,response);
            //Crear DAO
            DAOCliente dAOCliente = new DAOCliente();
                if(!dAOCliente.sqlInsert(clCliente)){
                    options.put("message", "[ERROR] No se pudo registrar el cliente.");
                }else{
//                    options.put("url", "/Login.jsp");
                    options.put("message", "[OK] Cliente registrado exitosamente.");
//                    request.getRequestDispatcher("/Login.jsp").forward(request, response);
                }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            options.put("message", ex.getMessage());
        }
        options.put("url", "/HomeCliente.jsp");
        return options;
    }

    private ClCliente rescataParamInCliente(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stRut=request.getParameter("txtRut");
        String stNombre=request.getParameter("txtNombre");
        String stApellido=request.getParameter("txtApellido");
        String stCorreo=request.getParameter("txtCorreo");
        String stCelular=request.getParameter("txtCelular");
        int stIdLogin=Integer.parseInt(request.getParameter("txtIdLogin"));
        ClCliente clCliente = new ClCliente(stRut, stNombre, stApellido, stCorreo, stCelular, stIdLogin);
        return clCliente;
    }

    private Map<String, String> ClienteActualizar(HttpServletRequest request, HttpServletResponse response) {
        //inicializo sesion
        HttpSession session = request.getSession();
        Map<String, String> options = new LinkedHashMap<>();
        //declaro estado y mensaje
        String status, message;
        try {
            //cargar datos en la clase
            ClCliente clCliente = rescataParamAcCliente(request,response);
            //Crear DAO
            DAOCliente dAOCliente = new DAOCliente();
            if(!dAOCliente.sqlUpdate(clCliente)){
                options.put("message", "[ERROR] No se pudo actualizar el cliente.");
            }else{
                options.put("message", "[OK] Cliente actualizado exitosamente.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            options.put("message", ex.getMessage());
        }
        options.put("url", "/HomeCliente.jsp");
        return options;
    }

    private ClCliente rescataParamAcCliente(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int stId=Integer.parseInt(request.getParameter("txtId"));
        String stRut=request.getParameter("txtRut");
        String stNombre=request.getParameter("txtNombre");
        String stApellido=request.getParameter("txtApellido");
        String stCorreo=request.getParameter("txtCorreo");
        String stCelular=request.getParameter("txtCelular");
        int stIdLogin=Integer.parseInt(request.getParameter("txtIdLogin"));
        ClCliente clCliente = new ClCliente(stId, stRut, stNombre, stApellido, stCorreo, stCelular, stIdLogin);
        return clCliente;
    }

    private Map<String, String> ClienteBuscar(HttpServletRequest request, HttpServletResponse response) {
        //inicializo sesion
        HttpSession session = request.getSession();
        Map<String, String> options = new LinkedHashMap<>();
        //declaro estado y mensaje
        String status, message;
        status = "";
        message = "";
        try {
            //cargar datos en la clase
            ClCliente clCliente = rescataBuParamLogin(request,response);
            //Crear DAO
            DAOCliente dAOCliente = new DAOCliente();
            if(dAOCliente.sqlSearchRut(clCliente)){
                status = "ok";
                message = "Sesion iniciada";
                session.setAttribute("sesion_rutCliente", clCliente.getRut());
                session.setAttribute("session_objetCliente", clCliente);
                options.put("status", status);
                options.put("message", message);
            }
        } catch (Exception ex) {
//            Logger.getLogger(ServletLogin.class.getName()).log(Level.SEVERE, null, ex);
//            Log.log(ex.getMessage());
//            log.warn(ex.getMessage());
            status = "error";
            message = "RUT y/o Contrasena incorrecta.";
            options.put("status", status);
            options.put("message", message);
        }
        options.put("url", "/Login.jsp");
        return options;
    }

    private ClCliente rescataBuParamLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stRut=request.getParameter("cbo_tipo_cliente");
        ClCliente clCliente = new ClCliente(stRut);
        return clCliente;
    }

    

}
