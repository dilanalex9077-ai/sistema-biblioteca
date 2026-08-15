package conexion;

// Importa la clase Connection, que representa una conexión
// activa con una base de datos.
import java.sql.Connection;

// Importa DriverManager, encargado de establecer la conexión
// con la base de datos utilizando una URL.
import java.sql.DriverManager;

// Importa la excepción que puede ocurrir durante las operaciones
// relacionadas con bases de datos.
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * ============================================================
 * CLASE ConexionBD
 * ============================================================
 *
 * Esta clase tiene la responsabilidad de crear conexiones
 * con el servidor MySQL.
 *
 * En lugar de escribir el mismo código de conexión en todas
 * las clases del proyecto, se centraliza aquí.
 *
 * Cada vez que otra clase necesite acceder a la base de datos,
 * simplemente llamará alguno de los métodos de esta clase.
 *
 * Ejemplo:
 *
 * Connection conexion = ConexionBD.getConexion();
 *
 * De esta manera el código es más organizado y fácil de mantener.
 *
 * @author UTTT
 */
public class ConexionBD {

    // =====================================================
    // CONSTANTES DE CONEXIÓN
    // =====================================================
    // Se almacenan los datos necesarios para conectarse
    // a la base de datos.

    // URL de conexión.
    //
    // jdbc:mysql://  -> Indica que se utilizará el controlador JDBC para MySQL.
    // localhost      -> El servidor está instalado en la misma computadora.
    // 3306           -> Puerto por defecto de MySQL.
    // biblioteca     -> Nombre de la base de datos.
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    // Nombre del usuario de MySQL.
    private static final String USUARIO = System.getenv().getOrDefault("BIBLIOTECA_DB_USUARIO", "root");

    // La contraseña se obtiene del entorno para evitar publicarla
    // en el código fuente o en el historial de Git.
    private static String password = System.getenv().getOrDefault("BIBLIOTECA_DB_PASSWORD", "");

    /**
     * =====================================================
     * MÉTODO getConexion()
     * =====================================================
     *
     * Establece una conexión directamente con la base de datos
     * llamada "biblioteca".
     *
     * Este método será utilizado por los DAO para ejecutar
     * consultas SQL.
     *
     * Devuelve un objeto Connection.
     *
     * Puede lanzar una SQLException si ocurre algún problema,
     * por ejemplo:
     * - El servidor MySQL está apagado.
     * - La contraseña es incorrecta.
     * - La base de datos no existe.
     */
    public static Connection getConexion() throws SQLException {

        // DriverManager se encarga de crear la conexión utilizando:
        // - La URL.
        // - El usuario.
        // - La contraseña.
        return conectar(URL);

    }

    /**
     * =====================================================
     * MÉTODO getConexionServidor()
     * =====================================================
     *
     * Establece una conexión únicamente con el servidor MySQL,
     * sin seleccionar ninguna base de datos.
     *
     * Este método suele utilizarse cuando se necesita crear
     * una base de datos por primera vez.
     *
     * Por ejemplo:
     *
     * CREATE DATABASE biblioteca;
     *
     * Después de crear la base de datos, el sistema utilizará
     * getConexion() para conectarse directamente a ella.
     */
    public static Connection getConexionServidor() throws SQLException {

        // URL del servidor MySQL.
        // Observa que no aparece el nombre de la base de datos.
        String urlServidor = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        // Se crea la conexión al servidor.
        return conectar(urlServidor);

    }

    private static Connection conectar(String url) throws SQLException {

        try {

            return DriverManager.getConnection(url, USUARIO, password);

        } catch (SQLException e) {

            if ("28000".equals(e.getSQLState())) {

                String nuevaPassword = JOptionPane.showInputDialog(
                        null,
                        "Escribe la contrasena de MySQL Workbench para el usuario root:",
                        "Conexion a MySQL",
                        JOptionPane.QUESTION_MESSAGE
                );

                if (nuevaPassword != null) {

                    password = nuevaPassword;
                    return DriverManager.getConnection(url, USUARIO, password);

                }

            }

            throw e;

        }

    }

}
