package dao;

import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.util.ArrayList; 

import conexion.Conexion; 
import entidades.Autor; 

public class DaoAutor {


    public ArrayList<Autor> listadoAutores() throws SQLException, Exception {
        ArrayList<Autor> listadoAutores = new ArrayList<>(); 
        Conexion conexion = new Conexion(); 
        Connection con = null; 
        ResultSet rs = null; 
        PreparedStatement st = null; 

        try {
            con = conexion.getConexion(); 
            String ordenSQL = "SELECT * FROM AUTOR ORDER By IDAUTOR"; 
            st = con.prepareStatement(ordenSQL); 
            rs = st.executeQuery(ordenSQL); 

            while (rs.next()) {
                Autor miAutor = new Autor();
                miAutor.setIdAutor(rs.getInt("IDAUTOR"));
                miAutor.setNombre(rs.getString("NOMBRE"));
                miAutor.setFechaNacimiento(rs.getDate("FECHANACIMIENTO"));
                listadoAutores.add(miAutor); 
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
        return listadoAutores; 
    }


    public void insertaAutor(Autor a) throws SQLException, Exception {
        Connection con = null; 
        PreparedStatement st = null; 

        try {
            Conexion miconex = new Conexion(); 
            con = miconex.getConexion(); 
      
            String ordenSQL = "INSERT INTO AUTOR VALUES(S_AUTOR.NEXTVAL,?,?)";
            st = con.prepareStatement(ordenSQL);
            st.setString(1, a.getNombre()); 
            st.setDate(2, a.getFechaNacimiento()); 
            st.executeUpdate(); 
        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            throw e;
        } finally {
            if (st != null) st.close(); 
            if (con != null) con.close(); 
        }
    }
}
