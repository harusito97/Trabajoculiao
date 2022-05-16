CREATE TABLE Cliente
(
    id_cliente int         not null auto_increment,
    nombre     varchar(25),
    apellido   varchar(25),
    rut        varchar(10) not null unique,
    email      varchar(50),
    telefono   int,
    primary key (id_cliente)
);

CREATE TABLE Usuario
(
    id_usuario int         not null auto_increment,
    nombre     varchar(25),
    email      varchar(50),
    rut        varchar(10) not null unique,
    telefono   varchar(25),
    primary key (id_usuario)
);

CREATE TABLE Servicio
(
    id_servicio int not null auto_increment,
    nombre      varchar(70),
    direccion   varchar(70),
    telefono    int,
    primary key (id_servicio)
);

CREATE TABLE Review
(
    id_review   int not null auto_increment,
    valor       int,
    descripcion varchar(150),
    fecha       timestamp,
    primary key (id_review)
);

CREATE TABLE Reserva
(
    id_reserva int not null auto_increment,
    fecha      timestamp,
    hora       datetime,
    primary key (id_reserva)
);
