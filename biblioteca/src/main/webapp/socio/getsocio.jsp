<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- Importa la biblioteca de etiquetas JSTL Core para usar en la página -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %> <!-- Define el lenguaje y la codificación de la página JSP -->

<!DOCTYPE html> <!-- Declara el tipo de documento como HTML5 -->
<html>
<head>
    <meta charset="UTF-8"> <!-- Establece la codificación de caracteres de la página -->
    <title>Buscar Socio</title> <!-- Título que aparecerá en la pestaña del navegador -->
    <jsp:directive.include file="../includes/includefile.jspf" /> <!-- Incluye un archivo JSP común que puede contener configuraciones o elementos comunes -->
</head>
<body>
    <div class="container"> <!-- Contenedor principal para la página -->
        <div class="header"></div> <!-- Sección del encabezado, que puede contener un título o logo -->
        <div class="menu"> <!-- Contenedor para el menú de navegación -->
            <jsp:directive.include file="../WEB-INF/menu.jspf" /> <!-- Incluye el menú de navegación de la aplicación -->
        </div>

        <!-- Comprobación de errores -->
        <c:if test="${error != null}"> <!-- Verifica si existe un mensaje de error en el contexto -->
            <div class="diverror"> <!-- Contenedor para mostrar el error -->
                <p>
                    <strong>Error:</strong><br> <!-- Texto en negrita que indica que hay un error -->
                    <c:out value="${error}" /> <!-- Muestra el mensaje de error almacenado en el contexto -->
                </p>
            </div>
        </c:if>

        <!-- Comprobación de confirmaciones -->
        <c:if test="${confirmaroperacion != null}"> <!-- Verifica si existe un mensaje de confirmación -->
            <div class="divconfirmacion"> <!-- Contenedor para mostrar el mensaje de confirmación -->
                <p>
                    <strong>Mensaje:</strong><br> <!-- Texto en negrita que indica un mensaje -->
                    <c:out value="${confirmaroperacion}" /> <!-- Muestra el mensaje de confirmación almacenado en el contexto -->
                    <script>
                        // Cambia el estado en el historial del navegador para evitar el reenvío del formulario al actualizar la página
                        window.history.replaceState(null, null, "socio/getsocio.jsp")
                    </script>
                </p>
            </div>
        </c:if>

        <!-- Formulario de búsqueda de socio -->
        <div id="formBusquedaSocio" class="formulariogeneral"> <!-- Contenedor para el formulario de búsqueda -->
            <form name="frmBusquedaSocio" method="get" action="${pageContext.request.contextPath}/controllerSocio"> <!-- Formulario que envía datos mediante el método GET -->
                <fieldset id="busquedaSocio"> <!-- Agrupación de campos de búsqueda -->
                    <legend>
                        <img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Buscar Socio <!-- Imagen y título del formulario -->
                    </legend>
                    <div class="etiquetas"> <!-- Contenedor para las etiquetas de los campos -->
                        <label for="nombre">Nombre del Socio:</label> <!-- Etiqueta para el campo de nombre -->
                    </div>
                    <div class="campos"> <!-- Contenedor para los campos de entrada -->
                        <input type="text" id="nombre" name="nombre" value="${nombreBuscado}" /> <!-- Campo de entrada para el nombre del socio, con valor predeterminado -->
                    </div>
                    <div class="cb"></div> <!-- Espacio entre campos -->
                    <div class="botones"> <!-- Contenedor para los botones del formulario -->
                        <input type="submit" name="Buscar" value="Buscar"> <!-- Botón para enviar el formulario de búsqueda -->
                    </div>
                </fieldset>
            </form>
        </div>

        <!-- Mostrar resultados de la búsqueda -->
        <c:if test="${not empty socios}"> <!-- Verifica si la lista de socios no está vacía -->
            <h2>Resultados de la búsqueda:</h2> <!-- Título para la sección de resultados -->
            <table class="table tablaconborde tablacebra tabla-hover"> <!-- Tabla para mostrar los resultados -->
                <thead>
                    <tr>
                        <th>Nombre</th> <!-- Cabecera para el nombre del socio -->
                        <th>Dirección</th> <!-- Cabecera para la dirección del socio -->
                        <th>Email</th> <!-- Cabecera para el email del socio -->
                        <th>Versión</th> <!-- Cabecera para la versión del socio -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="socio" items="${socios}"> <!-- Itera sobre la lista de socios -->
                        <tr>
                            <td class="txtcentrado"><c:out value="${socio.nombre}" /></td> <!-- Muestra el nombre del socio -->
                            <td class="txtcentrado"><c:out value="${socio.direccion}" /></td> <!-- Muestra la dirección del socio -->
                            <td class="txtcentrado"><c:out value="${socio.email}" /></td> <!-- Muestra el email del socio -->
                            <td class="txtcentrado"><c:out value="${socio.version}" /></td> <!-- Muestra la versión del socio -->
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>
