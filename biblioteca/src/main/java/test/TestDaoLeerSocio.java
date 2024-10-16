package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import dao.DaoSocio;
import entidades.Socio;

public class TestDaoLeerSocio {
	
	public static void main(String[] args) {
		DaoSocio dao=new DaoSocio();
		try {
			ArrayList<Socio> listado = dao.listadoSocios();
			for (Socio socio : listado) {
				System.out.println(socio);
			}
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		}
}
