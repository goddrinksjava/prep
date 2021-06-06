<%--
  Created by IntelliJ IDEA.
  User: Razer
  Date: 6/3/2021
  Time: 10:29 PM
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

<form method="post">
    <div class="d-flex justify-content-between">
        <label for="tipoCasilla">Tipo de casilla:</label>
        <select id="tipoCasilla">
            <c:forEach items="${tipoCasillaList}" var="tipoCasilla">
                <option value="${tipoCasilla.id}">${tipoCasilla.tipo}</option>
            </c:forEach>
        </select>
    </div>
    <div class="d-flex justify-content-between">
        <label for="distrito">Distrito:</label>
        <select id="distrito"></select>
    </div>

    <div class="d-flex justify-content-between">
        <label for="seccional">Seccional:</label>
        <select id="seccional"></select>
    </div>

    <div class="d-flex justify-content-between">
        <label for="casilla">Casilla:</label>
        <select name="casilla" id="casilla"></select>
    </div>

    <input type="submit">
</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>
<script src="js/seleccionCasilla.js"></script>
</body>
</html>
