<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Nuevo Autor</title>
    <!-- Inclusión de un archivo de cabecera o configuración -->
    <jsp:directive.include file="../includes/includefile.jspf" />
</head>

<body>
    <div class="container">
        <div class="header"></div>
        <div class="menu">
            <!-- Inclusión del menú de navegación -->
            <jsp:directive.include file="../WEB-INF/menu.jspf" />
        </div>

        <!-- Manejo de errores -->
        <c:if test="${not empty error}">
            <div class="diverror">
                <p>
                    <strong><c:out value="Error" /></strong> <br />
                    <!-- Mostrar mensaje de error -->
                    <c:out value="${error}" />
                </p>
            </div>
        </c:if>

        <!-- Manejo de confirmaciones -->
        <c:if test="${not empty confirmaroperacion}">
            <div class="divconfirmacion">
                <p>
                    <strong><c:out value="Mensaje" /></strong> <br />
                    <!-- Mostrar mensaje de confirmación -->
                    <c:out value="${confirmaroperacion}" />
                    <script>
                        // Cambiar el estado de la URL sin recargar la página
                        window.history.replaceState(null, null, "admin/altaautor.jsp");
                    </script>
                </p>
            </div>
        </c:if>

        <div id="formAutor" class="formulariogeneral">
            <!-- Formulario para crear un nuevo autor -->
            <form name="frmAutor" method="post" action="${pageContext.request.contextPath}/controlleradmin">
                <fieldset id="datosAutor">
                    <legend>
                        <!-- Imagen del formulario -->
                        <img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif" />
                        &nbsp;Nuevo Autor
                    </legend>
                    <div class="etiquetas">
                        <label for="nombre">Nombre:</label> <!-- Etiqueta para el campo de nombre -->
                    </div>
                    <div class="campos">
                        <input type="text" id="nombre" name="nombre" value="${(nuevoautor.nombre)}" required />
                    </div>
                    <div class="cb"></div> <!-- Limpiar flotantes -->

                    <div class="etiquetas">
                        <label for="fechaNacimiento">Fecha Nacimiento:</label> <!-- Etiqueta para la fecha de nacimiento -->
                    </div>
                    <div class="campos">
                        <input type="date" id="fechaNacimiento" name="fechaNacimiento" 
                            value="${(fechaErronea)}" required />
                        <input name="operacion" type="hidden" id="operacion" value="insertaautor" />
                    </div>
                    <div class="cb"></div> <!-- Limpiar flotantes -->

                    <div class="botones">
                        <input type="submit" name="Submit" value="Guardar"> <!-- Botón de envío del formulario -->
                    </div>
                </fieldset>
            </form>
        </div>

        <div id="separacion">
            <br /> <!-- Separador visual -->
        </div>
    </div>
</body>

</html>
