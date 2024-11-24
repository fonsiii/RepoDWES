package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.Conexion;
import entidades.SocioMoroso;

public class DaoSocioMoroso {

    public ArrayList<SocioMoroso> listadoSociosMorosos() throws SQLException, Exception {
        ArrayList<SocioMoroso> listadoSociosMorosos = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            con = conexion.getConexion();

            String ordenSQL = "select s.idsocio, s.nombre\n"
            		+ "from socio s\n"
            		+ "inner join prestamo p on s.idsocio = p.idsocio\n"
            		+ "where\n"
            		+ "p.fechalimitedevolucion < sysdate\n"
            		+ "and not exists (\n"
            		+ "select 1\n"
            		+ "from devolucion d\n"
            		+ "where d.idsocio = p.idsocio\n"
            		+ "and d.idejemplar = p.idejemplar\n"
            		+ ") group by s.idsocio, s.nombre\n"
            		+ "order by s.idsocio";

            st = con.prepareStatement(ordenSQL);
            rs = st.executeQuery();

            while (rs.next()) {
                SocioMoroso socioMoroso = new SocioMoroso();
                socioMoroso.setId(rs.getInt("IDSOCIO"));
                socioMoroso.setNombre(rs.getString("NOMBRE"));
                listadoSociosMorosos.add(socioMoroso);
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
        return listadoSociosMorosos;
    }

    public SocioMoroso buscarSocioPorId(int idSocio) throws SQLException, Exception {
        SocioMoroso socioMoroso = null;
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

            if (rs.next()) {
                socioMoroso = new SocioMoroso();
                socioMoroso.setId(rs.getInt("IDSOCIO"));
                socioMoroso.setNombre(rs.getString("NOMBRE"));
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
        return socioMoroso;
    }
}
