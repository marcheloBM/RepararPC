/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cl.Burgos.RepararPC.EXP;

/**
 *
 * @author march
 */
public class ExpCliente extends Exception {
    public static final int ERR_IdCliente=1;
    public static final int ERR_RutNull=2;
    public static final int ERR_Rut=3;
    public static final int ERR_NombreNull=4;
    public static final int ERR_Nombre=5;
    public static final int ERR_Apellido=6;
    public static final int ERR_Correo=7;
    public static final int ERR_Celular=8;
    public static final int ERR_IdLogin=9;
    
    public ExpCliente (int error) throws Exception{
        switch(error){
            case ERR_IdCliente:
                throw new Exception("El ID deve ser mayor que 0");
            case ERR_RutNull:
                throw new Exception("El Rut no puede estar vacio");
            case ERR_Rut:
                throw new Exception("El Rut es Incorrecto");
            case ERR_NombreNull:
                throw new Exception("El Nombre no puede estar vacio");
            case ERR_Nombre:
                throw new Exception("El Nombre debe estar entre 4...25 caracteres");
            case ERR_Apellido:
                throw new Exception("El Apellido debe estar entre 4...25 caracteres");
            case ERR_Correo:
                throw new Exception("El Correo es incorrecto");
            case ERR_Celular:
                throw new Exception("El Celular debe ser de 9 digitos");
            case ERR_IdLogin:
                throw new Exception("El ID deve ser mayor que 0");
                default:
                    throw new Exception("Error desconocido "+ error);
        }
    }
}
