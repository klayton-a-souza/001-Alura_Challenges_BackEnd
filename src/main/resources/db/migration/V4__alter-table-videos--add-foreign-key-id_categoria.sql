ALTER TABLE videos ADD CONSTRAINT FOREIGN KEY (id_categoria)
REFERENCES categorias(id_categoria);