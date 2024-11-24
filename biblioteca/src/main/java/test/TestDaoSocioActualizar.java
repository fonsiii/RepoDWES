package test;

import java.sql.SQLException;
import dao.DaoSocio;
import entidades.Socio;

public class TestDaoSocioActualizar {

    public static void main(String[] args) {
        DaoSocio dao = new DaoSocio();
        try {
            // Primero obtenemos un socio para actualizar
            Socio socio = dao.buscarSocioPorId(1); // Cambiar por el ID que quieras actualizar
            if (socio != null) {
                // Modificamos los datos del socio
                socio.setNombre("Nombre Actualizado");
                socio.setDireccion("Nueva Dirección Actualizada");

                // Llamamos al método de actualización
                dao.actualizarSocio(socio);
                System.out.println("Socio actualizado correctamente.");
            } else {
                System.out.println("No se encontró el socio con el ID especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
