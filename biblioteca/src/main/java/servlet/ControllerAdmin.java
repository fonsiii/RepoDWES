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
		switch (operacion) {
		case "listarAutores":
			dispatcher = request.getRequestDispatcher("admin/listadoautores.jsp");
			DaoAutor dao=new DaoAutor();
			try {
				ArrayList<Autor> listado = dao.listadoAutores();
				request.setAttribute("autores", listado);
			} catch (SQLException sqle) {
				// TODO Auto-generated catch block
				procesarErrorSql(request, response, sqle, "admin/listadoautores.jsp");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				procesarError(request, response, e, "admin/listadoautores.jsp");
			}
		    dispatcher.forward(request, response);
		    break;
	    }
			
		}


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
	    } catch (SQLException sqle) {
	        procesarErrorSql(request, response, sqle, "admin/altaautor.jsp");
	    } catch (Exception e) {
	        procesarError(request, response, e, "admin/altaautor.jsp");
	    }
		request.getRequestDispatcher("admin/altaautor.jsp").forward(request, response);
	}
	
	protected void procesarError(HttpServletRequest request, HttpServletResponse response , Exception e, String url) throws ServletException, IOException{
		String mensajeError = e.getMessage();
		request.setAttribute("error", mensajeError);
		request.getRequestDispatcher("admin/error.jsp").forward(request, response);
		if(url==null) {
			url="admin/error.jsp";
		}	
	}
	protected void procesarErrorSql(HttpServletRequest request, HttpServletResponse response , SQLException sqle, String url) throws ServletException, IOException{
		int codigoError = sqle.getErrorCode();
		String mensajeError;
		switch (codigoError) {
		case 30006:
			mensajeError = "Registro en proceso de modificacion.Intentelo mas tarde";
			break;
			default:
				mensajeError = sqle.getMessage();
		}
		request.setAttribute("error", mensajeError);
		request.getRequestDispatcher("admin/error.jsp").forward(request, response);
		if(url==null) {
			url="admin/error.jsp";
		}	
	}
	
}
