<%@ page import="java.util.List"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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

		<h1 class="txtCentrado">LISTADO DE SOCIOS PAGINADO</h1>

		<c:if test="${totalRegistros != null}">
			<p>Total de socios: ${totalRegistros}</p>
		</c:if>

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
							<td class="txtcentrado">${socio.idSocio}</td>
							<td class="txtcentrado">${socio.nombre}</td>
							<td class="txtcentrado">${socio.email}</td>
							<td class="txtcentrado">${socio.direccion}</td>
							<td class="txtcentrado">${socio.version}</td>
						</tr>
					</c:forEach>
				</table>
			</div>

			<div class="pagination">
				<c:if test="${paginaActual == 0}">
					<a
						href="controllerAdmin?operacion=listarSocios&pag=${totalPages - 1}&nrp=${registrosPorPagina}">Anterior</a>
				</c:if>
				<c:if test="${paginaActual > 0}">
					<a
						href="controllerAdmin?operacion=listarSocios&pag=${paginaActual - 1}&nrp=${registrosPorPagina}">Anterior</a>
				</c:if>

				<c:forEach begin="1" end="${totalPages}" var="i">
					<c:choose>
						<c:when test="${i - 1 == paginaActual}">
							<a href="#" class="active">${i}</a>
						</c:when>
						<c:otherwise>
							<a
								href="controllerAdmin?operacion=listarSocios&pag=${i - 1}&nrp=${registrosPorPagina}">${i}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<c:if test="${paginaActual < totalPages - 1}">
					<a
						href="controllerAdmin?operacion=listarSocios&pag=${paginaActual + 1}&nrp=${registrosPorPagina}">Siguiente</a>
				</c:if>
				<c:if test="${paginaActual == totalPages - 1}">
					<a
						href="controllerAdmin?operacion=listarSocios&pag=0&nrp=${registrosPorPagina}">Siguiente</a>
				</c:if>
			</div>



		</c:if>

		<c:if test="${empty socios}">
			<p>No hay socios disponibles</p>
		</c:if>
	</div>
</body>
</html>
