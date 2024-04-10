package org.registro.interfaces;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.registro.entidad.Personas;

/**
 *
 * @author joel
 */
public interface iRegistro {
    
    public void AgregarPersonas(Personas personas);

    public void EliminarPersonas(int idPersona);

    public void ModificarPersonas(Personas personas);
    
    public  void MostrarPersonas(DefaultTableModel modeloTabla, JTable tabla);
    
}

