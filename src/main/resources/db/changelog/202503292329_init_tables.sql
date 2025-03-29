CREATE SCHEMA x6_application;

CREATE TABLE x6_application.user
(
    id              SERIAL PRIMARY KEY,
    first_name      VARCHAR        NOT NULL,
    last_name       VARCHAR        NOT NULL,
    surname         VARCHAR,
    email           VARCHAR(20) UNIQUE NOT NULL,
    login           VARCHAR(10) UNIQUE NOT NULL,
    phone           VARCHAR     UNIQUE NOT NULL,
    birthday        DATE,
    date_reg        DATE,
    is_active       NUMERIC(1)
);