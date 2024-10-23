<%@ taglib uri="jakarta.tags.core" prefix="c" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %> 

<!DOCTYPE html> 
<html>
<head>
    <meta charset="UTF-8"> 
    <title>Buscar Socio</title> 
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
                    <strong>Error:</strong><br> 
                    <c:out value="${error}" /> 
                </p>
            </div>
        </c:if>

        <c:if test="${confirmaroperacion != null}"> 
            <div class="divconfirmacion"> 
                <p>
                    <strong>Mensaje:</strong><br> 
                    <c:out value="${confirmaroperacion}" /> 
                    <script>
                        window.history.replaceState(null, null, "socio/getsocio.jsp")
                    </script>
                </p>
            </div>
        </c:if>

        <div id="formBusquedaSocio" class="formulariogeneral"> 
            <form name="frmBusquedaSocio" method="post" action="${pageContext.request.contextPath}/controllerSocio?operacion=getSocio"> 
                <fieldset id="busquedaSocio"> 
                    <legend>
                        <img src="${pageContext.request.contextPath}/resources/img/azarquiel.gif">&nbsp;Buscar Socio 
                    </legend>
                    <div class="etiquetas"> 
                        <label for="nombre">Nombre del Socio:</label> 
                    </div>
                    <div class="campos"> 
                        <input type="text" id="nombre" name="nombre" value="${nombreBuscado}" /> 
                    </div>
                    <div class="cb"></div> 
                    <div class="botones"> 
                        <input type="hidden" name="formSubmitted" value="true" /> <!-- Campo oculto -->
                        <input type="submit" name="Buscar" value="Buscar"> 
                    </div>
                </fieldset>
            </form>
        </div>

        <c:if test="${empty socioBuscado && param.formSubmitted != null}">
            <div class="diverror">
                No hay nadie con el nombre: <c:out value="${nombreBuscado}"/> 
            </div>
        </c:if>

        <c:if test="${not empty socioBuscado}"> 
            <div class="w-75 ma">
                <table class="table tablaconborde tablacebra tabla-hover">
                    <tr>
                        <th>ID de socio</th>
                        <th>Nombre</th>
                        <th>Direccion</th>
                        <th>Editar</th>
                    </tr>
                    <c:forEach items="${socioBuscado}" var="socio">
                        <tr>
                            <td class="txtcentrado">${socio.getIdSocio()}</td>
                            <td class="txtcentrado">${socio.getNombre()}</td>
                            <td class="txtcentrado">${socio.getDireccion()}</td>
                            <td class="txtcentrado">Editar</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>          
        </c:if>
    </div>
</body>
</html>
