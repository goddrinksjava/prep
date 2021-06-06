"use strict";

const tipoCasillaElement = document.getElementById("tipoCasilla");
const distritoElement = document.getElementById("distrito");
const seccionalElement = document.getElementById("seccional");
const casillaElement = document.getElementById("casilla");
let distritos = [];

setDistritos()

function setDistritos() {
    distritoElement.innerHTML = "";
    fetch('/Resource/Distritos').then(function (response) {
        return response.json();
    }).then(function (_distritos) {
        distritos = _distritos;
    }).then(function () {
        distritos.forEach(function (distrito) {
            const optionElement = document.createElement("option");
            optionElement.setAttribute("value", distrito.id);
            optionElement.textContent = distrito.id;
            distritoElement.appendChild(optionElement);
        });
        setSeccionales();
    });
}

distritoElement.onchange = setSeccionales;
tipoCasillaElement.onchange = setCasillas;
seccionalElement.onchange = setCasillas;

function setSeccionales() {
    seccionalElement.innerHTML = "";

    const value = parseInt(distritoElement.value);

    const distrito = distritos.find(function (distrito) {
        return distrito.id === value
    });

    distrito.seccionalDTOList.forEach(function (seccional) {
        const optionElement = document.createElement("option");
        optionElement.setAttribute("value", seccional.id);
        optionElement.textContent = seccional.id;
        seccionalElement.appendChild(optionElement);
    });

    setCasillas();
}

function setCasillas() {
    fetch(`/Resource/Casillas?tipoCasilla=${tipoCasillaElement.value}&seccional=${seccionalElement.value}`).then(function (response) {
        return response.json();
    }).then(function (casillas) {
        casillaElement.innerHTML = "";
        casillas.forEach(function (casilla) {
            const optionElement = document.createElement("option");
            optionElement.setAttribute("value", casilla.id);
            optionElement.textContent = casilla.id;
            casillaElement.appendChild(optionElement);
        })
    });
}


