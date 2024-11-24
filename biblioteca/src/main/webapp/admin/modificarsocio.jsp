<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modificar Socio</title>
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
                        window.history.replaceState(null, null, "admin/modificarsocio.jsp");
                    </script>
                </p>
            </div>
        </c:if>

        <div id="formSocio" class="formulariogeneral">
            <form name="frmSocio" method="post" action="${pageContext.request.contextPath}/controllerAdmin">
                <fieldset id="datosSocio">
                    <legend>
                        <img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif" />
                        &nbsp;Modificar Socio
                    </legend>

                    <!-- Campo oculto para la operación -->
                    <input type="hidden" name="operacion" value="modificarSocio" />
                    <input type="hidden" name="idSocio" value="${socio.idSocio}" />

                    <div class="etiquetas">
                        <label for="socio">Nombre:</label>
                    </div>
                    <div class="campos">
                        <input type="text" id="nombre" name="nombre" value="${socio.nombre}" required />
                    </div>
                    <div class="cb"></div>

                    <div class="etiquetas">
                        <label for="direccion">Direccion:</label>
                    </div>
                    <div class="campos">
                        <input type="text" id="direccion" name="direccion" value="${socio.direccion}" required /> 
                    </div>
                    <div class="cb"></div>

                    <div class="etiquetas">
                        <label for="version">Version:</label>
                    </div>
                    <div class="campos">
                        <input type="text" id="version" name="version" value="${socio.version}" required /> 
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