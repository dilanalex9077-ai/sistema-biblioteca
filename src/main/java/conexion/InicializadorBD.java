package conexion;

// Importa la clase Connection, que representa una conexión
// activa con la base de datos.
import java.sql.Connection;

// Importa la excepción que puede producirse durante las
// operaciones relacionadas con la base de datos.
import java.sql.SQLException;

// Importa la clase Statement, utilizada para ejecutar
// instrucciones SQL que no reciben parámetros.
import java.sql.Statement;

/**
 * ============================================================
 * CLASE InicializadorBD
 * ============================================================
 *
 * Esta clase prepara la base de datos para que la aplicación
 * pueda funcionar correctamente.
 *
 * Cuando el programa inicia:
 *
 * 1. Verifica si existe la base de datos.
 * 2. Si no existe, la crea.
 * 3. Después verifica si existen las tablas.
 * 4. Si no existen, también las crea.
 *
 * Gracias a esto, el usuario no necesita crear la base de datos
 * manualmente desde MySQL Workbench o la consola.
 *
 * @author UTTT
 */
public class InicializadorBD {

    /**
     * Método encargado de crear la base de datos y las tablas
     * necesarias para el funcionamiento del sistema.
     */
    public static void inicializar() {

        // =====================================================
        // PASO 1
        // CREAR LA BASE DE DATOS
        // =====================================================

        // Primero nos conectamos únicamente al servidor MySQL.
        // Todavía no seleccionamos ninguna base de datos.
        try (

                Connection conexion = ConexionBD.getConexionServidor();
                Statement sentencia = conexion.createStatement()

        ) {

            // Ejecuta la instrucción SQL que crea la base de datos.
            //
            // IF NOT EXISTS evita que aparezca un error si la base
            // ya había sido creada anteriormente.
            sentencia.executeUpdate("""
                    CREATE DATABASE IF NOT EXISTS biblioteca
                    CHARACTER SET utf8mb4
                    COLLATE utf8mb4_unicode_ci
                    """);

        } catch (SQLException e) {

            // Si ocurre algún error, se muestra en la consola
            // y se termina el método.
            e.printStackTrace();
            return;

        }

        // =====================================================
        // PASO 2
        // CREAR LAS TABLAS
        // =====================================================

        // Ahora nos conectamos directamente a la base de datos
        // "biblioteca".
        try (

                Connection conexion = ConexionBD.getConexion();
                Statement sentencia = conexion.createStatement()

        ) {

            // =====================================================
            // TABLA LIBROS
            // =====================================================
            //
            // Esta tabla almacena la información de todos los libros.
            //
            // Campos:
            // id          -> Identificador único.
            // isbn        -> Código ISBN (único).
            // titulo      -> Nombre del libro.
            // autor       -> Autor del libro.
            // editorial   -> Editorial.
            // anio        -> Año de publicación.
            // categoria   -> Categoría del libro.
            // disponible  -> Indica si puede prestarse.

            sentencia.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS libros(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        isbn VARCHAR(20) UNIQUE,
                        titulo VARCHAR(200) NOT NULL,
                        autor VARCHAR(150) NOT NULL,
                        editorial VARCHAR(150),
                        anio INT,
                        categoria VARCHAR(100),
                        disponible BOOLEAN DEFAULT TRUE
                    )
                    """);

            // =====================================================
            // TABLA USUARIOS
            // =====================================================
            //
            // Esta tabla almacena la información de los usuarios
            // registrados en la biblioteca.
            //
            // Campos:
            // id         -> Identificador único.
            // nombre     -> Nombre del usuario.
            // apellido   -> Apellido.
            // telefono   -> Número telefónico.
            // correo     -> Correo electrónico.
            // activo     -> Indica si el usuario puede solicitar préstamos.

            sentencia.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS usuarios(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(100) NOT NULL,
                        apellido VARCHAR(100) NOT NULL,
                        telefono VARCHAR(20),
                        correo VARCHAR(150),
                        activo BOOLEAN DEFAULT TRUE
                    )
                    """);

            // =====================================================
            // TABLA PRESTAMOS
            // =====================================================
            //
            // Esta tabla registra todos los préstamos realizados.
            //
            // Campos:
            // id                 -> Identificador del préstamo.
            // libro_id           -> Libro prestado.
            // usuario_id         -> Usuario que realiza el préstamo.
            // fecha_prestamo     -> Fecha en que se prestó el libro.
            // fecha_limite       -> Fecha máxima para devolverlo.
            // fecha_devolucion   -> Fecha real de devolución.
            //
            // Relaciones:
            // libro_id   -> Hace referencia a la tabla libros.
            // usuario_id -> Hace referencia a la tabla usuarios.

            sentencia.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS prestamos(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        libro_id INT NOT NULL,
                        usuario_id INT NOT NULL,
                        fecha_prestamo DATE NOT NULL,
                        fecha_limite DATE NOT NULL,
                        fecha_devolucion DATE,

                        FOREIGN KEY(libro_id)
                            REFERENCES libros(id),

                        FOREIGN KEY(usuario_id)
                            REFERENCES usuarios(id)
                    )
                    """);

            // Si todo fue exitoso, se informa en la consola.
            System.out.println("Base de datos inicializada correctamente.");

        } catch (SQLException e) {

            // Si ocurre algún problema durante la creación
            // de las tablas, se muestra el error.
            e.printStackTrace();

        }

    }

}