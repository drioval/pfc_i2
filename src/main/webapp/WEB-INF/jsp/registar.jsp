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
<%//fmt:setLocale value="${sessionScope.locale}" /%>
<fmt:setLocale value="gl_ES" />
<fmt:setBundle basename="properties.propiedades"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PFC - Congreso Científico - Iteración 1</title>
    </head>
    <body class="cuerpo">
        <div class="cabecera">
            <header class="header">
                <img src="images/logo.png" class="logo">
                <form class="form" method="post" action="acceder.htm" >
                    <input name="username" type="text" placeholder="Nome de usuario" maxlength="45" required/>
                    <input name="password" type="password" placeholder="Contrasinal" maxlength="45" required/>
                    <button class="button blue" title="Preme para acceder ao teu perfil de usuario">Entrar</button>
                    <a class="footer" href="recordar.htm">¿Recordar contrasinal?</a>
                </form>

                <h class="title"><fmt:message key="msg_titulo" /></h><br>
                <h class="subtitle">Iteración 1</h>
            </header>
            <br>
        </div>
        <div class="menu">
            <nav>
                <ul>
                    <li><a href='index.htm'><fmt:message key="msg_menu01"/></a></li>
                    <li><a href='acceder.htm'><fmt:message key="msg_menu02"/></a></li>
                    <li><a href='registrarse.htm'><fmt:message key="msg_menu03"/></a></li>
                    <li><a href='#'><fmt:message key="msg_menu04"/></a></li>
                    <li><a href='#'><fmt:message key="msg_menu05"/></a></li>
                    <li><a href='#'><fmt:message key="msg_menu06"/></a></li>
                    <li><a href='#'><fmt:message key="msg_menu07"/></a></li>
                </ul>
            </nav>
        </div>

        <fieldset class="center_form gris_oscuro">
            <legend><a class="subtitulo"><fmt:message key="reg_msg_01"/></a></legend>
            <br>
            <form method="post" action="enviar_registro.htm">

                <a class="articulo"><fmt:message key="reg_msg_02"/></a>
                <br><br>
                <a class="articulo"><fmt:message key="reg_msg_03"/></a>
                <br>
                <input name="email" type="email" class="recordar_email" style="width:250px;">
                <br>
                <a class="articulo"><fmt:message key="reg_msg_04"/></a>
                <br>
                <input name="usuario" type="text" maxlength="45" class="recordar_usuario" style="width:250px;">
                <br>
                <a class="articulo"><fmt:message key="reg_msg_05"/></a>
                <br>
                <input name="password" type="password" maxlength="15" class="recordar_usuario" style="width:250px;">
                <br>
                <a class="articulo"><fmt:message key="reg_msg_06"/></a>
                <br>
                <input name="re_password" type="password" maxlength="15" class="recordar_usuario" style="width:250px;">
                <br>
                
                <input type="submit" class="button blue" value="Enviar">
                <input type="reset" class="button blue" value="Borrar">

            </form>
        </fieldset>
        <fieldset class="center_form_p gris_claro">
            <legend><a class="articulo"><fmt:message key="msg_footer_01"/></a></legend>
            <a class="articulo" href="contactar.htm"><fmt:message key="msg_footer_02"/></a>
        </fieldset>

        <footer class="footer"><fmt:message key="msg_footer_03"/></footer>
    </body>
</html>