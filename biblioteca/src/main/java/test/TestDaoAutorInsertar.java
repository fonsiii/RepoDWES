package test;

import java.sql.SQLException;
import java.util.Date;

import dao.DaoAutor;
import entidades.Autor;

public class TestDaoAutorInsertar {
	public TestDaoAutorInsertar() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		Autor a=new Autor();
		java.util.Date fecha=new Date();
		java.sql.Date fechaautor=new java.sql.Date(fecha.getTime());
		a.setNombre("XXXXXX TEST XXXX");
		a.setFechaNacimiento(fechaautor);
		DaoAutor dao=new DaoAutor();
		try {
		dao.insertaAutor(a);
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		}
}
