insert into PARTIDO (NOMBRE)
values ('PAN'),
       ('PRD'),
       ('PRI'),
       ('VERDE ECOLÓGISTA'),
       ('PT'),
       ('MOVIMIENTO'),
       ('CIUDADANO'),
       ('MORENA'),
       ('PES'),
       ('RSP'),
       ('FUERZA MÉXICO'),
       ('HAGAMOS'),
       ('SOMOS y FUTURO'),
       ('INDEPENDIENTE');



insert into CARGO (NOMBRE)
values ('Presidente Municipal'),
       ('Diputado Federal'),
       ('Diputado Local');



insert into TIPO_CASILLA (TIPO)
values ('Básica'),
       ('Contigua'),
       ('Especial'),
       ('Extraordinaria'),
       ('Otra');


insert into CANDIDATO (NOMBRE, FK_CARGO)
VALUES ('Juan', 2),
       ('Pedro', 2),
       ('Miguel', 2);

insert into CANDIDATO_PARTIDO (FK_CANDIDATO, FK_PARTIDO)
values (1, 4),
       (1, 5),
       (1, 8);

insert into CANDIDATO_PARTIDO (FK_CANDIDATO, FK_PARTIDO)
values (2, 4);

insert into CANDIDATO_PARTIDO (FK_CANDIDATO, FK_PARTIDO)
values (3, 1);


insert into CASILLA (DISTRITO, SECCION, FK_TIPO_CASILLA)
VALUES (1, 1, 1);

insert into VOTOS (CANTIDAD, FK_CASILLA, FK_CANDIDATO)
VALUES (0, 1, 1),
       (0, 1, 2),
       (0, 1, 3);


insert into PRIVILEGIO (NOMBRE)
values ('administrador'),
       ('capturista');

insert into USUARIO (NOMBRE, CALLE, COLONIA, NUMERO_CASA, MUNICIPIO, CODIGO_POSTAL, TELEFONO, EMAIL, PASSWORD, ENABLED)
VALUES (null, null, null, null, null, null, null, 'admin@goddrinksjava.codes', 'ShootYourGoo', true);

insert into USUARIO_PRIVILEGIOS (FK_USUARIO, FK_PRIVILEGIO)
VALUES (1, 1);

