package org.registro.basedatos;

import java.sql.Connection;
import java.sql.DriverManager;

public class BaseDatos {

    private static Connection connection = null;

    public static Connection getConection() {

        String usuario = "sysdba";
        String clave = "masterkey";
        String baseDatos = "jdbc:firebirdsql:127.0.0.1/3050:C:\\Users\\joel\\OneDrive\\Documentos\\NetBeansProjects\\DatosPersonasTAS\\data\\REGISTROPERSONAS.FDB?encoding=UTF8";
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            connection = DriverManager.getConnection(baseDatos, usuario, clave);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

}
