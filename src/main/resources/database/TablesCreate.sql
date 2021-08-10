CREATE TABLE IF NOT EXISTS role
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS person
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(20) NOT NULL ,
    email VARCHAR(20) NOT NULL ,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS people_roles
(
    employee_id SERIAL ,
    roles_id SERIAL
);