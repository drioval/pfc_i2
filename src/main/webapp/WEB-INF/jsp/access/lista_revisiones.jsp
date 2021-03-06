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
            <legend class="subtitulo"><fmt:message key="admin_traballo01"/></legend>
            <br>
            <form method="post" action="accion_aceptar_traballo.htm" id="aceptar_traballo">
                <a class="articulo"><fmt:message key="${textoAccion}"/></a>
                <br><br>
                <div style="overflow-x:auto;">
                    <table>
                        <tr>
                            <th><fmt:message key="tabla_revision01"/></th>
                            <th><fmt:message key="puntuacion"/></th>
                            <th><fmt:message key="tabla_revision03"/></th>
                            <th colspan="4"></th>
                        </tr>
                        <c:forEach items="${listaRevisiones}" var="revision" varStatus="indice">
                            <tr>
                                <td><c:out value="${revision.userProfileRevisor.usuario}"/></td>
                                <c:if test="${revision.puntuacion eq null}">
                                    <td></td>
                                </c:if>
                                <c:if test="${revision.puntuacion ne null}">
                                    <td><c:out value="${revision.puntuacion}"/></td>
                                </c:if>
                                <c:if test="${revision.recomendacion == 1}">
                                    <td><fmt:message key="sugerencia_rechazar"/></td>
                                </c:if>
                                <c:if test="${revision.recomendacion == 2}">
                                    <td><fmt:message key="sugerencia_cmayores"/></td>
                                </c:if>
                                <c:if test="${revision.recomendacion == 3}">
                                    <td><fmt:message key="sugerencia_cmenores"/></td>
                                </c:if>
                                <c:if test="${revision.recomendacion == 4}">
                                    <td><fmt:message key="sugerencia_aceptar"/></td>
                                </c:if>
                                <c:if test="${revision.recomendacion eq null}">
                                    <td></td>
                                </c:if>
                                <td><a class="articulo" target="_blank" href="abrir_traballo.htm?id=${revision.traballo.idTraballo}"><fmt:message key="accion_ver_traballo"/></a></td>
                                <td><a class="articulo" href="ver_revision_traballo.htm?id=${revision.idRevision}"><fmt:message key="accion_ver_revision_traballo"/></a></td>
                            </tr>
                        </c:forEach>    
                    </table>
                </div>
                <input type="hidden" name="idTraballo" value="${idTraballo}">
                <c:set var="idEstadoTraballo" scope="session" value="${idEstadoTraballo}"/>
                <c:if test="${idEstadoTraballo == 7}">
                    <input type="submit" class="button blue" value="<fmt:message key="aceptar_trabajo"/>">
                    <input type="submit" formaction="accion_corregir_trabajo.htm?id=${idTraballo}" class="button blue" value="<fmt:message key="corregir_traballo"/>">
                    <input type="submit" formaction="accion_rexeitar_traballo.htm?id=${idTraballo}" class="button blue" value="<fmt:message key="accion_rexeitar_traballo"/>">
                </c:if>
                <input type="submit" formaction="trabajos.htm" class="button blue" value="Regresar">
            </form>
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