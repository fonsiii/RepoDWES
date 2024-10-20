package dao;

import java.sql.Connection; // Importa la clase Connection para manejar conexiones a la base de datos.
import java.sql.PreparedStatement; // Importa PreparedStatement para ejecutar consultas parametrizadas.
import java.sql.ResultSet; // Importa ResultSet para almacenar los resultados de una consulta SQL.
import java.sql.SQLException; // Importa SQLException para manejar errores de SQL.
import java.util.ArrayList; // Importa ArrayList para utilizar listas dinámicas.

import conexion.Conexion; // Importa la clase Conexion para obtener conexiones a la base de datos.
import entidades.SocioMoroso; // Importa la clase SocioMoroso que representa la entidad SocioMoroso.

public class DaoSocioMoroso {

    /**
     * Método que devuelve una lista de todos los socios morosos.
     * @return ArrayList<SocioMoroso> - lista de socios morosos en la base de datos.
     * @throws SQLException - en caso de error en el acceso o ejecución de la consulta.
     * @throws Exception - cualquier otro error que pueda ocurrir.
     */
    public ArrayList<SocioMoroso> listadoSociosMorosos() throws SQLException, Exception {
        ArrayList<SocioMoroso> listadoSociosMorosos = new ArrayList<>(); // Inicializa el ArrayList para almacenar los socios morosos.
        Conexion conexion = new Conexion(); // Crea un objeto de conexión a la base de datos.
        Connection con = null; // Variable para almacenar la conexión.
        ResultSet rs = null; // Variable para almacenar el resultado de la consulta.
        PreparedStatement st = null; // Variable para preparar la consulta SQL.

        try {
            con = conexion.getConexion(); // Obtiene la conexión a la base de datos.

            // SQL para seleccionar los socios que tienen préstamos en mora.
            String ordenSQL = "SELECT \n"
                    + "    s.IDSOCIO, \n"
                    + "    s.NOMBRE\n"
                    + "FROM \n"
                    + "    SOCIO s\n"
                    + "INNER JOIN PRESTAMO p ON s.IDSOCIO = p.IDSOCIO\n"
                    + "WHERE \n"
                    + "    p.FECHALIMITEDEVOLUCION < SYSDATE\n"
                    + "    AND NOT EXISTS (\n"
                    + "        SELECT 1 \n"
                    + "        FROM DEVOLUCION d \n"
                    + "        WHERE d.IDSOCIO = p.IDSOCIO \n"
                    + "        AND d.IDEJEMPLAR = p.IDEJEMPLAR\n"
                    + "    )\n"
                    + "GROUP BY \n"
                    + "    s.IDSOCIO, \n"
                    + "    s.NOMBRE";

            st = con.prepareStatement(ordenSQL); // Prepara la consulta.
            rs = st.executeQuery(); // Ejecuta la consulta y almacena los resultados.

            // Itera a través de los resultados del ResultSet.
            while (rs.next()) {
                // Crea un nuevo objeto SocioMoroso por cada fila obtenida.
                SocioMoroso socioMoroso = new SocioMoroso();
                socioMoroso.setId(rs.getInt("IDSOCIO")); // Establece el ID del socio.
                socioMoroso.setNombre(rs.getString("NOMBRE")); // Establece el nombre del socio.
                listadoSociosMorosos.add(socioMoroso); // Añade el socio moroso al ArrayList.
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
        return listadoSociosMorosos; // Devuelve la lista de socios morosos.
    }

    /**
     * Método que busca un socio por su ID.
     * @param idSocio - ID del socio a buscar.
     * @return SocioMoroso - objeto SocioMoroso encontrado o null si no existe.
     * @throws SQLException - en caso de error en el acceso o ejecución de la consulta.
     * @throws Exception - cualquier otro error que pueda ocurrir.
     */
    public SocioMoroso buscarSocioPorId(int idSocio) throws SQLException, Exception {
        SocioMoroso socioMoroso = null; // Inicializa el objeto SocioMoroso a null.
        Conexion conexion = new Conexion(); // Crea un objeto de conexión a la base de datos.
        Connection con = null; // Variable para almacenar la conexión.
        PreparedStatement st = null; // Variable para preparar la consulta SQL.
        ResultSet rs = null; // Variable para almacenar el resultado de la consulta.

        try {
            con = conexion.getConexion(); // Obtiene la conexión a la base de datos.
            String sql = "SELECT * FROM SOCIO WHERE IDSOCIO = ?"; // Consulta SQL para buscar el socio por ID.

            st = con.prepareStatement(sql); // Prepara la consulta.
            st.setInt(1, idSocio); // Establece el ID del socio en la consulta.
            rs = st.executeQuery(); // Ejecuta la consulta y almacena los resultados.

            if (rs.next()) { // Si hay un resultado, crea el objeto SocioMoroso.
                socioMoroso = new SocioMoroso(); // Inicializa el objeto SocioMoroso.
                socioMoroso.setId(rs.getInt("IDSOCIO")); // Establece el ID del socio.
                socioMoroso.setNombre(rs.getString("NOMBRE")); // Establece el nombre del socio.
            }
        } catch (SQLException e) {
            throw e; // Relanza la excepción si ocurre un error SQL.
        } catch (Exception ex) {
            throw ex; // Relanza cualquier otro tipo de excepción.
        } finally {
            if (rs != null) rs.close(); // Cierra el ResultSet si está abierto.
            if (st != null) st.close(); // Cierra el PreparedStatement si está abierto.
            if (con != null) con.close(); // Cierra la conexión si está abierta.
        }
        return socioMoroso; // Devuelve el socio encontrado o null si no existe.
    }
}
