<%-- 
    Document   : FormLogin
    Created on : 27-07-2018, 2:44:58
    Author     : march
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <center>
        <form action="ServletLogin" method="post" >    
            <div class="colorgraph">
                <br>
                <font style="color: deepskyblue"  face="Lato" size="5">Login</font><br><br>
                <input class="form-horizontal" maxlength="10" name="txtRut" placeholder="RUT" required oninput="checkRut(this)" autofocus="" />
                <br> 
                <br>
                <input type="password" class="form-horizontal" name="txtPassword" placeholder="*****"
                       minlength="4" maxlength="40" required/><br><br>

                <br>
                <input class="btn btn-clear btn-sm btn-primary" type="submit" name="submit" value="INGRESARLOGIN" >
                ${mensaje}
            </div>

        </form>



    </center>
</html>
