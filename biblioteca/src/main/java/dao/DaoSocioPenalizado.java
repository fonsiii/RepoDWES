package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import conexion.Conexion;
import entidades.SocioPenalizado;

public class DaoSocioPenalizado{
	public ArrayList<SocioPenalizado> listadoSociosPenalizados(int idSocio) throws SQLException,Exception{
	ArrayList<SocioPenalizado> socioPenalizado = new ArrayList<>();
	Conexion conexion=new Conexion();
	Connection con=null;
	ResultSet rs=null;
	PreparedStatement st = null; 
	
	try {
		con=conexion.getConexion();
		String ordenSQL="SELECT * FROM SOCIOPENALIZADO WHERE IDSOCIO=? AND SYSDATE<LIMITEPENALIZACION";
		st = con.prepareStatement(ordenSQL);
		st.setInt(1, idSocio);
		rs=st.executeQuery(ordenSQL);
		
		while(rs.next()) {
			SocioPenalizado socio = new SocioPenalizado();
			socio = new SocioPenalizado();
			socio.setIdSocio(rs.getInt("IDSOCIO"));
			socio.setLimitePenalizacion(rs.getDate("LIMITEPENALIZACION"));
			socioPenalizado.add(socio);
			}
	}catch(SQLException sqle){
		throw sqle;
	}catch(Exception e) {
		throw e;
} finally {
    if (rs != null) rs.close(); 
    if (st != null) st.close(); 
    if (con != null) con.close(); 
}
	return socioPenalizado;
}
	public SocioPenalizado insertarSocioPenalizado(int idSocio, LocalDate fechaActual) throws SQLException, Exception{
		Connection con = null;
		PreparedStatement st = null;
		
		try {
			Conexion conexion = new Conexion();
			con=conexion.getConexion();
			String ordenSQL = "INSERT INTO SOCIOPENALIZADO(IDSOCIO, LIMITEPENALIZACION) VALUES (?,?) ";
			st = con.prepareStatement(ordenSQL);
			st.setInt(1, idSocio);
			st.setObject(2, java.sql.Date.valueOf(fechaActual));
			st.executeUpdate();
		}finally {
			if(st!=null) {
				st.close();
			}
			if(con!=null) {
				con.close();
			}
		}
		return null;
	}
}