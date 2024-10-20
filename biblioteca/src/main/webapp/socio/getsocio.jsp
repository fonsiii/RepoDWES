<%@taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Buscar Socio</title>
<jsp:directive.include file="../includes/includefile.jspf" />
</head>
<body>
    <div class="container">
        <div class="header"></div>
        <div class="menu">
            <jsp:directive.include file="../WEB-INF/menu.jspf" />
        </div>
        <!-- Mostrar error si lo hay -->
        <c:if test="${error != null}">
            <div class="diverror">
                <p>
                    <strong>Error:</strong><br>
                    <c:out value="${error}" />
                </p>
            </div>
        </c:if>
        <!-- Mostrar confirmación si lo hay -->
        <c:if test="${confirmaroperacion != null}">
            <div class="divconfirmacion">
                <p>
                    <strong>Mensaje:</strong><br>
                    <c:out value="${confirmaroperacion}" />
                    <script>
                        window.history.replaceState(null, null, "socio/getsocio.jsp")
                    </script>
                </p>
            </div>
        </c:if>
        
        <!-- Formulario de búsqueda de socio -->
        <div id="formBusquedaSocio" class="formulariogeneral">
            <form name="frmBusquedaSocio" method="get"
                action="${pageContext.request.contextPath}/controllerSocio">
                <fieldset id="busquedaSocio">
                    <legend>
                        <img
                            src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Buscar
                        Socio
                    </legend>
                    <div class="etiquetas">
                        <label for="nombre">Nombre del Socio:</label>
                    </div>
                    <div class="campos">
                        <input type="text" id="nombre" name="nombre"
                            value="${nombreBuscado}" />
                    </div>
                    <div class="cb"></div>
                    <div class="botones">
                        <input type="submit" name="Buscar" value="Buscar">
                    </div>
                </fieldset>
            </form>
        </div>

        <!-- Mostrar resultados de la búsqueda -->
        <c:if test="${not empty socios}">
            <h2>Resultados de la búsqueda:</h2>
            <table class="table tablaconborde tablacebra tabla-hover">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Dirección</th>
                        <th>Email</th>
                        <th>Versión</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="socio" items="${socios}">
                        <tr>
                            <td class="txtcentrado"><c:out value="${socio.nombre}" /></td>
                            <td class="txtcentrado"><c:out value="${socio.direccion}" /></td>
                            <td class="txtcentrado"><c:out value="${socio.email}" /></td>
                            <td class="txtcentrado"><c:out value="${socio.version}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>
