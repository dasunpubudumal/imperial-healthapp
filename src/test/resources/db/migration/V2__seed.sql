INSERT INTO patients
(
    patient_id,
 first_name,
 last_name
)
VALUES (101, 'Patient', 'One');

INSERT INTO patients
(
    patient_id,
    first_name,
    last_name
)
VALUES (102, 'Patient', 'Two');

INSERT INTO patients
(
    patient_id,
    first_name,
    last_name
)
VALUES (103, 'Patient', 'Three');

INSERT INTO measurement_types
(measurement_type,
 unit)
VALUES      ( 'heart-rate',
              'beats/minute' );

INSERT INTO measurement_types
(measurement_type,
 unit)
VALUES      ( 'skin-temperature',
              'degrees Celcius' );

INSERT INTO measurement_types
(measurement_type,
 unit)
VALUES      ( 'respiratory-rate',
              'breaths/minute' );

INSERT INTO observations
(measurement_type,
 date,
 patient,
 value)
VALUES      ( 'heart-rate',
              '2023-09-06T11:02:44Z',
              101,
              65 );

INSERT INTO observations
(measurement_type,
 date,
 patient,
 value)
VALUES      ( 'skin-temperature',
              '2023-09-07T11:23:24Z',
              101,
              37.2 );

INSERT INTO observations
(measurement_type,
 date,
 patient,
 value)
VALUES      ( 'respiratory-rate',
              '2023-09-06T11:02:44Z',
              101,
              15 );

INSERT INTO observations
(measurement_type,
 date,
 patient,
 value)
VALUES      ( 'heart-rate',
              '2023-09-04T08:54:33Z',
              102,
              76 );

INSERT INTO observations
(measurement_type,
 date,
 patient,
 value)
VALUES      ( 'heart-rate',
              '2023-09-04T08:54:33Z',
              102,
              76 );

INSERT INTO observations
(measurement_type,
 date,
 patient,
 value)
VALUES      ( 'respiratory-rate',
              '2023-09-04T08:54:33Z',
              102,
              18 );

INSERT INTO observations
(measurement_type,
 date,
 patient,
 value)
VALUES      ( 'skin-temperature',
              '2023-09-05T15:12:23Z',
              103,
              37.8 );

INSERT INTO observations
(id,
 measurement_type,
 date,
 patient,
 value)
VALUES      ('a94d682f-e537-4d87-829f-c6d2af2ca0fc',
             'skin-temperature',
              '2023-09-05T15:12:23Z',
              103,
              37.8 );
