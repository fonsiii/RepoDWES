<%@ page import="java.util.List" %> <!-- Importa la clase List de Java, necesaria para trabajar con listas en la página JSP -->
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- Importa la biblioteca de etiquetas JSTL Core para usar en la página -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <!-- Define el lenguaje y la codificación de la página JSP -->

<!DOCTYPE html> <!-- Declara el tipo de documento como HTML5 -->
<html>
<head>
    <meta charset="UTF-8"> <!-- Establece la codificación de caracteres de la página -->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> <!-- Define el tipo de contenido de la página -->
    <title>Listado de socios</title> <!-- Título que aparecerá en la pestaña del navegador -->
    <jsp:directive.include file="/includes/includefile.jspf" /> <!-- Incluye un archivo JSP común que puede contener configuraciones o elementos comunes -->
</head>

<body>
    <div class="container"> <!-- Contenedor principal para la página -->
        <div class="header"></div> <!-- Sección del encabezado, que puede contener un título o logo -->
        <div class="menu"> <!-- Contenedor para el menú de navegación -->
            <jsp:directive.include file="/WEB-INF/menu.jspf" /> <!-- Incluye el menú de navegación de la aplicación -->
        </div>
        
        <h1>LISTADO DE SOCIOS</h1> <!-- Título principal de la página que indica que se mostrará el listado de socios -->
        
        <c:if test="${not empty socios}"> <!-- Verifica si la lista de socios no está vacía -->
            <div class="w-75 ma"> <!-- Contenedor para la tabla, con clases CSS para estilos -->
                <table class="table tablaconborde tablacebra tabla-hover"> <!-- Tabla para mostrar los datos de los socios -->
                    <tr> <!-- Fila de encabezados de la tabla -->
                        <th>ID de socio</th> <!-- Cabecera para el ID del socio -->
                        <th>Nombre</th> <!-- Cabecera para el nombre del socio -->
                        <th>E-mail</th> <!-- Cabecera para el email del socio -->
                        <th>Direccion</th> <!-- Cabecera para la dirección del socio -->
                        <th>Version</th> <!-- Cabecera para la versión del socio -->
                    </tr>
                    <c:forEach items="${socios}" var="socio"> <!-- Itera sobre la lista de socios -->
                        <tr> <!-- Fila para cada socio -->
                            <td class="txtcentrado">${ socio.getIdSocio() }</td> <!-- Muestra el ID del socio, centrado -->
                            <td class="txtcentrado">${ socio.getNombre() }</td> <!-- Muestra el nombre del socio, centrado -->
                            <td class="txtcentrado">${ socio.getEmail() }</td> <!-- Muestra el email del socio, centrado -->
                            <td class="txtcentrado">${ socio.getDireccion() }</td> <!-- Muestra la dirección del socio, centrado -->
                            <td class="txtcentrado">${ socio.getVersion() }</td> <!-- Muestra la versión del socio, centrado -->
                        </tr>
                    </c:forEach> <!-- Fin de la iteración sobre socios -->
                </table>
            </div>
        </c:if> <!-- Fin de la comprobación si la lista de socios no está vacía -->
        
        <c:if test="${empty socios}"> <!-- Verifica si la lista de socios está vacía -->
            <p>No hay socios disponibles</p> <!-- Mensaje que se muestra si no hay socios en la lista -->
        </c:if> <!-- Fin de la comprobación si la lista de socios está vacía -->
    </div>
</body>

</html>
