<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<link href="css/default.css" rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Yellowtail' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Yanone+Kaffeesatz:400,300' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Homemade+Apple' rel='stylesheet' type='text/css'>
<link href="../images/favicon.png" rel="shorcut icon" type="image/x-icon">

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${sessionScope.locale}"/>
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
                <li><a href='registrar.htm'><fmt:message key="msg_menu02"/></a></li>
                <li><a href='trabajos.htm'><fmt:message key="msg_menu03"/></a></li>
                <li><a href='#'><fmt:message key="msg_menu04"/></a></li>
                <li><a href='#'><fmt:message key="msg_menu05"/></a></li>
                <li><a href='contacto.htm'><fmt:message key="msg_menu06"/></a></li>
                <li><a href='#'><fmt:message key="msg_menu07"/></a></li>
                <li><a href='prefil_usuario.htm'><fmt:message key="msg_menu08"/></a></li>
            </ul>
        </nav>
        <fieldset class="center_form gris_oscuro">
            <legend class="subtitulo"><fmt:message key="msg_actualizarPerf01"/></legend>
            <br>
            <form method="post" action="actualizar_prefil.htm">
                
                <input name="usuario" type="text" maxlength="45" class="recordar_usuario" style="width:250px;" value="${usuario}" hidden    ">

                <a class="articulo"><fmt:message key="msg_actualizarPerf02"/></a>
                <br><br>
                <a class="articulo"><fmt:message key="reg_msg_03"/></a>
                <br>
                <input name="email" type="email" maxlength="45" class="recordar_usuario" style="width:250px;" value="${email}" required>
                <br>
                <a class="articulo"><fmt:message key="comp_perf_03"/></a>
                <br>
                <input name="nome" type="text" maxlength="45" class="recordar_usuario" style="width:250px;" value="${nome}" required>
                <br>
                <a class="articulo"><fmt:message key="comp_perf_04"/></a>
                <br>
                <input name="apelido1" type="text" maxlength="45" class="recordar_usuario" style="width:250px;" value="${apelido1}" required>
                <br>
                <a class="articulo"><fmt:message key="comp_perf_05"/></a>
                <br>
                <input name="apelido2" type="text" maxlength="45" class="recordar_usuario" style="width:250px;" value="${apelido2}" required>
                <br>
                <a class="articulo"><fmt:message key="comp_perf_06"/></a>
                <br>
                <input name="telefono" type="tel" pattern="[0-9]{9}" placeholder="Formato: 0123456789" class="recordar_usuario" style="width:250px;" value="${telefono}" required>
                <br>
                <a class="articulo"><fmt:message key="act_msg_05"/></a>
                <br>
                <input name="password" type="password" maxlength="15" class="recordar_usuario" style="width:250px;" required>
                <a class="articulo"><fmt:message key="act_msg_06"/></a>
                <br>
                <a class="articulo"><fmt:message key="act_msg_07"/></a>
                <br>
                <input name="re_password" type="password" maxlength="15" class="recordar_usuario" style="width:250px;">
                <br>
                <a class="articulo"><fmt:message key="act_msg_08"/></a>
                <br>
                <input name="re_password_2" type="password" maxlength="15" class="recordar_usuario" style="width:250px;"
                <br>
                <input type="submit" class="button blue" value="Actualizar">
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