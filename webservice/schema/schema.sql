DROP TABLE IF EXISTS aluno;
DROP TABLE IF EXISTS professor;
DROP TABLE IF EXISTS titulacao;

CREATE TABLE titulacao(
	id serial,
	descricao varchar(100),
	PRIMARY KEY(id)
);

INSERT INTO titulacao VALUES (DEFAULT, 'Graduação');
INSERT INTO titulacao VALUES (DEFAULT, 'Especialização');
INSERT INTO titulacao VALUES (DEFAULT, 'Mestrado');
INSERT INTO titulacao VALUES (DEFAULT, 'Doutorado');

CREATE TABLE professor(
	id serial,
	prontuario varchar(20),
	nome varchar(100),
	titulacao_id int,
	PRIMARY KEY (id),
	FOREIGN KEY(titulacao_id) REFERENCES titulacao(id)
);

INSERT INTO professor VALUES (DEFAULT, 'p123', 'Gustavo', 3);
INSERT INTO professor VALUES (DEFAULT, 'p321', 'André', 4);
INSERT INTO professor VALUES (DEFAULT, 'p456', 'Daniela', 3);
INSERT INTO professor VALUES (DEFAULT, 'p654', 'Rodolfo', 2);
INSERT INTO professor VALUES (DEFAULT, 'p789', 'Leandro', 3);

CREATE TABLE aluno(
	id serial,
	prontuario varchar(20),
	nome varchar(100),
	professor_id int,
	PRIMARY KEY (id),
	FOREIGN KEY(professor_id) REFERENCES professor(id)
);

INSERT INTO aluno VALUES (DEFAULT, 'a123', 'Joana', 1);
INSERT INTO aluno VALUES (DEFAULT, 'a321', 'Carlos', 1);
INSERT INTO aluno VALUES (DEFAULT, 'a456', 'Marina', 2);
INSERT INTO aluno VALUES (DEFAULT, 'a654', 'Miriam', 5);
INSERT INTO aluno VALUES (DEFAULT, 'a356', 'Luis', NULL);

--View do Professor
CREATE VIEW v_professor AS
SELECT professor.id AS professor_id
     , professor.prontuario
     , professor.nome
     , titulacao.id AS titulacao_id
     , titulacao.descricao AS titulo
FROM      professor
LEFT JOIN titulacao
       ON (professor.titulacao_id = titulacao.id);