/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.ENT;

import Cl.Burgos.RepararPC.EXP.ExpComputador;
//import Cl.Burgos.RepararPC.Enum.TipoReparacion;
import Cl.Burgos.RepararPC.FUN.Metodos;
import java.util.Date;

/**
 *
 * @author march
 */
public class ClComputador {
    private int idPC;
    private String marca;
    private String modelo;
    private String numSerie;
    private String sistemaOpe;
    private String arquitectura;
    private String tipoRepa;
    private String descripcion;
    private int valor;
    private Date fecha;
    
    private byte[] archivo;
    private String ruta;
    private String extArchivo;
    
    private int idCliente;
    private int idLogin;

    public int getIdPC() {
        return idPC;
    }

    public void setIdPC(int idPC) throws Exception {
        if(idPC<=0){
            throw new ExpComputador(ExpComputador.ERR_idPc);
        }else{
            this.idPC = idPC;
        }
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) throws Exception {
        if(marca.length()<=1 || marca.length()>=46){
            throw new ExpComputador(ExpComputador.ERR_marca);
        }else{
            this.marca = marca;
        }
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) throws Exception {
        if(modelo.length()<=3 || modelo.length()>=46){
            throw new ExpComputador(ExpComputador.ERR_modelo);
        }else{
            this.modelo = modelo;
        }
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) throws Exception {
        if(numSerie.length()<=3 || numSerie.length()>=46){
            throw new ExpComputador(ExpComputador.ERR_numSerie);
        }else{
            this.numSerie = numSerie;
        }
    }

    public String getSistemaOpe() {
        return sistemaOpe;
    }

    public void setSistemaOpe(String sistemaOpe) throws Exception {
        if(sistemaOpe.length()<=3 || sistemaOpe.length()>=26){
            throw new ExpComputador(ExpComputador.ERR_SO);
        }else{
            this.sistemaOpe = sistemaOpe;
        }
    }

    public String getArquitectura() {
        return arquitectura;
    }

    public void setArquitectura(String arquitectura) {
        if(arquitectura.length()<=3 || arquitectura.length()>=26){
            this.arquitectura = arquitectura;
        }else{
            this.arquitectura = arquitectura;
        }
    }

    public String getTipoRepa() {
        return tipoRepa;
    }

    public void setTipoRepa(String tipoRepa) {
        this.tipoRepa = tipoRepa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) throws Exception {
        if(descripcion.length()<=3 || descripcion.length()>=201){
            throw new ExpComputador(ExpComputador.ERR_descripcion);
        }else{
            this.descripcion = descripcion;
        }
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) throws Exception {
        if(valor>=100000){
            throw new ExpComputador(ExpComputador.ERR_Valor);
        }else{
            this.valor = valor;
        }
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) throws Exception {
        if(Metodos.validaFecha(fecha)){
            throw new ExpComputador(ExpComputador.ERR_fecha);
        }else{
            this.fecha = fecha;
        }
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }

    //Insertar
    public ClComputador(String marca, String modelo, String numSerie, String sistemaOpe, String arquitectura, String tipoRepa, String descripcion, int valor, Date fecha, int idCliente, int idLogin) throws Exception {
        setMarca(marca);
        setModelo(modelo);
        setNumSerie(numSerie);
        setSistemaOpe(sistemaOpe);
        setArquitectura(arquitectura);
        setTipoRepa(tipoRepa);
        setDescripcion(descripcion);
        setValor(valor);
        setFecha(fecha);
        setIdCliente(idCliente);
        setIdLogin(idLogin);
    }

    //Buscar
    public ClComputador(int idPC) throws Exception {
        setIdPC(idPC);
    }
    
    //Actualizar
    public ClComputador(int idPC, String marca, String modelo, String numSerie, String sistemaOpe, String arquitectura, String tipoRepa, String descripcion, int valor, Date fecha, int idCliente, int idLogin) throws Exception {
        setIdPC(idPC);
        setMarca(marca);
        setModelo(modelo);
        setNumSerie(numSerie);
        setSistemaOpe(sistemaOpe);
        setArquitectura(arquitectura);
        setTipoRepa(tipoRepa);
        setDescripcion(descripcion);
        setValor(valor);
        setFecha(fecha);
        setIdCliente(idCliente);
        setIdLogin(idLogin);
    }

    //Imprimir

    public ClComputador(String marca, String modelo, String numSerie, String sistemaOpe, String arquitectura, Date fecha) throws Exception {
        setMarca(marca);
        setModelo(modelo);
        setNumSerie(numSerie);
        setSistemaOpe(sistemaOpe);
        setArquitectura(arquitectura);
        setFecha(fecha);
    }

    @Override
    public String toString() {
        return marca + ";" + modelo + ";" + numSerie + ";" + sistemaOpe + ";" + arquitectura + ";" + fecha;
    }
    
    
    
}
