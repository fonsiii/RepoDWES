package test;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.DaoSocioMoroso;
import entidades.SocioMoroso; 

public class TestDaoSociosMorosos {

    public static void main(String[] args) {
        DaoSocioMoroso dao = new DaoSocioMoroso(); 

        try {
            ArrayList<SocioMoroso> listadoMorosos = dao.listadoSociosMorosos();

            for (SocioMoroso socioMoroso : listadoMorosos) {
                System.out.println("ID Socio: " + socioMoroso.getId() +
                                   ", Nombre: " + socioMoroso.getNombre());
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
