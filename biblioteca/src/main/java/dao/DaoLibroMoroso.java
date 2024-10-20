package dao;

import java.sql.Connection; // Importa la clase Connection para manejar la conexión a la base de datos.
import java.sql.PreparedStatement; // Importa PreparedStatement para ejecutar consultas parametrizadas.
import java.sql.ResultSet; // Importa ResultSet para almacenar los resultados de una consulta SQL.
import java.sql.SQLException; // Importa SQLException para manejar errores de SQL.
import java.util.ArrayList; // Importa ArrayList para utilizar listas dinámicas.

import conexion.Conexion; // Importa la clase Conexion para obtener conexiones a la base de datos.
import entidades.LibroMoroso; // Importa la clase LibroMoroso que representa la entidad LibroMoroso.

public class DaoLibroMoroso {

    /**
     * Método que devuelve un ArrayList de objetos LibroMoroso.
     * @param idSocio - ID del socio para el que se desean obtener los libros morosos.
     * @return ArrayList<LibroMoroso> - lista de libros morosos asociados al socio.
     * @throws SQLException - en caso de error en el acceso o ejecución de la consulta.
     * @throws Exception - cualquier otro error que pueda ocurrir.
     */
    public ArrayList<LibroMoroso> listadoLibrosMorosos(int idSocio) throws SQLException, Exception {
        ArrayList<LibroMoroso> listadoLibrosMorosos = new ArrayList<>(); // Inicializa el ArrayList para almacenar libros morosos.
        Conexion conexion = new Conexion(); // Crea un objeto de conexión a la base de datos.
        Connection con = null; // Variable para almacenar la conexión.
        ResultSet rs = null; // Variable para almacenar el resultado de la consulta.
        PreparedStatement st = null; // Variable para preparar la consulta SQL.

        try {
            con = conexion.getConexion(); // Obtiene la conexión a la base de datos.

            // SQL para obtener los libros morosos asociados al socio especificado.
            String ordenSQL = "SELECT \n"
                    + "    l.TITULO AS TITULOLIBRO,             \n"
                    + "    p.FECHAPRESTAMO,                      \n"
                    + "    TRUNC(SYSDATE - p.FECHALIMITEDEVOLUCION) AS DIASDEMORA \n"
                    + "FROM \n"
                    + "    PRESTAMO p\n"
                    + "INNER JOIN EJEMPLAR e ON p.IDEJEMPLAR = e.IDEJEMPLAR\n"
                    + "INNER JOIN LIBRO l ON e.ISBN = l.ISBN\n"
                    + "WHERE \n"
                    + "    p.IDSOCIO = ?                 \n" 
                    + "    AND p.FECHALIMITEDEVOLUCION < SYSDATE  \n"
                    + "    AND NOT EXISTS (\n"
                    + "        SELECT 1 \n"
                    + "        FROM DEVOLUCION d \n"
                    + "        WHERE d.IDSOCIO = p.IDSOCIO \n"
                    + "        AND d.IDEJEMPLAR = p.IDEJEMPLAR)"; // Asegura que no exista una devolución para el ejemplar.

            st = con.prepareStatement(ordenSQL); // Prepara la consulta.
            st.setInt(1, idSocio); // Establece el ID del socio en la consulta.
            rs = st.executeQuery(); // Ejecuta la consulta y almacena los resultados.

            // Itera a través de los resultados del ResultSet.
            while (rs.next()) {
                // Crea un nuevo objeto LibroMoroso por cada fila obtenida.
                LibroMoroso libroMoroso = new LibroMoroso();
                libroMoroso.setTituloLibro(rs.getString("TITULOLIBRO")); // Establece el título del libro.
                libroMoroso.setFechaPrestamo(rs.getDate("FECHAPRESTAMO")); // Establece la fecha de préstamo.
                libroMoroso.setDiasDemora(rs.getInt("DIASDEMORA")); // Establece el número de días de demora.
                listadoLibrosMorosos.add(libroMoroso); // Añade el libro moroso al ArrayList.
            }
        } catch (SQLException e) {
            throw e; // Relanza la excepción si ocurre un error SQL.
        } catch (Exception ex) {
            throw ex; // Relanza cualquier otro tipo de excepción.
        } finally {
            // Liberación de recursos en el bloque finally para asegurar que se cierran.
            if (rs != null) rs.close(); // Cierra el ResultSet si está abierto.
            if (st != null) st.close(); // Cierra el PreparedStatement si está abierto.
            if (con != null) con.close(); // Cierra la conexión si está abierta.
        }
        return listadoLibrosMorosos; // Devuelve la lista de libros morosos.
    }
}
