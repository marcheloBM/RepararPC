/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.ENT;

import Cl.Burgos.RepararPC.EXP.ExpCliente;
import Cl.Burgos.RepararPC.FUN.Metodos;

/**
 *
 * @author march
 */
public class ClCliente {
    private int id;
    private String rut;
    private String nombre;
    private String apellido;
    private String correo;
    private String celular;
//    private ClLogin login;
    private int idLogin;

    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception {
        if(id<0){
            throw new ExpCliente(ExpCliente.ERR_IdCliente);
        }else{
            this.id = id;
        }
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) throws Exception {
        if(rut.length()==0){
            throw new ExpCliente(ExpCliente.ERR_RutNull);
        }else{
            if(!Metodos.validarRut(Metodos.formatear(rut))){
                throw new ExpCliente(ExpCliente.ERR_Rut);
            }else{
                this.rut=Metodos.formatear(rut);
            }
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws Exception {
        if(nombre.length()==0){
            throw new ExpCliente(ExpCliente.ERR_NombreNull);
        }else{
            if(nombre.length()<=3 || nombre.length()>=26){
                throw new ExpCliente(ExpCliente.ERR_Nombre);
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
                throw new ExpCliente(ExpCliente.ERR_Apellido);
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
                throw new ExpCliente(ExpCliente.ERR_Correo);
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
                throw new ExpCliente(ExpCliente.ERR_Celular);
            }else{
                this.celular = celular;
            }
        }
    }

//    public ClLogin getLogin() {
//        return login;
//    }
//
//    public void setLogin(ClLogin login) throws Exception {
//        if(login==null){
//            throw new ExpCliente(ExpCliente.ERR_IdLogin);
//        }else{
//            this.login = login;
//        }
//    }

    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) throws Exception {
        if(idLogin==0){
            throw new ExpCliente(ExpCliente.ERR_IdLogin);
        }else{
            this.idLogin = idLogin;
        }
    }

    //Insertar
    public ClCliente(String rut, String nombre, String apellido, String correo, String celular, int idLogin) throws Exception {
        setRut(rut);
        setNombre(nombre);
        setApellido(apellido);
        setCorreo(correo);
        setCelular(celular);
        setIdLogin(idLogin);
    }

    //Lista
    public ClCliente(int id, String rut, String nombre, String apellido, String correo, String celular, int idLogin) throws Exception {
        setId(id);
        setRut(rut);
        setNombre(nombre);
        setApellido(apellido);
        setCorreo(correo);
        setCelular(celular);
        setIdLogin(idLogin);
    }
    //Buscar

    public ClCliente(int id) throws Exception {
        setId(id);
    }
    
    public ClCliente(String rut) throws Exception {
        setRut(rut);
    }
    
    //Imprimir
    public ClCliente(String rut, String nombre, String apellido, String correo, String celular) throws Exception {
        setRut(rut);
        setNombre(nombre);
        setApellido(apellido);
        setCorreo(correo);
        setCelular(celular);
    }

    public ClCliente(int id, String rut, String nombre, String apellido, String correo, String celular) throws Exception {
        setId(id);
        setRut(rut);
        setNombre(nombre);
        setApellido(apellido);
        setCorreo(correo);
        setCelular(celular);
    }
    
    @Override
    public String toString() {
        return rut + ";" + nombre + ";" + apellido + ";" + correo + ";" + celular;
    }

    public ClCliente() {
    }
    
}
