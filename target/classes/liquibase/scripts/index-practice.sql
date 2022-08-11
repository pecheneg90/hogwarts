--liquibase formatted sql

--changeSet pecheneg:1

CREATE INDEX index_student_name ON student (name);

--changeSet pecheneg:2

CREATE INDEX index_faculty_name_and_color ON faculty (name, color);