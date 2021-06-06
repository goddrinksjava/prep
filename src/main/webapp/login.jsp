<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/extended.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="container-fluid form-login">
    <div class="row">
        <div class="col d-flex justify-content-center">
            <div class="pt-5">
                <div class="d-flex justify-content-center">
                    <img src="${pageContext.request.contextPath}/img/LOGO.png" alt="Logo" height="50">
                </div>
                <form method="post" style="width: 400px">
                    <div class="d-flex justify-content-center">
                        <label for="email" class="visually-hidden-focusable">Email</label>
                        <input name="email" id="email" type="email" placeholder="Email"/>
                    </div>
                    <div class="d-flex justify-content-center">
                        <label for="password" class="visually-hidden-focusable">Password</label>
                        <input name="password" id="password" type="password" placeholder="Password"/>
                    </div>
                    <div class="d-flex justify-content-center">
                        <button type="submit">Iniciar sesion</button>
                    </div>
                </form>

                <div class="d-flex justify-content-center mb-1">
                    <a href="${pageContext.request.contextPath}/GoogleOauth">
                        <button>Inicia sesion con Google</button>
                    </a>
                </div>

                <div class="d-flex justify-content-center mb-1">
                    <a href="${pageContext.request.contextPath}/FacebookOauth">
                        <button>Inicia sesion con Facebook</button>
                    </a>
                </div>

                <div class="d-flex justify-content-center">
                    <a class="text-secondary" href="${pageContext.request.contextPath}/Signup">Crear cuenta</a>
                </div>


            </div>
        </div>
    </div>
</div>


<script src="${pageContext.request.contextPath}/js/ws.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>
</body>
</html>