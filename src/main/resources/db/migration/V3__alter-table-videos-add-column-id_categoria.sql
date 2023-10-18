ALTER TABLE videos ADD id_categoria bigint;

update videos set id_categoria = 1;
