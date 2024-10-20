package test;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.DaoLibroMoroso; // Asegúrate de que el nombre de la clase sea correcto
import entidades.LibroMoroso; // Asegúrate de que la entidad esté importada correctamente

public class TestDaoLibrosMorosos {

    public static void main(String[] args) {
        DaoLibroMoroso dao = new DaoLibroMoroso(); 

        try {
            int idSocio = 3; 
            ArrayList<LibroMoroso> listadoLibrosMorosos = dao.listadoLibrosMorosos(idSocio); 

            if (listadoLibrosMorosos.isEmpty()) {
                System.out.println("No hay libros morosos para el socio con ID: " + idSocio);
            } else {
                for (LibroMoroso libroMoroso : listadoLibrosMorosos) {
                    System.out.println("Título: " + libroMoroso.getTituloLibro() +
                                       ", Fecha de Préstamo: " + libroMoroso.getFechaPrestamo() +
                                       ", Días de Demora: " + libroMoroso.getDiasDemora());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
