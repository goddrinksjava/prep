<%--
  Created by IntelliJ IDEA.
  User: Razer
  Date: 5/27/2021
  Time: 2:15 PM
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

<h1>${cargo}</h1>

<form method="post">
    <c:forEach items="${candidatos}" var="candidato">
        <div>
            <input type="hidden" value="${candidato.id}" name="id">

            <p>${candidato.partido}</p>

            <div>
                <label for="numero_votos_${candidato.id}">Numero de votos</label>
                <input
                        type="number"
                        name="numero_votos"
                        id="numero_votos_${candidato.id}"
                        value="${candidato.votos}"
                />
            </div>
        </div>
    </c:forEach>

    <input type="submit">
</form>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>

</body>
</html>
