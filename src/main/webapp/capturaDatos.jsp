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
</head>
<body>

<h1>${cargo}</h1>

<form method="post">
    <c:forEach items="${candidatos}" var="candidato">
        <div>
            <input type="hidden" value="${candidato.id}" name="id">

            <p>${candidato.partido}</p>

            <p>${candidato.nombre}</p>

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


<%--<div>--%>
<%--    <p>{Cargo}</p>--%>
<%--    <p>{Partido}</p>--%>
<%--    <label for="numero_votos">Numero de votos</label>--%>
<%--    <input name="numero_votos" id="numero_votos" type="number"/>--%>
<%--    <label for="candidato">Candidato</label>--%>
<%--    <input name="candidato" id="candidato" type="text"/>--%>
<%--</div>--%>

</body>
</html>
