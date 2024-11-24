package entidades;

public class Libro {

    private String isbn;  // Cambiado a String para que coincida con la consulta
    private String titulo;
    private String autor;  // Cambiado de idAutor a autor (String)
    private int ejemplaresTotales;
    private int ejemplaresEnPrestamo;
    private int ejemplaresDisponibles;

    // Constructor con los parámetros esperados
    public Libro(String isbn, String titulo, String autor, int ejemplaresTotales, int ejemplaresEnPrestamo, int ejemplaresDisponibles) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.ejemplaresTotales = ejemplaresTotales;
        this.ejemplaresEnPrestamo = ejemplaresEnPrestamo;
        this.ejemplaresDisponibles = ejemplaresDisponibles;
    }

    // Getters y setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getEjemplaresTotales() {
        return ejemplaresTotales;
    }

    public void setEjemplaresTotales(int ejemplaresTotales) {
        this.ejemplaresTotales = ejemplaresTotales;
    }

    public int getEjemplaresEnPrestamo() {
        return ejemplaresEnPrestamo;
    }

    public void setEjemplaresEnPrestamo(int ejemplaresEnPrestamo) {
        this.ejemplaresEnPrestamo = ejemplaresEnPrestamo;
    }

    public int getEjemplaresDisponibles() {
        return ejemplaresDisponibles;
    }

    public void setEjemplaresDisponibles(int ejemplaresDisponibles) {
        this.ejemplaresDisponibles = ejemplaresDisponibles;
    }

    @Override
    public String toString() {
        return "Libro [isbn=" + isbn + ", titulo=" + titulo + ", autor=" + autor 
               + ", ejemplaresTotales=" + ejemplaresTotales 
               + ", ejemplaresEnPrestamo=" + ejemplaresEnPrestamo 
               + ", ejemplaresDisponibles=" + ejemplaresDisponibles + "]";
    }
}
