insert into partido (id, nombre)
values (1, 'PAN'),
       (2, 'PRD'),
       (3, 'PRI'),
       (4, 'VERDE ECOLÓGISTA'),
       (5, 'PT'),
       (6, 'MOVIMIENTO CIUDADANO'),
       (7, 'MORENA'),
       (8, 'PES'),
       (9, 'RSP'),
       (10, 'FUERZA MÉXICO'),
       (11, 'HAGAMOS'),
       (12, 'SOMOS y FUTURO'),
       (13, 'INDEPENDIENTE'),
       (14, 'NULO');


insert into cargo (id, nombre)
values (1, 'Presidente Municipal'),
       (2, 'Diputado Federal'),
       (3, 'Diputado Local');



insert into tipo_casilla (id, tipo)
values (1, 'Básica'),
       (2, 'Contigua'),
       (3, 'Especial'),
       (4, 'Extraordinaria'),
       (5, 'Otra');


insert into candidato (ID, FK_CARGO)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 1),
       (10, 1),
       (11, 1),
       (12, 1),
       (13, 1),
       (14, 1);

insert into candidato_partido (fk_candidato, fk_partido)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10),
       (11, 11),
       (12, 12),
       (13, 13),
       (14, 14);

insert into CANDIDATO (id, fk_cargo)
VALUES (15, 2),
       (16, 2),
       (17, 2),
       (18, 2),
       (19, 2),
       (20, 2),
       (21, 2),
       (22, 2),
       (23, 2);

insert into candidato_partido (fk_candidato, fk_partido)
VALUES (15, 1),
       (16, 2),
       (17, 3),
       (18, 4),
       (19, 5),
       (20, 6),

       (21, 1),
       (21, 2),
       (21, 3),

       (22, 4),
       (22, 5),
       (22, 7),

       (23, 14);

insert into candidato (id, fk_cargo)
VALUES (24, 3),
       (25, 3),
       (26, 3),
       (27, 3),
       (28, 3),
       (29, 3),
       (30, 3),
       (31, 3),
       (32, 3),
       (33, 3),
       (34, 3),
       (35, 3),
       (36, 3),
       (37, 3);

insert into candidato_partido (fk_candidato, fk_partido)
VALUES (24, 1),
       (25, 2),
       (26, 3),
       (27, 4),
       (28, 5),
       (29, 6),
       (30, 7),
       (31, 8),
       (32, 9),
       (33, 10),
       (34, 11),
       (35, 12),
       (36, 13),
       (37, 14);

insert into privilegio (id, nombre)
values (1, 'administrador'),
       (2, 'capturista'),
       (3, 'usuario');

insert into usuario (email, password, email_confirmed, enabled)
VALUES ('admin@goddrinksjava.codes', 'ShootYourGoo', true, true);

insert into usuario_privilegios (fk_usuario, fk_privilegio)
VALUES (1, 1);

insert into oauth_application (name)
values ('google'),
       ('facebook');

insert into distrito(id)
values (11),
       (20);

insert into seccional (id, fk_distrito)
values (2683, 11),
       (2691, 11),
       (2695, 11),
       (2696, 11),
       (2721, 11),
       (2722, 11),
       (2723, 11),
       (2724, 11),
       (2725, 11),
       (2726, 11),
       (2728, 11),
       (2729, 11),
       (3311, 11),
       (2650, 20),
       (2651, 20),
       (2652, 20),
       (2653, 20),
       (2654, 20),
       (2655, 20),
       (2656, 20),
       (2657, 20),
       (2658, 20),
       (2659, 20),
       (2660, 20),
       (2661, 20),
       (2662, 20),
       (2663, 20),
       (2664, 20),
       (2665, 20),
       (2666, 20),
       (2667, 20),
       (2668, 20),
       (2669, 20),
       (2670, 20),
       (2671, 20),
       (2672, 20),
       (2673, 20),
       (2674, 20),
       (2675, 20),
       (2676, 20),
       (2677, 20),
       (2678, 20),
       (2679, 20),
       (2680, 20),
       (2681, 20),
       (2682, 20),
       (2684, 20),
       (2685, 20),
       (2686, 20),
       (2687, 20),
       (2688, 20),
       (2689, 20),
       (2690, 20),
       (2692, 20),
       (2693, 20),
       (2694, 20),
       (2697, 20),
       (2698, 20),
       (2699, 20),
       (2701, 20),
       (2702, 20),
       (2703, 20),
       (2704, 20),
       (2705, 20),
       (2706, 20),
       (2707, 20),
       (2708, 20),
       (2709, 20),
       (2710, 20),
       (2711, 20),
       (2712, 20),
       (2713, 20),
       (2714, 20),
       (2715, 20),
       (2716, 20),
       (2717, 20),
       (2718, 20),
       (2719, 20),
       (2720, 20),
       (2727, 20);


insert into casilla (fk_seccional, fk_tipo_casilla)
select seccional.id, tipo_casilla.id
from seccional
         join tipo_casilla;

insert into casilla (fk_seccional, fk_tipo_casilla)
select seccional.id, 2
from seccional