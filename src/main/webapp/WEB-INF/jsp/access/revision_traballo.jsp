<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<link href="css/default.css" rel="stylesheet" />
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href='http://fonts.googleapis.com/css?family=Yellowtail' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Yanone+Kaffeesatz:400,300' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Homemade+Apple' rel='stylesheet' type='text/css'>
<link href="../images/favicon.png" rel="shorcut icon" type="image/x-icon">

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${sessionScope.locale}" />
<%//fmt:setLocale value="gl_ES" /%>
<fmt:setBundle basename="properties.propiedades"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="title" /></title>
    </head>
    <body class="cuerpo">
        <header class="header">
            <img src="images/logo.png" class="logo">
            <form class="form" action="j_spring_security_logout" method="post">
                <a class="articulo">Usuario</a><a class="articulo" href="prefil_usuario.htm">${usuario}</a>
                <button class="button blue" title="Cerrar sesion"><fmt:message key="msg_header02"/></button>
            </form>

            <h class="title"><fmt:message key="msg_header_title" /></h><br>
            <h class="subtitle"><fmt:message key="msg_header_subtitle" /></h>
            <br>
        </header>
        <nav class="menu">
            <ul>
                <li><a href='aindex.htm'><fmt:message key="msg_menu01"/></a></li>
                <li><a href='congreso.htm'><fmt:message key="msg_menu02"/></a></li>
                <li><a href='trabajos.htm'><fmt:message key="msg_menu03"/></a></li>
                <li><a href='#'><fmt:message key="msg_menu04"/></a></li>
                <li><a href='#'><fmt:message key="msg_menu05"/></a></li>
                <li><a href='contacto.htm'><fmt:message key="msg_menu06"/></a></li>
                <li><a href='#'><fmt:message key="msg_menu07"/></a></li>
                <li><a href='prefil_usuario.htm'><fmt:message key="msg_menu08"/></a></li>
            </ul>
        </nav>
        <fieldset class="center_form gris_oscuro">
            <legend class="subtitulo"><fmt:message key="revision_traballo01"/></legend>
            <br>

            <a class="articulo"><fmt:message key="${textoAccion}"/></a>
            <br><br>
            <div style="overflow-x:auto;">
                <table>
                    <tr>
                        <th><fmt:message key="tabla_trabajo01"/></th>
                        <th><fmt:message key="tabla_trabajo08"/></th>
                        <th><fmt:message key="tabla_trabajo09"/></th>
                        <th><fmt:message key="tabla_trabajo06"/></th>
                        <th><fmt:message key="tabla_trabajo07"/></th>
                        <th colspan="2"></th>
                    </tr>
                    <tr>
                        <td><c:out value="${traballoDetalle.nomeTraballo}"/></td>
                        <td><c:out value="${traballoDetalle.categoria}"/></td>
                        <td><c:out value="${traballoDetalle.autores}"/></td>
                        <td><fmt:formatDate  pattern="dd/MM/yyyy" value="${traballoDetalle.fFinRevision}"/></td>

                        <td><a class="articulo" target="_blank" href="abrir_traballo.htm?id=${traballoDetalle.idTraballo}"><fmt:message key="accion_ver_traballo"/></a></td>
                            <jsp:useBean id="now" class="java.util.Date" />
                            <c:set var="idEstadoTraballo" scope="session" value="${traballoDetalle.estadoTraballo.idEstadoTraballo}"/>
                            <c:if test="${idEstadoTraballo == 2}">
                            <td><a class="articulo" href="rexeitar_traballo.htm?id=${traballoDetalle.idTraballo}"><fmt:message key="accion_rexeitar_traballo"/></a></td>  
                            </c:if>
                    </tr>
                </table>
            </div>
            <br>
            <div>
                <form method="post" action="ver_lista_revisiones.htm?id=${traballoDetalle.idTraballo}" id="revision_traballo">
                    <a class="articulo"><fmt:message key="informe_publico"/></a>
                    <br>
                    <textarea name="informe_publico" maxlength="500" class="areaTexto" readonly>${revision.revisionPublica}</textarea>
                    <br><br>
                    <a class="articulo"><fmt:message key="informe_privado"/></a>
                    <br>
                    <textarea name="informe_privado" maxlength="500" class="areaTexto" readonly>${revision.revisionPrivada}</textarea>
                    <br><br>
                    <a class="articulo"><fmt:message key="puntuacion"/></a>
                    <c:set var="puntuacion" scope="session" value="${revision.puntuacion}"/>
                    <c:choose>
                        <c:when test="${puntuacion == 1}">
                            <input type="radio" name="puntuacion" value="1" checked readonly><a class="articulo">1</a>
                            <input type="radio" name="puntuacion" value="2" readonly><a class="articulo">2</a>
                            <input type="radio" name="puntuacion" value="3" readonly><a class="articulo">3</a>
                            <input type="radio" name="puntuacion" value="4" readonly><a class="articulo">4</a>
                            <input type="radio" name="puntuacion" value="5" readonly><a class="articulo">5</a>
                        </c:when>
                        <c:when test="${puntuacion == 2}">
                            <input type="radio" name="puntuacion" value="1" readonly><a class="articulo">1</a>
                            <input type="radio" name="puntuacion" value="2" checked readonly><a class="articulo">2</a>
                            <input type="radio" name="puntuacion" value="3" readonly><a class="articulo">3</a>
                            <input type="radio" name="puntuacion" value="4" readonly><a class="articulo">4</a>
                            <input type="radio" name="puntuacion" value="5" readonly><a class="articulo">5</a>
                        </c:when>
                        <c:when test="${puntuacion == 3}">
                            <input type="radio" name="puntuacion" value="1" readonly><a class="articulo">1</a>
                            <input type="radio" name="puntuacion" value="2" readonly><a class="articulo">2</a>
                            <input type="radio" name="puntuacion" value="3" checked readonly><a class="articulo">3</a>
                            <input type="radio" name="puntuacion" value="4" readonly><a class="articulo">4</a>
                            <input type="radio" name="puntuacion" value="5" readonly><a class="articulo">5</a>
                        </c:when>
                        <c:when test="${puntuacion == 4}">
                            <input type="radio" name="puntuacion" value="1" readonly><a class="articulo">1</a>
                            <input type="radio" name="puntuacion" value="2" readonly><a class="articulo">2</a>
                            <input type="radio" name="puntuacion" value="3" readonly><a class="articulo">3</a>
                            <input type="radio" name="puntuacion" value="4" checked readonly><a class="articulo">4</a>
                            <input type="radio" name="puntuacion" value="5" readonly><a class="articulo">5</a>
                        </c:when>
                        <c:when test="${puntuacion == 5}">
                            <input type="radio" name="puntuacion" value="1" readonly><a class="articulo">1</a>
                            <input type="radio" name="puntuacion" value="2" readonly><a class="articulo">2</a>
                            <input type="radio" name="puntuacion" value="3" readonly><a class="articulo">3</a>
                            <input type="radio" name="puntuacion" value="4" readonly><a class="articulo">4</a>
                            <input type="radio" name="puntuacion" value="5" checked readonly><a class="articulo">5</a>
                        </c:when>
                    </c:choose>
                    <br><br>
                    <a class="articulo"><fmt:message key="sugerencia"/></a>
                    <c:set var="puntuacion" scope="session" value="${revision.recomendacion}"/>
                    <c:choose>
                        <c:when test="${recomendacion.equals('1')}">
                            <input type="radio" name="sugerencia" value="1" readonly checked><a class="articulo"><fmt:message key="sugerencia_rechazar"/></a>
                            <input type="radio" name="sugerencia" value="2" readonly><a class="articulo"><fmt:message key="sugerencia_cmayores"/></a>
                            <input type="radio" name="sugerencia" value="3" readonly><a class="articulo"><fmt:message key="sugerencia_cmenores"/></a>
                            <input type="radio" name="sugerencia" value="4" readonly><a class="articulo"><fmt:message key="sugerencia_aceptar"/></a>
                        </c:when>
                        <c:when test="${recomendacion.equals('2')}">
                            <input type="radio" name="sugerencia" value="1" readonly class="articulo"><fmt:message key="sugerencia_rechazar"/></a>
                            <input type="radio" name="sugerencia" value="2" readonly checked><a class="articulo"><fmt:message key="sugerencia_cmayores"/></a>
                            <input type="radio" name="sugerencia" value="3" readonly><a class="articulo"><fmt:message key="sugerencia_cmenores"/></a>
                            <input type="radio" name="sugerencia" value="4" readonly><a class="articulo"><fmt:message key="sugerencia_aceptar"/></a>
                        </c:when>
                        <c:when test="${recomendacion.equals('3')}">
                            <input type="radio" name="sugerencia" value="1" readonly class="articulo"><fmt:message key="sugerencia_rechazar"/></a>
                            <input type="radio" name="sugerencia" value="2" readonly><a class="articulo"><fmt:message key="sugerencia_cmayores"/></a>
                            <input type="radio" name="sugerencia" value="3" readonly checked><a class="articulo"><fmt:message key="sugerencia_cmenores"/></a>
                            <input type="radio" name="sugerencia" value="4" readonly><a class="articulo"><fmt:message key="sugerencia_aceptar"/></a>
                        </c:when>
                        <c:when test="${recomendacion.equals('4')}">
                            <input type="radio" name="sugerencia" value="1" readonly class="articulo"><fmt:message key="sugerencia_rechazar"/></a>
                            <input type="radio" name="sugerencia" value="2" readonly><a class="articulo"><fmt:message key="sugerencia_cmayores"/></a>
                            <input type="radio" name="sugerencia" value="3" readonly><a class="articulo"><fmt:message key="sugerencia_cmenores"/></a>
                            <input type="radio" name="sugerencia" value="4" readonly checked><a class="articulo"><fmt:message key="sugerencia_aceptar"/></a>
                        </c:when>
                    </c:choose>
                    <br><br>
                    <input type="hidden" name="idTraballo" value="${traballoDetalle.idTraballo}">
                    <input type="submit" formaction="ver_lista_revisiones.htm?id=${traballoDetalle.idTraballo}" class="button blue" value="Regresar">
                </form>
            </div>
        </fieldset>
        <fieldset class="center_form gris_claro">
            <legend class="articulo"><fmt:message key="msg_footer_01"/></legend>
            <a class="articulo" href="contacto.htm"><fmt:message key="msg_footer_02"/></a>
        </fieldset>
        <footer>
            <a class="articulo"><fmt:message key="msg_footer_03"/></a>
        </footer>
    </body>
</html>