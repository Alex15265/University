DROP TABLE IF EXISTS times;

CREATE TABLE IF NOT EXISTS times
(
time_id SERIAL PRIMARY KEY,
lesson_start VARCHAR(50) NOT NULL,
lesson_end VARCHAR(50) NOT NULL
);

INSERT INTO times (time_id, lesson_start, lesson_end)
VALUES (1, '2020-09-05 08:00:00', '2020-09-05 09:30:00');
INSERT INTO times (time_id, lesson_start, lesson_end)
VALUES (2, '2020-09-05 09:45:00', '2020-09-05 11:15:00');
INSERT INTO times (time_id, lesson_start, lesson_end)
VALUES (3, '2020-09-14 09:45:00', '2020-09-14 11:15:00');
INSERT INTO times (time_id, lesson_start, lesson_end)
VALUES (4, '2020-09-14 11:30:00', '2020-09-14 13:00:00');