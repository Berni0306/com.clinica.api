create table medicos(

    id bigint not null auto_increment,
    nombre varchar(100) not null,
    email varchar(100) not null unique,
    documento varchar(6) not null unique,
    especialidad varchar(100) not null,
    calle varchar(100) not null,
    colonia varchar(100) not null,
    ciudad varchar(100) not null,
    estado varchar(100) not null,
    cp varchar(5) not null,

    primary key(id)

);