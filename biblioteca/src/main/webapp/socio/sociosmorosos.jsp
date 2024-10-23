<%@ page import="dao.DaoSocioMoroso" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidades.SocioMoroso" %>
<%@ page import="dao.DaoSocio" %>
<%@ page import="dao.DaoLibroMoroso" %>
<%@ page import="entidades.LibroMoroso" %>
<%@ page import="java.sql.SQLException" %>
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

        <h1>LISTADO DE SOCIOS MOROSOS</h1>

        <div class="w-75 ma">
            <table class="table tablaconborde tablacebra tabla-hover">
                <tr>
                    <th>ID de Socio</th>
                    <th>Nombre</th>
                    <th>Libros</th>
                </tr>
                <c:forEach items="${sociosMorosos}" var="socioMoroso">
                    <tr>
                        <td class="txtcentrado">${socioMoroso.getId()}</td>
                        <td class="txtcentrado">${socioMoroso.getNombre()}</td>
                        <td class="txtcentrado">
                            <a href="${pageContext.request.contextPath}/controllerSocio?operacion=librosSocioMoroso&idSocio=${socioMoroso.getId()}">Ver Libros</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div class="w-75 ma">
            <c:if test="${not empty librosMorosos}">
                <h2>Préstamos No Devueltos de ${socioNombre}</h2>
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
        
        <c:if test="${empty librosMorosos}">
            <c:if test="${not empty socioNombre}">
                <h2>Préstamos No Devueltos de ${socioNombre}</h2>
                <p>No hay libros morosos para este socio.</p>
            </c:if>
        </c:if>
        
    </div>
</body>

</html>
