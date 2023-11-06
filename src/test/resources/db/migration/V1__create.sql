CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS observations;
DROP TABLE IF EXISTS measurement_types;

CREATE TABLE patients
(
    patient_id              INTEGER PRIMARY KEY,
    first_name      VARCHAR(255),
    last_name       VARCHAR(255)
);

CREATE TABLE measurement_types
(
    measurement_type VARCHAR(255) PRIMARY KEY,
    unit             VARCHAR(255)
);

CREATE TABLE observations
(
    id               uuid DEFAULT uuid_generate_v4(),
    measurement_type VARCHAR(255) REFERENCES measurement_types(measurement_type),
    date             TIMESTAMP WITH TIME ZONE,
    patient          INTEGER REFERENCES patients(patient_id),
    value            DOUBLE PRECISION,
    PRIMARY KEY (id)
);

-- auto-generated definition
create table users
(
    username   varchar(255) not null primary key,
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255)
);

-- auto-generated definition
create table roles
(
    id        serial not null primary key,
    role_name varchar(255)
);


-- auto-generated definition
create table user_role
(
    username varchar(255) not null references users(username),
    id       integer      not null references roles(id),
    primary key (username, id)
);

