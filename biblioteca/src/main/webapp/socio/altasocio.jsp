<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- Importa la biblioteca de etiquetas JSTL Core -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %> <!-- Define el lenguaje y la codificación de la página -->

<!DOCTYPE html> <!-- Declara el tipo de documento como HTML5 -->
<html>
<head>
    <meta charset="UTF-8"> <!-- Establece la codificación de caracteres de la página -->
    <title>Nuevo Socio</title> <!-- Título de la página que aparecerá en la pestaña del navegador -->
    <jsp:directive.include file="../includes/includefile.jspf" /> <!-- Incluye un archivo JSP que contiene configuraciones o elementos comunes -->
</head>
<body>
    <div class="container"> <!-- Contenedor principal para la página -->
        <div class="header"></div> <!-- Sección de encabezado, puede ser utilizada para título o logo -->
        <div class="menu">
            <jsp:directive.include file="../WEB-INF/menu.jspf" /> <!-- Incluye el menú de navegación de la aplicación -->
        </div>

        <!-- Comprobación de errores -->
        <c:if test="${error != null}"> <!-- Verifica si existe un mensaje de error en el contexto -->
            <div class="diverror"> <!-- Contenedor para mostrar errores -->
                <p>
                    <strong><c:out value="Error" /></strong> <br> <!-- Muestra el texto "Error" en negrita -->
                    <c:out value="${error}" /> <!-- Muestra el mensaje de error que se almacena en el contexto -->
                </p>
            </div>
        </c:if>

        <!-- Comprobación de confirmaciones -->
        <c:if test="${confirmaroperacion != null}"> <!-- Verifica si existe un mensaje de confirmación -->
            <div class="divconfirmacion"> <!-- Contenedor para mostrar mensajes de confirmación -->
                <p>
                    <strong><c:out value="Mensaje" /></strong> <br> <!-- Muestra el texto "Mensaje" en negrita -->
                    <c:out value="${confirmaroperacion}" /> <!-- Muestra el mensaje de confirmación almacenado en el contexto -->
                    <script>
                        // Cambia el estado en el historial del navegador, para que la página no se vuelva a enviar en caso de recarga
                        window.history.replaceState(null, null, "admin/altasocio.jsp") 
                    </script>
                </p>
            </div>
        </c:if>

        <div id="formSocio" class="formulariogeneral"> <!-- Contenedor para el formulario de nuevo socio -->
            <form name="frmSocio" method="post" action="${pageContext.request.contextPath}/controllerSocio"> <!-- Formulario que envía datos mediante POST -->
                <fieldset id="datosSocio"> <!-- Agrupación de campos del formulario -->
                    <legend>
                        <img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Nuevo Socio <!-- Imagen y título del formulario -->
                    </legend>
                    <div class="etiquetas"> <!-- Contenedor para las etiquetas de los campos -->
                        <label for="nombre">Nombre:</label> <!-- Etiqueta para el campo de nombre -->
                    </div>
                    <div class="campos"> <!-- Contenedor para los campos de entrada -->
                        <input type="text" id="nombre" name="nombre" value="${nuevosocio.nombre}" /> <!-- Campo de entrada para el nombre, con valor predeterminado -->
                    </div>
                    <div class="cb"></div> <!-- Espacio entre campos -->

                    <div class="etiquetas">
                        <label for="direccion">Dirección:</label> <!-- Etiqueta para el campo de dirección -->
                    </div>
                    <div class="campos">
                        <input type="text" id="direccion" name="direccion" value="${nuevosocio.direccion}" /> <!-- Campo de entrada para la dirección -->
                    </div>
                    <div class="cb"></div>

                    <div class="etiquetas">
                        <label for="email">Email:</label> <!-- Etiqueta para el campo de email -->
                    </div>
                    <div class="campos">
                        <input type="text" id="email" name="email" value="${nuevosocio.email}" /> <!-- Campo de entrada para el email -->
                    </div>
                    <div class="cb"></div>

                    <div class="botones"> <!-- Contenedor para los botones del formulario -->
                        <input type="submit" name="Submit" value="Guardar"> <!-- Botón para enviar el formulario -->
                    </div>
                </fieldset>
            </form>
        </div>

        <div id="separacion"> <!-- Espacio de separación -->
            <br>
        </div>
    </div>
</body>
</html>
