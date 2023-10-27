CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS observations;
DROP TABLE IF EXISTS measurement_types;

CREATE TABLE measurement_types
(
    measurement_type VARCHAR(255) PRIMARY KEY,
    unit             VARCHAR(255)
);

CREATE TABLE observations
(
    id               uuid DEFAULT uuid_generate_v4(),
    measurement_type VARCHAR(255) REFERENCES measurement_types(measurement_type
        ),
    date             DATE,
    patient          INTEGER,
    value            DOUBLE PRECISION,
    PRIMARY KEY (id)
);