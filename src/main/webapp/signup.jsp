<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>

<form method = "Post">
    <c:if test="${error != null}">
        <p>${error}</p>
    </c:if>

    <div>
        <label for="email">Email</label>
        <input name="email" id="email" type="email" required/>
    </div>

    <div>
        <label for="password">Password</label>
        <input name="password" id="password" type="password" required/>
    </div>

    <div>
        <label for="nombre">Nombre</label>
        <input name="nombre" id="nombre" type="text" required/>
    </div>

    <div>
        <label for="calle">Calle</label>
        <input name="calle" id="calle" type="text" required/>
    </div>

    <div>
        <label for="colonia">Colonia</label>
        <input name="colonia" id="colonia" type="text" required/>
    </div>

    <div>
        <label for="numeroCasa">#</label>
        <input name="numeroCasa" id="numeroCasa" type="text" required/>
    </div>

    <div>
        <label for="municipio">Municipio</label>
        <input name="municipio" id="municipio" type="text" required/>
    </div>

    <div>
        <label for="codigoPostal">C.P.</label>
        <input name="codigoPostal" id="codigoPostal" type="text" required/>
    </div>

    <div>
        <label for="telefono">Telefono</label>
        <input name="telefono" id="telefono" type="tel" required/>
    </div>

    <input type="submit" value="Enviar">

</form>

</body>
</html>