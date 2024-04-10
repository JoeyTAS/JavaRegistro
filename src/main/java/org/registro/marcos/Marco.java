package org.registro.marcos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import org.registro.entidad.Personas;
import org.registro.factory.Factoria;
import org.registro.interfaces.iRegistro;
import static org.registro.marcos.PanelTablas.modeloTabla;
import static org.registro.marcos.PanelTablas.tabla;

/**
 *
 * @author joel
 */
public class Marco extends JFrame {

    private PanelDatos panelDatos = new PanelDatos();
    private PanelTablas panelTablas = new PanelTablas();


    public Marco() {
        this.setTitle("Registro");
        this.setSize(700, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); 

        setLayout(new GridLayout(1, 2));

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.add(panelDatos, BorderLayout.CENTER);

        add(panelIzquierdo);

        add(panelTablas);
    }

}

class PanelDatos extends JPanel {

    private JLabel labelnombre = new JLabel("Nombres:");
    private JLabel labelapellido = new JLabel("Apellidos:");
    private JLabel labeltelefono = new JLabel("Telefono:");
    static JTextField textid = new JTextField(20);
    static JTextField textnombre = new JTextField(20);
    static JTextField textapellido = new JTextField(20);
    static JTextField texttelefono = new JTextField(20);

    public PanelDatos() {
        setLayout(new FlowLayout(FlowLayout.CENTER)); 

        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setLayout(new BoxLayout(panelIzquierda, BoxLayout.Y_AXIS)); 

        labelnombre.setFont(new Font("Comic Sans Ms", Font.BOLD, 15));
        labelapellido.setFont(new Font("Comic Sans Ms", Font.BOLD, 15));
        labeltelefono.setFont(new Font("Comic Sans Ms", Font.BOLD, 15));
        panelIzquierda.add(Box.createVerticalStrut(100));
        panelIzquierda.add(labelnombre);
        panelIzquierda.add(Box.createVerticalStrut(5));
        panelIzquierda.add(labelapellido);
        panelIzquierda.add(Box.createVerticalStrut(5));
        panelIzquierda.add(labeltelefono);

        // Panel para los JTextFields
        JPanel panelDerecha = new JPanel();
        panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS)); 
        panelDerecha.add(Box.createVerticalStrut(100));
        panelDerecha.add(textnombre);
        panelDerecha.add(Box.createVerticalStrut(5)); 
        panelDerecha.add(textapellido);
        panelDerecha.add(Box.createVerticalStrut(5)); 
        panelDerecha.add(texttelefono);

        add(panelIzquierda);
        add(panelDerecha);
        PanelBotones panelBotones = new PanelBotones();
        panelBotones.add(Box.createVerticalStrut(100));
        add(panelBotones, BorderLayout.SOUTH);
    }

}

class PanelBotones extends JPanel {

    private JButton jbNuevo = new JButton("Limpiar");
    private JButton jbGuardar = new JButton("Guardar");
    private JButton jbModifocar = new JButton("Modificar");
    private JButton jbEliminar = new JButton("Eliminar");
    private Personas ps = new Personas();

    public PanelBotones() {

        setLayout(new FlowLayout(FlowLayout.CENTER));

        add(jbNuevo);
        add(jbGuardar);
        add(jbModifocar);
        add(jbEliminar);

        jbModifocar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idString = JOptionPane.showInputDialog("Ingrese el ID de la persona a modificar:");
                if (idString != null && !idString.isEmpty()) {
                    int id = Integer.parseInt(idString);
                    String nombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:");
                    String apellidos = JOptionPane.showInputDialog("Ingrese los nuevos apellidos:");
                    String telefono = JOptionPane.showInputDialog("Ingrese el nuevo teléfono:");

                    // Crear un objeto Personas con los nuevos datos
                    Personas personaModificada = new Personas();
                    personaModificada.setId(id);
                    personaModificada.setNombre(nombre);
                    personaModificada.setApellidos(apellidos);
                    personaModificada.setTelefono(telefono);

                    iRegistro factory = Factoria.getFactoria();
                    factory.ModificarPersonas(personaModificada);

                    factory.MostrarPersonas(modeloTabla, tabla);
                } else {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        jbEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog(jbEliminar, "INGRESE EL ID");

                int idPersona = Integer.parseInt(id); 
                iRegistro factory = Factoria.getFactoria();
                factory.EliminarPersonas(idPersona);
                factory.MostrarPersonas(modeloTabla, tabla);

            }
        });

        jbNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelDatos.textid.setText("");
                PanelDatos.textnombre.setText("");
                PanelDatos.textapellido.setText("");
                PanelDatos.texttelefono.setText("");
            }
        });

        jbGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Personas persona = new Personas();
                persona.setNombre(PanelDatos.textnombre.getText());
                persona.setApellidos(PanelDatos.textapellido.getText());
                persona.setTelefono(PanelDatos.texttelefono.getText());

                iRegistro factory = Factoria.getFactoria();

                factory.AgregarPersonas(persona);

                PanelDatos.textid.setText("");
                PanelDatos.textnombre.setText("");
                PanelDatos.textapellido.setText("");
                PanelDatos.texttelefono.setText("");
                factory.MostrarPersonas(modeloTabla, tabla);
            }
        });

    }
}

class PanelTablas extends JPanel {

    static JTable tabla;
    static DefaultTableModel modeloTabla;

    public PanelTablas() {
        setLayout(new BorderLayout()); 
        setPreferredSize(new Dimension(200, 250));
        String[] columnas = {"ID", "Nombres", "Apellidos", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0); 
        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER); 
        iRegistro factory = Factoria.getFactoria();
        factory.MostrarPersonas(modeloTabla, tabla);

    }
}
