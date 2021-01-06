DROP TABLE IF EXISTS classrooms;

CREATE TABLE IF NOT EXISTS classrooms
(
room_id SERIAL PRIMARY KEY,
room_number INT NOT NULL
);

INSERT INTO classrooms (room_id, room_number)
VALUES (1, 101);
INSERT INTO classrooms (room_id, room_number)
VALUES (2, 102);
INSERT INTO classrooms (room_id, room_number)
VALUES (3, 103);