package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dao.DaoAutor;
import entidades.Autor;

@WebServlet("/controlleradmin") 
public class ControllerAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Constantes para las rutas de JSP
    private static final String ERROR_PAGE = "admin/error.jsp"; 
    private static final String LISTADO_AUTORES_PAGE = "admin/listadoautores.jsp"; 
    private static final String ALTA_AUTOR_PAGE = "admin/altaautor.jsp"; 

    public ControllerAdmin() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("operacion");

        if (operacion == null || operacion.isEmpty()) {
            procesarError(request, response, new Exception("Operación no válida"), ERROR_PAGE);
            return;
        }

        switch (operacion) {
            case "listarAutores": 
                listarAutores(request, response);
                break;
            default: 
                procesarError(request, response, new Exception("Operación no reconocida: " + operacion), ERROR_PAGE);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String operacion = request.getParameter("operacion");
        if (operacion == null || operacion.isEmpty()) {
            procesarError(request, response, new Exception("Operación no válida"), ERROR_PAGE);
            return;
        }
        
        switch(operacion) {
        case "altaAutor":
        	altaAutor(request,response, "nombre", "fechaNacimiento");
        	break;
        default: 
            procesarError(request, response, new Exception("Operación no reconocida: " + operacion), ERROR_PAGE);
            break;
        }
        }
        
        

       

    private void listarAutores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoAutor dao = new DaoAutor(); 
        try {
            ArrayList<Autor> listado = dao.listadoAutores();
            request.setAttribute("autores", listado);
            request.getRequestDispatcher(LISTADO_AUTORES_PAGE).forward(request, response);
        } catch (Exception e) {
            procesarError(request, response, e, LISTADO_AUTORES_PAGE);
        }
    }

    private void altaAutor (HttpServletRequest request, HttpServletResponse response, String nombre, String fecha )throws ServletException, IOException{
        String nombreAutor = request.getParameter("nombre");
        String fechaNacimientoStr = request.getParameter("fechaNacimiento");
        if (nombreAutor == null || nombreAutor.isBlank() || fechaNacimientoStr == null || fechaNacimientoStr.isBlank()) {
            request.setAttribute("error", "Nombre y fecha de nacimiento son obligatorios.");
            request.getRequestDispatcher(ALTA_AUTOR_PAGE).forward(request, response);
            return;
        }

        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date fechaUtil = formatoFecha.parse(fechaNacimientoStr);
            java.sql.Date fechaNacimiento = new java.sql.Date(fechaUtil.getTime()); 

            Autor nuevoAutor = new Autor();
            nuevoAutor.setNombre(nombreAutor);
            nuevoAutor.setFechaNacimiento(fechaNacimiento);

            DaoAutor dao = new DaoAutor();
            dao.insertaAutor(nuevoAutor);

            request.setAttribute("confirmaroperacion", "El autor ha sido creado correctamente");
        } catch (ParseException e) {
            request.setAttribute("error", "Fecha de nacimiento no válida: " + e.getMessage());
        } catch (SQLException sqle) {
            procesarError(request, response, sqle, ALTA_AUTOR_PAGE);
        } catch (Exception e) {
            procesarError(request, response, e, ALTA_AUTOR_PAGE);
        }

        request.getRequestDispatcher(ALTA_AUTOR_PAGE).forward(request, response);
    
        
    }
    protected void procesarError(HttpServletRequest request, HttpServletResponse response, Exception e, String url) throws ServletException, IOException {
        String mensajeError = e.getMessage();
        request.setAttribute("error", mensajeError);
        request.getRequestDispatcher(url).forward(request, response);
    }
}
