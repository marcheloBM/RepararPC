/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.ENT;

import Cl.Burgos.RepararPC.Enum.TipoLogin;
import Cl.Burgos.RepararPC.EXP.ExpLogin;
import Cl.Burgos.RepararPC.FUN.Metodos;

/**
 *
 * @author march
 */
public class ClLogin {
    private int id; //Primary KEY
    private String rut; //Not Null
    private String nombre; //Not null
    private String apellido; //null
    private String correo; //nul
    private String celular; //null
    private String passworld; //Not null
    private TipoLogin tipoLogin; //Not Null

    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception {
        if(id<0){
            throw new ExpLogin(ExpLogin.ERR_Id);
        }else{
            this.id = id;
        }
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) throws Exception {
        if(rut.length()==0){
            throw new ExpLogin(ExpLogin.ERR_RutNull);
        }else{
            if(!Metodos.validarRut(Metodos.formatear(rut))){
                throw new ExpLogin(ExpLogin.ERR_Rut);
            }else{
                this.rut = Metodos.formatear(rut);
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws Exception {
        if(nombre.length()==0){
            throw new ExpLogin(ExpLogin.ERR_NombreNull);
        }else{
            if(nombre.length()<=3 || nombre.length()>=26){
                throw new ExpLogin(ExpLogin.ERR_Nombre);
            }else{
                this.nombre = nombre;
            }
        }
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) throws Exception {
        if(apellido.length()==0){
            this.apellido = apellido;
        }else{
            if(apellido.length()<=3 || apellido.length()>=26){
                throw new ExpLogin(ExpLogin.ERR_Apellido);
            }else{
                this.apellido = apellido;
            }
        }
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) throws Exception {
        if(correo.length()==0){
            this.correo = correo;
        }else{
            if(!Metodos.validateEmail(correo)){
                throw new ExpLogin(ExpLogin.ERR_Correo);
            }else{
                this.correo = correo;
            }
        }
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) throws Exception {
        if(celular.length()==0){
            this.celular = celular;
        }else{
            if(celular.length()<=8 || celular.length()>=10){
                throw new ExpLogin(ExpLogin.ERR_Celular);
            }else{
                this.celular = celular;
            }
        }
    }

    public String getPassworld() {
        return passworld;
    }

    public void setPassworld(String passworld) throws Exception {
        if(passworld.length()==0){
            throw new ExpLogin(ExpLogin.ERR_Passworld);
        }else{
            this.passworld = Metodos.encriptaEnMD5(passworld);
        }
    }

    public TipoLogin getTipoLogin() {
        return tipoLogin;
    }

    public void setTipoLogin(TipoLogin tipoLogin) {
        this.tipoLogin = tipoLogin;
    }
        
    //Validar
    public ClLogin(String rut, String passworld) throws Exception {
        setRut(rut);
        setPassworld(passworld);
    }
    //Agregar
    public ClLogin(String rut, String nombre, String apellido, String correo, String celular, String passworld, TipoLogin tipoLogin) throws Exception {
        setRut(rut);
        setNombre(nombre);
        setApellido(apellido);
        setCorreo(correo);
        setCelular(celular);
        setPassworld(passworld);
        setTipoLogin(tipoLogin);
    }
    //Listar y Actualizar
    public ClLogin(int id, String rut, String nombre, String apellido, String correo, String celular, String passworld, TipoLogin tipoLogin) throws Exception {
        setId(id);
        setRut(rut);
        setNombre(nombre);
        setApellido(apellido);
        setCorreo(correo);
        setCelular(celular);
        setPassworld(passworld);
        setTipoLogin(tipoLogin);
    }
    //Buscar y Eliminar

    public ClLogin(int id) throws Exception {
        setId(id);
    }

    public ClLogin(String rut) throws Exception {
        setRut(rut);
    }
    
    public ClLogin() {
    }  
}
