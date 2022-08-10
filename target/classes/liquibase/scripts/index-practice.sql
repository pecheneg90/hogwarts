--liquibase formatted sql

--changeset pecheneg:1

CREATE INDEX index_student_name ON student (name);

--changeset pecheneg:2

CREATE INDEX index_faculty_name_and_color ON faculty (name, color);

