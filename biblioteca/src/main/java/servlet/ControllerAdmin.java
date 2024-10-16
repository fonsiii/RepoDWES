package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import dao.DaoAutor;
import entidades.Autor;

/**
 * Servlet implementation class ControllerAdmin
 */
@WebServlet("/controlleradmin")
public class ControllerAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    RequestDispatcher dispatcher;
	    dispatcher = request.getRequestDispatcher("home.jsp");
		String operacion = request.getParameter("operacion");
	    if (operacion.equals("listarAutores")) {
	      dispatcher = request.getRequestDispatcher("admin/listadoautores.jsp");
			DaoAutor dao=new DaoAutor();
			try {
				ArrayList<Autor> listado = dao.listadoAutores();
				request.setAttribute("autores", listado);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
	    }
	    dispatcher.forward(request, response);	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nombre = request.getParameter("nombre");
		String fechaNacimientoStr = request.getParameter("fechaNacimiento");
		
		if (nombre == null || nombre.trim().isEmpty() || fechaNacimientoStr == null || fechaNacimientoStr.trim().isEmpty()) {
			request.setAttribute("error", "Nombre y fecha de nacimiento son obligatorios");
			request.getRequestDispatcher("admin/altaautor.jsp").forward(request, response);
			return;
		}
		
		try {
			Autor nuevoAutor = new Autor();
			nuevoAutor.setNombre(nombre);
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date fechaUtil = formatoFecha.parse(fechaNacimientoStr);
			java.sql.Date fechaNacimiento = new java.sql.Date(fechaUtil.getTime());
			nuevoAutor.setFechaNacimiento(fechaNacimiento);
			DaoAutor dao=new DaoAutor();
			dao.insertaAutor(nuevoAutor);
			request.setAttribute("confirmaroperacion", "El autor ha sido creado correctamente");
			
		} catch (ParseException e) {
	        request.setAttribute("error", "Fecha de nacimiento no válida: " + e.getMessage());
	    } catch (SQLException e) {
	        request.setAttribute("error", "Error al insertar el autor: " + e.getMessage());
	    } catch (Exception e) {
	        request.setAttribute("error", "Error inesperado: " + e.getMessage());
	    }
		request.getRequestDispatcher("admin/altaautor.jsp").forward(request, response);
		
		
		

	}

}
