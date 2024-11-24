package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/controller") 
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("operacion");  // Obtener el valor de la operación

        switch (operacion) {
            case "logout":
                handleLogout(request, response);
                break;
            // Puedes agregar más casos aquí para otras operaciones
            default:
                response.sendRedirect(request.getContextPath() + "/error.jsp");  // Redirigir en caso de operación no reconocida
                break;
        }
    }

    // Método para manejar el logout
    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Invalidar la sesión
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();  // Eliminar los datos de la sesión
        }

        // Redirigir al usuario a la página de login después de hacer logout
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
