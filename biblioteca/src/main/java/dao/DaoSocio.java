package dao;

import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.util.ArrayList; 

import conexion.Conexion; 
import entidades.Socio; 

public class DaoSocio {


    public ArrayList<Socio> listadoSocios() throws SQLException, Exception {
        ArrayList<Socio> listadoSocios = new ArrayList<>(); 
        Conexion conexion = new Conexion();
        Connection con = null; 
        ResultSet rs = null; 
        PreparedStatement st = null; 
        
        try {
            con = conexion.getConexion();

            String ordenSQL = "SELECT * FROM SOCIO ORDER BY IDSOCIO";
            st = con.prepareStatement(ordenSQL); 
            rs = st.executeQuery(); 
            
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
            if (rs != null) rs.close(); 
            if (st != null) st.close(); 
            if (con != null) con.close(); 
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
            if (st != null) st.close();
            if (con != null) con.close(); 
        }
    }


    public ArrayList<Socio> buscarSocioPorNombre(String nombre) throws SQLException, Exception {
        ArrayList<Socio> listadoSociosBuscados = new ArrayList<>(); 
        Conexion miconex = new Conexion(); 
        Connection con = null; 
        ResultSet rs = null; 
        PreparedStatement st = null; 
        
        try {
            con = miconex.getConexion(); 
            
            String ordenSQL = "SELECT * FROM SOCIO WHERE LOWER(NOMBRE) LIKE LOWER(?)";
            st = con.prepareStatement(ordenSQL); 
            st.setString(1, nombre + "%");
            rs = st.executeQuery(); 
            
            while (rs.next()) {
                Socio miSocio = new Socio();
                miSocio.setIdSocio(rs.getInt("IDSOCIO"));
                miSocio.setNombre(rs.getString("NOMBRE")); 
                miSocio.setDireccion(rs.getString("DIRECCION")); 
                miSocio.setEmail(rs.getString("EMAIL")); 
                miSocio.setVersion(rs.getInt("VERSION")); 
                
                listadoSociosBuscados.add(miSocio); 
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
        return listadoSociosBuscados; 
    }
    public Socio buscarSocioPorId(int idSocio) throws SQLException, Exception{
    	Socio socio = null;
    	Conexion conexion = new Conexion();
    	Connection con = null;
    	PreparedStatement st = null;
    	ResultSet rs = null;
    	
    	try {
    		
    		con = conexion.getConexion();
    		String sql = "SELECT * FROM SOCIO WHERE IDSOCIO = ?";
    		st = con.prepareStatement(sql);
    		st.setInt(1, idSocio);
    		rs = st.executeQuery();
    		
    		if(rs.next()) {
    			socio = new Socio();
                socio.setIdSocio(rs.getInt("IDSOCIO"));
    			socio.setNombre(rs.getString("NOMBRE"));
    			socio.setDireccion(rs.getString("DIRECCION"));
    			socio.setVersion(rs.getInt("VERSION")+1);
    		}
    		
    		
    	}catch (SQLException e) {
            throw e;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }
        return socio;
    	
    }
    public void actualizarSocio(Socio socio) throws SQLException, Exception {
        Conexion conexion = new Conexion();
        Connection con = null;
        PreparedStatement st = null;

        try {
            con = conexion.getConexion();
            String sql = "UPDATE SOCIO SET NOMBRE = ?, DIRECCION = ?, VERSION = VERSION + 1 "
                    + "WHERE IDSOCIO = ? AND VERSION = ?";
            st = con.prepareStatement(sql);
            
            st.setString(1, socio.getNombre());
            st.setString(2, socio.getDireccion());

            st.setInt(3, socio.getIdSocio()); 
            st.setInt(4, socio.getVersion() - 1);

            int filas = st.executeUpdate();
            
            if (filas == 0) {
                throw new Exception("No se pudo actualizar el socio porque la versión no coincide.");
            }
            
        } catch (SQLException e) {
            throw e;
        } catch(Exception ex) {
            throw ex;
        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }
    }

}
