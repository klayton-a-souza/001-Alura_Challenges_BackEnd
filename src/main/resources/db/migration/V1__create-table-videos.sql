create table videos(

    id_video bigint not null auto_increment,
    titulo varchar(100) not null unique,
    descricao varchar(255) not null unique,
    url varchar(2048) not null,
    ativo tinyint,

    primary key(id_video)
);