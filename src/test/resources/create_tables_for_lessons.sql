DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS groups_lessons;

CREATE TABLE IF NOT EXISTS lessons
(
lesson_id SERIAL PRIMARY KEY,
professor_id INT,
course_id INT,
room_id INT,
time_id INT
);

CREATE TABLE IF NOT EXISTS groups_lessons
(
group_id INT NOT NULL,
lesson_id INT NOT NULL
);

INSERT INTO groups_lessons (group_id, lesson_id)
VALUES (1, 1);
INSERT INTO groups_lessons (group_id, lesson_id)
VALUES (2, 1);
INSERT INTO groups_lessons (group_id, lesson_id)
VALUES (1, 2);
INSERT INTO groups_lessons (group_id, lesson_id)
VALUES (2, 3);
INSERT INTO groups_lessons (group_id, lesson_id)
VALUES (1, 4);
INSERT INTO groups_lessons (group_id, lesson_id)
VALUES (2, 4);

INSERT INTO lessons (lesson_id, professor_id, course_id, room_id, time_id)
VALUES (1, 1, 1, 1, 1);
INSERT INTO lessons (lesson_id, professor_id, course_id, room_id, time_id)
VALUES (2, 2, 2, 2, 1);
INSERT INTO lessons (lesson_id, professor_id, course_id, room_id, time_id)
VALUES (3, 3, 3, 3, 1);
INSERT INTO lessons (lesson_id, professor_id, course_id, room_id, time_id)
VALUES (4, 3, 4, 3, 2);