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

import dao.DaoAutor;
import entidades.Autor;

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
	    dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
