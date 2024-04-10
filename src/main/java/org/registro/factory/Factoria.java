package org.registro.factory;

import org.registro.interfaces.iRegistro;
import org.registro.modelo.RegistroDAO;


public class Factoria {
    
    public static iRegistro getFactoria(){
        return new RegistroDAO();
    }
    
}
