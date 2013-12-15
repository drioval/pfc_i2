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
            <p class="titulo">TRABALLOS CIENTÍFICOS</p>
            <p class="subtitulo">DEFINICIÓN</p>
            <article class="articulo">

                <p>Se entenderá como trabajo científico alguna de las siguientes modalidades:</p>
                <ul>
                    <li><strong>Comunicaciones científicas:</strong>resultados de un trabajo de investigación en el área de conocimiento de Enfermería Familiar y Comunitaria. Contendrá los siguientes apartados:
                        <ul>
                            <li>Título</li>
                            <li>Autor y 4 coautores como máximo</li>
                            <li>Elección del bloque temático por el autor (ver apartado “bloques temáticos”)</li>
                            <li>Palabras clave</li>
                            <li>Resumen estructurado en cuatro apartados (introducción, metodología, resultados y discusión) con una extensión máxima de 3000 caracteres (aproximadamente 500-550 palabras).</li>
                        </ul>
                    </li>
                    <li><b>Casos clínicos: </b>Descripción de uno o varios casos clínicos recogidos de la práctica asistencial que puedan resultar de interés debido a su singularidad o complejidad. Contendrá los siguientes apartados:
                        <ul>
                            <li>Título</li>
                            <li>Autor y 4 coautores como máximo</li>
                            <li>Palabras clave</li>
                            <li>Resumen redactado en texto libre con una extensión máxima de 3000 caracteres (aproximadamente 500-550 palabras).</li>
                        </ul>
                    </li>
                    <li><b>Experiencias (relatos cortos):&nbsp;</b>Descripción de experiencias, personales o colectivas, relacionadas con el desarrollo de la práctica clínica de Enfermería Familiar y Comunitaria, que resulten de interés o sean innovadoras. Contendrá los siguientes apartados:
                        <ul>
                            <li>Título</li>
                            <li>Autor y 4 coautores como máximo</li>
                            <li>Palabras clave</li>
                            <li>Resumen redactado en texto libre con una extensión máxima de 6000 caracteres (aproximadamente 1000-1100 palabras).</li>
                        </ul>
                    </li>
                </ul>
                <p>Todos los trabajos científicos serán redactados en español.</p>
                <p>&nbsp;</p>
                <p><strong>BLOQUES TEMÁTICOS</strong></p>
                <article class="articulo">
                    <p>Los trabajos en la modalidad de “comunicaciones científicas” serán incluidos en alguno de los siguientes bloques temáticos:</p>
                    <ul>
                        <li>Cuidado de las enfermedades crónicas</li>
                        <li>Modificación de estilos de vida y consejo dietético</li>
                        <li>Gestión de casos y enfermería de práctica avanzada</li>
                        <li>Cuidados en la infancia y adolescencia</li>
                        <li>Cuidados domiciliarios y atención a la dependencia</li>
                        <li>Atención al embarazo y puerperio</li>
                        <li>Prescripción enfermera</li>
                        <li>Investigación y docencia</li>
                    </ul>
                    <p><b>&nbsp;</b></p>
                    <p><b>AUTORÍA DE LOS TRABAJOS CIENTÍFICOS</b></p>
                    <hr class="dotted" style="cursor: default;" />
                    <p>El número máximo de autores será de CINCO por trabajo científico, incluido el autor principal. TODOS LOS AUTORES DEBERÁN ESTAR INSCRITOS AL CONGRESO. Cada autor podrá enviar tantos trabajos como desee, siempre que se trate de trabajos científicos diferentes. El comité científico velará porque los trabajos científicos enviados por un mismo autor o coautores sean sobre temáticas diferentes para evitar la división de un mismo tema en varios trabajos.</p>
                    <p><b>&nbsp;</b></p>
                    <p><b>EVALUACIÓN DE LOS TRABAJOS CIENTÍFICOS</b></p>
                    <hr class="dotted" style="cursor: default;" />
                    <p>El comité científico del congreso evaluará los trabajos científicos bajo criterios rigurosos. Se valorarán la originalidad, pertinencia a la temática del congreso y/o a los bloques temáticos, definición del problema, adecuación de la metodología utilizada, resultados obtenidos, conclusiones y/o discusión, aplicabilidad y estilo de redacción.</p>
                    <p><b>&nbsp;</b></p>
                    <p><b>CARACTERÍSTICAS DE LAS PRESENTACIONES AL CONGRESO</b></p>
                    <hr class="dotted" style="cursor: default;" />
                    <p>Una vez aceptado el resumen del trabajo científico (en cualquiera de sus tres modalidades) por parte del comité científico, se solicitará al autor el envío de una presentación para su inclusión en la web del congreso y posterior visionado por los congresistas. Esta presentación contendrá de 1 a 8 páginas o diapositivas y deberá enviarse en formato PDF, no siendo el archivo mayor de 2 Megabytes. La primera página o diapositiva contendrá los siguientes apartados:</p>
                    <ul>
                        <li>Título</li>
                        <li>Autor y coautores</li>
                        <li>Palabras clave</li>
                        <li>Resumen</li>
                    </ul>
                    <p><b>&nbsp;</b></p>
                    <p><b>PERIODO DE ENVÍO</b></p>
                    <hr class="dotted" style="cursor: default;" />
                    <p>La fecha límite para el envío de trabajos científicos será las 23:59 del día 21 de septiembre de 2012 (horario peninsular de España: UTC + 1). Todos los autores del trabajo deberán estar inscritos al congreso. En caso contrario el trabajo científico no será publicado en la web del congreso y no se emitirá certificado acreditativo a los autores (para más información, ver la sección de preguntas frecuentes -FAQ's-). La fecha límite para la inscripción al Congreso Internacional Virtual de Enfermería Familiar y Comunitaria será hasta las 23:59 del día 30 de septiembre de 2012 (horario peninsular de España: UTC + 1).</p>
                    <p><b>&nbsp;</b></p>
                    <p><b>CAMBIOS EN LOS TRABAJOS CIENTÍFICOS</b></p>
                    <hr class="dotted" style="cursor: default;" />
                    <p>No se aceptarán cambios en los resúmenes enviados para su valoración ni en las presentaciones enviadas para su inclusión en la web del congreso.</p>
                    <p><b>&nbsp;</b></p>
                    <p><b>ACEPTACIÓN DEL TRABAJO</b></p>
                    <hr class="dotted" style="cursor: default;" />
                    <p>Una vez enviado el trabajo, la aplicación informática lo remitirá al Comité Científico, que procederá a su evaluación por pares de manera anónima. La aceptación o el rechazo del trabajo científico se comunicará al autor principal mediante correo electrónico. El resultado del comité científico será inapelable. En ningún caso se justificará a los participantes los motivos del rechazo.</p>
                    <p><b>&nbsp;</b></p>
                    <p><b>PROPIEDAD INTELECTUAL DEL TRABAJO</b></p>
                    <hr class="dotted" style="cursor: default;" />
                    <p>La autoría del trabajo y su propiedad intelectual es compartida entre el autor principal y los coautores. No obstante los autores autorizan a Asanec su difusión a través de los medios que usualmente utiliza, incluido la publicación de todos los trabajos en formato digital y/o CD/DVD. El participante podrá publicitar y publicar su trabajo haciendo mención de haber sido presentado y aceptado en el I Congreso Internacional Virtual Asanec.</p>
                    <p>&nbsp;</p>
                    <p><b>PROCEDIMIENTO DE ENVÍO DE TRABAJOS CIENTÍFICOS</b></p>
                    <hr class="dotted" style="cursor: default;" />
                    <p>El procedimiento de envío de trabajos científicos será el siguiente:</p>
                    <ul>
                        <li>Tanto el autor principal como los coautores deberán darse de alta en la web del congreso e introducir su nombre de usuario y contraseña.</li>
                        <li>A continuación, el autor principal, como responsable máximo del envío del trabajo científico, deberá acceder al área de "Trabajos científicos" y pinchar en "Envío de trabajos científicos". El envío también se podrá realizar desde el menú personal del congresista (Opción "Ver Trabajos Enviados"). Aparecerá un formulario con la posibilidad de elegir entre: comunicación científica, casos clínicos o experiencias (relatos cortos). Además, deberá aceptar las normas de envío de trabajos al I Congreso Internacional Virtual Asanec.</li>
                        <li>Una vez completado el formulario y aceptadas las normas, el autor principal deberá pinchar el botón "Enviar".</li>
                    </ul>
                    <p></p>
                </article>
            </article>

        </div></section>
</section>

<footer class="footer">PFC - Daniel Ríos Val</footer>
</body>
</html>