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

@WebServlet("/controllerSocio") 
public class ControllerSocio extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String ERROR_PAGE = "admin/error.jsp"; 
    private static final String LISTADO_SOCIOS_PAGE = "socio/listadosocios.jsp"; 
    private static final String SOCIOS_MOROSOS_PAGE = "socio/sociosmorosos.jsp"; 
    private static final String ALTA_SOCIO_PAGE = "socio/altasocio.jsp"; 
    private static final String GET_SOCIO_PAGE = "socio/getsocio.jsp"; 
    private static final String MODIFICAR_SOCIO_PAGE = "socio/modificarsocio.jsp"; 


    
    private final DaoSocio daoSocio = new DaoSocio(); 
    private final DaoSocioMoroso daoMoroso = new DaoSocioMoroso();
    private final DaoLibroMoroso daoLibroMoroso = new DaoLibroMoroso();
    
    
    public ControllerSocio() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("operacion");

        if (operacion == null || operacion.isBlank()) {
            procesarError(request, response, new Exception("Operación no válida"), ERROR_PAGE);
            return;
        }

        switch (operacion) {
            case "listarSocios":
                listarSocios(request, response);
                break;

            case "socioslibrosfueraplazo": 
                listarSociosMorosos(request, response);
                break;

            case "librosSocioMoroso": 
                mostrarLibrosSocioMoroso(request, response);
                break;
                
            case "modificarSocio":
            	mostrarDatosSocio(request, response);
            	break;

            default: 
                procesarError(request, response, new Exception("Operación no reconocida"), ERROR_PAGE);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String operacion = request.getParameter("operacion");

        if (operacion == null || operacion.isBlank()) {
            procesarError(request, response, new Exception("Operación no válida"), ERROR_PAGE);
            return;
        }

        switch (operacion) {
            case "altaSocio": 
            	altaSocio(request, response, "nombre", "direccion", "email");
                break;
            case "getSocio":
            	listarSociosBuscados(request, response);
            	break;
                case "modificarSocio":
                    actualizarSocio(request, response);
                    break;
            	
        }
    }

    private void listarSocios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ArrayList<Socio> listado = daoSocio.listadoSocios();
            request.setAttribute("socios", listado);
            request.getRequestDispatcher(LISTADO_SOCIOS_PAGE).forward(request, response);
        } catch (Exception e) {
            procesarError(request, response, e, LISTADO_SOCIOS_PAGE);
        }
    }
    private void listarSociosBuscados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        request.setAttribute("nombreBuscado", nombre);
        if (nombre == null || nombre.isBlank()) {
            request.setAttribute("error", "Nombre obligatorio.");
            request.getRequestDispatcher(GET_SOCIO_PAGE).forward(request, response);
            return;
        }
        try {
            ArrayList<Socio> socioBuscado = daoSocio.buscarSocioPorNombre(nombre);
            request.setAttribute("socioBuscado", socioBuscado);
            request.getRequestDispatcher(GET_SOCIO_PAGE).forward(request, response);
        } catch (Exception e) {
            procesarError(request, response, e, GET_SOCIO_PAGE);
        }
    }

    
    private void altaSocio (HttpServletRequest request, HttpServletResponse response, String nombre, String direccion , String email )throws ServletException, IOException{
    	
        String nombreSocio = request.getParameter("nombre");
        String direccionSocio = request.getParameter("direccion");
        String emailSocio = request.getParameter("email");

        if (nombreSocio == null || nombreSocio.isBlank() || direccionSocio == null || direccionSocio.isBlank() || emailSocio == null || emailSocio.isBlank()) {
            request.setAttribute("error", "Nombre, dirección y email son obligatorios.");
            request.getRequestDispatcher(ALTA_SOCIO_PAGE).forward(request, response);
            return;
        }

        try {
            Socio nuevoSocio = new Socio();
            nuevoSocio.setNombre(nombreSocio);
            nuevoSocio.setDireccion(direccionSocio);
            nuevoSocio.setEmail(emailSocio);
            nuevoSocio.setVersion(1);

            daoSocio.insertaSocio(nuevoSocio);
            request.setAttribute("confirmaroperacion", "El socio ha sido creado correctamente");
        } catch (SQLException sqle) {
            procesarError(request, response, sqle, ALTA_SOCIO_PAGE);
        } catch (Exception e) {
            procesarError(request, response, e, ALTA_SOCIO_PAGE);
        }

        request.getRequestDispatcher(ALTA_SOCIO_PAGE).forward(request, response);
    }

    private void listarSociosMorosos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ArrayList<SocioMoroso> listadoMorosos = daoMoroso.listadoSociosMorosos();
            request.setAttribute("sociosMorosos", listadoMorosos);
            request.getRequestDispatcher(SOCIOS_MOROSOS_PAGE).forward(request, response);
        } catch (Exception e) {
            procesarError(request, response, e, SOCIOS_MOROSOS_PAGE);
        }
    }

    private void mostrarLibrosSocioMoroso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idSocio;
        try {
            idSocio = Integer.parseInt(request.getParameter("idSocio"));
        } catch (NumberFormatException nfe) {
            procesarError(request, response, new Exception("ID de socio no válido"), ERROR_PAGE);
            return;
        }

        try {
            ArrayList<LibroMoroso> librosMorosos = daoLibroMoroso.listadoLibrosMorosos(idSocio);
            SocioMoroso socioMoroso = daoMoroso.buscarSocioPorId(idSocio);
            ArrayList<SocioMoroso> listadoMorosos = daoMoroso.listadoSociosMorosos();

            request.setAttribute("socioNombre", socioMoroso.getNombre()); 
            request.setAttribute("librosMorosos", librosMorosos); 
            request.setAttribute("sociosMorosos", listadoMorosos);

            request.getRequestDispatcher(SOCIOS_MOROSOS_PAGE).forward(request, response);
        } catch (SQLException sqle) {
            procesarError(request, response, sqle, SOCIOS_MOROSOS_PAGE);
        } catch (Exception e) {
            procesarError(request, response, e, SOCIOS_MOROSOS_PAGE);
        }
    }
    
    private void mostrarDatosSocio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idSocio;
        try {
            idSocio = Integer.parseInt(request.getParameter("idSocio"));
        } catch (NumberFormatException nfe) {
            procesarError(request, response, new Exception("ID de socio no válido"), ERROR_PAGE);
            return;
        }

        try {
            Socio socio = daoSocio.buscarSocioPorId(idSocio);
            request.setAttribute("socio", socio);
            request.getRequestDispatcher(MODIFICAR_SOCIO_PAGE).forward(request, response);
        } catch (SQLException sqle) {
            procesarError(request, response, sqle, MODIFICAR_SOCIO_PAGE);
        } catch (Exception e) {
            procesarError(request, response, e, MODIFICAR_SOCIO_PAGE);
        }
    }

    private void actualizarSocio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idSocio = Integer.parseInt(request.getParameter("idSocio"));
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        int version = Integer.parseInt(request.getParameter("version"));

        if (nombre == null || nombre.isBlank() || direccion == null || direccion.isBlank()) {
            request.setAttribute("error", "Nombre y dirección son obligatorios.");
            mostrarDatosSocio(request, response); 
            return;
        }

        try {
            Socio socio = new Socio();
            socio.setIdSocio(idSocio);
            socio.setNombre(nombre);
            socio.setDireccion(direccion);
            socio.setVersion(version);

            daoSocio.actualizarSocio(socio);
            request.setAttribute("confirmaroperacion", "El socio ha sido actualizado correctamente.");
        } catch (SQLException sqle) {
            procesarError(request, response, sqle, MODIFICAR_SOCIO_PAGE);
        } catch (Exception e) {
            procesarError(request, response, e, MODIFICAR_SOCIO_PAGE);
        }

        mostrarDatosSocio(request, response); 
    }

    protected void procesarError(HttpServletRequest request, HttpServletResponse response, Exception e, String url) throws ServletException, IOException {
        String mensajeError = (e instanceof SQLException) ? ((SQLException) e).getErrorCode() + ": " + e.getMessage() : e.getMessage();
        request.setAttribute("error", mensajeError); 
        request.getRequestDispatcher(url == null ? ERROR_PAGE : url).forward(request, response);
    }
}
