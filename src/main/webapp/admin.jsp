<%--
  Created by IntelliJ IDEA.
  User: Razer
  Date: 5/27/2021
  Time: 2:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<div>
    <form method="post">
        <c:forEach items="${capturistas}" var="capturista">
            <div>
                <input type="hidden" value="${capturista.id}" name="id">

                <p>Capturista: ${capturista.nombre}</p>
                <p>E-mail: ${capturista.email}</p>
                <p>Nombre: ${capturista.nombre}</p>
                <p>Calle: ${capturista.calle}</p>
                <p>Colonia: ${capturista.colonia}</p>
                <p>#: ${capturista.numeroCasa}</p>
                <p>Municipio: ${capturista.municipio}</p>
                <p>C.P.: ${capturista.codigoPostal}</p>
                <p>Telefono: ${capturista.telefono}</p>

                <div>
                    <input type="radio" name="status" value="aceptado" id="aceptado">
                    <label for="aceptado">Aceptado</label>

                    <input type="radio" name="status" value="rechazado" id="rechazado">
                    <label for="rechazado">Rechazado</label>

                    <input type="radio" name="status" value="pendiente" id="pendiente" checked>
                    <label for="pendiente">Pendiente</label>
                </div>

                <input type="submit">
            </div>
        </c:forEach>
    </form>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>
</body>
</html>
