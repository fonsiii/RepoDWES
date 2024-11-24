package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import dao.DaoAutor;
import dao.DaoEjemplar;
import dao.DaoLibroMoroso;
import dao.DaoPrestamo;
import dao.DaoSocio;
import dao.DaoSocioMoroso;
import dao.DaoSocioPenalizado;
import entidades.Autor;
import entidades.Ejemplar;
import entidades.LibroMoroso;
import entidades.Prestamo;
import entidades.Socio;
import entidades.SocioMoroso;
import excepciones.PrestamoException;

@WebServlet("/controllerAdmin") 
public class ControllerAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String ERROR_PAGE = "admin/error.jsp"; 
    private static final String PRESTAMO_PAGE = "admin/prestamo.jsp"; 
    private static final String DEVOLUCION_PAGE = "admin/devolucion.jsp"; 
    private static final String LISTADO_SOCIOS_PAGE = "admin/listadosocios.jsp";
    private static final String SOCIOS_MOROSOS_PAGE = "admin/sociosmorosos.jsp";
    private static final String GET_SOCIO_PAGE = "admin/getsocio.jsp";
    private static final String MODIFICAR_SOCIO_PAGE = "admin/modificarsocio.jsp";
    private static final String ALTA_AUTOR_PAGE = "admin/altaautor.jsp"; 
    private static final String ALTA_SOCIO_PAGE = "admin/altasocio.jsp";

    private final DaoAutor daoAutor = new DaoAutor();
    private final DaoSocio daoSocio = new DaoSocio();
    private final DaoSocioMoroso daoMoroso = new DaoSocioMoroso();
    private final DaoLibroMoroso daoLibroMoroso = new DaoLibroMoroso();
    private final DaoEjemplar daoEjemplar = new DaoEjemplar();
    private final DaoPrestamo daoPrestamo = new DaoPrestamo();
    private final DaoSocioPenalizado daoSocioPenalizado = new DaoSocioPenalizado();

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
    

        
        switch (operacion) {
            case "altaAutor":
                altaAutor(request, response);
                break;
            case "altaSocio":
                altaSocio(request, response);
                break;
            case "getSocio":
                listarSociosBuscados(request, response);
                break;
            case "modificarSocio":
                actualizarSocio(request, response);
                break;
            case "nuevoPrestamo":
            	nuevoPrestamo(request, response);
            	break;
            case "devolverEjemplar":
            	devolverEjemplar(request,response);
            	break;
            default: 
                procesarError(request, response, new Exception("Operación no reconocida: " + operacion), ERROR_PAGE);
                break;
        }
    }

    private void altaAutor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombreAutor = request.getParameter("nombre");
        String fechaNacimientoStr = request.getParameter("fechaNacimiento");

        if (nombreAutor == null || nombreAutor.isBlank() || fechaNacimientoStr == null || fechaNacimientoStr.isBlank()) {
            request.setAttribute("error", "Nombre y fecha de nacimiento son obligatorios.");
            request.getRequestDispatcher(ALTA_AUTOR_PAGE).forward(request, response);
            return;
        }

        try {
            Date fechaNacimiento = Date.valueOf(fechaNacimientoStr);

            Autor nuevoAutor = new Autor();
            nuevoAutor.setNombre(nombreAutor);
            nuevoAutor.setFechaNacimiento(fechaNacimiento);

            daoAutor.insertaAutor(nuevoAutor);
            request.setAttribute("confirmaroperacion", "El autor ha sido creado correctamente.");
        } catch (SQLException sqle) {
            procesarErrorSQL(request, response, sqle);
        } catch (Exception e) {
            procesarError(request, response, e, ALTA_AUTOR_PAGE);
        }

        request.getRequestDispatcher(ALTA_AUTOR_PAGE).forward(request, response);
    }

    private void altaSocio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            request.setAttribute("confirmaroperacion", "El socio ha sido creado correctamente.");
        } catch (SQLException sqle) {
            procesarErrorSQL(request, response, sqle);
        } catch (Exception e) {
            procesarError(request, response, e, ALTA_SOCIO_PAGE);
        }

        request.getRequestDispatcher(ALTA_SOCIO_PAGE).forward(request, response);
    }


    private void listarSocios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int paginaActual = 0; 
        int registrosPorPagina = 5; 

        try {
            String pagParam = request.getParameter("pag");
            String nrpParam = request.getParameter("nrp");

            if (pagParam != null && !pagParam.isBlank()) {
                paginaActual = Math.max(0, Integer.parseInt(pagParam));
            }
            if (nrpParam != null && !nrpParam.isBlank()) {
                registrosPorPagina = Integer.parseInt(nrpParam);
            }

            int totalRegistros = daoSocio.contarNumeroSocios();
            
            int totalPages = (int) Math.ceil((double) totalRegistros / registrosPorPagina);
            
            if (paginaActual >= totalPages) {
                paginaActual = totalPages - 1; 
            }
            
            ArrayList<Socio> listado = daoSocio.listadoSocios(paginaActual, registrosPorPagina);

            int limiteInferior = (paginaActual * registrosPorPagina) + 1;
            int limiteSuperior = Math.min((paginaActual + 1) * registrosPorPagina, totalRegistros);

            request.setAttribute("socios", listado);
            request.setAttribute("limiteInferior", limiteInferior);
            request.setAttribute("limiteSuperior", limiteSuperior);
            request.setAttribute("paginaActual", paginaActual);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("registrosPorPagina", registrosPorPagina);
            request.setAttribute("totalRegistros", totalRegistros);

            request.getRequestDispatcher(LISTADO_SOCIOS_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Los parámetros de paginación no son válidos.");
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
        	procesarErrorSQL(request, response, sqle);
        } catch (Exception e) {
            procesarError(request, response, e, SOCIOS_MOROSOS_PAGE);
        }
    }

    private void mostrarDatosSocio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idSocio = Integer.parseInt(request.getParameter("idSocio"));
        try {
            Socio socio = daoSocio.buscarSocioPorId(idSocio);
            request.setAttribute("socio", socio);
            request.getRequestDispatcher(MODIFICAR_SOCIO_PAGE).forward(request, response);
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
        	procesarErrorSQL(request, response, sqle);
        } catch (Exception e) {
            procesarError(request, response, e, MODIFICAR_SOCIO_PAGE);
        }

        mostrarDatosSocio(request, response); 
    }
    
    private void nuevoPrestamo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	int idSocio = Integer.parseInt(request.getParameter("idSocio"));
    	int idEjemplar = Integer.parseInt(request.getParameter("idEjemplar"));
    	
    	try {
    	validarEjemplar(idEjemplar, daoEjemplar);
    	validarSocio(idSocio, daoSocio);
    	validarPrestamoDupliado(idEjemplar, daoPrestamo);
    	validarLibrosPendientes(idSocio, daoPrestamo);
    	validarSocioPenalizado(idSocio, daoSocioPenalizado);
    	validarISBNDuplicado(idEjemplar, idSocio, daoEjemplar, daoPrestamo);
    	
    	LocalDate fechaHoy = LocalDate.now();
    	LocalDate fechaLimite = calcularFechaLimite(fechaHoy);
    	
    	daoPrestamo.insertarPrestamo(idEjemplar, idSocio, fechaHoy, fechaLimite);
    	
    	request.setAttribute("confirmaroperacion", "Prestamo insertado correctamente");
    	request.getRequestDispatcher(PRESTAMO_PAGE).forward(request, response);
    } catch(Exception e) {
    	procesarError(request, response, e, PRESTAMO_PAGE);
    }
    }
    
    private void validarEjemplar(int idEjemplar, DaoEjemplar daoEjemplar) throws PrestamoException, SQLException, Exception{
    	Ejemplar ejemplar = daoEjemplar.ejemplarById(idEjemplar);
    	if(ejemplar == null)
    		throw new PrestamoException("El ejemplar " + idEjemplar + " no es valido");
    }


    private void validarSocio(int idSocio, DaoSocio daoSocio) throws PrestamoException, SQLException, Exception{
    	Socio socio = daoSocio.buscarSocioPorId(idSocio);
    	if(socio==null)
    		throw new PrestamoException("El socio " + idSocio + " no es valido");
    }

    private void validarPrestamoDupliado(int idEjemplar, DaoPrestamo daoPrestamo) throws PrestamoException, SQLException, Exception{
    	Prestamo prestamo = daoPrestamo.listadoPrestamoBYId(idEjemplar);
    	if(prestamo!=null)
    		throw new PrestamoException("El ejemplar " + idEjemplar + " ya esta prestado");
    }
    
    private void validarLibrosPendientes(int idSocio, DaoPrestamo daoPrestamo) throws PrestamoException, SQLException, Exception{
    	if(!daoPrestamo.listadoSocioPrestamoId(idSocio).isEmpty())
    		throw new PrestamoException("El socio " + idSocio + " tiene libros pendientes de devolver");
    }
    
    private void validarSocioPenalizado(int idSocio, DaoSocioPenalizado daoSocioPenalizado) throws PrestamoException, SQLException, Exception{
    	if(!daoSocioPenalizado.listadoSociosPenalizados(idSocio).isEmpty())
    		throw new PrestamoException("El socio " + idSocio + " esta penalizado");
    }
    
    private void validarISBNDuplicado(int idEjemplar, int idSocio, DaoEjemplar daoEjemplar, DaoPrestamo daoPrestamo) throws PrestamoException, SQLException, Exception{
    	String ISBN = daoEjemplar.buscarIsbn(idEjemplar).getIsbn();
    	ArrayList<Ejemplar> listadoEjemplares = daoEjemplar.buscarEjemplaresPorISBN(ISBN);
    	ArrayList<Prestamo> ejemplarSocio = daoPrestamo.ejemplaresSocio(idSocio);
    	for(Ejemplar ejemplar : listadoEjemplares) {
    		for(Prestamo prestamo : ejemplarSocio) {
    			if(ejemplar.getIdEjemplar() == prestamo.getIdEjemplar()) {
    				throw new PrestamoException("El socio " + idSocio + " ya tiene un ejemplar de ese libro");
    			}
    		}
    	}
    }
    
    private LocalDate calcularFechaLimite(LocalDate fechaInicio) {
    	LocalDate fechaLimite = fechaInicio.plusDays(5);
    	if(fechaLimite.getDayOfWeek() == DayOfWeek.SATURDAY) {
    		fechaLimite = fechaLimite.plusDays(2);
    	} else if(fechaLimite.getDayOfWeek() == DayOfWeek.SUNDAY) {
    		fechaLimite = fechaLimite.plusDays(1);
    	}
    	return fechaLimite;
    }
    
    private void devolverEjemplar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		int idEjemplar = Integer.parseInt(request.getParameter("idEjemplar"));
    		validarEjemplar(idEjemplar, daoEjemplar);
    		Prestamo prestamo = validarEjemplarPrestado(idEjemplar);
    		LocalDate fechaHoy = LocalDate.now();
    		LocalDate fechaLimite = prestamo.getFechaLimiteDevolucion().toLocalDate();
    		int idSocio = prestamo.getIdSocio();
    		
    		if(fechaHoy.isAfter(fechaLimite))
    			penalizarSocio(idSocio, fechaHoy);
    		
    		devolver(prestamo, fechaHoy);
    		request.setAttribute("confirmaroperacion", "Devolucion realizada correctamente");
    		request.getRequestDispatcher(DEVOLUCION_PAGE).forward(request, response);
    		}catch(Exception e) {
    			procesarError(request, response, e, DEVOLUCION_PAGE);
    		}
    }
    private Prestamo validarEjemplarPrestado(int idEjemplar) throws PrestamoException, SQLException, Exception{
    	ArrayList<Prestamo> prestamos = daoPrestamo.seleccionarEjemplares(idEjemplar);
    	if(prestamos.isEmpty()) {
    		throw new PrestamoException("El ejemplar " + idEjemplar + " no esta prestado");
    	}
    		return prestamos.get(0);
    	}
    
    private void penalizarSocio(int idSocio, LocalDate fechaHoy) throws SQLException,Exception{
    	LocalDate fechaFinPenalizacion = fechaHoy.plusDays(15);
    	daoSocioPenalizado.insertarSocioPenalizado(idSocio, fechaFinPenalizacion);
    }
    
    private void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws SQLException, Exception{
    	daoPrestamo.borrarPrestamo(prestamo.getIdEjemplar());
    }
    

    protected void procesarError(HttpServletRequest request, HttpServletResponse response, Exception e, String url) throws ServletException, IOException {
        String mensajeError = (e instanceof SQLException) ? ((SQLException) e).getErrorCode() + ": " + e.getMessage() : e.getMessage();
        request.setAttribute("error", mensajeError); 
        request.getRequestDispatcher(url == null ? ERROR_PAGE : url).forward(request, response);
    }
    protected void procesarErrorSQL(HttpServletRequest request,HttpServletResponse response,
    		SQLException e)

    		throws ServletException, IOException {

    		int codigoError = e.getErrorCode();
    		String mensajeError;
    		switch (codigoError) {
    		case 30006:
    		mensajeError = "Registro en proceso de modificación. Inténtelo más tarde";
    		break;
    		default:
    		mensajeError = e.getMessage();
    		}
    		request.setAttribute("error", mensajeError);
    		request.getRequestDispatcher("admin/error.jsp").forward(request,response);
    		}
}

