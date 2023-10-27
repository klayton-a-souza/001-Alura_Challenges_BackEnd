create table usuarios(

    id_usuario bigint not null auto_increment,
    titulo varchar(100) not null,
    descricao varchar(255) not null,

    primary key(id_usuario)
);