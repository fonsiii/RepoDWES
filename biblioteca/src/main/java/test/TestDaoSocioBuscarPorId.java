package test;

import java.sql.SQLException;
import dao.DaoSocio;
import entidades.Socio;

public class TestDaoSocioBuscarPorId {

    public static void main(String[] args) {
        DaoSocio dao = new DaoSocio();
        try {
            int idBuscar = 1; // Puedes cambiar este valor para probar diferentes IDs
            Socio socio = dao.buscarSocioPorId(idBuscar);
            if (socio != null) {
                System.out.println("ID: " + socio.getIdSocio() + ", Nombre: " + socio.getNombre() + ", Dirección: " + socio.getDireccion() + ", Versión: " + socio.getVersion());
            } else {
                System.out.println("No se encontró el socio con ID: " + idBuscar);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
