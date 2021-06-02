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
</head>
<body>

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


</body>
</html>
