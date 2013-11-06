<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>                
<link href="css/default.css" rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Yellowtail' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Yanone+Kaffeesatz:400,300' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Homemade+Apple' rel='stylesheet' type='text/css'>
<link href="../images/favicon.png" rel="shorcut icon" type="image/x-icon">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PFC - Congreso Científico - Iteración 1</title>
    </head>
    <body class="cuerpo">
        <div class="cabecera">
            <header class="header">
                <img src="images/logo.png" class="logo">               
                <h class="title"> Benvido a Congreso Científico</h><br>
                <h class="subtitle">Iteración 1</h>
            </header>
            <br>
        </div>
        <div class="menu">
            <nav>
                <ul>
                    <li><a href='index.htm'>Inicio</a></li>
                    <li><a href='acceder.htm'>Congresos</a></li>
                    <li class="dropdown ">
                        <a href='#'>Traballos Científicos</a>
                        <ul class="dropdown_menu">
                            <li><a href="#">Congreso Virtual</a></li>
                            <li><a href="#">Normativa</a></li>
                            <li><a href="#">Cuotas</a></li>
                            <li><a href="#">Congreso Presencial</a></li>
                            <li><a href="#">Normativa virtual</a></li>
                            <li><a href="#">Cuota virtual</a></li>
                        </ul>
                    </li>
                    <li><a href='#'>Novas</a></li>
                    <li><a href='#'>Calendario</a></li>
                    <li><a href='#'>Contacto</a></li>
                    <li><a href='#'>FAQ'S</a></li>
                </ul>
            </nav>
        </div>

        <section>
            <br>
            <form class="center_form gris_oscuro" method="post" action="reenviar_contrasinal.htm">
                <a class="subtitulo tab">¿Olvidaches o contrasinal?</a>
                <br>
                <a class="articulo tab">Introduze a túa dirección de correo electrónico</a>
                <br>
                <input name="email" type="email" class="recordar_email">
                <br>
                <a class="subtitulo tab">ou</a>
                <br>
                <a class="articulo tap">Introduze o teu nome de usuario</a>
                <br>
                <input name="usuario" type="text" maxlength="45" class="recordar_usuario">
                <br>
                <button class="button blue" title="Enviar recordatorio">Enviar</button>
            </form>
            <form class="center_form_p gris_claro" method="post" action="contactar_admin">
                <a class="footer">¿Necesitas axuda? </a>                
                <a class="footer" href="contactar.htm">Por favor, contacta co administrador</a>
            </form>
        </section>

        <footer class="footer">PFC - Daniel Ríos Val</footer>
    </body>
</html>