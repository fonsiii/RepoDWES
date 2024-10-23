package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.Conexion;
import entidades.LibroMoroso;

public class DaoLibroMoroso {

    public ArrayList<LibroMoroso> listadoLibrosMorosos(int idSocio) throws SQLException, Exception {
        ArrayList<LibroMoroso> listadoLibrosMorosos = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement st = null;

        try {
            con = conexion.getConexion();

            String ordenSQL = "SELECT \n"
                    + "    l.TITULO AS TITULOLIBRO,             \n"
                    + "    p.FECHAPRESTAMO,                      \n"
                    + "    TRUNC(SYSDATE - p.FECHALIMITEDEVOLUCION) AS DIASDEMORA \n"
                    + "FROM \n"
                    + "    PRESTAMO p\n"
                    + "INNER JOIN EJEMPLAR e ON p.IDEJEMPLAR = e.IDEJEMPLAR\n"
                    + "INNER JOIN LIBRO l ON e.ISBN = l.ISBN\n"
                    + "WHERE \n"
                    + "    p.IDSOCIO = ?                 \n" 
                    + "    AND p.FECHALIMITEDEVOLUCION < SYSDATE  \n"
                    + "    AND NOT EXISTS (\n"
                    + "        SELECT 1 \n"
                    + "        FROM DEVOLUCION d \n"
                    + "        WHERE d.IDSOCIO = p.IDSOCIO \n"
                    + "        AND d.IDEJEMPLAR = p.IDEJEMPLAR)";

            st = con.prepareStatement(ordenSQL);
            st.setInt(1, idSocio);
            rs = st.executeQuery();

            while (rs.next()) {
                LibroMoroso libroMoroso = new LibroMoroso();
                libroMoroso.setTituloLibro(rs.getString("TITULOLIBRO"));
                libroMoroso.setFechaPrestamo(rs.getDate("FECHAPRESTAMO"));
                libroMoroso.setDiasDemora(rs.getInt("DIASDEMORA"));
                listadoLibrosMorosos.add(libroMoroso);
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
        return listadoLibrosMorosos;
    }
}
