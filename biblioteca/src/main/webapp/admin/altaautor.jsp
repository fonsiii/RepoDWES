<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Nuevo Autor</title>
    <jsp:directive.include file="../includes/includefile.jspf" />
</head>

<body>
    <div class="container">
        <div class="header"></div>
        <div class="menu">
            <jsp:directive.include file="../WEB-INF/menu.jspf" />
        </div>

        <c:if test="${not empty error}">
            <div class="diverror">
                <p>
                    <strong><c:out value="Error" /></strong> <br />
                    <c:out value="${error}" />
                </p>
            </div>
        </c:if>

        <c:if test="${not empty confirmaroperacion}">
            <div class="divconfirmacion">
                <p>
                    <strong><c:out value="Mensaje" /></strong> <br />
                    <c:out value="${confirmaroperacion}" />
                    <script>
                        window.history.replaceState(null, null, "admin/altaautor.jsp");
                    </script>
                </p>
            </div>
        </c:if>

        <div id="formAutor" class="formulariogeneral">
            <form name="frmAutor" method="post" action="${pageContext.request.contextPath}/controllerAdmin?operacion=altaAutor">
                <fieldset id="datosAutor">
                    <legend>
                        <img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif" />
                        &nbsp;Nuevo Autor
                    </legend>
                    <div class="etiquetas">
                        <label for="nombre">Nombre:</label>
                    </div>
                    <div class="campos">
                        <input type="text" id="nombre" name="nombre" value="${(nuevoautor.nombre)}" required />
                    </div>
                    <div class="cb"></div>

                    <div class="etiquetas">
                        <label for="fechaNacimiento">Fecha Nacimiento:</label>
                    </div>
                    <div class="campos">
                        <input type="date" id="fechaNacimiento" name="fechaNacimiento" 
                            value="${(fechaErronea)}" required />
                        <input name="operacion" type="hidden" id="operacion" value="insertaautor" />
                    </div>
                    <div class="cb"></div>

                    <div class="botones">
                        <input type="submit" name="Submit" value="Guardar">
                    </div>
                </fieldset>
            </form>
        </div>

        <div id="separacion">
            <br />
        </div>
    </div>
</body>

</html>
