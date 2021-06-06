create table distrito
(
    id int primary key
) default character set utf8;

create table seccional
(
    id          int primary key,
    fk_distrito int not null,
    foreign key (fk_distrito) references distrito (id)
) default character set utf8;

create table tipo_casilla
(
    id   int primary key auto_increment,
    tipo varchar(20) not null unique
) default character set utf8;

create table casilla
(
    id              int primary key auto_increment,
    fk_seccional    int not null,
    fk_tipo_casilla int not null,
    foreign key (fk_tipo_casilla) references tipo_casilla (id),
    foreign key (fk_seccional) references seccional (id)
) default character set utf8;

create table usuario
(
    id              int auto_increment primary key,
    nombre          varchar(60),
    calle           varchar(30),
    colonia         varchar(30),
    numero_casa     varchar(5),
    municipio       varchar(30),
    codigo_postal   varchar(5),
    telefono        varchar(10),
    email           varchar(320) not null unique,
    password        varchar(64)  not null,
    email_confirmed bool default false,
    enabled         bool default false,
    fk_casilla      int,
    foreign key (fk_casilla) references casilla (id)
) default character set utf8;

create table privilegio
(
    id     int auto_increment primary key,
    nombre varchar(30) not null unique
) default character set utf8;

create table usuario_privilegios
(
    fk_usuario    int not null,
    fk_privilegio int not null,
    foreign key (fk_usuario) references usuario (id) on delete cascade,
    foreign key (fk_privilegio) references privilegio (id) on delete cascade,
    primary key (fk_usuario, fk_privilegio)
) default character set utf8;

create table email_verification
(
    token      varchar(36) primary key,
    fk_usuario int not null unique,
    foreign key (fk_usuario) references usuario (id) on delete cascade
) default character set utf8;

create table oauth_application
(
    id   int primary key auto_increment,
    name varchar(20) not null unique
) default character set utf8;

create table oauth
(
    sub                  varchar(64) not null,
    fk_usuario           int         not null,
    fk_oauth_application int         not null,
    foreign key (fk_usuario) references usuario (id) on delete cascade,
    foreign key (fk_oauth_application) references oauth_application (id) on delete cascade,
    primary key (fk_usuario, fk_oauth_application),
    unique (sub, fk_oauth_application)
) default character set utf8;

create table cargo
(
    id     int primary key auto_increment,
    nombre varchar(40) not null unique
) default character set utf8;

create table candidato
(
    id       int primary key auto_increment,
    fk_cargo int,
    foreign key (fk_cargo) references cargo (id) on delete set null
) default character set utf8;

create table partido
(
    id     int primary key auto_increment,
    nombre varchar(50) not null unique
) default character set utf8;

-- Los candidatos pueden pertenecer a muchos partidos en caso de las coaliciones o a ninguno en caso de los independientes.
create table candidato_partido
(
    fk_candidato int not null,
    fk_partido   int not null,
    foreign key (fk_candidato) references candidato (id) on delete cascade,
    foreign key (fk_partido) references partido (id) on delete cascade,
    primary key (fk_candidato, fk_partido)
) default character set utf8;

create table votos
(
    cantidad     int not null default 0,
    fk_casilla   int not null,
    fk_candidato int not null,
    foreign key (fk_casilla) references casilla (id) on delete cascade,
    foreign key (fk_candidato) references candidato (id) on delete cascade,
    primary key (fk_casilla, fk_candidato)
) default character set utf8;

create trigger populateCasillaVotos
    after insert
    on casilla
    for each row
begin
    insert into votos (fk_casilla, fk_candidato)
    select NEW.id, candidato.id
    from candidato;
end;

create trigger defaultRole
    after insert
    on usuario
    for each row
begin
    insert into usuario_privilegios (fk_usuario, fk_privilegio)
    select NEW.id, 3;
end;
