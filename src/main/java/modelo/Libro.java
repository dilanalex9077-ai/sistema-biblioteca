package modelo;

/**
 * La clase Libro representa un libro dentro del sistema de biblioteca.
 *
 * Esta clase pertenece al modelo de la aplicación, por lo que únicamente
 * almacena información y proporciona métodos para acceder o modificar
 * sus atributos.
 *
 * Cada objeto Libro representa un registro de la tabla "libros"
 * en la base de datos.
 *
 * @author UTTT
 */
public class Libro {

    // =====================================================
    // ATRIBUTOS
    // =====================================================
    // Los atributos almacenan la información de cada libro.
    // Se declaran como "private" para proteger los datos y
    // permitir que únicamente se acceda a ellos mediante
    // los métodos getters y setters.

    // Identificador único del libro dentro de la base de datos.
    private int id;

    // Código ISBN del libro.
    private String isbn;

    // Título del libro.
    private String titulo;

    // Nombre del autor del libro.
    private String autor;

    // Nombre de la editorial.
    private String editorial;

    // Año de publicación del libro.
    private int anio;

    // Categoría o género del libro.
    private String categoria;

    // Indica si el libro está disponible para préstamo.
    // true = Disponible
    // false = No disponible
    private boolean disponible;

    // =====================================================
    // CONSTRUCTOR VACÍO
    // =====================================================
    /**
     * Constructor sin parámetros.
     *
     * Permite crear un objeto Libro vacío para posteriormente
     * asignar sus valores mediante los métodos set.
     *
     * Ejemplo:
     *
     * Libro libro = new Libro();
     * libro.setTitulo("Java Básico");
     */
    public Libro() {
    }

    // =====================================================
    // CONSTRUCTOR CON PARÁMETROS
    // =====================================================
    /**
     * Constructor que recibe todos los datos del libro.
     *
     * Permite crear el objeto completamente inicializado
     * desde una sola instrucción.
     */
    public Libro(int id, String isbn, String titulo, String autor,
                 String editorial, int anio,
                 String categoria, boolean disponible) {

        // La palabra "this" hace referencia al objeto actual.
        // Se utiliza para diferenciar los atributos de la clase
        // de los parámetros del constructor.

        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.categoria = categoria;
        this.disponible = disponible;
    }

    // =====================================================
    // GETTERS Y SETTERS
    // =====================================================
    // Los métodos Getter permiten obtener el valor de un atributo.
    // Los métodos Setter permiten modificar el valor de un atributo.
    //
    // Esta práctica forma parte del principio de Encapsulamiento,
    // uno de los pilares de la Programación Orientada a Objetos.

    /**
     * Devuelve el identificador del libro.
     */
    public int getId() {
        return id;
    }

    /**
     * Modifica el identificador del libro.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el ISBN del libro.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Modifica el ISBN del libro.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Devuelve el título del libro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Modifica el título del libro.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Devuelve el nombre del autor.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Modifica el nombre del autor.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Devuelve el nombre de la editorial.
     */
    public String getEditorial() {
        return editorial;
    }

    /**
     * Modifica la editorial del libro.
     */
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    /**
     * Devuelve el año de publicación.
     */
    public int getAnio() {
        return anio;
    }

    /**
     * Modifica el año de publicación.
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * Devuelve la categoría del libro.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Modifica la categoría del libro.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Indica si el libro está disponible.
     *
     * En los atributos booleanos es común utilizar el prefijo
     * "is" en lugar de "get".
     *
     * Devuelve:
     * true  -> Disponible.
     * false -> No disponible.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Cambia el estado de disponibilidad del libro.
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // =====================================================
    // MÉTODO toString()
    // =====================================================
    /**
     * Este método devuelve una representación en texto del objeto.
     *
     * Se ejecuta automáticamente cuando un objeto Libro necesita
     * convertirse en una cadena de texto.
     *
     * Por ejemplo, si un JComboBox contiene objetos Libro,
     * mostrará el título en lugar de algo como:
     *
     * modelo.Libro@15db9742
     *
     * Gracias a este método, se visualizará únicamente el título.
     */
    @Override
    public String toString() {

        // Devuelve el título del libro.
        return titulo;
    }

}