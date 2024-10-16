<%@page import="entidades.Autor"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Listado de autores</title>

<jsp:directive.include file="/includes/includefile.jspf" />
</head>

<body>

	<div class="container">
		<div class="header"></div>
		<div class="menu">
			<jsp:directive.include file="/WEB-INF/menu.jspf" />
		</div>
			<h1>LISTADO DE AUTORES</h1>
		<%
		ArrayList<Autor> autores = (ArrayList<Autor>) session.getAttribute("autores");
		%>
		<div class="w-75 ma">
			<table class="table tablaconborde tablacebra tabla-hover">
				<tr>
					<th>ID de autor</th>
					<th>Nombre</th>
					<th>Fecha de nacimiento</th>
				</tr>
				<c:forEach items="${autores}" var="autor">
					<tr>
						<td class="txtcentrado">${ autor.getIdAutor() }</td>
						<td class="txtcentrado">${ autor.getNombre() }</td>
						<td class="txtcentrado"><fmt:formatDate
								value="${autor.getFechaNacimiento()}" pattern="dd/MM/yyyy" /></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>

</html>