<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid">
    <div class="row">
        <div class="col-lg-2 col-sm text-start justify-content-center align-self-center">
            <img src="${pageContext.request.contextPath}/img/LOGO.png" alt="Logo" height="50">
        </div>
        <div class="col-lg-8 text-center d-none d-lg-block">
            <h1 class="fw-normal fs-3">Elecciones 2021</h1>
            <h3 class="fw-light fs-5">Programa de Resultados Electorales Preliminares</h3>
        </div>
        <div class="col-lg-2 col-sm text-end justify-content-center align-self-center">
            <c:choose>
                <c:when test="${usuario == null}">
                    <a href="${pageContext.request.contextPath}/Login" class="text-secondary">Login</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/Logout" class="text-secondary">Logout</a>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>

<div class="bg-prep-pink" style="min-height: 10px;">
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Inicio</a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">

                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <c:if test='${usuarioPrivilegios.contains("capturista") || usuarioPrivilegios.contains("administrador")}'>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/SeleccionCasilla">Seleccion de casilla</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Candidatura
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/CapturaDatos/1">Presidente
                                        Municipal</a>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/CapturaDatos/2">Diputado
                                        Federal</a>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/CapturaDatos/3">Diputado
                                        Local</a>
                                </li>
                            </ul>
                        </li>
                    </c:if>

                    <c:if test="${usuario != null}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/Perfil">Perfil</a>
                        </li>
                    </c:if>
                </ul>


                <c:if test='${usuarioPrivilegios.contains("administrador")}'>
                    <ul class="navbar-nav mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/Admin">Panel de
                                administrador</a>
                        </li>
                    </ul>
                </c:if>
            </div>
        </div>
    </nav>
</div>

