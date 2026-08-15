package dao;

// Importa la clase encargada de proporcionar la conexión
// con la base de datos.
import conexion.ConexionBD;

// Importa la clase Libro, que representa un registro
// de la tabla libros.
import modelo.Libro;

// Importa todas las clases necesarias para trabajar
// con JDBC (Java Database Connectivity).
import java.sql.*;

// Importa las clases necesarias para trabajar con listas.
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * CLASE LibroDAO
 * ============================================================
 *
 * DAO significa Data Access Object (Objeto de Acceso a Datos).
 *
 * Su función es ser el intermediario entre la aplicación
 * y la base de datos.
 *
 * Gracias al patrón DAO, las ventanas (Vista) no necesitan
 * conocer cómo funcionan las consultas SQL.
 *
 * La Vista únicamente llama métodos como:
 *
 * guardar()
 * actualizar()
 * eliminar()
 * listar()
 *
 * y el DAO se encarga de ejecutar las instrucciones SQL.
 *
 * Esto hace que el código sea más organizado y fácil
 * de mantener.
 *
 * @author UTTT
 */
public class LibroDAO {

    /**
     * =====================================================
     * MÉTODO guardar()
     * =====================================================
     *
     * Inserta un nuevo libro dentro de la base de datos.
     *
     * Devuelve:
     *
     * true  -> Si el libro fue guardado correctamente.
     * false -> Si ocurrió algún error.
     */
    public boolean guardar(Libro libro) {

        // Consulta SQL para insertar un nuevo registro.
        //
        // Los signos ? representan parámetros que serán
        // reemplazados posteriormente con los datos del libro.
        String sql = """
                INSERT INTO libros
                (isbn, titulo, autor, editorial, anio, categoria, disponible)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        // try-with-resources abre automáticamente la conexión
        // y la cierra al finalizar, incluso si ocurre un error.
        try (

                // Obtiene una conexión con la base de datos.
                Connection conexion = ConexionBD.getConexion();

                // PreparedStatement permite ejecutar consultas SQL
                // utilizando parámetros de forma segura.
                PreparedStatement ps = conexion.prepareStatement(sql)

        ) {

            // Asigna el ISBN al primer parámetro (?).
            ps.setString(1, libro.getIsbn());

            // Asigna el título.
            ps.setString(2, libro.getTitulo());

            // Asigna el autor.
            ps.setString(3, libro.getAutor());

            // Asigna la editorial.
            ps.setString(4, libro.getEditorial());

            // Asigna el año.
            ps.setInt(5, libro.getAnio());

            // Asigna la categoría.
            ps.setString(6, libro.getCategoria());

            // Asigna la disponibilidad.
            ps.setBoolean(7, libro.isDisponible());

            // Ejecuta el INSERT.
            //
            // executeUpdate() devuelve el número de registros
            // afectados por la consulta.
            //
            // Si el resultado es mayor que cero,
            // significa que el registro fue insertado.
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            // Si ocurre algún error durante la consulta,
            // se imprime la información del error
            // en la consola.
            e.printStackTrace();

        }

        // Si ocurrió un error, devuelve false.
        return false;

    }

    /**
     * =====================================================
     * MÉTODO actualizar()
     * =====================================================
     *
     * Modifica la información de un libro existente.
     *
     * El libro que será actualizado se identifica
     * mediante su ID.
     */
    public boolean actualizar(Libro libro) {

        // Consulta SQL para actualizar un registro.
        //
        // WHERE id = ? indica cuál libro será modificado.
        String sql = """
                UPDATE libros
                SET isbn = ?,
                    titulo = ?,
                    autor = ?,
                    editorial = ?,
                    anio = ?,
                    categoria = ?,
                    disponible = ?
                WHERE id = ?
                """;

        try (

                // Obtiene la conexión.
                Connection conexion = ConexionBD.getConexion();

                // Prepara la consulta SQL.
                PreparedStatement ps = conexion.prepareStatement(sql)

        ) {

            // Se asignan los nuevos valores que tendrá el libro.
            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());
            ps.setString(3, libro.getAutor());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getAnio());
            ps.setString(6, libro.getCategoria());
            ps.setBoolean(7, libro.isDisponible());

            // Finalmente se indica el ID del registro
            // que será actualizado.
            ps.setInt(8, libro.getId());

            // Ejecuta la actualización.
            //
            // Si al menos un registro fue modificado,
            // devuelve true.
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            // Muestra la información del error.
            e.printStackTrace();

        }

        // Si ocurrió algún problema,
        // devuelve false.
        return false;

    }

    /**
     * =====================================================
     * MÉTODO eliminar()
     * =====================================================
     *
     * Elimina un libro de la base de datos utilizando su ID.
     *
     * Parámetro:
     * id -> Identificador del libro que se desea eliminar.
     *
     * Devuelve:
     * true  -> Si el registro fue eliminado correctamente.
     * false -> Si ocurrió algún error.
     */
    public boolean eliminar(int id) {

        // Consulta SQL para eliminar un registro.
        // Solamente se eliminará el libro cuyo ID coincida
        // con el valor recibido como parámetro.
        String sql = "DELETE FROM libros WHERE id = ?";

        try (

                // Obtiene una conexión con la base de datos.
                Connection conexion = ConexionBD.getConexion();

                // Prepara la consulta SQL.
                PreparedStatement ps = conexion.prepareStatement(sql)

        ) {

            // Asigna el valor del ID al parámetro de la consulta.
            ps.setInt(1, id);

            // Ejecuta la instrucción DELETE.
            // Si al menos un registro fue eliminado,
            // executeUpdate() devolverá un número mayor que cero.
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            // Si ocurre un error durante la eliminación,
            // se muestra la información del error en la consola.
            e.printStackTrace();

        }

        // Si ocurrió algún problema, devuelve false.
        return false;

    }

    /**
     * =====================================================
     * MÉTODO buscarPorId()
     * =====================================================
     *
     * Busca un libro utilizando su identificador.
     *
     * Si encuentra el registro devuelve un objeto Libro.
     * Si no existe, devuelve null.
     */
    public Libro buscarPorId(int id) {

        // Consulta SQL que busca un único libro.
        String sql = "SELECT * FROM libros WHERE id = ?";

        try (

                // Obtiene la conexión con la base de datos.
                Connection conexion = ConexionBD.getConexion();

                // Prepara la consulta SQL.
                PreparedStatement ps = conexion.prepareStatement(sql)

        ) {

            // Asigna el ID que se desea buscar.
            ps.setInt(1, id);

            // Ejecuta la consulta.
            // executeQuery() devuelve un ResultSet,
            // que contiene los registros encontrados.
            ResultSet rs = ps.executeQuery();

            // next() mueve el cursor al primer registro.
            // Devuelve true si encontró información.
            if (rs.next()) {

                // Se crea un objeto Libro para almacenar
                // los datos recuperados.
                Libro libro = new Libro();

                // Se copian los datos del ResultSet
                // hacia el objeto Libro.
                libro.setId(rs.getInt("id"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setAnio(rs.getInt("anio"));
                libro.setCategoria(rs.getString("categoria"));
                libro.setDisponible(rs.getBoolean("disponible"));

                // Devuelve el objeto completamente lleno.
                return libro;

            }

        } catch (SQLException e) {

            // Muestra el error ocurrido.
            e.printStackTrace();

        }

        // Si no encontró ningún libro,
        // devuelve null.
        return null;

    }

    /**
     * =====================================================
     * MÉTODO listar()
     * =====================================================
     *
     * Recupera todos los libros registrados
     * en la base de datos.
     *
     * Devuelve una lista de objetos Libro.
     */
    public List<Libro> listar() {

        // Se crea una lista vacía donde se almacenarán
        // todos los libros encontrados.
        List<Libro> lista = new ArrayList<>();

        // Consulta SQL que obtiene todos los registros,
        // ordenados alfabéticamente por título.
        String sql = "SELECT * FROM libros ORDER BY titulo";

        try (

                // Obtiene la conexión.
                Connection conexion = ConexionBD.getConexion();

                // Statement permite ejecutar consultas SQL
                // que no reciben parámetros.
                Statement st = conexion.createStatement();

                // Ejecuta la consulta.
                ResultSet rs = st.executeQuery(sql)

        ) {

            // Mientras existan registros...
            while (rs.next()) {

                // Se crea un nuevo objeto Libro.
                Libro libro = new Libro();

                // Se copian los datos del registro actual
                // al objeto.
                libro.setId(rs.getInt("id"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setAnio(rs.getInt("anio"));
                libro.setCategoria(rs.getString("categoria"));
                libro.setDisponible(rs.getBoolean("disponible"));

                // Se agrega el objeto a la lista.
                lista.add(libro);

            }

        } catch (SQLException e) {

            // Si ocurre algún error durante la consulta,
            // se muestra en la consola.
            e.printStackTrace();

        }

        // Devuelve la lista con todos los libros.
        return lista;

    }

    /**
     * =====================================================
     * MÉTODO listarDisponibles()
     * =====================================================
     *
     * Recupera únicamente los libros que
     * se encuentran disponibles para préstamo.
     *
     * Devuelve una lista de objetos Libro.
     */
    public List<Libro> listarDisponibles() {

        // Lista donde se almacenarán los libros encontrados.
        List<Libro> lista = new ArrayList<>();

        // Consulta SQL que únicamente obtiene los libros
        // cuyo campo disponible sea verdadero.
        String sql = """
            SELECT *
            FROM libros
            WHERE disponible = true
            ORDER BY titulo
            """;

        try (

                // Obtiene la conexión.
                Connection conexion = ConexionBD.getConexion();

                // Crea el objeto Statement.
                Statement st = conexion.createStatement();

                // Ejecuta la consulta.
                ResultSet rs = st.executeQuery(sql)

        ) {

            // Recorre todos los registros obtenidos.
            while (rs.next()) {

                // Crea un nuevo objeto Libro.
                Libro libro = new Libro();

                // Copia los datos del ResultSet
                // hacia el objeto Libro.
                libro.setId(rs.getInt("id"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setAnio(rs.getInt("anio"));
                libro.setCategoria(rs.getString("categoria"));
                libro.setDisponible(rs.getBoolean("disponible"));

                // Agrega el libro a la lista.
                lista.add(libro);

            }

        } catch (SQLException e) {

            // Muestra la información del error.
            e.printStackTrace();

        }

        // Devuelve la lista de libros disponibles.
        return lista;

    }

}