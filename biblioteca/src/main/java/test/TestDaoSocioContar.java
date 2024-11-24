package test;

import java.sql.SQLException;
import dao.DaoSocio; // Asegúrate de importar tu clase DAO correcta
import entidades.Socio; // Asegúrate de importar tu entidad Socio

public class TestDaoSocioContar {
    
    public static void main(String[] args) {
        DaoSocio daoSocio = new DaoSocio(); // Inicializa tu DAO aquí
        try {
            // Contamos el número total de socios
            int totalSocios = daoSocio.contarNumeroSocios();
            System.out.println("Total de socios: " + totalSocios);
            
            // Aquí puedes añadir lógica para verificar que el número total es el esperado
            // Por ejemplo, podrías imprimir un mensaje de éxito si coincide con un valor conocido
            int expectedTotal = 5; // Cambia este valor al número esperado de socios en tu base de datos
            if (totalSocios == expectedTotal) {
                System.out.println("Test exitoso: El total de socios es el esperado.");
            } else {
                System.out.println("Test fallido: Se esperaba " + expectedTotal + " pero se obtuvo " + totalSocios);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones SQL
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de otras excepciones
        }
    }
}
