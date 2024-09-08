/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.ENT;

import Cl.Burgos.RepararPC.EXP.ExpComputador;
import Cl.Burgos.RepararPC.Enum.TipoReparacion;
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
    private String ram;
    private String hdd;
    private String sistemaOpe;
    private String arquitectura;
    private String version;
    private TipoReparacion tipoRepa;
    private String descripcion;
    private int valor;
    private Date fecha;
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
        if(marca.length()<=3 || marca.length()>=46){
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

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) throws Exception {
        if(ram.length()<=3 || ram.length()>=26){
            throw new ExpComputador(ExpComputador.ERR_ram);
        }else{
            this.ram = ram;
        }
    }

    public String getHdd() {
        return hdd;
    }

    public void setHdd(String hdd) throws Exception {
        if(hdd.length()<=3 || hdd.length()>=26){
            throw new ExpComputador(ExpComputador.ERR_hdd);
        }else{
            this.hdd = hdd;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) throws Exception {
        if(version.length()<=1 || version.length()>=26){
            throw new ExpComputador(ExpComputador.ERR_Version);
        }else{
            this.version = version;
        }
    }

    public TipoReparacion getTipoRepa() {
        return tipoRepa;
    }

    public void setTipoRepa(TipoReparacion tipoRepa) {
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
    public ClComputador(String marca, String modelo, String numSerie, String ram, String hdd, String sistemaOpe, String arquitectura, String version, TipoReparacion tipoRepa, String descripcion, int valor, Date fecha, int idCliente, int idLogin) throws Exception {
        setMarca(marca);
        setModelo(modelo);
        setNumSerie(numSerie);
        setRam(ram);
        setHdd(hdd);
        setSistemaOpe(sistemaOpe);
        setArquitectura(arquitectura);
        setVersion(version);
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
    public ClComputador(int idPC, String marca, String modelo, String numSerie, String ram, String hdd, String sistemaOpe, String arquitectura, String version, TipoReparacion tipoRepa, String descripcion, int valor, Date fecha, int idCliente, int idLogin) throws Exception {
        setIdPC(idPC);
        setMarca(marca);
        setModelo(modelo);
        setNumSerie(numSerie);
        setRam(ram);
        setHdd(hdd);
        setSistemaOpe(sistemaOpe);
        setArquitectura(arquitectura);
        setVersion(version);
        setTipoRepa(tipoRepa);
        setDescripcion(descripcion);
        setValor(valor);
        setFecha(fecha);
        setIdCliente(idCliente);
        setIdLogin(idLogin);
    }

    //Imprimir

    public ClComputador(String marca, String modelo, String numSerie, String sistemaOpe, String arquitectura, String version, Date fecha) throws Exception {
        setMarca(marca);
        setModelo(modelo);
        setNumSerie(numSerie);
        setSistemaOpe(sistemaOpe);
        setArquitectura(arquitectura);
        setVersion(version);
        setFecha(fecha);
    }

    @Override
    public String toString() {
        return marca + ";" + modelo + ";" + numSerie + ";" + sistemaOpe + ";" + arquitectura + ";" + version + ";" + fecha;
    }
    
    
    
}
