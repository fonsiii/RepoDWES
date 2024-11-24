<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Devolución de Préstamo</title>
    <jsp:directive.include file="/includes/includefile.jspf" />
</head>
<body>
    <div class="container">
        <div class="header"></div>
        <div class="menu">
            <jsp:directive.include file="/WEB-INF/menu.jspf" />
        </div>

        <!-- Mostrar mensaje de error si existe -->
        <c:if test="${error != null}">
            <div class="diverror">
                <p>
                    <strong>Error:</strong><br>
                    <c:out value="${error}" />
                </p>
            </div>
        </c:if>

        <!-- Mostrar mensaje de confirmación si existe -->
        <c:if test="${confirmaroperacion != null}">
            <div class="divconfirmacion">
                <p>
                    <strong>Mensaje:</strong><br>
                    <c:out value="${confirmaroperacion}" />
                </p>
                <script>
                    window.history.replaceState(null, null, "${pageContext.request.contextPath}/admin/devolucion.jsp");
                </script>
            </div>
        </c:if>

        <!-- Formulario para Devolución de Préstamo -->
        <div id="formDevolucionPrestamo" class="formulariogeneral">
            <form name="frmDevolucionPrestamo" method="post"
                action="${pageContext.request.contextPath}/controllerAdmin?operacion=devolverEjemplar">
                <fieldset id="devolucionPrestamo">
                    <legend>
                        <img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif" alt="Logo">&nbsp;Devolución de Préstamo
                    </legend>

                    <!-- Campo para Código de Ejemplar -->
                    <div class="etiquetas">
                        <label for="idEjemplar">Código de Ejemplar:</label>
                    </div>
                    <div class="campos">
                        <input type="text" id="idEjemplar" name="idEjemplar"
                            value="${not empty param.idEjemplar ? param.idEjemplar : ''}"
                            required pattern="\d+" title="Debe ser un número válido" />
                    </div>
                    <div class="cb"></div>

                    <div class="botones">
                        <input type="hidden" name="btnDevolucion" value="true" />
                        <input type="submit" name="Guardar" value="Guardar Devolución">
                    </div>
                </fieldset>
            </form>
        </div>

    </div>
</body>
</html>
