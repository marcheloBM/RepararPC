/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.Controlador;

import Cl.Burgos.RepararPC.DAO.DAOComputador;
import Cl.Burgos.RepararPC.ENT.ClComputador;
import Cl.Burgos.RepararPC.Enum.TipoReparacion;
import Cl.Burgos.RepararPC.FUN.ComandosCMD;
import Cl.Burgos.RepararPC.FUN.FormatoFecha;
import Cl.Burgos.RepararPC.Inter.Confi;
import Cl.Burgos.RepararPC.Log4j.Log;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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
@WebServlet(name = "ServletPC", urlPatterns = {"/ServletPC"})
public class ServletPC extends HttpServlet {

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
            case "REGISTRARPC":
                System.out.println("Servlet_PC:Switch.PCIngresar");
                data = PcIngresar(request, response);
                break;
            case "ACTUALIZARPC":
                System.out.println("Servlet_PC:Switch.PCActualizar");
                data = PcActualizar(request, response);
                break;
            case "ELIMINARPC":
                System.out.println("Servlet_PC:Switch.PCEliminar");
                data = PcEliminar(request, response);
                break;
            case "CONSULTARPC":
                System.out.println("Servlet_PC:Switch.PCConsultar");
                PcConsultar(request, response);
                break;
            default:
                response.sendRedirect("HomeLogin.jsp");
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

    private Map<String, String> PcIngresar(HttpServletRequest request, HttpServletResponse response) {
        //inicializo sesion
        HttpSession session = request.getSession();
        Map<String, String> options = new LinkedHashMap<>();
        try {
            //declaro estado y mensaje
            String status, message;
            //cargar datos en la clase
            ClComputador clComputador = rescataParamInPC(request,response);
            //Crear DAO
            DAOComputador dAOComputador = new DAOComputador();
                if(!dAOComputador.sqlInsert(clComputador)){
                    options.put("message", "[ERROR] No se pudo registrar el Pc.");
                }else{
//                    options.put("url", "/Login.jsp");
                    options.put("message", "[OK] PC registrado exitosamente.");
//                    request.getRequestDispatcher("/Login.jsp").forward(request, response);
                }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            options.put("message", ex.getMessage());
        }
        options.put("url", "/HomeLogin.jsp");
        return options;
    }

    private ClComputador rescataParamInPC(HttpServletRequest request, HttpServletResponse response) throws ParseException, Exception {
        String stMarca=request.getParameter("txtMarca");
        String stModelo=request.getParameter("txtModelo");
        String stNumSerie=request.getParameter("txtNumeroSerie");
        String stRam=request.getParameter("txtRam");
        String stCapRam=request.getParameter("cbo_capacidad_ram");
        
        String stRam1 = stRam+" "+stCapRam;
        
        String stHdd=request.getParameter("txtHdd");
        String stCapHdd=request.getParameter("cbo_capcidad_hdd");
        
        String stHdd1=stHdd+" "+stCapHdd;
        
        String stSistemaOpe=request.getParameter("txtSO");
        String stArquitectura=request.getParameter("txtArqui");
        String stVersion=request.getParameter("txtVersion");
        TipoReparacion stTipoReparacion= TipoReparacion.valueOf(request.getParameter("cbo_tipo_rapracion"));
        String stDescripcion=request.getParameter("txtDescripcion");
        int stValor=Integer.parseInt(request.getParameter("txtValor"));
        
        java.util.Date dd = FormatoFecha.mostrarFechaYMD(request.getParameter("txtFecha"));
        java.sql.Timestamp d = new java.sql.Timestamp(dd.getTime());
        
        int stIdCliente=Integer.parseInt(request.getParameter("txtIdCliente"));
        int stIdLogin=Integer.parseInt(request.getParameter("txtIdLogin"));
        ClComputador clComputador=new ClComputador(stMarca, stModelo, stNumSerie, stRam1, stHdd1, 
                stSistemaOpe, stArquitectura, stVersion, stTipoReparacion, stDescripcion, stValor, 
                d, stIdCliente, stIdLogin);
        return clComputador;
    }

    private Map<String, String> PcActualizar(HttpServletRequest request, HttpServletResponse response) {
        //inicializo sesion
        HttpSession session = request.getSession();
        Map<String, String> options = new LinkedHashMap<>();
        try {
            //declaro estado y mensaje
            String status, message;
            //cargar datos en la clase
            ClComputador clComputador = rescataParamAcPC(request,response);
            //Crear DAO
            DAOComputador dAOComputador = new DAOComputador();
                if(!dAOComputador.sqlUpdate(clComputador)){
                    options.put("message", "[ERROR] No se pudo actualizar el Pc.");
                }else{
//                    options.put("url", "/Login.jsp");
                    options.put("message", "[OK] PC Actualizado exitosamente.");
//                    request.getRequestDispatcher("/Login.jsp").forward(request, response);
                }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            options.put("message", ex.getMessage());
        }
        options.put("url", "/HomeLogin.jsp");
        return options;
    }

    private ClComputador rescataParamAcPC(HttpServletRequest request, HttpServletResponse response) throws ParseException, Exception {
        int stIdPc=Integer.parseInt(request.getParameter("txtIdPc"));
        String stMarca=request.getParameter("txtMarca");
        String stModelo=request.getParameter("txtModelo");
        String stNumSerie=request.getParameter("txtNumSerie");
        String stRam=request.getParameter("txtRam");
        String stHdd=request.getParameter("txtHdd");
        String stSistemaOpe=request.getParameter("txtSO");
        String stArquitectura=request.getParameter("txtArqui");
        String stVersion=request.getParameter("txtVersion");
        TipoReparacion stTipoReparacion= TipoReparacion.valueOf(request.getParameter("txtTipoRep"));
        String stDescripcion=request.getParameter("txtDescripcion");
        int stValor=Integer.parseInt(request.getParameter("txtValor"));
        
        java.util.Date dd = FormatoFecha.mostrarFechaYMD(request.getParameter("txtFecha"));
        java.sql.Timestamp d = new java.sql.Timestamp(dd.getTime());
        
        int stIdCliente=Integer.parseInt(request.getParameter("txtIdCliente"));
        int stIdLogin=Integer.parseInt(request.getParameter("txtIdLogin"));
        ClComputador clComputador=new ClComputador(stIdPc, stMarca, stModelo, stNumSerie, stRam, stHdd, 
                stSistemaOpe, stArquitectura, stVersion, stTipoReparacion, stDescripcion, stValor, 
                d, stIdCliente, stIdLogin);
        return clComputador;
    }

    private Map<String, String> PcEliminar(HttpServletRequest request, HttpServletResponse response) {
        //inicializo sesion
        HttpSession session = request.getSession();
        Map<String, String> options = new LinkedHashMap<>();
        try {
            //declaro estado y mensaje
            String status, message;
            //cargar datos en la clase
            ClComputador clComputador = rescataParamElPC(request,response);
            //Crear DAO
            DAOComputador dAOComputador = new DAOComputador();
                if(!dAOComputador.sqlDelete(clComputador)){
                    options.put("message", "[ERROR] No se pudo eliminar el Pc.");
                }else{
//                    options.put("url", "/Login.jsp");
                    options.put("message", "[OK] PC Eliminado exitosamente.");
//                    request.getRequestDispatcher("/Login.jsp").forward(request, response);
                }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            options.put("message", ex.getMessage());
        }
        options.put("url", "/HomeLogin.jsp");
        return options;
    }

    private ClComputador rescataParamElPC(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int stIdPc=Integer.parseInt(request.getParameter("txtIdPc"));
        ClComputador clComputador=new ClComputador(stIdPc);
        return clComputador;
    }

    private void PcConsultar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String so = Confi.SO;
        String marca= "cmd /c wmic computersystem get name";
        String ma = ComandosCMD.cmd(marca);
        ma = ma.replaceAll("\\s*$","");
        
        String modelo= "cmd /c wmic csproduct get name";
        String mo = ComandosCMD.cmd(modelo);
        mo = mo.replaceAll("\\s*$","");
        
        String numSerie = "cmd /c wmic bios get serialnumber";
        String ns = ComandosCMD.cmd(numSerie);
        ns = ns.replaceAll("\\s*$","");
        
        String fi = fechaInstalacion();
        Date d=null;
        try {
            d = FormatoFecha.mostrarFechaDMYHMS(fi);
        } catch (ParseException ex) {
//                Logger.getLogger(FrHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String arq = System.getProperty("os.arch");
        String versionSO = System.getProperty("os.version");
        
        request.setAttribute("marca", ma);
        request.setAttribute("modelo", mo);
        request.setAttribute("numSerie", ns);
        request.setAttribute("fechaIns", d);
        request.setAttribute("OS", so);
        request.setAttribute("arqui", arq);
        request.setAttribute("vercion", versionSO);
        request.getRequestDispatcher("/HomeLogin.jsp").forward(request, response);
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

}
