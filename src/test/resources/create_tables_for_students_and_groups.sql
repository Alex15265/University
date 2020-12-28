DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS groups;

CREATE TABLE IF NOT EXISTS students
(
student_id SERIAL PRIMARY KEY,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
group_id INT
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
INSERT INTO groups (group_id, group_name)
VALUES (3, 'aa-03');
INSERT INTO groups (group_id, group_name)
VALUES (4, 'aa-04');

INSERT INTO students (student_id, first_name, last_name, group_id)
VALUES (1, 'Alex', 'Smith', 1);
INSERT INTO students (student_id, first_name, last_name, group_id)
VALUES (2, 'Ann', 'White', 2);
INSERT INTO students (student_id, first_name, last_name, group_id)
VALUES (3, 'Leo', 'Messi', 2);
INSERT INTO students (student_id, first_name, last_name, group_id)
VALUES (4, 'Lisa', 'Ann', 3);
INSERT INTO students (student_id, first_name, last_name, group_id)
VALUES (5, 'Roy', 'Jones', 3);
INSERT INTO students (student_id, first_name, last_name, group_id)
VALUES (6, 'Bart', 'Simpson', 3);