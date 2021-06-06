<%--
  Created by IntelliJ IDEA.
  User: Razer
  Date: 6/6/2021
  Time: 3:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/extended.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<p>Nombre: ${usuario.nombre}</p>
<p>Calle: ${usuario.calle}</p>
<p>Colonia: ${usuario.colonia}</p>
<p>Numero de casa: ${usuario.numeroCasa}</p>
<p>Municipio: ${usuario.municipio}</p>
<p>Codigo postal: ${usuario.codigoPostal}</p>
<p>Telefono: ${usuario.telefono}</p>
<p>Email: ${usuario.email}</p>


<c:choose>
    <c:when test="${usuario.emailConfirmed}">
        <div>
            <p>Email confirmado</p>
        </div>
    </c:when>
    <c:otherwise>
        <div>
            <p>Email no ha sido confirmado</p>
            <a href="${pageContext.request.contextPath}/ConfirmarEmail">Confirmar email</a>
        </div>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${userOauthList.contains(Integer.valueOf(1))}">
        <div>
            <p>Cuenta enlazada con Google</p>
        </div>
    </c:when>
    <c:otherwise>
        <div>
            <a href="${pageContext.request.contextPath}/GoogleOauth">Enlazar con Google</a>
        </div>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${userOauthList.contains(Integer.valueOf(2))}">
        <p>Cuenta enlazada con Facebook</p>
    </c:when>
    <c:otherwise>
        <a href="${pageContext.request.contextPath}/FacebookOauth">Enlazar con Facebook</a>
    </c:otherwise>
</c:choose>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>
</body>
</html>
