<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Listado de Socios Morosos</title>
    <jsp:directive.include file="/includes/includefile.jspf" />
</head>

<body>

    <div class="container">
        <div class="header"></div>
        <div class="menu">
            <jsp:directive.include file="/WEB-INF/menu.jspf" />
        </div>

        <h1 class="txtCentrado">LISTADO DE SOCIOS MOROSOS</h1>

        <div class="w-75 ma">
            <table class="table tablaconborde tablacebra tabla-hover">
                <tr>
                    <th>ID de Socio</th>
                    <th>Nombre</th>
                    <th>Libros</th>
                </tr>
                <!-- Itera sobre la lista de socios morosos proporcionada por el controlador -->
                <c:forEach items="${sociosMorosos}" var="socioMoroso">
                    <tr>
                        <td class="txtcentrado">${socioMoroso.id}</td>
                        <td class="txtcentrado">${socioMoroso.nombre}</td>
                        <td class="txtcentrado">
                            <a href="${pageContext.request.contextPath}/controllerAdmin?operacion=librosSocioMoroso&idSocio=${socioMoroso.id}">Ver Libros</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <!-- Condicional para mostrar libros morosos solo si existen -->
        <div class="w-75 ma">
            <c:if test="${not empty librosMorosos}">
                <h2 class="txtCentrado">Préstamos No Devueltos de ${socioNombre}</h2>
                <table class="table tablaconborde tablacebra tabla-hover">
                    <thead>
                        <tr>
                            <th>Título</th>
                            <th>Fecha de Préstamo</th>
                            <th>Días de Demora</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${librosMorosos}" var="libro">
                            <tr>
                                <td class="txtcentrado">${libro.tituloLibro}</td>
                                <td class="txtcentrado">
                                    <fmt:formatDate value="${libro.fechaPrestamo}" pattern="dd/MM/yyyy" />
                                </td>
                                <td class="txtcentrado">${libro.diasDemora}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
        
        <!-- Mensaje si no hay libros morosos para el socio seleccionado -->
        <c:if test="${empty librosMorosos}">
            <c:if test="${not empty socioNombre}">
                <h2>Préstamos No Devueltos de ${socioNombre}</h2>
                <p>No hay libros morosos para este socio.</p>
            </c:if>
        </c:if>
        
    </div>
</body>

</html>
