package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexion.Conexion;
import entidades.Autor;

public class DaoAutor {
	/**
    *
    * @return Devuelve un ArrayList de objetos autor con los autores
    * hay actualmente en la tabla AUTOR de nuestra base de datos.
    * @throws SQLException: Cualquier error en el acceso o en la ejecución
    */
   public ArrayList<Autor>listadoAutores() throws SQLException,Exception{
       
       ArrayList<Autor>listadoAutores=new ArrayList<>();
       Conexion conexion=new Conexion(); // Creamos un objeto Conexion.
       Connection con=null; // Objeto para conectar a la bbdd.
       ResultSet rs = null; // Donde recojo los resultados de la consulta
       Statement st = null; // Para crear la consulta.
       try {
           con=conexion.getConexion(); //Obtenemos el objeto java.sql.Connection
           st = con.createStatement();// Creamos un objeto Statement
           // Un objeto Statement permite ejecutar una sentencia SQL estática
           // y retornar los resultados que produce
           String ordenSQL = "SELECT * FROM AUTOR ORDER By NOMBRE"; // sentencia a ejecutar
           rs = st.executeQuery(ordenSQL);    
           // el método executeQuery ejecuta la sentencia y devuelve los resultados
           // en un objeto ResultSet
           // El objeto ResulSet representa el conjunto de resultados de la consulta
           // Mantiene un cursor a la fila actual de datos
           // Inicialmente apunta a la primera fila.
           // El método next mueve el cursor a la siguiente fila
           // next() devuelve false cuando ha llegado a la última fila.
           while (rs.next()) {
               // Por cada fila obtenida, creamos un objeto autor
               // que añadimos al ArrayList listadoAutores.
               Autor miAutor = new Autor();
               miAutor.setIdAutor(rs.getInt("IDAUTOR"));
               miAutor.setNombre(rs.getString("NOMBRE"));
               miAutor.setFechaNacimiento(rs.getDate("FECHANACIMIENTO"));
               listadoAutores.add(miAutor);
           }            
       } catch (SQLException e) {
           //e.printStackTrace();
           throw e;
       } catch (Exception ex) {
           //ex.printStackTrace();
           throw ex;
       }
       finally {
           // liberamos los recursos en un finally para asegurarnos que se cierra
           // todo lo abierto
           if(rs!=null)
               rs.close();
           if(st!=null)
               st.close();
           if(con!=null)
               con.close();
       }
       return listadoAutores; // retornamos el resultado en forma de array
   }

}
