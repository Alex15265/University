DROP TABLE IF EXISTS professors;
DROP TABLE IF EXISTS courses;

CREATE TABLE IF NOT EXISTS professors
(
professor_id SERIAL PRIMARY KEY,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS courses
(
course_id SERIAL PRIMARY KEY,
course_name VARCHAR(50) NOT NULL,
course_description VARCHAR(50) NOT NULL,
professor_id INT
);

INSERT INTO courses (course_id, course_name, course_description, professor_id)
VALUES (1, 'History', 'History Description', 1);
INSERT INTO courses (course_id, course_name, course_description, professor_id)
VALUES (2, 'Robotics', 'Robotics Description', 2);
INSERT INTO courses (course_id, course_name, course_description, professor_id)
VALUES (3, 'Criminology', 'Criminology Description', 3);
INSERT INTO courses (course_id, course_name, course_description, professor_id)
VALUES (4, 'Architecture', 'Architecture Description', 3);


INSERT INTO professors (professor_id, first_name, last_name)
VALUES (1, 'Gerbert', 'Shildt');
INSERT INTO professors (professor_id, first_name, last_name)
VALUES (2, 'Albert', 'Einshtein');
INSERT INTO professors (professor_id, first_name, last_name)
VALUES (3, 'Robert', 'Martin');