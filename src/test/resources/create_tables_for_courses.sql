DROP TABLE IF EXISTS professors;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS students_courses;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS groups;

CREATE TABLE IF NOT EXISTS students
(
student_id SERIAL PRIMARY KEY,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
group_id INT
);

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

CREATE TABLE IF NOT EXISTS students_courses
(
student_id INT NOT NULL,
course_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS groups
(
group_id SERIAL PRIMARY KEY,
group_name VARCHAR(50) NOT NULL
);

INSERT INTO groups (group_id, group_name)
VALUES (1, 'aa-01');
INSERT INTO groups (group_id, group_name)
VALUES (2, 'aa-02');

INSERT INTO students_courses (student_id, course_id)
VALUES (1, 3);
INSERT INTO students_courses (student_id, course_id)
VALUES (2, 3);
INSERT INTO students_courses (student_id, course_id)
VALUES (3, 3);

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

INSERT INTO students (student_id, first_name, last_name, group_id)
VALUES (1, 'Alex', 'Smith', 1);
INSERT INTO students (student_id, first_name, last_name, group_id)
VALUES (2, 'Ann', 'White', 2);
INSERT INTO students (student_id, first_name, last_name, group_id)
VALUES (3, 'Leo', 'Messi', 2);