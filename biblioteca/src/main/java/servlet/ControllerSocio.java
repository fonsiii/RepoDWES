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
import java.util.ArrayList;

import dao.DaoSocio;
import entidades.Socio;

/**
 * Servlet implementation class ControllerSocio
 */
@WebServlet("/controllerSocio")
public class ControllerSocio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerSocio() {
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
		case "listarSocios":
		      dispatcher = request.getRequestDispatcher("socio/listadosocios.jsp");
				DaoSocio dao=new DaoSocio();
				try {
					ArrayList<Socio> listado = dao.listadoSocios();
					request.setAttribute("socios", listado);
				} catch (SQLException sqle) {
					// TODO Auto-generated catch block
					procesarErrorSql(request, response, sqle, "socio/listadosocios.jsp");
					} catch (Exception e) {
					// TODO Auto-generated catch block
						procesarError(request, response, e, "socio/listadosocios.jsp");
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
		String direccion = request.getParameter("direccion");
		String email = request.getParameter("email");		
		if (nombre == null || nombre.trim().isEmpty() || nombre == null || direccion == null || direccion.trim().isEmpty() || email == null || email.trim().isEmpty()) {
			request.setAttribute("error", "Nombre, direccion y email es obligatorio");
			request.getRequestDispatcher("socio/altasocio.jsp").forward(request, response);
			return;
		}
		
		try {
			Socio nuevoSocio = new Socio();
			nuevoSocio.setNombre(nombre);
			nuevoSocio.setDireccion(direccion);
			nuevoSocio.setEmail(email);
			nuevoSocio.setVersion(1);
			DaoSocio dao=new DaoSocio();
			dao.insertaSocio(nuevoSocio);
			request.setAttribute("confirmaroperacion", "El socio ha sido creado correctamente");	
		}
	    catch (SQLException sqle) {
			procesarErrorSql(request, response, sqle, "socio/altasocio.jsp");
	    } catch (Exception e) {
			procesarError(request, response, e, "socio/altasocio.jsp");
	    }
		request.getRequestDispatcher("socio/altasocio.jsp").forward(request, response);	}
	
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
