package org.registro.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.registro.basedatos.BaseDatos;
import org.registro.entidad.Personas;
import org.registro.interfaces.iRegistro;

public class RegistroDAO implements iRegistro {

    PreparedStatement ps;
    ResultSet rs;
    int r;

    @Override
    public void EliminarPersonas(int idPersona) {
        try {
            Connection conectar = BaseDatos.getConection();
            String sql = "DELETE FROM PERSONAS WHERE ID_PERSONAS = ?";
            ps = conectar.prepareStatement(sql);
            ps.setInt(1, idPersona);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Se eliminó la persona con ID: " + idPersona + " correctamente.");
            } else {
                System.out.println("No se encontró ninguna persona con ID: " + idPersona + ".");
            }

            ps.close();
            conectar.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ModificarPersonas(Personas persona) {
        try {
            Connection conectar = BaseDatos.getConection();
            String sql = "UPDATE PERSONAS SET NOMBRES=?, APELLIDOS=?, TELEFONO=? WHERE ID_PERSONAS=?";
            ps = conectar.prepareStatement(sql);
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellidos());
            ps.setString(3, persona.getTelefono());
            ps.setInt(4, persona.getId());
            ps.executeUpdate();


            ps.close();
            conectar.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void MostrarPersonas(DefaultTableModel modeloTabla, JTable tabla) {
        try {
            Connection conectar = BaseDatos.getConection();
            String sql = "SELECT * FROM PERSONAS";
            ps = conectar.prepareStatement(sql);
            rs = ps.executeQuery();

            modeloTabla.setRowCount(0);


            while (rs.next()) {
                int id = rs.getInt("ID_PERSONAS");
                String nombre = rs.getString("NOMBRES");
                String apellidos = rs.getString("APELLIDOS");
                String telefono = rs.getString("TELEFONO");


                modeloTabla.addRow(new Object[]{id, nombre, apellidos, telefono});
            }

            // Cerrar conexión y recursos
            rs.close();
            ps.close();
            conectar.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void AgregarPersonas(Personas personas) {
        String sql = "INSERT INTO PERSONAS (NOMBRES, APELLIDOS, TELEFONO) VALUES (?, ?, ?)";

        try {
            Connection conectar = BaseDatos.getConection();
            ps = conectar.prepareStatement(sql);
            ps.setString(1, personas.getNombre());
            ps.setString(2, personas.getApellidos());
            ps.setString(3, personas.getTelefono());
            r = ps.executeUpdate();

            if (r > 0) {
                System.out.println("Se ha agregado correctamente la persona a la base de datos.");
            } else {
                System.out.println("No se pudo agregar la persona a la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta SQL: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar PreparedStatement: " + ex.getMessage());
            }
        }
    }

}
