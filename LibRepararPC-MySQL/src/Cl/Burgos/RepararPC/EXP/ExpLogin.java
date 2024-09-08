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
public class ExpLogin extends Exception{
    public static final int ERR_Id=1;
    public static final int ERR_RutNull=2;
    public static final int ERR_Rut=3;
    public static final int ERR_Nombre=4;
    public static final int ERR_NombreNull=5;
    public static final int ERR_Apellido=6;
    public static final int ERR_Correo=7;
    public static final int ERR_Celular=8;
    public static final int ERR_Passworld=9;
    //public static final int ERR_Tipo=8;
    
     public ExpLogin (int error) throws Exception{
        switch(error){
            case ERR_Id:
                throw new Exception("El Id debe ser Mayor que 0");
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
            case ERR_Passworld:
                throw new Exception("El Passworld debe estar entre 4...25 caracteres");
                default:
                    throw new Exception("Error desconocido "+ error);
        }
    }
}
