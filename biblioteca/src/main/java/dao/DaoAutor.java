package dao;

import java.sql.Connection; // Importa la clase Connection para manejar la conexión a la base de datos.
import java.sql.PreparedStatement; // Importa PreparedStatement para ejecutar consultas parametrizadas.
import java.sql.ResultSet; // Importa ResultSet para almacenar los resultados de una consulta SQL.
import java.sql.SQLException; // Importa SQLException para manejar errores de SQL.
import java.util.ArrayList; // Importa ArrayList para utilizar listas dinámicas.

import conexion.Conexion; // Importa la clase Conexion para obtener conexiones a la base de datos.
import entidades.Autor; // Importa la clase Autor que representa la entidad Autor.

public class DaoAutor {

    /**
     * Método que devuelve un ArrayList de objetos Autor.
     * @return ArrayList<Autor> - lista de autores en la tabla AUTOR de la base de datos.
     * @throws SQLException - en caso de error en el acceso o ejecución de la consulta.
     * @throws Exception - cualquier otro error que pueda ocurrir.
     */
    public ArrayList<Autor> listadoAutores() throws SQLException, Exception {
        ArrayList<Autor> listadoAutores = new ArrayList<>(); // Inicializa el ArrayList para almacenar autores.
        Conexion conexion = new Conexion(); // Crea un objeto de conexión a la base de datos.
        Connection con = null; // Variable para almacenar la conexión.
        ResultSet rs = null; // Variable para almacenar el resultado de la consulta.
        PreparedStatement st = null; // Variable para preparar la consulta SQL.

        try {
            con = conexion.getConexion(); // Obtiene la conexión a la base de datos.
            // SQL para obtener todos los autores ordenados por IDAUTOR.
            String ordenSQL = "SELECT * FROM AUTOR ORDER By IDAUTOR"; 
            st = con.prepareStatement(ordenSQL); // Prepara la consulta.
            rs = st.executeQuery(ordenSQL); // Ejecuta la consulta y almacena el resultado.

            // Itera a través de los resultados del ResultSet.
            while (rs.next()) {
                // Crea un nuevo objeto Autor por cada fila obtenida.
                Autor miAutor = new Autor();
                // Establece los atributos del autor a partir de los resultados.
                miAutor.setIdAutor(rs.getInt("IDAUTOR"));
                miAutor.setNombre(rs.getString("NOMBRE"));
                miAutor.setFechaNacimiento(rs.getDate("FECHANACIMIENTO"));
                listadoAutores.add(miAutor); // Añade el autor al ArrayList.
            }
        } catch (SQLException e) {
            // Relanza la excepción si ocurre un error SQL.
            throw e;
        } catch (Exception ex) {
            // Relanza cualquier otro tipo de excepción.
            throw ex;
        } finally {
            // Liberación de recursos en el bloque finally para asegurar que se cierran.
            if (rs != null) rs.close(); // Cierra el ResultSet si está abierto.
            if (st != null) st.close(); // Cierra el PreparedStatement si está abierto.
            if (con != null) con.close(); // Cierra la conexión si está abierta.
        }
        return listadoAutores; // Devuelve la lista de autores.
    }

    /**
     * Método para insertar un nuevo autor en la base de datos.
     * @param a - objeto Autor que se desea insertar.
     * @throws SQLException - en caso de error en el acceso o ejecución de la consulta.
     * @throws Exception - cualquier otro error que pueda ocurrir.
     */
    public void insertaAutor(Autor a) throws SQLException, Exception {
        Connection con = null; // Variable para almacenar la conexión.
        PreparedStatement st = null; // Variable para preparar la consulta SQL.

        try {
            Conexion miconex = new Conexion(); // Crea un nuevo objeto de conexión.
            con = miconex.getConexion(); // Obtiene la conexión a la base de datos.
            // SQL para insertar un nuevo autor en la tabla AUTOR.
            String ordenSQL = "INSERT INTO AUTOR VALUES(S_AUTOR.NEXTVAL,?,?)";
            st = con.prepareStatement(ordenSQL); // Prepara la consulta para la inserción.
            st.setString(1, a.getNombre()); // Establece el nombre del autor en la consulta.
            st.setDate(2, a.getFechaNacimiento()); // Establece la fecha de nacimiento en la consulta.
            st.executeUpdate(); // Ejecuta la actualización para insertar el autor.
        } catch (SQLException se) {
            // Relanza la excepción si ocurre un error SQL.
            throw se;
        } catch (Exception e) {
            // Relanza cualquier otro tipo de excepción.
            throw e;
        } finally {
            // Liberación de recursos en el bloque finally para asegurar que se cierran.
            if (st != null) st.close(); // Cierra el PreparedStatement si está abierto.
            if (con != null) con.close(); // Cierra la conexión si está abierta.
        }
    }
}
