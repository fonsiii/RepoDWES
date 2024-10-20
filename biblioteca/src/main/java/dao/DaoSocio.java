package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexion.Conexion;
import entidades.Socio;

public class DaoSocio {

	public ArrayList<Socio> listadoSocios() throws SQLException, Exception{
		
		ArrayList<Socio> listadoSocios = new ArrayList<>();
		Conexion conexion = new Conexion();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		
		try {
			con = conexion.getConexion();
			
			String ordenSQL = "SELECT * FROM SOCIO ORDER BY IDSOCIO";
			st = con.prepareStatement(ordenSQL);
			rs = st.executeQuery(ordenSQL);
			
			while (rs.next()) {
				Socio miSocio = new Socio();
				miSocio.setIdSocio(rs.getInt("IDSOCIO"));
				miSocio.setNombre(rs.getString("NOMBRE"));
				miSocio.setDireccion(rs.getString("DIRECCION"));
				miSocio.setEmail(rs.getString("EMAIL"));
				miSocio.setVersion(rs.getInt("VERSION"));
				listadoSocios.add(miSocio);
			}
			
			} catch (SQLException e) {
				throw e;
			} catch (Exception ex) {
				throw ex;
			} finally {

				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (con != null)
					con.close();
			}
			return listadoSocios; 
		}


	
	public void insertaSocio(Socio s) throws SQLException, Exception {
		Connection con = null;
		PreparedStatement st = null;
		try {
			Conexion miconex = new Conexion();
			con = miconex.getConexion();
			String ordenSQL = "INSERT INTO SOCIO VALUES(S_AUTOR.NEXTVAL,?,?,?,?)";
			st = con.prepareStatement(ordenSQL);
			st.setString(1, s.getEmail());
			st.setString(2, s.getNombre());
			st.setString(3, s.getDireccion());
			st.setInt(4, s.getVersion());
			st.executeUpdate();
			st.close();
			con.close();
		} catch (SQLException se) {
			throw se;
		} catch (Exception e) {
			throw e;
		} finally {
			if (st != null)
				st.close();
			if (con != null)
				con.close();
		}
	}
	
	public ArrayList<Socio> buscarSocioPorNombre(String nombre) throws SQLException, Exception{
		ArrayList<Socio> listadoSocios = new ArrayList<>();
		Conexion miconex = new Conexion();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		
		try {
			
			con = miconex.getConexion();
			String ordenSQL = "SELECT * FROM SOCIO WHERE LOWER(NOMBRE) LIKE LOWER(?)";
			st = con.prepareStatement(ordenSQL);
			
			st.setString(1,"%" + nombre + "%");
			
			rs = st.executeQuery();
			
			while (rs.next()) {
				
				Socio miSocio = new Socio();
				miSocio.setNombre(rs.getString("NOMBRE"));
				miSocio.setDireccion(rs.getString("DIRECCION"));
				miSocio.setEmail(rs.getString("EMAIL"));
				miSocio.setVersion(rs.getInt("VERSION"));
				
				listadoSocios.add(miSocio);
				
			}
		} catch (SQLException e) {
	        throw e;
	    } catch (Exception ex) {
	        throw ex;
	    } finally {
	        if (rs != null)
	            rs.close();
	        if (st != null)
	            st.close();
	        if (con != null)
	            con.close();
	    }
	    return listadoSocios;
	}
	}
	

		
		
		

	
	

