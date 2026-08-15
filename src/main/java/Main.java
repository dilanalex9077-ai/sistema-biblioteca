// Importa la clase VentanaPrincipal, que contiene la interfaz gráfica principal del programa.
import vista.VentanaPrincipal;

// Importa la clase encargada de crear e inicializar la base de datos
// en caso de que aún no exista.
import conexion.InicializadorBD;

// Importa las clases de Swing, la biblioteca utilizada para crear
// interfaces gráficas en Java.
import javax.swing.*;

public class Main {

    // Método principal del programa.
    // Es el primer método que se ejecuta cuando inicia la aplicación.
    public static void main(String[] args) {

        // Inicializa la base de datos.
        // Si la base de datos o sus tablas no existen, este método las crea.
        // Si ya existen, simplemente verifica que todo esté listo para trabajar.
        InicializadorBD.inicializar();

        // invokeLater() garantiza que toda la interfaz gráfica se cree
        // dentro del hilo de eventos de Swing (Event Dispatch Thread),
        // lo cual evita errores y asegura el correcto funcionamiento
        // de los componentes gráficos.
        SwingUtilities.invokeLater(() -> {

            // Se crea un objeto de la ventana principal de la aplicación.
            VentanaPrincipal ventana = new VentanaPrincipal();

            // Hace visible la ventana para que el usuario pueda interactuar con ella.
            ventana.setVisible(true);

        });

    }

}