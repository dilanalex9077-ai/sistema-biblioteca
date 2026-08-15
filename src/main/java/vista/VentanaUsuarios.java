package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana para la gestion de usuarios del sistema de biblioteca.
 */
public class VentanaUsuarios extends JFrame {

    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JLabel lblActivo;

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private JTextField txtCorreo;

    private JCheckBox chkActivo;

    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnCerrar;

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    private UsuarioDAO usuarioDAO;
    private int idSeleccionado = -1;

    public VentanaUsuarios() {

        usuarioDAO = new UsuarioDAO();

        setTitle("Gestion de Usuarios");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(3, 4, 10, 10));
        panelFormulario.setBorder(
                BorderFactory.createTitledBorder("Datos del Usuario")
        );

        lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        lblApellido = new JLabel("Apellido:");
        txtApellido = new JTextField();

        lblTelefono = new JLabel("Telefono:");
        txtTelefono = new JTextField();

        lblCorreo = new JLabel("Correo:");
        txtCorreo = new JTextField();

        lblActivo = new JLabel("Activo:");
        chkActivo = new JCheckBox();
        chkActivo.setSelected(true);

        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);

        panelFormulario.add(lblApellido);
        panelFormulario.add(txtApellido);

        panelFormulario.add(lblTelefono);
        panelFormulario.add(txtTelefono);

        panelFormulario.add(lblCorreo);
        panelFormulario.add(txtCorreo);

        panelFormulario.add(lblActivo);
        panelFormulario.add(chkActivo);

        add(panelFormulario, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Telefono");
        modeloTabla.addColumn("Correo");
        modeloTabla.addColumn("Activo");

        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );

        btnNuevo = new JButton("Nuevo");
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.SOUTH);

        cargarTabla();

        btnNuevo.addActionListener(e -> limpiarCampos());
        btnGuardar.addActionListener(e -> guardarUsuario());
        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnCerrar.addActionListener(e -> dispose());

        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int fila = tablaUsuarios.getSelectedRow();

                if (fila >= 0) {

                    idSeleccionado = Integer.parseInt(
                            modeloTabla.getValueAt(fila, 0).toString());

                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtApellido.setText(modeloTabla.getValueAt(fila, 2).toString());
                    txtTelefono.setText(modeloTabla.getValueAt(fila, 3).toString());
                    txtCorreo.setText(modeloTabla.getValueAt(fila, 4).toString());

                    chkActivo.setSelected(
                            Boolean.parseBoolean(
                                    modeloTabla.getValueAt(fila, 5).toString()));

                }

            }

        });

    }

    private void limpiarCampos() {

        idSeleccionado = -1;

        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        chkActivo.setSelected(true);

        tablaUsuarios.clearSelection();
        txtNombre.requestFocus();

    }

    private void guardarUsuario() {

        Usuario usuario = new Usuario();

        usuario.setNombre(txtNombre.getText());
        usuario.setApellido(txtApellido.getText());
        usuario.setTelefono(txtTelefono.getText());
        usuario.setCorreo(txtCorreo.getText());
        usuario.setActivo(chkActivo.isSelected());

        if (usuarioDAO.guardar(usuario)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario guardado correctamente."
            );

            limpiarCampos();
            cargarTabla();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible guardar el usuario."
            );

        }

    }

    private void actualizarUsuario() {

        if (idSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un usuario."
            );

            return;

        }

        Usuario usuario = new Usuario();

        usuario.setId(idSeleccionado);
        usuario.setNombre(txtNombre.getText());
        usuario.setApellido(txtApellido.getText());
        usuario.setTelefono(txtTelefono.getText());
        usuario.setCorreo(txtCorreo.getText());
        usuario.setActivo(chkActivo.isSelected());

        if (usuarioDAO.actualizar(usuario)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario actualizado."
            );

            limpiarCampos();
            cargarTabla();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible actualizar."
            );

        }

    }

    private void eliminarUsuario() {

        if (idSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un usuario."
            );

            return;

        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "Desea eliminar el usuario seleccionado?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {

            if (usuarioDAO.eliminar(idSeleccionado)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Usuario eliminado."
                );

                limpiarCampos();
                cargarTabla();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible eliminar."
                );

            }

        }

    }

    private void cargarTabla() {

        modeloTabla.setRowCount(0);

        for (Usuario usuario : usuarioDAO.listar()) {

            Object[] fila = {
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getTelefono(),
                    usuario.getCorreo(),
                    usuario.isActivo()
            };

            modeloTabla.addRow(fila);

        }

    }

}
