<%@ page import="dao.DaoSocioMoroso" %> <!-- Importa la clase DaoSocioMoroso que maneja la lógica de acceso a datos para socios morosos -->
<%@ page import="java.util.ArrayList" %> <!-- Importa la clase ArrayList para utilizar listas dinámicas en Java -->
<%@ page import="entidades.SocioMoroso" %> <!-- Importa la clase SocioMoroso que representa a un socio moroso en la aplicación -->
<%@ page import="dao.DaoSocio" %> <!-- Importa la clase DaoSocio que maneja la lógica de acceso a datos para socios en general -->
<%@ page import="dao.DaoLibroMoroso" %> <!-- Importa la clase DaoLibroMoroso que gestiona libros morosos -->
<%@ page import="entidades.LibroMoroso" %> <!-- Importa la clase LibroMoroso que representa un libro moroso -->
<%@ page import="java.sql.SQLException" %> <!-- Importa SQLException para manejar errores de base de datos -->
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- Importa la biblioteca JSTL Core para usar etiquetas de control de flujo -->
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %> <!-- Importa la biblioteca de formato de JSTL para formatear datos -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <!-- Define el lenguaje y la codificación de la página JSP -->

<!DOCTYPE html> <!-- Declara que el documento es HTML5 -->
<html>

<head>
    <meta charset="UTF-8"> <!-- Establece la codificación de caracteres para el documento -->
    <title>Listado de Socios Morosos</title> <!-- Título de la página que se mostrará en la pestaña del navegador -->
    <jsp:directive.include file="/includes/includefile.jspf" /> <!-- Incluye un archivo JSP que contiene configuraciones o elementos comunes -->
</head>

<body>

    <div class="container"> <!-- Contenedor principal para la página -->
        <div class="header"></div> <!-- Sección del encabezado que puede contener un título o logo -->
        <div class="menu"> <!-- Contenedor para el menú de navegación -->
            <jsp:directive.include file="/WEB-INF/menu.jspf" /> <!-- Incluye el menú de navegación de la aplicación -->
        </div>

        <h1>LISTADO DE SOCIOS MOROSOS</h1> <!-- Título principal de la página -->

        <%
        // Obtiene la lista de socios morosos de la sesión o la carga desde la base de datos si no está disponible
        ArrayList<SocioMoroso> sociosMorosos = (ArrayList<SocioMoroso>) session.getAttribute("sociosMorosos");
        try {
            DaoSocioMoroso daoMoroso = new DaoSocioMoroso(); // Crea una instancia del DAO para manejar socios morosos
            sociosMorosos = daoMoroso.listadoSociosMorosos(); // Recupera la lista de socios morosos de la base de datos
            session.setAttribute("sociosMorosos", sociosMorosos); // Almacena la lista de socios morosos en la sesión
        } catch (SQLException e) {
            e.printStackTrace(); // Maneja excepciones relacionadas con la base de datos
        } catch (Exception ex) {
            ex.printStackTrace(); // Maneja cualquier otra excepción
        }
        %>

        <div class="w-75 ma"> <!-- Contenedor para la tabla de socios morosos -->
            <table class="table tablaconborde tablacebra tabla-hover"> <!-- Tabla para mostrar los datos de los socios morosos -->
                <tr> <!-- Fila de encabezado de la tabla -->
                    <th>ID de Socio</th> <!-- Encabezado para el ID del socio -->
                    <th>Nombre</th> <!-- Encabezado para el nombre del socio -->
                    <th>Libros</th> <!-- Encabezado para la acción de ver libros morosos -->
                </tr>
                <c:forEach items="${sociosMorosos}" var="socioMoroso"> <!-- Itera sobre la lista de socios morosos -->
                    <tr> <!-- Fila para cada socio moroso -->
                        <td class="txtcentrado">${socioMoroso.getId()}</td> <!-- Muestra el ID del socio centrado -->
                        <td class="txtcentrado">${socioMoroso.getNombre()}</td> <!-- Muestra el nombre del socio centrado -->
                        <td class="txtcentrado">
                            <!-- Enlace para ver los libros morosos del socio -->
                            <a href="${pageContext.request.contextPath}/controllerSocio?operacion=librosSocioMoroso&idSocio=${socioMoroso.getId()}">Ver Libros</a>
                        </td>
                    </tr>
                </c:forEach> <!-- Fin de la iteración sobre socios morosos -->
            </table>
        </div>

        <!-- Sección para mostrar los libros morosos del socio seleccionado -->
        <div class="w-75 ma"> <!-- Contenedor para la tabla de libros morosos -->
            <c:if test="${not empty librosMorosos}"> <!-- Verifica si la lista de libros morosos no está vacía -->
                <h2>Préstamos No Devueltos de ${socioNombre}</h2> <!-- Título para la sección de libros morosos -->
                <table class="table tablaconborde tablacebra tabla-hover"> <!-- Tabla para mostrar los libros morosos -->
                    <thead>
                        <tr> <!-- Fila de encabezado -->
                            <th>Título</th> <!-- Encabezado para el título del libro -->
                            <th>Fecha de Préstamo</th> <!-- Encabezado para la fecha de préstamo -->
                            <th>Días de Demora</th> <!-- Encabezado para los días de demora -->
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${librosMorosos}" var="libro"> <!-- Itera sobre la lista de libros morosos -->
                            <tr> <!-- Fila para cada libro -->
                                <td>${libro.tituloLibro}</td> <!-- Muestra el título del libro -->
                                <td>${libro.fechaPrestamo}</td> <!-- Muestra la fecha de préstamo -->
                                <td>${libro.diasDemora}</td> <!-- Muestra los días de demora -->
                            </tr>
                        </c:forEach> <!-- Fin de la iteración sobre libros morosos -->
                    </tbody>
                </table>
            </c:if> <!-- Fin de la comprobación si la lista de libros morosos no está vacía -->
        </div>
        
        <c:if test="${empty librosMorosos}"> <!-- Verifica si la lista de libros morosos está vacía -->
            <c:if test="${not empty socioNombre}"> <!-- Verifica si el nombre del socio no está vacío -->
                <h2>Préstamos No Devueltos de ${socioNombre}</h2> <!-- Título para la sección de libros morosos -->
                <p>No hay libros morosos para este socio.</p> <!-- Mensaje que indica que no hay libros morosos -->
            </c:if> <!-- Fin de la comprobación del nombre del socio -->
        </c:if> <!-- Fin de la comprobación si la lista de libros morosos está vacía -->
        
    </div>
</body>

</html>
