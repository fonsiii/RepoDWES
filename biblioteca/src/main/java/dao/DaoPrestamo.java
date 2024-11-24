package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.ResultSet;

import conexion.Conexion;
import entidades.Prestamo;
import entidades.Socio;

public class DaoPrestamo {

	public ArrayList<Prestamo>sociosFueraDePlazo(int idsocio)throws SQLException, Exception{
		ArrayList<Prestamo>socioFueraDePlazo=new ArrayList<>();
		Conexion conexion=new Conexion(); 
		Connection con=null;
		ResultSet rs=null; 
		PreparedStatement st = null; 
		
		try {
			con=conexion.getConexion(); 
			
			String ordenSQL="SELECT E.IDEJEMPLAR, L.TITULO, P.FECHAPRESTAMO, P.FECHALIMITEDEVOLUCION, "
                    + "TRUNC(SYSDATE - P.FECHALIMITEDEVOLUCION) AS DIAS_DE_RETRASO "
                    + "FROM PRESTAMO P "
                    + "JOIN EJEMPLAR E ON P.IDEJEMPLAR = E.IDEJEMPLAR "
                    + "JOIN LIBRO L ON E.ISBN = L.ISBN "
                    + "WHERE P.IDSOCIO = ?";
			
			 st = con.prepareStatement(ordenSQL);
		     st.setInt(1, idsocio); 
		     rs=st.executeQuery();
		     
		     while(rs.next()) {
		    	 Prestamo miPrestamo=new Prestamo();
		    	 miPrestamo.setIdEjemplar(rs.getInt("IDEJEMPLAR"));
		    	 miPrestamo.setTitulo(rs.getString("TITULO"));
		    	 miPrestamo.setFechaPrestamo(rs.getDate("FECHAPRESTAMO"));
		    	 miPrestamo.setFechaLimiteDevolucion(rs.getDate("FECHALIMITEDEVOLUCION"));
		    	 miPrestamo.setDiasRetraso(rs.getInt("DIASRETRASO"));
		    	 socioFueraDePlazo.add(miPrestamo);
		     }
		}catch (SQLException e) {
			//e.printStackTrace();
			throw e;
		}catch (Exception ex) {
			//ex.printStackTrace();
			throw ex;
		}
		finally {
			if(rs!=null)
				rs.close();
			if(st!=null)
				st.close();
			if(con!=null)
				con.close();
		}
		
		
		return socioFueraDePlazo;
		
		
	}
	
	public ArrayList<Prestamo>seleccionarEjemplares(int idEjemplar)throws SQLException, Exception{
		ArrayList<Prestamo>seleccionarEjemplar=new ArrayList<>();
		Conexion conexion=new Conexion(); //Creamos un objeto conexion
		Connection con=null;//Objeto para conectar a la bbdd.
		ResultSet rs=null; //Donde recojo los resultados de la consulta
		Statement st=null; //Para crear la consulta
		PreparedStatement ps = null; // Usamos PreparedStatement para parametrizar la consulta
		
		try {
			
			con=conexion.getConexion();
			
			String ordenSQL="SELECT * FROM PRESTAMO WHERE IDEJEMPLAR=?";
			ps = con.prepareStatement(ordenSQL);
		    ps.setInt(1, idEjemplar);
		    
		    rs=ps.executeQuery();
		    
		    while(rs.next()) {
		    	Prestamo miPrestamo=new Prestamo();
		    	miPrestamo.setIdEjemplar(rs.getInt("IDEJEMPLAR"));
		    	miPrestamo.setIdSocio(rs.getInt("IDSOCIO"));
		    	miPrestamo.setFechaPrestamo(rs.getDate("FECHAPRESTAMO"));
		    	miPrestamo.setFechaLimiteDevolucion(rs.getDate("FECHALIMITEDEVOLUCION"));
		    	seleccionarEjemplar.add(miPrestamo);
		    }
			
		}catch (SQLException e) {
			//e.printStackTrace();
			throw e;
		}catch (Exception ex) {
			//ex.printStackTrace();
			throw ex;
		}
		finally {
			//liberamos los recursos en un finally para asegurarnos de que se cierra
			//todo lo abierto
			if(rs!=null)
				rs.close();
			if(st!=null)
				st.close();
			if(con!=null)
				con.close();
		}
		
		
		
		
		
		return seleccionarEjemplar;
		
	}
	
	public Prestamo listadoPrestamoBYId(int idEjemplar) throws SQLException, Exception {
		Prestamo prestamo = null;
		Conexion conexion=new Conexion(); //Creamos un objeto conexion
		Connection con=null;//Objeto para conectar a la bbdd.
		ResultSet rs=null; //Donde recojo los resultados de la consulta
		PreparedStatement st = null; //para consultas parametrizadas (que le metod datos desde fuera)
		
		try {
			
			con=conexion.getConexion();
			String ordenSQL="SELECT * FROM PRESTAMO WHERE IDEJEMPLAR=?";
			st=con.prepareStatement(ordenSQL);
			st.setInt(1, idEjemplar); //Asignamos el valor de ID al parámetro en la consulta
			
			rs=st.executeQuery(); //Ejecutamos la consulta
			
			if(rs.next()) { //Si hay resultado
				prestamo=new Prestamo();
				prestamo.setIdEjemplar(rs.getInt("IDEJEMPLAR"));
				prestamo.setIdSocio(rs.getInt("IDSOCIO"));
				prestamo.setFechaPrestamo(rs.getDate("FECHAPRESTAMO"));
				prestamo.setFechaLimiteDevolucion(rs.getDate("FECHALIMITEDEVOLUCION"));
			}
			
			
		}catch (SQLException e) {
			//e.printStackTrace();
			throw e;
		}catch (Exception ex) {
			//ex.printStackTrace();
			throw ex;
		}
		finally {
			//liberamos los recursos en un finally para asegurarnos de que se cierra
			//todo lo abierto
			if(rs!=null)
				rs.close();
			if(st!=null)
				st.close();
			if(con!=null)
				con.close();
		}
		
		return prestamo;
	}
	
	public ArrayList<Prestamo> listadoSocioPrestamoId(int idSocio) throws SQLException, Exception {
	    ArrayList<Prestamo> prestamosPendientesDevolucion = new ArrayList<>();
	    Conexion conexion = new Conexion();
	    Connection con = null;
	    ResultSet rs = null;
	    PreparedStatement st = null;

	    try {
	        con = conexion.getConexion();
	        String ordenSQL = "SELECT * FROM PRESTAMO WHERE IDSOCIO = ? AND FECHALIMITEDEVOLUCION < SYSDATE";
	        st = con.prepareStatement(ordenSQL);
	        st.setInt(1, idSocio);
	        rs = st.executeQuery();

	        while (rs.next()) {
	            Prestamo prestamo = new Prestamo();
	            prestamo.setIdEjemplar(rs.getInt("IDEJEMPLAR"));
	            prestamo.setIdSocio(rs.getInt("IDSOCIO"));
	            prestamo.setFechaPrestamo(rs.getDate("FECHAPRESTAMO"));
	            prestamo.setFechaLimiteDevolucion(rs.getDate("FECHALIMITEDEVOLUCION"));
	            prestamosPendientesDevolucion.add(prestamo);
	        }

	    } catch (SQLException e) {
	        throw e;
	    } catch (Exception ex) {
	        throw ex;
	    } finally {
	        if (rs != null) rs.close();
	        if (st != null) st.close();
	        if (con != null) con.close();
	    }

	    return prestamosPendientesDevolucion;
	}
	
	public ArrayList<Prestamo> ejemplaresSocio(int idSocio) throws SQLException, Exception {
		ArrayList<Prestamo> ejemparSocio = new ArrayList<>();
		Conexion conexion = new Conexion();
	    Connection con = null;
	    ResultSet rs = null;
	    PreparedStatement st = null;
	    
	    try {
	    	
	    	con=conexion.getConexion();
	    	String ordenSQL="SELECT IDEJEMPLAR FROM PRESTAMO WHERE IDSOCIO=?";
	    	st = con.prepareStatement(ordenSQL);
	    	st.setInt(1, idSocio);
		    rs = st.executeQuery();
		    
		    while(rs.next()) {
		    	Prestamo prestamo = new Prestamo();
		    	prestamo.setIdEjemplar(rs.getInt("IDEJEMPLAR"));
		    	ejemparSocio.add(prestamo);
		    }
	    	
	    } catch (SQLException e) {
	        throw e;
	    } catch (Exception ex) {
	        throw ex;
	    } finally {
	        if (rs != null) rs.close();
	        if (st != null) st.close();
	        if (con != null) con.close();
	    }
		
		return ejemparSocio;
		
	}
	
	public Prestamo insertarPrestamo(int idEjemplar, int idSocio, LocalDate fechaActual, LocalDate fechaLimiteDevolucion) throws SQLException, Exception {
		
		Conexion conexion = new Conexion();
		Connection con=null;
		PreparedStatement st=null;
		
		
		
		
		try {
			
			con=conexion.getConexion();
			String ordenSQL="INSERT INTO PRESTAMO (IDEJEMPLAR, IDSOCIO, FECHAPRESTAMO, FECHALIMITEDEVOLUCION) VALUES(?,?,?,?)";
			st=con.prepareStatement(ordenSQL);
			
			st.setInt(1, idEjemplar);
			st.setInt(2, idSocio);
			st.setObject(3, fechaActual); 
			st.setObject(4, fechaLimiteDevolucion);
			
			//Ejecutar la insercción
			st.executeUpdate();
		} catch (SQLException e) {
	        throw e;
	    } catch (Exception ex) {
	        throw ex;
	    } finally {
	        if (st != null) st.close();
	        if (con != null) con.close();
	    }
		
		return null;
	}
	
	public Prestamo borrarPrestamo(int idEjemplar) throws SQLException, Exception {
		
		Conexion conexion = new Conexion();
		Connection con=null;
		PreparedStatement st=null;
		
		try {
			con=conexion.getConexion();
			String ordenSQL="DELETE FROM PRESTAMO WHERE IDEJEMPLAR=?";
			st = con.prepareStatement(ordenSQL);
			st.setInt(1, idEjemplar);
			st.executeUpdate();
			
		}finally {
			
		}
		return null;
		
	}
	

}
