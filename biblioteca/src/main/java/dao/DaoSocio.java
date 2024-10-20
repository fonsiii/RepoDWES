package dao;

import java.sql.Connection; // Importa la clase Connection para manejar conexiones a la base de datos.
import java.sql.PreparedStatement; // Importa PreparedStatement para ejecutar consultas parametrizadas.
import java.sql.ResultSet; // Importa ResultSet para almacenar los resultados de una consulta SQL.
import java.sql.SQLException; // Importa SQLException para manejar errores de SQL.
import java.util.ArrayList; // Importa ArrayList para utilizar listas dinámicas.

import conexion.Conexion; // Importa la clase Conexion para obtener conexiones a la base de datos.
import entidades.Socio; // Importa la clase Socio que representa la entidad Socio.

public class DaoSocio {

    /**
     * Método que devuelve una lista de todos los socios.
     * @return ArrayList<Socio> - lista de socios en la base de datos.
     * @throws SQLException - en caso de error en el acceso o ejecución de la consulta.
     * @throws Exception - cualquier otro error que pueda ocurrir.
     */
    public ArrayList<Socio> listadoSocios() throws SQLException, Exception {
        ArrayList<Socio> listadoSocios = new ArrayList<>(); // Inicializa el ArrayList para almacenar los socios.
        Conexion conexion = new Conexion(); // Crea un objeto de conexión a la base de datos.
        Connection con = null; // Variable para almacenar la conexión.
        ResultSet rs = null; // Variable para almacenar el resultado de la consulta.
        PreparedStatement st = null; // Variable para preparar la consulta SQL.
        
        try {
            con = conexion.getConexion(); // Obtiene la conexión a la base de datos.

            // SQL para seleccionar todos los socios y ordenarlos por ID.
            String ordenSQL = "SELECT * FROM SOCIO ORDER BY IDSOCIO";
            st = con.prepareStatement(ordenSQL); // Prepara la consulta.
            rs = st.executeQuery(); // Ejecuta la consulta y almacena los resultados.
            
            // Itera a través de los resultados del ResultSet.
            while (rs.next()) {
                // Crea un nuevo objeto Socio por cada fila obtenida.
                Socio miSocio = new Socio();
                miSocio.setIdSocio(rs.getInt("IDSOCIO")); // Establece el ID del socio.
                miSocio.setNombre(rs.getString("NOMBRE")); // Establece el nombre del socio.
                miSocio.setDireccion(rs.getString("DIRECCION")); // Establece la dirección del socio.
                miSocio.setEmail(rs.getString("EMAIL")); // Establece el email del socio.
                miSocio.setVersion(rs.getInt("VERSION")); // Establece la versión del socio.
                listadoSocios.add(miSocio); // Añade el socio al ArrayList.
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
        return listadoSocios; // Devuelve la lista de socios.
    }

    /**
     * Método que inserta un nuevo socio en la base de datos.
     * @param s - objeto Socio a insertar.
     * @throws SQLException - en caso de error en el acceso o ejecución de la consulta.
     * @throws Exception - cualquier otro error que pueda ocurrir.
     */
    public void insertaSocio(Socio s) throws SQLException, Exception {
        Connection con = null; // Variable para almacenar la conexión.
        PreparedStatement st = null; // Variable para preparar la consulta SQL.

        try {
            Conexion miconex = new Conexion(); // Crea un objeto de conexión a la base de datos.
            con = miconex.getConexion(); // Obtiene la conexión a la base de datos.
            
            // SQL para insertar un nuevo socio. Se utiliza S_AUTOR.NEXTVAL para obtener un nuevo ID.
            String ordenSQL = "INSERT INTO SOCIO VALUES(S_AUTOR.NEXTVAL,?,?,?,?)";
            st = con.prepareStatement(ordenSQL); // Prepara la consulta.
            st.setString(1, s.getEmail()); // Establece el email del socio.
            st.setString(2, s.getNombre()); // Establece el nombre del socio.
            st.setString(3, s.getDireccion()); // Establece la dirección del socio.
            st.setInt(4, s.getVersion()); // Establece la versión del socio.
            st.executeUpdate(); // Ejecuta la inserción.
            st.close(); // Cierra el PreparedStatement.
            con.close(); // Cierra la conexión.
        } catch (SQLException se) {
            throw se; // Relanza la excepción si ocurre un error SQL.
        } catch (Exception e) {
            throw e; // Relanza cualquier otro tipo de excepción.
        } finally {
            if (st != null) st.close(); // Cierra el PreparedStatement si está abierto.
            if (con != null) con.close(); // Cierra la conexión si está abierta.
        }
    }

    /**
     * Método que busca socios por nombre.
     * @param nombre - nombre del socio a buscar.
     * @return ArrayList<Socio> - lista de socios que coinciden con el nombre dado.
     * @throws SQLException - en caso de error en el acceso o ejecución de la consulta.
     * @throws Exception - cualquier otro error que pueda ocurrir.
     */
    public ArrayList<Socio> buscarSocioPorNombre(String nombre) throws SQLException, Exception {
        ArrayList<Socio> listadoSocios = new ArrayList<>(); // Inicializa el ArrayList para almacenar los socios encontrados.
        Conexion miconex = new Conexion(); // Crea un objeto de conexión a la base de datos.
        Connection con = null; // Variable para almacenar la conexión.
        ResultSet rs = null; // Variable para almacenar el resultado de la consulta.
        PreparedStatement st = null; // Variable para preparar la consulta SQL.
        
        try {
            con = miconex.getConexion(); // Obtiene la conexión a la base de datos.
            
            // SQL para buscar socios cuyo nombre coincida con el parámetro.
            String ordenSQL = "SELECT * FROM SOCIO WHERE LOWER(NOMBRE) LIKE LOWER(?)";
            st = con.prepareStatement(ordenSQL); // Prepara la consulta.
            
            st.setString(1, "%" + nombre + "%"); // Establece el nombre en la consulta, permitiendo coincidencias parciales.
            
            rs = st.executeQuery(); // Ejecuta la consulta y almacena los resultados.
            
            // Itera a través de los resultados del ResultSet.
            while (rs.next()) {
                // Crea un nuevo objeto Socio por cada fila obtenida.
                Socio miSocio = new Socio();
                miSocio.setNombre(rs.getString("NOMBRE")); // Establece el nombre del socio.
                miSocio.setDireccion(rs.getString("DIRECCION")); // Establece la dirección del socio.
                miSocio.setEmail(rs.getString("EMAIL")); // Establece el email del socio.
                miSocio.setVersion(rs.getInt("VERSION")); // Establece la versión del socio.
                
                listadoSocios.add(miSocio); // Añade el socio a la lista.
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
        return listadoSocios; // Devuelve la lista de socios encontrados.
    }
}
