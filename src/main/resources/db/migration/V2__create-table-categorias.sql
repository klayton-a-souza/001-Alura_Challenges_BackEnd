create table categorias(
    id_categoria bigint not null auto_increment,
    titulo varchar(100) not null unique,
    cor varchar(7) not null,
    ativo tinyint,

    primary key(id_categoria)
);