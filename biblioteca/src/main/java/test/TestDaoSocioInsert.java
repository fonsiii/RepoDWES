package test;

import java.sql.SQLException;

import dao.DaoSocio;
import entidades.Socio;

public class TestDaoSocioInsert {

	public static void main(String[] args) {
		Socio s=new Socio();
		s.setNombre("SOCIO 21");
		s.setDireccion("C/ SOCIO 21");
		s.setEmail("SOCIO21@AZARQUIEL.ES");
		s.setVersion(1);
		DaoSocio dao=new DaoSocio();
		try {
		dao.insertaSocio(s);
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		}
}
