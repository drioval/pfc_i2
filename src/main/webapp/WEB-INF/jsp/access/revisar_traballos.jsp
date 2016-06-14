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
            <legend class="subtitulo"><fmt:message key="revisar_traballo01"/></legend>
            <br>
            <form method="post" action="accion_aceptar_traballo.htm" id="aceptar_traballo">
                <a class="articulo"><fmt:message key="${textoAccion}"/></a>
                <br><br>
                <div style="overflow-x:auto;">
                    <c:if test="${existenRevisiones == 1}">
                        <table>
                            <tr>
                                <th><fmt:message key="nome_traballo"/></th>
                                <th><fmt:message key="fecha_inicio_revision"/></th>
                                <th><fmt:message key="fecha_fin_revision"/></th>
                                <th colspan="5"></th>
                            </tr>
                            <c:forEach items="${listaTraballoDetalle}" var="traballosRevisar" varStatus="indice">
                                <tr>
                                    <td><c:out value="${traballosRevisar.nomeTraballo}"/></td>
                                    <td><fmt:formatDate  pattern="dd/MM/yyyy" value="${traballosRevisar.fIncioRevision}"/></td>
                                    <td><fmt:formatDate  pattern="dd/MM/yyyy" value="${traballosRevisar.fFinRevision}"/></td>
                                    <td><a class="articulo" target="_blank" href="abrir_traballo.htm?id=${traballosRevisar.idTraballo}"><fmt:message key="accion_ver_traballo"/></a></td>
                                    <jsp:useBean id="now" class="java.util.Date" />
                                    <c:if test="${listaRevisiones[indice.index].estadoRevision.idEstadoRevision == 2 && traballosRevisar.fFinRevision gt now}">  
                                        <td><a class="articulo" href="revisar_traballo.htm?id=${traballosRevisar.idTraballo}"><fmt:message key="accion_revisar_traballo"/></a></td>
                                        <td><a class="articulo" href="rexeitar_revision_revisor.htm?id=${listaRevisiones[indice.index].idRevision}"><fmt:message key="accion_rexeitar_revision"/></a></td>
                                    </c:if>
                                    <c:if test="${listaRevisiones[indice.index].estadoRevision.idEstadoRevision == 3  && traballosRevisar.fFinRevision gt now}">
                                        <td><a class="articulo" href="editar_revision.htm?id=${listaRevisiones[indice.index].idRevision}"><fmt:message key="accion_editar_revision"/></a></td>
                                        <td><a class="articulo" href="rexeitar_revision_revisor.htm?id=${listaRevisiones[indice.index].idRevision}"><fmt:message key="accion_rexeitar_revision"/></a></td>
                                    </c:if>
                                        
                                    <c:if test="${listaRevisiones[indice.index].estadoRevision.idEstadoRevision == 4}">
                                        <td><a class="articulo" href="ver_revision_traballo.htm?id=${listaRevisiones[indice.index].idRevision}"><fmt:message key="accion_ver_revision_traballo"/></a></td>
                                    </c:if>
                                        
                                </tr>
                            </c:forEach>    
                        </table>
                    </c:if>
                </div>

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