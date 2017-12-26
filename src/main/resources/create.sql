CREATE TABLE person (
  user_id    CHARACTER VARYING(20) PRIMARY KEY,
  email      CHARACTER VARYING(100) NOT NULL,
  password   CHARACTER(128)         NOT NULL,
  first_name CHARACTER VARYING(20)  NOT NULL,
  last_name  CHARACTER VARYING(30)  NOT NULL
);

CREATE TABLE product (
  id          SERIAL PRIMARY KEY,
  name        CHARACTER VARYING(10) NOT NULL,
  description TEXT                  NOT NULL,
  price       NUMERIC(5, 2)         NOT NULL
);
