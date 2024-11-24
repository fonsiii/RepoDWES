<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Buscar Libros</title>
<jsp:directive.include file="../includes/includefile.jspf" />
</head>
<body>
	<div class="container">
		<div class="header"></div>
		<div class="menu">
			<jsp:directive.include file="../WEB-INF/menu.jspf" />
		</div>

		<!-- Mostrar mensaje de error si existe -->
		<c:if test="${error != null}">
			<div class="diverror">
				<p>
					<strong>Error:</strong><br>
					<c:out value="${error}" />
					<br>
				</p>
			</div>
		</c:if>


		<!-- Mostrar mensaje de confirmación si existe -->
		<c:if test="${confirmaroperacion != null}">
			<div class="divconfirmacion">
				<p>
					<strong>Mensaje:</strong><br>
					<c:out value="${confirmaroperacion}" />
					<script>
						window.history.replaceState(null, null,
								"socio/getlibros.jsp")
					</script>
				</p>
			</div>
		</c:if>

		<!-- Formulario de búsqueda de libros -->
		<div id="formBusquedaLibro" class="formulariogeneral">
			<form name="frmBusquedaLibro" method="post"
				action="${pageContext.request.contextPath}/controllerSocio?operacion=buscarLibros">
				<fieldset id="busquedaLibro">
					<legend>
						<img
							src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Buscar
						Libro
					</legend>
					<div class="etiquetas">
						<label for="searchType">Buscar por:</label>
					</div>
					<div class="campos">
						<select id="searchType" name="searchType">
							<option value="autor"
								${param.searchType == 'autor' ? 'selected' : ''}>Autor</option>
							<option value="titulo"
								${param.searchType == 'titulo' ? 'selected' : ''}>Título</option>
							<option value="isbn"
								${param.searchType == 'isbn' ? 'selected' : ''}>ISBN</option>

						</select>
					</div>
					<div class="cb"></div>

					<div class="etiquetas">
						<label for="searchInput">Ingrese su búsqueda:</label>
					</div>
					<div class="campos">
						<input type="text" id="searchInput" name="searchInput"
							value="${not empty param.searchInput ? param.searchInput : ''}" />
					</div>
					<div class="cb"></div>

					<div class="botones">
						<input type="hidden" name="formSubmitted" value="true" /> <input
							type="submit" name="Buscar" value="Buscar">
					</div>
				</fieldset>
			</form>

		</div>

		<!-- Mostrar mensaje si no hay resultados -->
		<c:if test="${empty resultados && param.formSubmitted != null}">
			<div class="diverror">
				No se encontraron libros para:
				<c:out value="${searchInput}" />
			</div>
		</c:if>

		<!-- Mostrar los resultados de la búsqueda en una tabla -->
		<c:if test="${not empty resultados}">
			<div class="w-75 ma">
				<table class="table tablaconborde tablacebra tabla-hover">
					<tr>
						<th>Título</th>
						<th>Autor</th>
						<th>Ejemplares Totales</th>
						<th>Ejemplares en Préstamo</th>
						<th>Ejemplares Disponibles</th>
					</tr>
					<c:forEach items="${resultados}" var="libro">
						<tr>
							<td class="txtcentrado"><c:out value="${libro.titulo}" /></td>
							<td class="txtcentrado"><c:out value="${libro.autor}" /></td>
							<td class="txtcentrado"><c:out
									value="${libro.ejemplaresTotales}" /></td>
							<td class="txtcentrado"><c:out
									value="${libro.ejemplaresEnPrestamo}" /></td>
							<td class="txtcentrado"><c:out
									value="${libro.ejemplaresDisponibles}" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:if>
	</div>
</body>
</html>
