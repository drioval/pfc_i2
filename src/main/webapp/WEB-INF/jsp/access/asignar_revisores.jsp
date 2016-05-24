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
                        <td><c:out value="${nomeTraballo}"/></td>
                        <td><c:out value="${categoria}"/></td>
                        <td><c:out value="${autores}"/></td>
                        <td><fmt:formatDate  pattern="dd/MM/yyyy" value="${fFinRevision}"/></td>
                        <td><c:out value="${nomeEstado}"/></td>
                        <td><a class="articulo" target="_blank" href="abrir_traballo.htm?id=${idTraballo}"><fmt:message key="accion_ver_traballo"/></a></td>
                            <jsp:useBean id="now" class="java.util.Date" />
                            <c:set var="idEstadoTraballo" scope="session" value="${idEstadoTraballo}"/>
                            <c:if test="${idEstadoTraballo == 2}">
                            <td><a class="articulo" href="rexeitar_traballo.htm?id=${idTraballo}"><fmt:message key="accion_rexeitar_traballo"/></a></td>  
                            </c:if>
                    </tr>
                </table>
            </div>
            <br>
            <div>
                <form method="post" action="accion_asigna_revisores.htm" id="asigna_revisores">
                    <p>
                        <a class="articulo"><fmt:message key="asigna_revisores01"/></a>
                        <br>
                        <select multiple name="revisores[]" class="areaListaRevisores">
                            <c:forEach items="${listaRevisores}" var="revisor" varStatus="indice">
                                <option value="${revisor.userid}">${revisor.apelido1} ${revisor.apelido2}, ${revisor.nome}</option>
                            </c:forEach>
                        </select>
                        <a class="articulo"><fmt:message key="accion_invitar_revisor"/></a>
                        <textarea name="invitar_revisores" maxlength="500" class="areaListaRevisores"></textarea>
                    </p>
                    <p><input type="submit" value="<fmt:message key="asignar_revisores"/>" class="button blue"></p>
                    <br><br>
                    <td><a class="articulo" href="invitar_revisor.htm"><fmt:message key="accion_invitar_revisor"/></a></td>
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