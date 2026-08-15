package vista;

// Importa las clases de Swing necesarias para crear la interfaz gráfica.
import javax.swing.*;

// Importa las clases de AWT utilizadas para manejar diseños (Layout)
// y fuentes de texto.
import java.awt.*;

// La clase VentanaPrincipal hereda de JFrame, lo que significa que
// esta clase representa una ventana de la aplicación.
public class VentanaPrincipal extends JFrame {

    // Etiqueta que mostrará el título de la aplicación.
    private JLabel lblTitulo;

    // Botones que permitirán acceder a los diferentes módulos del sistema.
    private JButton btnLibros;
    private JButton btnUsuarios;
    private JButton btnPrestamos;
    private JButton btnSalir;

    // Constructor de la clase.
    // Se ejecuta automáticamente cuando se crea un objeto VentanaPrincipal.
    public VentanaPrincipal() {

        // Llama al método que crea y configura todos los componentes
        // de la interfaz gráfica.
        inicializarComponentes();

    }

    // Método encargado de construir toda la ventana.
    // Aquí se crean los controles, se configuran y se agregan a la ventana.
    private void inicializarComponentes() {

        // ==========================
        // PROPIEDADES DE LA VENTANA
        // ==========================

        // Establece el título que aparecerá en la barra superior.
        setTitle("Sistema de Biblioteca");

        // Define el ancho y alto de la ventana.
        setSize(500, 350);

        // Coloca la ventana en el centro de la pantalla.
        setLocationRelativeTo(null);

        // Indica que al cerrar la ventana finalizará toda la aplicación.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Impide que el usuario pueda cambiar el tamaño de la ventana.
        setResizable(false);

        // ==========================
        // LAYOUT PRINCIPAL
        // ==========================

        // Se utiliza un BorderLayout, que divide la ventana en cinco regiones:
        // NORTH, SOUTH, EAST, WEST y CENTER.
        setLayout(new BorderLayout());

        // ==========================
        // TÍTULO
        // ==========================

        // Se crea una etiqueta con el nombre del sistema.
        lblTitulo = new JLabel("SISTEMA DE BIBLIOTECA");

        // Centra horizontalmente el texto dentro de la etiqueta.
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Cambia el tipo de letra, el estilo (negrita) y el tamaño.
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        // Agrega la etiqueta en la parte superior de la ventana.
        add(lblTitulo, BorderLayout.NORTH);

        // ==========================
        // PANEL DE BOTONES
        // ==========================

        // Se crea un panel que contendrá todos los botones.
        JPanel panelBotones = new JPanel();

        // Se utiliza un GridLayout para acomodar los botones.
        // Tendrá:
        // - 4 filas
        // - 1 columna
        // - 10 píxeles de separación horizontal
        // - 10 píxeles de separación vertical
        panelBotones.setLayout(new GridLayout(4, 1, 10, 10));

        // Se crean los botones del sistema.
        btnLibros = new JButton("Gestión de Libros");
        btnUsuarios = new JButton("Gestión de Usuarios");
        btnPrestamos = new JButton("Préstamos");
        btnSalir = new JButton("Salir");

        // Se agregan los botones al panel.
        // El orden en que se agregan será el mismo en que aparecerán.
        panelBotones.add(btnLibros);
        panelBotones.add(btnUsuarios);
        panelBotones.add(btnPrestamos);
        panelBotones.add(btnSalir);

        // Agrega un margen interno alrededor del panel para que los botones
        // no queden pegados a los bordes de la ventana.
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));

        // Agrega el panel de botones al centro de la ventana.
        add(panelBotones, BorderLayout.CENTER);

        // ==========================
        // EVENTOS DE LOS BOTONES
        // ==========================

        // Cuando el usuario haga clic en el botón "Gestión de Libros",
        // se abrirá la ventana correspondiente.
        btnLibros.addActionListener(e -> {

            // Se crea un objeto de la ventana de libros.
            VentanaLibros ventana = new VentanaLibros();

            // Se muestra la ventana en pantalla.
            ventana.setVisible(true);

        });

        // Evento del botón "Gestión de Usuarios".
        btnUsuarios.addActionListener(e -> {

            // Se crea un objeto de la ventana de usuarios.
            VentanaUsuarios ventana = new VentanaUsuarios();

            // Se muestra la ventana en pantalla.
            ventana.setVisible(true);

        });

        // Evento del botón "Préstamos".
        btnPrestamos.addActionListener(e -> {

            // Muestra un mensaje indicando que el módulo
            // todavía está en desarrollo.
            JOptionPane.showMessageDialog(
                    this,
                    "Módulo de préstamos en construcción."
            );

        });

        // Evento del botón "Salir".
        btnSalir.addActionListener(e ->

                // Finaliza completamente la ejecución del programa.
                System.exit(0)
        );

    }

}
