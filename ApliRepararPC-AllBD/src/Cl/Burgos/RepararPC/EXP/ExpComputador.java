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
public class ExpComputador extends Exception {

    public static final int ERR_idPc=1;
    public static final int ERR_marca=2;
    public static final int ERR_modelo=3;
    public static final int ERR_numSerie=4;
    public static final int ERR_SO=5;
    public static final int ERR_Arquitectura=6;
    public static final int ERR_descripcion=7;
    public static final int ERR_Valor=8;
    public static final int ERR_fecha=9;
    public static final int ERR_idCliente=10;
    public static final int ERR_idLogin=11;
    
    public ExpComputador(int error) throws Exception{
        switch(error){
            case ERR_idPc:
                throw new Exception("El Id debe ser Mayor que 0");
            case ERR_marca:
                throw new Exception("La Marca debe estar entre 2...45 caracteres");
            case ERR_modelo:
                throw new Exception("El Modelo debe estar entre 4...45 caracteres");
            case ERR_numSerie:
                throw new Exception("El Numero de Serie debe estar entre 4...45 caracteres");
            case ERR_SO:
                throw new Exception("La Sistema Operativo debe estar entre 4...25 caracteres");
            case ERR_Arquitectura:
                throw new Exception("La Arquitectura debe estar entre 4...25 caracteres");
            case ERR_descripcion:
                throw new Exception("La Descripcion debe estar entre 4...200 caracteres");
            case ERR_Valor:
                throw new Exception("El Valor debe estar entre 4...25 Digitos");
            case ERR_fecha:
                throw new Exception("La Fecha debe ser Anterior a la Actual");
            case ERR_idCliente:
                throw new Exception("El Id debe ser Mayor que 0");
                default:
                    throw new Exception("Error desconocido "+ error);
        }
    }
}
