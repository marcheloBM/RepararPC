<%-- 
    Document   : FormLogin
    Created on : 27-07-2018, 2:44:58
    Author     : march
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
    <%-- start web service invocation --%><hr/>
    <%
    try {
	Cl.Burgos.RepararPC.WSDL.WebLoginService_Service service = new Cl.Burgos.RepararPC.WSDL.WebLoginService_Service();
	Cl.Burgos.RepararPC.WSDL.WebLoginService port = service.getWebLoginServicePort();
	 // TODO initialize WS operation arguments here
	java.lang.String rut = request.getParameter("txtRut");
	java.lang.String pass = request.getParameter("txtPassword");
	// TODO process result here
	boolean result = port.validarLogin(rut, pass);
        Cl.Burgos.RepararPC.WSDL.ClLogin result2 = port.buscarRutLogin(rut, pass);
        if(result){
            //response.sendRedirect("/WebRepararPCWebService/Login.jsp");
            request.setAttribute("message", "Sesion iniciada");
            HttpSession se = request.getSession();
            se.setAttribute("sesion_rut", result2.getRut());
            se.setAttribute("session_objetLogin", result2);            
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }else{
            request.setAttribute("message", "RUT y/o Contrasena incorrecta.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
        
	//out.println("Result = "+result);
    } catch (Exception ex) {
	// TODO handle custom exceptions here
    }
    %>
    <%-- end web service invocation --%><hr/>

<html>
    <center>
        <form action="Login.jsp" method="post" >    
            <div class="colorgraph">
                <br>
                <font style="color: deepskyblue"  face="Lato" size="5">Login</font><br><br>
                <input class="form-horizontal" maxlength="10" name="txtRut" placeholder="RUT" required oninput="checkRut(this)" autofocus="" />
                <br> 
                <br>
                <input type="password" class="form-horizontal" name="txtPassword" placeholder="*****"
                       minlength="4" maxlength="40" required/><br><br>

                <br>
                <input class="btn btn-clear btn-sm btn-primary" type="submit" name="submit" value="" >
                ${mensaje}
            </div>

        </form>



    </center>
</html>
