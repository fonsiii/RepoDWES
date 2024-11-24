package servlet;

import java.io.IOException;
import java.util.List;

import entidades.Autor;
import entidades.Libro;
import dao.DaoAutor;
import dao.DaoLibro;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/controllerSocio")
public class ControllerSocio extends HttpServlet {

    private static final String LISTAR_AUTORES_PAGE = "socio/listadoautores.jsp";
    private static final String GET_LIBROS_PAGE = "socio/getlibros.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("operacion");

        if ("listarAutores".equals(operacion)) {
            listarAutores(request, response);
        } else {
            procesarError(request, response, new Exception("Operación no válida"), LISTAR_AUTORES_PAGE);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("operacion");

        if ("buscarLibros".equals(operacion)) {
            buscarLibros(request, response);
        }
    }

    private void buscarLibros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchInput = request.getParameter("searchInput");
        String searchType = request.getParameter("searchType");

        // Verificar si se ha proporcionado un término de búsqueda
        if (searchInput == null || searchInput.trim().isEmpty()) {
            // Si no se ha ingresado un término, mostrar error
            request.setAttribute("error", "Debe ingresar un término de búsqueda.");
            request.getRequestDispatcher(GET_LIBROS_PAGE).forward(request, response);
            return; // Terminar la ejecución aquí si no hay término de búsqueda
        }

        // Inicializar los parámetros de búsqueda para autor, título o ISBN
        String autor = null;
        String titulo = null;
        String isbn = null;

        // Asignar el término de búsqueda al parámetro correspondiente según el tipo seleccionado
        switch (searchType) {
            case "autor":
                autor = searchInput;
                break;
            case "titulo":
                titulo = searchInput;
                break;
            case "isbn":
                isbn = searchInput;
                break;
            default:
                request.setAttribute("error", "Tipo de búsqueda no válido.");
                request.getRequestDispatcher(GET_LIBROS_PAGE).forward(request, response);
                return;
        }

        // Realizar la búsqueda con los parámetros correspondientes
        DaoLibro daoLibro = new DaoLibro();
        List<Libro> resultados = null;

        try {
            // Llamar al método buscarLibros con los tres parámetros
            resultados = daoLibro.buscarLibros(autor, titulo, isbn);

            if (resultados != null && !resultados.isEmpty()) {
                // Si hay resultados, enviarlos a la vista
                request.setAttribute("resultados", resultados);
            } else {
                // Si no hay resultados, mostrar mensaje de error
            	// En el caso de no encontrar resultados
            	request.setAttribute("searchInput", searchInput);  // Pasar el valor ingresado al JSP
            	request.setAttribute("error", "No se encontraron libros que coincidan con su búsqueda.");
            	request.getRequestDispatcher(GET_LIBROS_PAGE).forward(request, response);            }

        } catch (Exception e) {
            // Si ocurre un error en la búsqueda, mostrar el mensaje de error
            request.setAttribute("error", "Error en la búsqueda: " + e.getMessage());
        }

        // Redirigir a la página de búsqueda de libros con los resultados o errores
        request.getRequestDispatcher(GET_LIBROS_PAGE).forward(request, response);
    }



    private void listarAutores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoAutor daoAutor = new DaoAutor();
        try {
            List<Autor> autores = daoAutor.listadoAutores();
            request.setAttribute("autores", autores);
            request.getRequestDispatcher(LISTAR_AUTORES_PAGE).forward(request, response);
        } catch (Exception e) {
            procesarError(request, response, e, LISTAR_AUTORES_PAGE);
        }
    }

    private void procesarError(HttpServletRequest request, HttpServletResponse response, Exception e, String errorPage) throws ServletException, IOException {
        request.setAttribute("error", e.getMessage());
        request.getRequestDispatcher(errorPage).forward(request, response);
    }
}
