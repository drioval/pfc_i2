<%-- 
    Document   : registarse
    Created on : 21-jun-2013, 20:07:55
    Author     : insdrv00
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <p>Greetings, it is now <c:out value="${usuario}"/></p>
        <c:out value="${contrasinal}"/>

    </body>
</html>
