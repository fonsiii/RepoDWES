<%@ page import="java.util.List" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listado de socios</title>
    <jsp:directive.include file="/includes/includefile.jspf" />
</head>

<body>
    <div class="container">
        <div class="header"></div>
        <div class="menu">
            <jsp:directive.include file="/WEB-INF/menu.jspf" />
        </div>
        
        <h1>LISTADO DE SOCIOS</h1>
        
        <c:if test="${not empty socios}">
            <div class="w-75 ma">
                <table class="table tablaconborde tablacebra tabla-hover">
                    <tr>
                        <th>ID de socio</th>
                        <th>Nombre</th>
                        <th>E-mail</th>
                        <th>Direccion</th>
                        <th>Version</th>
                    </tr>
                    <c:forEach items="${socios}" var="socio">
                        <tr>
                            <td class="txtcentrado">${ socio.getIdSocio() }</td>
                            <td class="txtcentrado">${ socio.getNombre() }</td>
                            <td class="txtcentrado">${ socio.getEmail() }</td>
                            <td class="txtcentrado">${ socio.getDireccion() }</td>
                            <td class="txtcentrado">${ socio.getVersion() }</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
        
        <c:if test="${empty socios}">
            <p>No hay socios disponibles</p>
        </c:if>
    </div>
</body>

</html>
