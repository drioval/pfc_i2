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

        <section>
            <br>
            <form class="center_form gris_oscuro" method="post" action="recordar.htm">
                <a class="subtitulo tab">¿Olvidaches o contrasinal?</a>
                <br>
                <br>
                <a class="articulo tab">${mensaxe}</a>
                <br>
                <br>
                <a class="articulo">Usuario: ${usuario}</a>
                <a class="articulo tab">Enderezo electrónico: ${email}</a>
                <br>
                
                <button class="button blue" title="Volver a paxina anterior">Volver</button>
            </form>
            <form class="center_form_p gris_claro" method="post" action="contactar_admin">
                <a class="footer">¿Necesitas axuda? </a>                
                <a class="footer" href="contactar.htm">Por favor, contacta co administrador</a>
            </form>
        </section>

        <footer class="footer">PFC - Daniel Ríos Val</footer>
    </body>
</html>