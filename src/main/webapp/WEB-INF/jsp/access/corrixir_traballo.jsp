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
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
        <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        
        <script>
            $(function() {
                $.datepicker.regional['es'] =
                        {
                            closeText: 'Cerrar',
                            prevText: 'Previo',
                            nextText: 'Próximo',
                            monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
                                'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
                            monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
                                'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
                            monthStatus: 'Ver otro mes', yearStatus: 'Ver otro año',
                            dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
                            dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sáb'],
                            dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'],
                            firstDay: 1,
                            initStatus: 'Selecciona la fecha', isRTL: false};
                $.datepicker.setDefaults($.datepicker.regional['es']);

                $("#fecha_inicio_envio").datepicker({
                    dateFormat: "yy/mm/dd",
                    altFormat: "dd/mm/yy",
                    altField: "#fecha_inicio_envio",
                    minDate: new Date(),
                    maxDate: '+2y',
                    onSelect: function(date) {
                        var selectedDate = new Date(date);
                        var msecsInADay = 86400000; //1 dia
                        var msecsInADay2 = 63072000000; //2 anos
                        var endDate = new Date(selectedDate.getTime() + msecsInADay);
                        var maxDate = new Date(selectedDate.getTime() + msecsInADay2);
                        $("#fecha_fin_envio").datepicker("option", "minDate", endDate);
                        $("#fecha_fin_envio").datepicker("option", "maxDate", maxDate);
                    }
                });
                $("#fecha_fin_envio").datepicker({
                    dateFormat: "yy/mm/dd",
                    altFormat: "dd/mm/yy",
                    altField: "#fecha_fin_envio",
                    minDate: new Date(),
                    maxDate: '+2y',
                    onSelect: function(date) {
                        var selectedDate = new Date(date);
                        var msecsInADay = 86400000; //1 dia
                        var msecsInADay2 = 63072000000; //2 anos
                        var endDate = new Date(selectedDate.getTime() + msecsInADay);
                        var maxDate = new Date(selectedDate.getTime() + msecsInADay2);
                        $("#fecha_inicio_revision").datepicker("option", "minDate", endDate);
                        $("#fecha_inicio_revision").datepicker("option", "maxDate", maxDate);
                    }
                });
                $("#fecha_inicio_revision").datepicker({
                    dateFormat: "yy/mm/dd",
                    altFormat: "dd/mm/yy",
                    altField: "#fecha_inicio_revision",
                    minDate: new Date(),
                    maxDate: '+2y',
                    onSelect: function(date) {
                        var selectedDate = new Date(date);
                        var msecsInADay = 86400000; //1 dia
                        var msecsInADay2 = 63072000000; //2 anos
                        var endDate = new Date(selectedDate.getTime() + msecsInADay);
                        var maxDate = new Date(selectedDate.getTime() + msecsInADay2);
                        $("#fecha_fin_revision").datepicker("option", "minDate", endDate);
                        $("#fecha_fin_revision").datepicker("option", "maxDate", maxDate);
                    }
                });
                $("#fecha_fin_revision").datepicker({
                    dateFormat: "yy/mm/dd",
                    altFormat: "dd/mm/yy",
                    altField: "#fecha_fin_revision",
                    minDate: new Date(),
                    maxDate: '+2y'
                });

            });
        </script>
        
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
            <legend class="subtitulo"><fmt:message key="datos_traballo01"/></legend>
            <br>
            <form method="post" action="corrixir_traballo.htm" id="alta_traballo">
                <input name="idTraballo" type="hidden" value="${idTraballo}">
                <a class="articulo"><fmt:message key="${textoAccion}"/></a>
                <br><br>
                <a class="articulo"><fmt:message key="nome_traballo"/></a>
                <br>
                <input name="nome_traballo" type="congreso" maxlength="250" style="width:600px;" readonly required="true"  value="${nomeTraballo}">
                <br>
                <a class="articulo"><fmt:message key="categoria"/></a>
                <br>
                <select name="categoria" id="categoria" required="">
                    <option value="${idCategoria}" selected>${categoria}</option>
                </select>
                <br>
                <a class="articulo"><fmt:message key="autores"/></a>
                <br>
                <textarea name="textoAutor" maxlength="250" class="areaTexto" readonly="">${autores}</textarea>
                <br><br>
                <br>
                <a class="articulo"><fmt:message key="textoCorreccionAutor"/></a>
                <br>
                <textarea name="textoCorreccionAutor" maxlength="250" class="areaTexto"></textarea>
                <br><br>
                <a class="articulo"><fmt:message key="fecha_inicio_correccion"/></a>
                <br>
                <input name="fecha_inicio_envio" type="calendar" id="fecha_inicio_envio" required="true" placeholder="dd/mm/aaaa">
                <br>
                <a class="articulo"><fmt:message key="fecha_fin_correccion"/></a>
                <br>
                <input name="fecha_fin_envio" type="calendar" id="fecha_fin_envio" required="true" placeholder="dd/mm/aaaa">
                <br><br>
                <a class="articulo"><fmt:message key="trabajo_actual"/></a>
                <td><a class="articulo" target="_blank" href="abrir_traballo.htm?id=${idTraballo}">${nomeTraballo}</a></td>
                <br><br>
                <c:set var="idEstadoTraballo" scope="session" value="${idEstadoTraballo}"/>
                <c:if test="${idEstadoTraballo != 4}">
                    <input type="submit" formaction="corrixir_traballo.htm?id=${idTraballo}" class="button blue" value="<fmt:message key="corregir_traballo"/>">
                </c:if>
                <input type="submit" formaction="trabajos.htm" class="button blue" value="Regresar">
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