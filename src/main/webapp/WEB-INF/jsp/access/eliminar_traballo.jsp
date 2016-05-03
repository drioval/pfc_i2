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
            <legend class="subtitulo"><fmt:message key="eliminar_traballo01"/></legend>
            <br>
            <form method="post" action="borra_traballo.htm" id="borra_traballo">
                <a class="articulo"><fmt:message key="eliminar_traballo02"/></a>
                <br><br>
                <a class="articulo"><fmt:message key="nome_traballo"/></a>
                <br>
                <input name="idTraballo" type="hidden" value="${idTraballo}">
                <input name="nome_traballo" type="congreso" maxlength="250" style="width:600px;" required="true" value="${nomeTraballo}" readonly>
                <br>
                <a class="articulo"><fmt:message key="categoria"/></a>
                <br>
                <select name="categoria" id="categoria" required="">
                    <option value="1" selected>Publicación</option>
                    <option value="2">Artículo</option>
                    <option value="3">Tesis</option>
                    <option value="4">Investigación</option>
                    <option value="5">Disertación</option>
                </select>
                <br>
                <a class="articulo"><fmt:message key="autores"/></a>
                <br>
                <textarea name="autores" maxlength="250" class="areaTexto" readonly>${autores}</textarea>
                <br><br>
                <a class="articulo"><fmt:message key="eliminar_traballo02"/></a>
                <td><a class="articulo" target="_blank" href="abrir_traballo.htm?id=${idTraballo}">"${nomeTraballo}"</a></td>
                <br><br>
                <input type="submit" class="button blue" value="Eliminar" formaction="borra_traballo.htm"/>
                <input type="submit" class="button blue" value="Regresar" formaction="trabajos.htm"/>
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