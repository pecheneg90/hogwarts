CREATE TABLE car
(
    key   INTEGER primary key,
    label VARCHAR,
    model VARCHAR,
    price NUMERIC
);

ALTER TABLE car
    ADD CONSTRAINT key_check UNIQUE (key);

CREATE TABLE person
(
    key            INTEGER REFERENCES car (key),
    name           VARCHAR,
    age            INTEGER,
    driver_license BOOLEAN
);