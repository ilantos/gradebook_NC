ALTER SEQUENCE lesson_id_lesson_seq RESTART;
ALTER SEQUENCE person_id_person_seq RESTART;
ALTER SEQUENCE location_id_location_seq RESTART;
ALTER SEQUENCE subject_id_subject_seq RESTART;

INSERT INTO location (id_parent, type_loc, title) VALUES (null, 'COUNTRY', 'ukraine');
INSERT INTO location (id_parent, type_loc, title) VALUES (1, 'CITY', 'sumy');
INSERT INTO location (id_parent, type_loc, title) VALUES (2, 'UNIVERSITY', 'Sumy State University');
INSERT INTO location (id_parent, type_loc, title) VALUES (3, 'GROUP', 'IN-007');
INSERT INTO location (id_parent, type_loc, title) VALUES (3, 'GROUP', 'IK-001');

INSERT INTO person (id_location, first_name, last_name, patronymic, login, password, email, is_admin)
	VALUES (null, 'Admin', 'Admin', 'Adminovich', 'admin', '$2y$12$d0g1IqSX3FnqWaP/mio1ZejgIaNNC4qlDz/1bef1BWeEIlVqwprbm', 'anton.ilchenko98@gmail.com', true);
INSERT INTO person (id_location, first_name, last_name, patronymic, login, password, email)
	VALUES (4, 'Petro', 'Petro', 'Petrovich', 'petro', '$2y$12$XVvjYCTzSC5YzgMb7Xt8suZ89//53geTY13CIWjC83Zpr2MAd8J9S', 'petya@gmail.com');
INSERT INTO person (id_location, first_name, last_name, patronymic, login, password, email)
	VALUES (4, 'Vasya', 'Vasya', 'Vasilevich', 'vasya', '$2y$12$XVvjYCTzSC5YzgMb7Xt8suZ89//53geTY13CIWjC83Zpr2MAd8J9S', 'vasya@gmail.com');
INSERT INTO person (id_location, first_name, last_name, patronymic, login, password, email)
	VALUES (5, 'Kate', 'Kate', 'Katevich', 'kate', '$2y$12$XVvjYCTzSC5YzgMb7Xt8suZ89//53geTY13CIWjC83Zpr2MAd8J9S', 'kate@gmail.com');

INSERT INTO subject (title, description) VALUES ('Rocket science', 'The hardest subject in the world');
INSERT INTO lesson (id_subject, title, description, max_grade, start_date) VALUES (1, 'Matrix', 'Triangle method', 5.0, '2021-04-01 10:05');
INSERT INTO lesson (id_subject, title, description, max_grade, start_date) VALUES (1, 'Integral', 'The entry lesson', 5.0, '2021-04-01 11:25');
INSERT INTO lesson (id_subject, title, description, max_grade, start_date) VALUES (1, 'Fourier', 'Expand the function by the Fourier method', 5.0, '2021-04-01 13:00');

INSERT INTO subject (title, description) VALUES ('Cyber security', 'Subject about methods of cyber security');
INSERT INTO lesson (id_subject, title, description, max_grade, start_date) VALUES (2, 'Cesar cipher', 'The entry lesson', 5.0, '2021-05-01 11:25');
INSERT INTO lesson (id_subject, title, description, max_grade, start_date) VALUES (2, 'BCrypt', 'The entry lesson', 5.0, '2021-05-01 13:00');

INSERT INTO person_subject (id_person, id_subject, person_role) VALUES (2, 1, 'TEACHER');
INSERT INTO person_subject (id_person, id_subject, person_role) VALUES (3, 1, 'STUDENT');
INSERT INTO person_subject (id_person, id_subject, person_role) VALUES (4, 1, 'STUDENT');

INSERT INTO person_subject (id_person, id_subject, person_role) VALUES (3, 2, 'TEACHER');
INSERT INTO person_subject (id_person, id_subject, person_role) VALUES (2, 2, 'STUDENT');
INSERT INTO person_subject (id_person, id_subject, person_role) VALUES (4, 2, 'STUDENT');