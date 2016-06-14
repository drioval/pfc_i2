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
                <input name="j_username" type="text" placeholder="Nome de usuario" maxlength="45" required/>
                <input name="j_password" type="password" placeholder="Contrasinal" maxlength="45" required/>
                <button class="button blue" title="Preme para acceder ao teu perfil de usuario"><fmt:message key="msg_header01" /></button>
                <c:if test="${hayError == 1}">
                    <a class="articulo" style="color: red"><fmt:message key="error_acceso_01"/></a>
                </c:if>
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
        <section>
            <p class="titulo">CONGRESO 2015</p>
            <p class="subtitulo">BIENVENIDA</p>
            <article class="articulo">

                <p>Estimados colegas,</p>

                <ul>Es un placer anunciarles la edición del <strong>Congreso Científico 2015</strong>, a celebrarse del <strong>XX al YY de Septiembre de 2015</strong> en el Centro ZZZZZZ.
                    El Congreso será una excelente oportunidad presentar y compartir distintos trabajos sobre la especialidad y fortalecer los lazos de cooperación entre especialistas e investigadores.
                </ul>
                <ul>Nos complace invitarlos a participar en el Congreso.</ul>

            </article>
        </section>
    </body>

    <footer>
        <a class="articulo"><fmt:message key="msg_footer_03"/></a>
    </footer>
</html>
