package org.registro.marcos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import static org.registro.marcos.PanelTablas.tabla;

/**
 *
 * @author joel
 */
public class Marco extends JFrame {

    private PanelPrincipal panelPrincipal = new PanelPrincipal();
    private PanelDatos panelDatos = new PanelDatos();
    private PanelTablas panelTablas = new PanelTablas();
    private PanelBotones panelBotones = new PanelBotones();

    public Marco() {
        this.setTitle("Registro");
        this.setSize(700, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(this);
        this.add(panelPrincipal);
        this.add(panelDatos);
        this.add(panelTablas);
        this.add(panelBotones);
        setLayout(new BorderLayout()); // Agregado para establecer el layout del JFrame
        this.add(panelDatos, BorderLayout.NORTH);
        this.add(panelTablas, BorderLayout.SOUTH);
        this.add(panelBotones, BorderLayout.CENTER);

    }

}

class PanelPrincipal extends JPanel {

    private Border borde = new LineBorder(Color.BLUE, 2);

    public PanelPrincipal() {
        setBorder(borde);

    }

}

class PanelDatos extends JPanel {


    private JLabel labelid = new JLabel("ID: ");
    private JLabel labelnombre = new JLabel("NOMBRES:");
    private JLabel labelapellido = new JLabel("APELLIDOS: ");
    private JLabel labeltelefono = new JLabel("TELEFONO: ");
    static JTextField textid = new JTextField(30);
    static JTextField textnombre = new JTextField(30);
    static JTextField textapellido = new JTextField(30);
    static JTextField texttelefono = new JTextField(30);

    public PanelDatos() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 150));

        JPanel panelDerecha = new JPanel();
        panelDerecha.setLayout(new GridLayout(4, 1));
        panelDerecha.add(textid);
        panelDerecha.add(textnombre);
        panelDerecha.add(textapellido);
        panelDerecha.add(texttelefono);

        JPanel panelIzquierda = new JPanel();
        panelIzquierda.setLayout(new GridLayout(4, 1));
        panelIzquierda.add(labelid);
        panelIzquierda.add(labelnombre);
        panelIzquierda.add(labelapellido);
        panelIzquierda.add(labeltelefono);

        add(panelIzquierda, BorderLayout.WEST);
        add(panelDerecha, BorderLayout.EAST);
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
        setBorder(new EtchedBorder(EtchedBorder.RAISED));
        add(jbNuevo);
        add(jbGuardar);
        add(jbModifocar);
        add(jbEliminar);
        // Declaración de una variable de control

        jbModifocar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoId = PanelDatos.textid.getText();
                if (!textoId.isEmpty()) {
                    int idModificar = Integer.parseInt(textoId);
                    DefaultTableModel modeloTabla = (DefaultTableModel) tabla.getModel();
                    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                        int idTabla = (int) modeloTabla.getValueAt(i, 0); 

                        if (idTabla == idModificar) {
                            modeloTabla.setValueAt(PanelDatos.textnombre.getText(), i, 1);
                            modeloTabla.setValueAt(PanelDatos.textapellido.getText(), i, 2);
                            modeloTabla.setValueAt(PanelDatos.texttelefono.getText(), i, 3);
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        jbEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tabla.getSelectedRow();

                if (filaSeleccionada != -1) {
                    DefaultTableModel modeloTabla = (DefaultTableModel) tabla.getModel();
                    modeloTabla.removeRow(filaSeleccionada);
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
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
                ps.setId(Integer.parseInt(PanelDatos.textid.getText()));
                ps.setNombre(PanelDatos.textnombre.getText());
                ps.setApellidos(PanelDatos.textapellido.getText());
                ps.setTelefono(PanelDatos.texttelefono.getText());

                DefaultTableModel modeloTabla = (DefaultTableModel) PanelTablas.tabla.getModel();
                modeloTabla.addRow(new Object[]{ps.getId(), ps.getNombre(), ps.getApellidos(), ps.getTelefono()});
                PanelDatos.textid.setText("");
                PanelDatos.textnombre.setText("");
                PanelDatos.textapellido.setText("");
                PanelDatos.texttelefono.setText("");

            }
        });

    }
}

class PanelTablas extends JPanel {


    static JTable tabla;
    private DefaultTableModel modeloTabla;

    public PanelTablas() {

        setPreferredSize(new Dimension(400, 250));

        Object[][] datosTabla = {};

        String[] columnas = {"ID", "Nombres", "Apellidos", "Teléfono"};
        modeloTabla = new DefaultTableModel(datosTabla, columnas);

        tabla = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);
    }
}
