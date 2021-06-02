<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>

<form method = "Post">

    <div>
        <label for="email">Email</label>
        <input name="email" id="email" type="email"/>
    </div>

    <div>
        <label for="password">Password</label>
        <input name="password" id="password" type="password"/>
    </div>

    <div>
        <label for="nombre">Nombre</label>
        <input name="nombre" id="nombre" type="text"/>
    </div>

    <div>
        <label for="calle">Calle</label>
        <input name="calle" id="calle" type="text"/>
    </div>

    <div>
        <label for="colonia">Colonia</label>
        <input name="colonia" id="colonia" type="text"/>
    </div>

    <div>
        <label for="numero_casa">#</label>
        <input name="numero_casa" id="numero_casa" type="text"/>
    </div>

    <div>
        <label for="municipio">Municipio</label>
        <input name="municipio" id="municipio" type="text"/>
    </div>

    <div>
        <label for="codigo_postal">C.P.</label>
        <input name="codigo_postal" id="codigo_postal" type="text"/>
    </div>

    <div>
        <label for="telefono">Telefono</label>
        <input name="telefono" id="telefono" type="tel"/>
    </div>

    <input type="submit" value="Enviar">

</form>

</body>
</html>