--begin state table initialization

INSERT INTO state (id, name, abbvr) VALUES ('1', 'Paraná', 'PR');
--end state table initialization
--begin city table initialization
INSERT INTO city (id, name, state_id) VALUES ('1', 'Guarapuava', '1');
--end city table initialization
--begin neighborhood table initialization
INSERT INTO neighborhood (id, name, city_id) VALUES ('1', 'Aeroporto', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('2', 'Alto Cascavel', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('3', 'Alto da XV', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('4', 'Bairro dos Estados', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('5', 'Batel', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('6', 'Bonsucesso', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('7', 'Boqueirão', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('8', 'Cascavel', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('9', 'Centro', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('10', 'Cidade dos Lagos', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('11', 'Conradinho', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('12', 'Feroz II', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('13', 'Industrial', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('14', 'Jardim das Américas', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('15', 'Jordão', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('16', 'Karpinski', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('17', 'Morro Alto', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('18', 'Primavera', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('19', 'Residencial 2000', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('20', 'Residencial Virmond', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('21', 'Santa Cruz', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('22', 'Santana', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('23', 'São Cristovão', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('24', 'São João', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('25', 'Sol Poente', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('26', 'Trianon', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('27', 'Vila Bela', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('28', 'Vila Carli', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('29', 'Vila Colibri', '1');
INSERT INTO neighborhood (id, name, city_id) VALUES ('30', 'Xarquinho', '1');
--end neighborhood table initialization
