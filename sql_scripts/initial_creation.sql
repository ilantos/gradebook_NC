ALTER SEQUENCE lesson_id_lesson_seq RESTART;
ALTER SEQUENCE person_id_person_seq RESTART;
ALTER SEQUENCE location_id_location_seq RESTART;
ALTER SEQUENCE subject_id_subject_seq RESTART;

INSERT INTO location (id_parent, type_loc, title) VALUES (null, 'COUNTRY', 'ukraine');
INSERT INTO location (id_parent, type_loc, title) VALUES (1, 'CITY', 'sumy');
INSERT INTO location (id_parent, type_loc, title) VALUES (2, 'UNIVERSITY', 'Sumy State University');
INSERT INTO location (id_parent, type_loc, title) VALUES (3, 'GROUP', 'IN-007');
INSERT INTO location (id_parent, type_loc, title) VALUES (3, 'GROUP', 'IK-001');

INSERT INTO person (id_location, first_name, last_name, login, password, is_admin)
	VALUES (null, 'Admin', 'Admin', 'admin1', 'password', true);
INSERT INTO person (id_location, first_name, last_name, login, password)
	VALUES (4, 'Petro', 'Petro', 'petro', 'password');
INSERT INTO person (id_location, first_name, last_name, login, password)
	VALUES (4, 'Vasya', 'Vasya', 'vasya', 'password');
INSERT INTO person (id_location, first_name, last_name, login, password)
	VALUES (5, 'Kate', 'Kate', 'kate', 'password');

INSERT INTO subject (title, description) VALUES ('Rocket science', 'The hardest subject in the world');
INSERT INTO lesson (id_subject, title, description, max_grade) VALUES (1, 'Matrix', 'Triangle method', 5.0);
INSERT INTO lesson (id_subject, title, description, max_grade) VALUES (1, 'Integral', 'The entry lesson', 5.0);
INSERT INTO lesson (id_subject, title, description, max_grade) VALUES (1, 'Fourier', 'Expand the function by the Fourier method', 5.0);

INSERT INTO subject (title, description) VALUES ('Cyber security', 'Subject about methods of cyber security');
INSERT INTO lesson (id_subject, title, description, max_grade) VALUES (2, 'Cesar cipher', 'The entry lesson', 5.0);
INSERT INTO lesson (id_subject, title, description, max_grade) VALUES (2, 'BCrypt', 'The entry lesson', 5.0);

INSERT INTO person_subject (id_person, id_subject, person_role) VALUES (2, 1, 'TEACHER');
INSERT INTO person_subject (id_person, id_subject, person_role) VALUES (3, 1, 'STUDENT');
INSERT INTO person_subject (id_person, id_subject, person_role) VALUES (4, 1, 'STUDENT');

INSERT INTO person_subject (id_person, id_subject, person_role) VALUES (3, 2, 'TEACHER');
INSERT INTO person_subject (id_person, id_subject, person_role) VALUES (2, 2, 'STUDENT');
INSERT INTO person_subject (id_person, id_subject, person_role) VALUES (4, 2, 'STUDENT');