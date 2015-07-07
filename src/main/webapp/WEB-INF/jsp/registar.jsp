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
            <form class="form" action="j_spring_security_check" method="post">
                <input name="j_username" type="text" placeholder="Usuario" maxlength="45" required/>
                <input name="j_password" type="password" placeholder="Password" maxlength="45" required/>
                <button class="button blue" title="Preme para acceder ao teu perfil de usuario"><fmt:message key="msg_header01" /></button>
                <a class="articulo" href="recordar.htm"><fmt:message key="msg_header03" /></a>
            </form>

            <h class="title"><fmt:message key="msg_header_title" /></h><br>
            <h class="subtitle"><fmt:message key="msg_header_subtitle" /></h>
            <br>
        </header>
            <nav class="menu">
                <ul>
                    <li><a href='index.htm'><fmt:message key="msg_menu01"/></a></li>
                    <li><a href='congreso.htm'><fmt:message key="msg_menu02"/></a></li>
                    <li><a href='registrar.htm'><fmt:message key="msg_menu03"/></a></li>
                    <li><a href='#'><fmt:message key="msg_menu04"/></a></li>
                    <li><a href='#'><fmt:message key="msg_menu05"/></a></li>
                    <li><a href='contacto.htm'><fmt:message key="msg_menu06"/></a></li>
                    <li><a href='#'><fmt:message key="msg_menu07"/></a></li>
                </ul>
            </nav>
        <fieldset class="center_form gris_oscuro">
            <legend class="subtitulo"><fmt:message key="reg_msg_01"/></legend>
            <br>
            <form method="post" action="enviar_registro.htm">

                <a class="articulo"><fmt:message key="reg_msg_02"/></a>
                <br><br>
                <a class="articulo"><fmt:message key="reg_msg_03"/></a>
                <br>
                <input type="email"  name="email" class="recordar_email" style="width:250px;" value="${email}" required>
                <br>
                <a class="articulo"><fmt:message key="reg_msg_04"/></a>
                <br>
                <input name="usuario" type="text" maxlength="45" class="recordar_usuario" style="width:250px;" value="${usuario}" required>
                <br>
                <a class="articulo"><fmt:message key="reg_msg_05"/></a>
                <br>
                <input name="password" type="password" maxlength="15" class="recordar_usuario" style="width:250px;" required>
                <br>
                <a class="articulo"><fmt:message key="reg_msg_06"/></a>
                <br>
                <input name="re_password" type="password" maxlength="15" class="recordar_usuario" style="width:250px;" required>
                <br>

                <button type="submit" class="button blue" value="Submit">Enviar</button>
                <button type="reset" class="button blue" value="Reset">Borrar</button>
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