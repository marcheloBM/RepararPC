/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.ENT;

/**
 *
 * @author march
 */
public class ClArchivo {
    private int id;
    private String nombre;
    private byte[] archivo;
    private int idComputador;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public int getIdComputador() {
        return idComputador;
    }

    public void setIdComputador(int idComputador) {
        this.idComputador = idComputador;
    }

    public ClArchivo(int id, String nombre, byte[] archivo,int idComputador) {
        this.id = id;
        this.nombre = nombre;
        this.archivo = archivo;
        this.idComputador= idComputador;
    }

    public ClArchivo(String nombre, byte[] archivo,int idComputador) {
        this.nombre = nombre;
        this.archivo = archivo;
        this.idComputador= idComputador;
    }

    public ClArchivo(int id) {
        this.id = id;
    }
}
