<%@ page import="entidades.Autor" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Listado de Autores</title>
    <jsp:directive.include file="/includes/includefile.jspf" />
</head>

<body>

    <div class="container">
        <div class="header"></div>
        <div class="menu">
            <jsp:directive.include file="/WEB-INF/menu.jspf" />
        </div>

        <h1>LISTADO DE AUTORES</h1>

        <div class="w-75 ma">
            <c:if test="${empty autores}">
                <div class="diverror">
                    <p>No hay autores disponibles.</p>
                </div>
            </c:if>
            
            <c:if test="${not empty autores}">
                <table class="table tablaconborde tablacebra tabla-hover">
                    <thead>
                        <tr>
                            <th>ID de Autor</th>
                            <th>Nombre</th>
                            <th>Fecha de Nacimiento</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${autores}" var="autor">
                            <tr>
                                <td class="txtcentrado">${(autor.idAutor)}</td>
                                <td class="txtcentrado">${(autor.nombre)}</td>
                                <td class="txtcentrado">
                                    <fmt:formatDate value="${autor.fechaNacimiento}" pattern="dd/MM/yyyy" />
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</body>

</html>
