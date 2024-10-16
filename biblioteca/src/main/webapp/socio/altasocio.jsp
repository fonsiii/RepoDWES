<%@taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nuevo Socio</title>
<jsp:directive.include file="../includes/includefile.jspf" />
</head>
<body>
	<div class="container">
		<div class="header"></div>
		<div class="menu">
			<jsp:directive.include file="../WEB-INF/menu.jspf" />
		</div>
		<c:if test="${error != null}">
			<div class="diverror">
				<p>
					<strong><c:out value="Error" /></strong> <br>
					<c:out value="${error}" />
				</p>
			</div>
		</c:if>
		<c:if test="${confirmaroperacion != null}">

			<div class="divconfirmacion">
				<p>
					<strong><c:out value="Mensaje" /></strong> <br>
					<c:out value="${confirmaroperacion}" />
					<script>
						window.history.replaceState(null, null,
								"admin/altasocio.jsp")
					</script>
				</p>
			</div>
		</c:if>
		<div id="formSocio" class="formulariogeneral">
			<form name="frmSocio" method="post"
				action="${pageContext.request.contextPath}/controllerSocio">
				<fieldset id="datosSocio">
					<legend>
						<img
							src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Nuevo
						Socio
					</legend>
					<div class="etiquetas">
						<label for="nombre">Nombre:</label>
					</div>
					<div class="campos">
						<input type="text" id="nombre" name="nombre"
							value="${nuevosocio.nombre}" />
					</div>
					<div class="cb"></div>
					<div class="etiquetas">
						<label for="direccion">Dirección:</label>
					</div>
					<div class="campos">
						<input type="text" id="direccion" name="direccion"
							value="${nuevosocio.direccion}" />
					</div>
					<div class="cb"></div>
					<div class="etiquetas">
						<label for="direccion">Email:</label>
					</div>
					<div class="campos">
						<input type="text" id="email" name="email"
							value="${nuevosocio.email}" />
					</div>
					<div class="cb"></div>

					<div class="botones">
						<input type="submit" name="Submit" value="Guardar">
					</div>
				</fieldset>
			</form>
		</div>
		<div id="separacion">
			<br>
		</div>
	</div>
</body>
</html>