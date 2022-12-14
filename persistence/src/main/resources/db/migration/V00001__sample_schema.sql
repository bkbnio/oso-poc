CREATE TABLE users
(
    id         UUID                        NOT NULL,
    email      TEXT                        NOT NULL UNIQUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE repos
(
    id         UUID                        NOT NULL,
    name       TEXT                        NOT NULL UNIQUE,
    is_public  BOOLEAN                     NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

ALTER TABLE ONLY repos
    ADD CONSTRAINT repos_pkey PRIMARY KEY (id);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);