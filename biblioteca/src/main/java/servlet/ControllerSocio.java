package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DaoLibroMoroso;
import dao.DaoSocio;
import dao.DaoSocioMoroso;
import entidades.LibroMoroso;
import entidades.Socio;
import entidades.SocioMoroso;

@WebServlet("/controllerSocio") // Anotación para definir este servlet y su URL de acceso
public class ControllerSocio extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Constantes para las rutas de JSP
    private static final String ERROR_PAGE = "admin/error.jsp"; // Página de error
    private static final String LISTADO_SOCIOS_PAGE = "socio/listadosocios.jsp"; // Página para listar socios
    private static final String SOCIOS_MOROSOS_PAGE = "socio/sociosmorosos.jsp"; // Página para mostrar socios morosos
    private static final String ALTA_SOCIO_PAGE = "socio/altasocio.jsp"; // Página para dar de alta un nuevo socio

    public ControllerSocio() {
        super(); // Constructor del servlet
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la operación solicitada desde el parámetro de la solicitud
        String operacion = request.getParameter("operacion");

        // Validar que la operación no sea nula o vacía
        if (operacion == null || operacion.trim().isEmpty()) {
            // Procesar error si la operación no es válida
            procesarError(request, response, new Exception("Operación no válida"), ERROR_PAGE);
            return;
        }

        // Manejar las diferentes operaciones en función del valor de 'operacion'
        switch (operacion) {
            case "listarSocios": // Listar todos los socios
                listarSocios(request, response);
                break;

            case "socioslibrosfueraplazo": // Listar socios morosos
                listarSociosMorosos(request, response);
                break;

            case "librosSocioMoroso": // Mostrar libros morosos de un socio específico
                mostrarLibrosSocioMoroso(request, response);
                break;

            default: // Manejo de operaciones no reconocidas
                procesarError(request, response, new Exception("Operación no reconocida"), ERROR_PAGE);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoSocio dao = new DaoSocio(); // Instancia del DAO para la gestión de socios
        // Obtener parámetros de la solicitud
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String email = request.getParameter("email");

        // Validar que los campos no sean nulos o vacíos
        if (nombre == null || nombre.trim().isEmpty() || direccion == null || direccion.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            // Establecer un mensaje de error y redirigir a la página de alta
            request.setAttribute("error", "Nombre, dirección y email son obligatorios.");
            request.getRequestDispatcher(ALTA_SOCIO_PAGE).forward(request, response);
            return;
        }

        try {
            // Crear un nuevo objeto Socio y establecer sus atributos
            Socio nuevoSocio = new Socio();
            nuevoSocio.setNombre(nombre);
            nuevoSocio.setDireccion(direccion);
            nuevoSocio.setEmail(email);
            nuevoSocio.setVersion(1); // Establecer la versión inicial del socio

            // Insertar el nuevo socio en la base de datos
            dao.insertaSocio(nuevoSocio);
            // Establecer mensaje de confirmación
            request.setAttribute("confirmaroperacion", "El socio ha sido creado correctamente");
        } catch (SQLException sqle) {
            // Procesar error en caso de excepciones SQL
            procesarError(request, response, sqle, ALTA_SOCIO_PAGE);
        } catch (Exception e) {
            // Procesar error para cualquier otra excepción
            procesarError(request, response, e, ALTA_SOCIO_PAGE);
        }

        // Redirigir a la página de alta
        request.getRequestDispatcher(ALTA_SOCIO_PAGE).forward(request, response);
    }

    // Método para listar todos los socios
    private void listarSocios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoSocio dao = new DaoSocio(); // Instancia del DAO para la gestión de socios
        try {
            // Obtener la lista de socios
            ArrayList<Socio> listado = dao.listadoSocios();
            // Establecer la lista en el request para que esté disponible en la JSP
            request.setAttribute("socios", listado);
            // Redirigir a la página de listado de socios
            request.getRequestDispatcher(LISTADO_SOCIOS_PAGE).forward(request, response);
        } catch (Exception e) {
            // Procesar error en caso de excepciones
            procesarError(request, response, e, LISTADO_SOCIOS_PAGE);
        }
    }

    // Método para listar socios morosos
    private void listarSociosMorosos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoSocioMoroso daoMoroso = new DaoSocioMoroso(); // Instancia del DAO para la gestión de socios morosos
        try {
            // Obtener la lista de socios morosos
            ArrayList<SocioMoroso> listadoMorosos = daoMoroso.listadoSociosMorosos();
            // Establecer la lista en el request para que esté disponible en la JSP
            request.setAttribute("socios", listadoMorosos);
            // Redirigir a la página de socios morosos
            request.getRequestDispatcher(SOCIOS_MOROSOS_PAGE).forward(request, response);
        } catch (Exception e) {
            // Procesar error en caso de excepciones
            procesarError(request, response, e, SOCIOS_MOROSOS_PAGE);
        }
    }

    // Método para mostrar los libros morosos de un socio específico
    private void mostrarLibrosSocioMoroso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idSocio;
        try {
            // Obtener el ID del socio desde la solicitud
            idSocio = Integer.parseInt(request.getParameter("idSocio"));
        } catch (NumberFormatException nfe) {
            // Procesar error si el ID no es válido
            procesarError(request, response, new Exception("ID de socio no válido"), ERROR_PAGE);
            return;
        }

        DaoLibroMoroso daoLibroMoroso = new DaoLibroMoroso(); // Instancia del DAO para la gestión de libros morosos
        DaoSocioMoroso daoMoroso = new DaoSocioMoroso(); // Instancia del DAO para la gestión de socios morosos
        try {
            // Obtener la lista de libros morosos para el socio
            ArrayList<LibroMoroso> librosMorosos = daoLibroMoroso.listadoLibrosMorosos(idSocio);
            // Buscar el socio moroso correspondiente al ID
            SocioMoroso socioMoroso = daoMoroso.buscarSocioPorId(idSocio);

            // Establecer atributos en la solicitud para la JSP
            request.setAttribute("socioNombre", socioMoroso.getNombre()); // Nombre del socio
            request.setAttribute("librosMorosos", librosMorosos); // Lista de libros morosos
            // Redirigir a la página de socios morosos
            request.getRequestDispatcher(SOCIOS_MOROSOS_PAGE).forward(request, response);
        } catch (SQLException sqle) {
            // Procesar error en caso de excepciones SQL
            procesarError(request, response, sqle, SOCIOS_MOROSOS_PAGE);
        } catch (Exception e) {
            // Procesar error para cualquier otra excepción
            procesarError(request, response, e, SOCIOS_MOROSOS_PAGE);
        }
    }

    // Método centralizado para el manejo de errores generales
    protected void procesarError(HttpServletRequest request, HttpServletResponse response, Exception e, String url) throws ServletException, IOException {
        // Construir mensaje de error basado en el tipo de excepción
        String mensajeError = (e instanceof SQLException) ? ((SQLException) e).getErrorCode() + ": " + e.getMessage() : e.getMessage();
        request.setAttribute("error", mensajeError); // Establecer mensaje de error en el request
        // Redirigir a la página de error o a la URL proporcionada
        request.getRequestDispatcher(url == null ? ERROR_PAGE : url).forward(request, response);
    }
}
