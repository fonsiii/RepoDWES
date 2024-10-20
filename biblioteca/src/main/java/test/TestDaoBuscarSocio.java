package test;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.DaoSocio;
import entidades.Socio;

public class TestDaoBuscarSocio {

    public static void main(String[] args) {
        // Crea una instancia del DAO
        DaoSocio dao = new DaoSocio();

        // Nombre que se va a buscar
        String nombreBuscado = "so";

        try {
            // Llamamos al método buscaSocioPorNombre para buscar socios con el nombre especificado
            ArrayList<Socio> sociosEncontrados = dao.buscarSocioPorNombre(nombreBuscado);

            // Imprimimos los resultados de la búsqueda
            if (sociosEncontrados.isEmpty()) {
                System.out.println("No se encontraron socios con el nombre: " + nombreBuscado);
            } else {
                System.out.println("Socios encontrados:");
                for (Socio socio : sociosEncontrados) {
                    System.out.println("ID: " + socio.getIdSocio());
                    System.out.println("Nombre: " + socio.getNombre());
                    System.out.println("Dirección: " + socio.getDireccion());
                    System.out.println("Email: " + socio.getEmail());
                    System.out.println("Versión: " + socio.getVersion());
                    System.out.println("-------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
