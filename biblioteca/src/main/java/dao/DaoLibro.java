package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entidades.Libro;
import conexion.Conexion;

public class DaoLibro {

    public List<Libro> buscarLibros(String nombre, String titulo, String isbn) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Obtener conexión
            connection = conexion.getConexion();

            // Construir la consulta
            StringBuilder query = new StringBuilder();
            query.append("SELECT L.ISBN, L.TITULO, A.NOMBRE AS AUTOR, EJEMPLARESTOTALES, EJEMPLARESENPRESTAMO, ")
                 .append("(EJEMPLARESTOTALES - EJEMPLARESENPRESTAMO) AS EJEMPLARESDISPONIBLES ")
                 .append("FROM LIBRO L ")
                 .append("JOIN AUTOR A ON L.IDAUTOR = A.IDAUTOR ")
                 .append("JOIN ( ")
                 .append("  SELECT A.ISBN, EJEMPLARESTOTALES, NVL(EJEMPLARESENPRESTAMO, 0) AS EJEMPLARESENPRESTAMO ")
                 .append("  FROM ( ")
                 .append("    SELECT L.ISBN, COUNT(*) AS EJEMPLARESTOTALES ")
                 .append("    FROM LIBRO L, EJEMPLAR E ")
                 .append("    WHERE L.ISBN = E.ISBN AND E.BAJA = 'N' ")
                 .append("    GROUP BY L.ISBN ")
                 .append("  ) A LEFT JOIN ")
                 .append("  ( ")
                 .append("    SELECT ISBN, COUNT(*) AS EJEMPLARESENPRESTAMO ")
                 .append("    FROM PRESTAMO P, EJEMPLAR E ")
                 .append("    WHERE P.IDEJEMPLAR = E.IDEJEMPLAR ")
                 .append("    GROUP BY ISBN ")
                 .append("  ) B ON A.ISBN = B.ISBN ")
                 .append(") B ON L.ISBN = B.ISBN ");

            // Filtrar por autor, título e ISBN
            List<String> params = new ArrayList<>();
            if (nombre != null && !nombre.isEmpty()) {
                query.append("AND UPPER(TRANSLATE(A.NOMBRE, 'ÁÉÍÓÚ', 'AEIOU')) LIKE UPPER(TRANSLATE(?, 'áéíóú', 'aeiou')) ");
                params.add("%" + nombre + "%");
            }

            if (titulo != null && !titulo.isEmpty()) {
                query.append("AND TRANSLATE(UPPER(L.TITULO), 'ÁÉÍÓÚ', 'AEIOU') LIKE TRANSLATE(UPPER(?), 'áéíóú', 'aeiou') ");
                params.add("%" + titulo + "%");
            }
            if (isbn != null && !isbn.isEmpty()) {
                query.append("AND L.ISBN LIKE ? ");
                params.add(isbn);
            }

            query.append("ORDER BY A.NOMBRE, L.TITULO");

            // Preparar la consulta
            ps = connection.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            // Ejecutar la consulta
            rs = ps.executeQuery();

            // Procesar los resultados
            while (rs.next()) {
                String isbnResult = rs.getString("ISBN");
                String tituloResult = rs.getString("TITULO");
                String autor = rs.getString("AUTOR");
                int ejemplaresTotales = rs.getInt("EJEMPLARESTOTALES");
                int ejemplaresEnPrestamo = rs.getInt("EJEMPLARESENPRESTAMO");
                int ejemplaresDisponibles = rs.getInt("EJEMPLARESDISPONIBLES");

                // Crear un objeto Libro y añadirlo a la lista
                libros.add(new Libro(isbnResult, tituloResult, autor, ejemplaresTotales, ejemplaresEnPrestamo, ejemplaresDisponibles));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            // Cerrar recursos
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (connection != null) connection.close();
        }

        return libros;
    }
}
