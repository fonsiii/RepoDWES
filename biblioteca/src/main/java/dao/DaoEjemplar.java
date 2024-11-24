package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import conexion.Conexion;
import entidades.Ejemplar;

public class DaoEjemplar {
	
	public Ejemplar ejemplarById(int idEjemplar) throws SQLException, Exception {
		
		Conexion conexion=new Conexion(); //Creamos un objeto conexion
		Connection con=null;//Objeto para conectar a la bbdd.
		ResultSet rs=null; //Donde recojo los resultados de la consulta
		PreparedStatement st = null; //para consultas parametrizadas (que le metod datos desde fuera)
		Ejemplar ejemplar=null;
		
		try {
			
			con=conexion.getConexion();
			String ordenSQL="SELECT * FROM EJEMPLAR WHERE IDEJEMPLAR = ?"; //Consulta con parametro
			st=con.prepareStatement(ordenSQL);
			st.setInt(1, idEjemplar); //Asignamos el valor de ID al parámetro en la consulta
			
			rs=st.executeQuery(); //Ejecutamos la consulta
			
			if(rs.next()) { //Si hay resultado
				ejemplar=new Ejemplar();//creamos el objeto ejemplar
				ejemplar.setIdEjemplar(rs.getInt("IDEJEMPLAR"));
				ejemplar.setIsbn(rs.getString("ISBN"));
				ejemplar.setBaja(rs.getString("BAJA"));
			}
			
		} catch (SQLException e) {
	        throw e;
	    } catch (Exception ex) {
	        throw ex;
	    } finally {
	        // Cerramos recursos
	        if (rs != null) rs.close();
	        if (st != null) st.close();
	        if (con != null) con.close();
	    }
	    return ejemplar; // Retorna el ejemplar o null si no existe
		
	}
	
	public Ejemplar buscarIsbn (int idEjemplar) throws SQLException, Exception {
		
		Conexion conexion=new Conexion(); //Creamos un objeto conexion
		Connection con=null;//Objeto para conectar a la bbdd.
		ResultSet rs=null; //Donde recojo los resultados de la consulta
		PreparedStatement st = null; //para consultas parametrizadas (que le metod datos desde fuera)
		Ejemplar ejemplar=null;
		
		try {
			
			con=conexion.getConexion();
			String ordenSQL="SELECT ISBN FROM EJEMPLAR WHERE IDEJEMPLAR=?";
			st=con.prepareStatement(ordenSQL);
			st.setInt(1, idEjemplar); //Asignamos el valor de ID al parámetro en la consulta
			
			rs=st.executeQuery();
			
			if(rs.next()) {
				ejemplar=new Ejemplar();
				ejemplar.setIsbn(rs.getString("ISBN"));
			}
			
			
		} catch (SQLException e) {
	        throw e;
	    } catch (Exception ex) {
	        throw ex;
	    } finally {
	        // Cerramos recursos
	        if (rs != null) rs.close();
	        if (st != null) st.close();
	        if (con != null) con.close();
	    }
	    return ejemplar; 
		
	}
	
	public ArrayList<Ejemplar> buscarEjemplaresPorISBN(String isbn)throws SQLException, Exception {
		
		ArrayList<Ejemplar>buscarEjemplarPorISBN=new ArrayList<>();
		Conexion conexion=new Conexion(); //Creamos un objeto conexion
		Connection con=null;//Objeto para conectar a la bbdd.
		ResultSet rs=null; //Donde recojo los resultados de la consulta
		PreparedStatement st = null; //para consultas parametrizadas (que le metod datos desde fuera)
		
		try {
			
			con=conexion.getConexion();
			String ordenSQL="SELECT IDEJEMPLAR, ISBN FROM EJEMPLAR WHERE ISBN=?";
			st=con.prepareStatement(ordenSQL);
			st.setString(1, isbn);
			
			rs=st.executeQuery();
			
			while(rs.next()) {
				Ejemplar ejemplar=new Ejemplar();
				ejemplar.setIdEjemplar(rs.getInt("IDEJEMPLAR"));
				ejemplar.setIsbn(rs.getString("ISBN"));
				buscarEjemplarPorISBN.add(ejemplar);
			}
			
		} catch (SQLException e) {
	        throw e;
	    } catch (Exception ex) {
	        throw ex;
	    } finally {
	        // Cerramos recursos
	        if (rs != null) rs.close();
	        if (st != null) st.close();
	        if (con != null) con.close();
	    }
	    return buscarEjemplarPorISBN; 
		
	}

}
