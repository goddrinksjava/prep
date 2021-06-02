create table usuario
(
    id            int auto_increment primary key,
    nombre        varchar(60),
    calle         varchar(30),
    colonia       varchar(30),
    numero_casa   varchar(5),
    municipio     varchar(30),
    codigo_postal varchar(5),
    telefono      varchar(10),
    email         varchar(255) not null,
    password      varchar(64)  not null,
    enabled       bool         not null
);

create table privilegio
(
    id     int auto_increment primary key,
    nombre varchar(30) not null
);

create table usuario_privilegios
(
    fk_usuario    int not null,
    fk_privilegio int not null,
    foreign key (fk_usuario) references usuario (id),
    foreign key (fk_privilegio) references privilegio (id),
    primary key (fk_usuario, fk_privilegio)
);

create table oauth_application
(
    id   int primary key auto_increment,
    name varchar(20) not null
);

create table oauth
(
    id                   int primary key auto_increment,
    sub                  varchar(64) not null,
    fk_usuario           int         not null,
    fk_oauth_application int         not null,
    foreign key (fk_usuario) references usuario (id),
    foreign key (fk_oauth_application) references oauth_application (id)
);

-- presidente municipal, diputado federal, diputado local
create table cargo
(
    id     int primary key auto_increment,
    nombre varchar(40) not null
);

create table candidato
(
    id       int primary key auto_increment,
    nombre   varchar(60) not null,
    fk_cargo int         not null,
    foreign key (fk_cargo) references cargo (id)
);

create table partido
(
    id     int primary key auto_increment,
    nombre varchar(50) not null
);

-- Los candidatos pueden pertenecer a muchos partidos en caso de las coaliciones o a ninguno en caso de los independientes.
create table candidato_partido
(
    fk_candidato int not null,
    fk_partido   int not null,
    foreign key (fk_candidato) references candidato (id),
    foreign key (fk_partido) references partido (id),
    primary key (fk_candidato, fk_partido)
);

-- Basica, Contigua, Especial, Extraordinaria, Otra
create table tipo_casilla
(
    id   int primary key auto_increment,
    tipo varchar(20) not null
);

create table casilla
(
    id              int primary key auto_increment,
    distrito        int not null,
    seccion         int not null,
    fk_tipo_casilla int not null,
    foreign key (fk_tipo_casilla) references tipo_casilla (id)
);

create table votos
(
    cantidad     int not null default 0,
    fk_casilla   int not null,
    fk_candidato int not null,
    foreign key (fk_casilla) references casilla (id),
    foreign key (fk_candidato) references candidato (id),
    primary key (fk_casilla, fk_candidato)
)

