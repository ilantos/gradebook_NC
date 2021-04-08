CREATE TYPE person_role AS ENUM ('STUDENT', 'TEACHER');
CREATE TYPE type_location AS ENUM ('COUNTRY', 'CITY', 'UNIVERSITY', 'GROUP');
CREATE TABLE location (
	id_location SERIAL PRIMARY KEY,
	id_parent INTEGER REFERENCES location (id_location) 
		ON DELETE CASCADE,
	type_loc type_location NOT NULL,
	title VARCHAR (100)
);


CREATE TABLE person (
	id_person SERIAL PRIMARY KEY,
	id_location INTEGER REFERENCES location ON DELETE CASCADE,
	first_name VARCHAR (20),
	last_name VARCHAR (50),
	login VARCHAR (40) UNIQUE NOT NULL,
	password VARCHAR (30),
	is_admin BOOLEAN DEFAULT false
);

CREATE TABLE subject (
	id_subject SERIAL PRIMARY KEY,
	title VARCHAR (100),
	description VARCHAR (500)
);

CREATE TABLE lesson (
	id_lesson SERIAL PRIMARY KEY,
	id_subject INTEGER NOT NULL REFERENCES subject ON DELETE CASCADE,
	title VARCHAR (100),
	description VARCHAR (500),
	max_grade NUMERIC (5, 2),
	creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE person_subject (
	id_person INTEGER REFERENCES person ON DELETE CASCADE, 
	id_subject INTEGER REFERENCES subject ON DELETE CASCADE,
	person_role person_role NOT NULL,
	PRIMARY KEY (id_person, id_subject)
);

CREATE TABLE lesson_grade (
	id_person INTEGER REFERENCES person ON DELETE CASCADE,
	id_subject INTEGER REFERENCES subject ON DELETE CASCADE,
	id_lesson INTEGER REFERENCES lesson ON DELETE CASCADE,
	grade NUMERIC (5, 2) 
		DEFAULT 0
		CHECK (grade >= 0),
	PRIMARY KEY (id_person, id_subject, id_lesson)
);

CREATE FUNCTION insert_lesson_grade() RETURNS TRIGGER LANGUAGE PLPGSQL AS $insert_lesson_grade$
BEGIN
	IF NEW.person_role = 'STUDENT' THEN
		INSERT INTO lesson_grade (SELECT NEW.id_person, NEW.id_subject, id_lesson FROM lesson WHERE id_subject = NEW.id_subject);
	END IF;
	RETURN NEW;
END;
$insert_lesson_grade$;

CREATE TRIGGER insert_lesson_grade 
AFTER INSERT ON person_subject 
FOR EACH ROW 
EXECUTE PROCEDURE insert_lesson_grade();