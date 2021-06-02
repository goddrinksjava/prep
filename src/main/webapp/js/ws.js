window.onload = function () {
    console.log("loaded");
    startWS();
};

const startWS = function startWS() {
    const url = window.location;
    const baseUrl = url.host + "/";
    let ws = new WebSocket('ws://' + baseUrl + 'ws-prep');

    ws.onmessage = function (ev) {
        console.log(ev.data);
        const json = JSON.parse(ev.data);
        updatePrep(json);
    };

    ws.onclose = function () {
        ws = null;
        console.log("Closed");
        setTimeout(startWS, 3000);
    };
};

function updatePrep(prepDTO) {
    const content = document.getElementById("content");
    content.innerHTML = "";
    prepDTO.casillas
        .map(generateCasilla)
        .forEach(function (divCasilla) {
            content.appendChild(divCasilla)
        });
}

function generateCasilla(casilla) {
    const divCasilla = document.createElement("div");

    const pCasillaID = document.createElement("p");
    pCasillaID.textContent = "Casilla: " + casilla.id;
    divCasilla.appendChild(pCasillaID);

    casilla.candidaturas
        .map(generateCandidaturaElement)
        .forEach(function (divCandidatura) {
            divCasilla.appendChild(divCandidatura)
        });

    return divCasilla;
}

function generateCandidaturaElement(candidatura) {
    const divCandidatura = document.createElement("div");

    const pNombreCandidatura = document.createElement("p");
    pNombreCandidatura.textContent = candidatura.nombre;
    divCandidatura.appendChild(pNombreCandidatura);

    candidatura.candidatos
        .map(generateCandidato)
        .forEach(function (divCandidato) {
            divCandidatura.appendChild(divCandidato)
        });

    return divCandidatura;
}

function generateCandidato(candidato) {
    const divCandidato = document.createElement("div");

    const pPartidos = document.createElement("p");
    pPartidos.textContent = candidato.partidos.join(", ");
    divCandidato.appendChild(pPartidos);

    const pNombreCandidato = document.createElement("p");
    pNombreCandidato.textContent = candidato.nombre;
    divCandidato.appendChild(pNombreCandidato);

    const pVotosCandidato = document.createElement("p");
    pVotosCandidato.textContent = candidato.votos;
    divCandidato.appendChild(pVotosCandidato);

    return divCandidato;
}