package vista;

// Importa la clase LibroDAO, encargada de realizar las operaciones
// de acceso a la base de datos (guardar, consultar, actualizar y eliminar libros).
import dao.LibroDAO;

// Importa la clase Libro, que representa el modelo de un libro.
import modelo.Libro;

// Importa todos los componentes gráficos de Swing.
import javax.swing.*;

// Importa el modelo que utilizará la tabla para almacenar la información.
import javax.swing.table.DefaultTableModel;

// Importa las clases necesarias para utilizar los diferentes Layouts
// (BorderLayout, GridLayout, FlowLayout, etc.).
import java.awt.*;

// La clase VentanaLibros hereda de JFrame, por lo que representa
// una ventana independiente dentro de la aplicación.
public class VentanaLibros extends JFrame {

    // =====================================================
    // ETIQUETAS (JLabel)
    // =====================================================
    // Estas etiquetas muestran el nombre de cada campo
    // que el usuario deberá llenar.
    private JLabel lblISBN;
    private JLabel lblTitulo;
    private JLabel lblAutor;
    private JLabel lblEditorial;
    private JLabel lblAnio;
    private JLabel lblCategoria;
    private JLabel lblDisponible;

    // =====================================================
    // CAJAS DE TEXTO (JTextField)
    // =====================================================
    // Permiten al usuario capturar la información del libro.
    private JTextField txtISBN;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtEditorial;
    private JTextField txtAnio;
    private JTextField txtCategoria;

    // Casilla que indica si el libro está disponible para préstamo.
    private JCheckBox chkDisponible;

    // =====================================================
    // BOTONES
    // =====================================================
    // Cada botón realizará una acción diferente.
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnCerrar;

    // =====================================================
    // TABLA
    // =====================================================
    // JTable muestra la información en forma de filas y columnas.
    private JTable tablaLibros;

    // DefaultTableModel almacena los datos que aparecerán
    // dentro de la tabla.
    private DefaultTableModel modeloTabla;

    // =====================================================
    // OBJETO DAO
    // =====================================================
    // Este objeto permitirá comunicarse con la base de datos.
    private LibroDAO libroDAO;

    // Variable que almacena el ID del libro seleccionado.
    // El valor -1 significa que actualmente no hay ningún libro seleccionado.
    private int idSeleccionado = -1;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================
    // Se ejecuta automáticamente cuando se crea un objeto
    // de la clase VentanaLibros.
    public VentanaLibros() {

        // Se crea el objeto que permitirá acceder a la base de datos.
        libroDAO = new LibroDAO();

        // ===============================
        // CONFIGURACIÓN DE LA VENTANA
        // ===============================

        // Título que aparecerá en la parte superior de la ventana.
        setTitle("Gestión de Libros");

        // Define el tamaño de la ventana.
        setSize(900, 600);

        // Centra la ventana en la pantalla.
        setLocationRelativeTo(null);

        // Al cerrar esta ventana solamente se cerrará esta,
        // no toda la aplicación.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Se utilizará un BorderLayout para organizar los componentes.
        setLayout(new BorderLayout());

        // =====================================================
        // PANEL DEL FORMULARIO
        // =====================================================

        // Se crea un panel para colocar todos los controles
        // relacionados con la captura de información.
        JPanel panelFormulario = new JPanel(new GridLayout(4, 4, 10, 10));

        // Se agrega un borde con un título.
        panelFormulario.setBorder(
                BorderFactory.createTitledBorder("Datos del Libro")
        );

        // ===============================
        // CREACIÓN DE CONTROLES
        // ===============================

        // Etiqueta y caja de texto para el ISBN.
        lblISBN = new JLabel("ISBN:");
        txtISBN = new JTextField();

        // Etiqueta y caja de texto para el título.
        lblTitulo = new JLabel("Título:");
        txtTitulo = new JTextField();

        // Etiqueta y caja de texto para el autor.
        lblAutor = new JLabel("Autor:");
        txtAutor = new JTextField();

        // Etiqueta y caja de texto para la editorial.
        lblEditorial = new JLabel("Editorial:");
        txtEditorial = new JTextField();

        // Etiqueta y caja de texto para el año de publicación.
        lblAnio = new JLabel("Año:");
        txtAnio = new JTextField();

        // Etiqueta y caja de texto para la categoría.
        lblCategoria = new JLabel("Categoría:");
        txtCategoria = new JTextField();

        // Etiqueta y casilla de verificación para indicar
        // si el libro está disponible.
        lblDisponible = new JLabel("Disponible:");

        chkDisponible = new JCheckBox();

        // Por defecto el libro estará disponible.
        chkDisponible.setSelected(true);

        // =====================================================
        // AGREGAR COMPONENTES AL PANEL
        // =====================================================
        // El GridLayout los acomodará automáticamente
        // en el orden en que se agregan.

        panelFormulario.add(lblISBN);
        panelFormulario.add(txtISBN);

        panelFormulario.add(lblTitulo);
        panelFormulario.add(txtTitulo);

        panelFormulario.add(lblAutor);
        panelFormulario.add(txtAutor);

        panelFormulario.add(lblEditorial);
        panelFormulario.add(txtEditorial);

        panelFormulario.add(lblAnio);
        panelFormulario.add(txtAnio);

        panelFormulario.add(lblCategoria);
        panelFormulario.add(txtCategoria);

        panelFormulario.add(lblDisponible);
        panelFormulario.add(chkDisponible);

        // Se agrega el formulario en la parte superior de la ventana.
        add(panelFormulario, BorderLayout.NORTH);

        // =====================================================
        // CREACIÓN DE LA TABLA
        // =====================================================

        // Se crea el modelo donde se almacenarán los datos.
        modeloTabla = new DefaultTableModel();

        // Se agregan las columnas que tendrá la tabla.
        // Cada columna representa un atributo del libro.
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("ISBN");
        modeloTabla.addColumn("Título");
        modeloTabla.addColumn("Autor");
        modeloTabla.addColumn("Editorial");
        modeloTabla.addColumn("Año");
        modeloTabla.addColumn("Categoría");
        modeloTabla.addColumn("Disponible");

        // Se crea la tabla utilizando el modelo anterior.
        tablaLibros = new JTable(modeloTabla);

        // JScrollPane agrega barras de desplazamiento
        // cuando la tabla contiene muchos registros.
        JScrollPane scroll = new JScrollPane(tablaLibros);

        // La tabla se coloca en la parte central de la ventana.
        add(scroll, BorderLayout.CENTER);

        // =====================================================
        // PANEL DE BOTONES
        // =====================================================

        // Se crea un panel para organizar los botones.
        JPanel panelBotones = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );

        // Se crean los botones del formulario.
        btnNuevo = new JButton("Nuevo");
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnCerrar = new JButton("Cerrar");

        // Se agregan los botones al panel.
        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);

        // El panel de botones se coloca en la parte inferior.
        add(panelBotones, BorderLayout.SOUTH);

        // =====================================================
        // CARGAR INFORMACIÓN
        // =====================================================

        // Al abrir la ventana se cargan automáticamente
        // todos los libros registrados en la base de datos.
        cargarTabla();

        // =====================================================
        // EVENTOS DE LOS BOTONES
        // =====================================================

        // Botón Nuevo.
        // Limpia el formulario para capturar un nuevo libro.
        btnNuevo.addActionListener(e -> limpiarCampos());

        // Botón Guardar.
        // Almacena un nuevo libro en la base de datos.
        btnGuardar.addActionListener(e -> guardarLibro());

        // Botón Actualizar.
        // Modifica la información del libro seleccionado.
        btnActualizar.addActionListener(e -> actualizarLibro());

        // Botón Eliminar.
        // Elimina el libro seleccionado.
        btnEliminar.addActionListener(e -> eliminarLibro());

        // Botón Cerrar.
        // Cierra únicamente esta ventana.
        btnCerrar.addActionListener(e -> dispose());

        // =====================================================
        // EVENTO DE SELECCIÓN DE LA TABLA
        // =====================================================
        // Cuando el usuario selecciona una fila,
        // la información se copia automáticamente
        // al formulario para poder editarla.
        tablaLibros.getSelectionModel().addListSelectionListener(e -> {

            // Evita que el evento se ejecute dos veces
            // mientras el usuario cambia de selección.
            if (!e.getValueIsAdjusting()) {

                // Obtiene la fila actualmente seleccionada.
                int fila = tablaLibros.getSelectedRow();

                // Verifica que realmente exista una fila seleccionada.
                if (fila >= 0) {

                    // Obtiene el ID del libro seleccionado.
                    idSeleccionado = Integer.parseInt(
                            modeloTabla.getValueAt(fila, 0).toString());

                    // Copia la información de la tabla
                    // hacia las cajas de texto.
                    txtISBN.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtTitulo.setText(modeloTabla.getValueAt(fila, 2).toString());
                    txtAutor.setText(modeloTabla.getValueAt(fila, 3).toString());
                    txtEditorial.setText(modeloTabla.getValueAt(fila, 4).toString());
                    txtAnio.setText(modeloTabla.getValueAt(fila, 5).toString());
                    txtCategoria.setText(modeloTabla.getValueAt(fila, 6).toString());

                    // Convierte el valor de la tabla en un dato booleano
                    // para marcar o desmarcar la casilla.
                    chkDisponible.setSelected(
                            Boolean.parseBoolean(
                                    modeloTabla.getValueAt(fila, 7).toString()));

                }

            }

        });

    }

    // =====================================================
    // MÉTODO: limpiarCampos()
    // =====================================================
    // Este método limpia todos los controles del formulario para
    // permitir capturar un nuevo libro.
    private void limpiarCampos() {

        // Se reinicia el identificador del libro seleccionado.
        // El valor -1 indica que no hay ningún registro seleccionado.
        idSeleccionado = -1;

        // Se vacían todas las cajas de texto.
        txtISBN.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditorial.setText("");
        txtAnio.setText("");
        txtCategoria.setText("");

        // Se marca nuevamente la casilla de disponible.
        chkDisponible.setSelected(true);

        // Coloca el cursor sobre la caja de texto ISBN para que
        // el usuario pueda comenzar a escribir inmediatamente.
        txtISBN.requestFocus();

    }

    // =====================================================
    // MÉTODO: guardarLibro()
    // =====================================================
    // Obtiene la información del formulario y la almacena
    // en la base de datos.
    private void guardarLibro() {

        try {

            // Se crea un objeto Libro donde se almacenarán
            // temporalmente los datos capturados.
            Libro libro = new Libro();

            // Se asignan al objeto Libro los datos escritos
            // por el usuario en el formulario.
            libro.setIsbn(txtISBN.getText());
            libro.setTitulo(txtTitulo.getText());
            libro.setAutor(txtAutor.getText());
            libro.setEditorial(txtEditorial.getText());

            // Convierte el texto del campo Año a un número entero.
            libro.setAnio(Integer.parseInt(txtAnio.getText()));

            libro.setCategoria(txtCategoria.getText());

            // Obtiene si la casilla está marcada o no.
            libro.setDisponible(chkDisponible.isSelected());

            // Se intenta guardar el libro en la base de datos.
            if (libroDAO.guardar(libro)) {

                // Si el proceso fue exitoso se informa al usuario.
                JOptionPane.showMessageDialog(
                        this,
                        "Libro guardado correctamente."
                );

                // Se limpia el formulario.
                limpiarCampos();

                // Se actualiza la tabla para mostrar el nuevo registro.
                cargarTabla();

            } else {

                // Si ocurrió algún problema al guardar.
                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible guardar el libro."
                );

            }

        } catch (NumberFormatException ex) {

            // Esta excepción ocurre cuando el usuario escribe
            // un dato que no es numérico en el campo Año.
            JOptionPane.showMessageDialog(
                    this,
                    "El año debe ser un número."
            );

        }

    }

    // =====================================================
    // MÉTODO: actualizarLibro()
    // =====================================================
    // Modifica la información del libro seleccionado.
    private void actualizarLibro() {

        // Verifica que exista un libro seleccionado.
        if (idSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un libro."
            );

            // Sale del método para evitar continuar.
            return;

        }

        try {

            // Se crea un objeto Libro.
            Libro libro = new Libro();

            // Se asigna el ID del registro que será actualizado.
            libro.setId(idSeleccionado);

            // Se copian los datos del formulario al objeto.
            libro.setIsbn(txtISBN.getText());
            libro.setTitulo(txtTitulo.getText());
            libro.setAutor(txtAutor.getText());
            libro.setEditorial(txtEditorial.getText());
            libro.setAnio(Integer.parseInt(txtAnio.getText()));
            libro.setCategoria(txtCategoria.getText());
            libro.setDisponible(chkDisponible.isSelected());

            // Se intenta actualizar el registro en la base de datos.
            if (libroDAO.actualizar(libro)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Libro actualizado."
                );

                // Se limpia el formulario.
                limpiarCampos();

                // Se recarga la tabla para mostrar los cambios.
                cargarTabla();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible actualizar."
                );

            }

        } catch (NumberFormatException ex) {

            // Si el usuario escribió un dato no numérico
            // en el campo Año.
            JOptionPane.showMessageDialog(
                    this,
                    "El año debe ser un número."
            );

        }

    }

    // =====================================================
    // MÉTODO: eliminarLibro()
    // =====================================================
    // Elimina el libro actualmente seleccionado.
    private void eliminarLibro() {

        // Verifica que exista un registro seleccionado.
        if (idSeleccionado == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un libro."
            );

            return;

        }

        // Muestra un cuadro de confirmación antes de eliminar.
        int opcion = JOptionPane.showConfirmDialog(

                this,

                "¿Desea eliminar el libro seleccionado?",

                "Confirmar",

                JOptionPane.YES_NO_OPTION

        );

        // Si el usuario respondió "Sí".
        if (opcion == JOptionPane.YES_OPTION) {

            // Se intenta eliminar el registro de la base de datos.
            if (libroDAO.eliminar(idSeleccionado)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Libro eliminado."
                );

                // Limpia el formulario.
                limpiarCampos();

                // Actualiza la tabla.
                cargarTabla();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible eliminar."
                );

            }

        }

    }

    // =====================================================
    // MÉTODO: cargarTabla()
    // =====================================================
    // Consulta todos los libros almacenados en la base de datos
    // y los muestra dentro de la tabla.
    private void cargarTabla() {

        // Elimina todas las filas actuales de la tabla para evitar
        // que se repitan los registros al volver a cargarla.
        modeloTabla.setRowCount(0);

        // Recorre la lista de libros obtenida desde la base de datos.
        for (Libro libro : libroDAO.listar()) {

            // Se crea un arreglo de objetos con la información
            // de un libro.
            Object[] fila = {

                    libro.getId(),
                    libro.getIsbn(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getEditorial(),
                    libro.getAnio(),
                    libro.getCategoria(),
                    libro.isDisponible()

            };

            // Agrega la fila al modelo de la tabla.
            modeloTabla.addRow(fila);

        }

    }

}