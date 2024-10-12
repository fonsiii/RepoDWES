package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import dao.DaoAutor;
import entidades.Autor;

public class TestDaoAutorLeer {
	
	public static void main(String[] args) {
		DaoAutor dao=new DaoAutor();
		try {
			ArrayList<Autor> listado = dao.listadoAutores();
			for (Autor autor : listado) {
				System.out.println(autor);
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


