<%@page import="entidades.Autor" %>
	<%@page import="java.util.ArrayList" %>
		<%@taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

		
			<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>

		<jsp:directive.include file="/includes/includefile.jspf" />
	</head>

	<body>

		<div class="container">
			<div class="header"></div>
			<div class="menu">
				<jsp:directive.include file="/WEB-INF/menu.jspf" />
			</div>
			<h1>LISTADO DE AUTORES</h1>
			<% ArrayList<Autor> autores = (ArrayList<Autor>)session.getAttribute("autores"); %>
			<table class="table tablaconborde tablacebra tabla-hover">
			<c:forEach items="${autores}" var="autor">
				<tr>
					<td>${ autor.getNombre() }</td>
					<td>
					   <fmt:formatDate value="${autor.getFechaNacimiento()}" pattern="dd/MM/yyyy" />
					</td>
				</tr>
			</c:forEach>
			</table>
			</div>
	</body>

</html>