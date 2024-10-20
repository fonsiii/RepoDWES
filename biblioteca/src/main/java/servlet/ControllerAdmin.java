package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dao.DaoAutor;
import entidades.Autor;

@WebServlet("/controlleradmin") // Anotación para definir este servlet y su URL de acceso
public class ControllerAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Constantes para las rutas de JSP
    private static final String ERROR_PAGE = "admin/error.jsp"; // Página de error
    private static final String LISTADO_AUTORES_PAGE = "admin/listadoautores.jsp"; // Página para listar autores
    private static final String ALTA_AUTOR_PAGE = "admin/altaautor.jsp"; // Página para dar de alta un nuevo autor

    public ControllerAdmin() {
        super(); // Constructor del servlet
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la operación solicitada desde el parámetro de la solicitud
        String operacion = request.getParameter("operacion");

        // Validar que la operación no sea nula o vacía
        if (operacion == null || operacion.isEmpty()) {
            // Procesar error si la operación no es válida
            procesarError(request, response, new Exception("Operación no válida"), ERROR_PAGE);
            return;
        }

        // Manejar las diferentes operaciones en función del valor de 'operacion'
        switch (operacion) {
            case "listarAutores": // Listar todos los autores
                listarAutores(request, response);
                break;
            default: // Manejo de operaciones no reconocidas
                procesarError(request, response, new Exception("Operación no reconocida: " + operacion), ERROR_PAGE);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros de la solicitud
        String nombre = request.getParameter("nombre");
        String fechaNacimientoStr = request.getParameter("fechaNacimiento");

        // Validación de campos obligatorios
        if (nombre == null || nombre.trim().isEmpty() || fechaNacimientoStr == null || fechaNacimientoStr.trim().isEmpty()) {
            // Establecer un mensaje de error y redirigir a la página de alta
            request.setAttribute("error", "Nombre y fecha de nacimiento son obligatorios.");
            request.getRequestDispatcher(ALTA_AUTOR_PAGE).forward(request, response);
            return;
        }

        try {
            // Formatear la fecha de nacimiento desde el string recibido
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fechaUtil = formatoFecha.parse(fechaNacimientoStr);
            java.sql.Date fechaNacimiento = new java.sql.Date(fechaUtil.getTime()); // Convertir a tipo SQL

            // Crear un nuevo objeto Autor y establecer sus atributos
            Autor nuevoAutor = new Autor();
            nuevoAutor.setNombre(nombre);
            nuevoAutor.setFechaNacimiento(fechaNacimiento);

            // Insertar el nuevo autor en la base de datos
            DaoAutor dao = new DaoAutor();
            dao.insertaAutor(nuevoAutor);

            // Establecer mensaje de confirmación
            request.setAttribute("confirmaroperacion", "El autor ha sido creado correctamente");
        } catch (ParseException e) {
            // Procesar error si la fecha de nacimiento no es válida
            request.setAttribute("error", "Fecha de nacimiento no válida: " + e.getMessage());
        } catch (SQLException sqle) {
            // Procesar error en caso de excepciones SQL
            procesarError(request, response, sqle, ALTA_AUTOR_PAGE);
        } catch (Exception e) {
            // Procesar error para cualquier otra excepción
            procesarError(request, response, e, ALTA_AUTOR_PAGE);
        }

        // Redirigir a la página de alta
        request.getRequestDispatcher(ALTA_AUTOR_PAGE).forward(request, response);
    }

    // Método para listar autores
    private void listarAutores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoAutor dao = new DaoAutor(); // Instancia del DAO para la gestión de autores
        try {
            // Obtener la lista de autores
            ArrayList<Autor> listado = dao.listadoAutores();
            // Establecer la lista en el request para que esté disponible en la JSP
            request.setAttribute("autores", listado);
            // Redirigir a la página de listado de autores
            request.getRequestDispatcher(LISTADO_AUTORES_PAGE).forward(request, response);
        } catch (Exception e) {
            // Procesar error en caso de excepciones
            procesarError(request, response, e, LISTADO_AUTORES_PAGE);
        }
    }

    // Método centralizado para manejo de errores
    protected void procesarError(HttpServletRequest request, HttpServletResponse response, Exception e, String url) throws ServletException, IOException {
        // Obtener el mensaje de error y establecerlo en el request
        String mensajeError = e.getMessage();
        request.setAttribute("error", mensajeError);
        // Redirigir a la página de error
        request.getRequestDispatcher(url).forward(request, response);
    }
}
