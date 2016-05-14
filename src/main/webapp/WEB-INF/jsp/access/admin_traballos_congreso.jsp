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
            <form method="post" action="alta_traballo.htm" id="alta_traballo" enctype="multipart/form-data">

                <a class="articulo"><fmt:message key="${textoAccion}"/></a>
                <br><br>
                <div style="overflow-x:auto;">
                    <table>
                        <tr>
                            <th><fmt:message key="tabla_trabajo01"/></th>
                            <th><fmt:message key="tabla_trabajo05"/></th>
                            <th><fmt:message key="tabla_trabajo06"/></th>
                            <th><fmt:message key="tabla_trabajo07"/></th>
                            <th colspan="5"></th>
                        </tr>
                        <c:forEach items="${listaTraballos}" var="traballos" varStatus="indice">
                            <tr>
                                <td><c:out value="${traballos.nomeTraballo}"/></td>
                                <td><fmt:formatDate  pattern="dd/MM/yyyy" value="${traballos.fIncioRevision}"/></td>
                                <td><fmt:formatDate  pattern="dd/MM/yyyy" value="${traballos.fFinRevision}"/></td>
                                <td><c:out value="${listaEstadoTraballos[indice.index].nomeEstado}"/></td>
                                <td><a class="articulo" target="_blank" href="abrir_traballo.htm?id=${traballos.idTraballo}"><fmt:message key="accion_ver_traballo"/></a></td>
                                <jsp:useBean id="now" class="java.util.Date" />
                                <c:set var="idEstadoTraballo" scope="session" value="${listaEstadoTraballos[indice.index].idEstadoTraballo}"/>
                                <c:if test="${idEstadoTraballo == 2}">
                                    <td><a class="articulo" href="ver_datos_traballo.htm?id=${traballos.idTraballo}"><fmt:message key="accion_ver_datos_traballo"/></a></td>
                                    <td><a class="articulo" href="asignar_revisor.htm?id=${traballos.idTraballo}"><fmt:message key="accion_asignar_revisor"/></a></td>
                                    <td><a class="articulo" href="revisar_traballo.htm?id=${traballos.idTraballo}"><fmt:message key="accion_revisar_traballo"/></a></td>
                                    <td><a class="articulo" href="rexeitar_traballo.htm?id=${traballos.idTraballo}"><fmt:message key="accion_rexeitar_traballo"/></a></td>  
                                </c:if>
                            </tr>
                        </c:forEach>
                        <c:if test="${fFinEnvio gt now}">
                            <td><a class="articulo" href="anadir_trabajo.htm"><fmt:message key="accion_anadir_traballo"/></a></td>
                        </c:if>
                        
                    </table>
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