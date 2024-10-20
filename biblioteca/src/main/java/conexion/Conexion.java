package conexion;

import java.sql.Connection; // Importa la clase Connection para establecer la conexión a la base de datos.
import java.sql.DatabaseMetaData; // Importa la clase DatabaseMetaData para obtener información sobre la base de datos.
import java.sql.SQLException; // Importa la clase SQLException para manejar errores relacionados con la base de datos.
import oracle.jdbc.pool.OracleDataSource; // Importa OracleDataSource, que es un proveedor de conexiones para Oracle.

public class Conexion {
    
    // Constructor de la clase. En este caso, no realiza ninguna operación específica.
    public Conexion() {
    }

    // Método para obtener una conexión a la base de datos.
    public Connection getConexion() throws SQLException, Exception {
        // URL de conexión a la base de datos. 
        // Incluye el tipo de driver, el nombre de usuario, la contraseña y la dirección del servidor.
        String url = "jdbc:oracle:thin:VS2DAWBIBLIOTECA4/VS2DAWBIBLIOTECA4@80.28.158.14:1521:oradai";
        
        // Declaración de variables para la conexión y el datasource.
        Connection con; // Variable para almacenar la conexión.
        OracleDataSource ods; // Variable para el datasource de Oracle.

        try {
            // Crea una nueva instancia de OracleDataSource.
            ods = new OracleDataSource();
            // Establece la URL de conexión en el datasource.
            ods.setURL(url);
            // Obtiene la conexión usando el datasource.
            con = ods.getConnection();
            // Obtiene los metadatos de la base de datos.
            DatabaseMetaData meta = con.getMetaData();
            // Imprime la versión del driver JDBC.
            System.out.println("JDBC driver version is " + meta.getDriverVersion());
            // Imprime un mensaje indicando que la conexión se ha establecido.
            System.out.println("Data Source definido y conexión establecida");
        } catch (SQLException sqle) {
            // Captura y muestra el error si ocurre una SQLException.
            System.out.println(sqle.toString());
            // Relanza la excepción para manejarla más arriba en la cadena de llamadas.
            throw sqle;
        } catch (Exception e) {
            // Captura y muestra cualquier otra excepción que no sea SQLException.
            System.out.println(e.toString());
            // Relanza la excepción.
            throw e;
        }
        // Devuelve la conexión establecida.
        return con;
    }
}
