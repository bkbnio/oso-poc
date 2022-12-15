CREATE TABLE users
(
    id         UUID                        NOT NULL,
    email      TEXT                        NOT NULL UNIQUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE repos
(
    id         UUID                        NOT NULL,
    name       TEXT                        NOT NULL UNIQUE,
    is_public  BOOLEAN                     NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE repo_role
(
    user_id    UUID                        NOT NULL,
    repo_id    UUID                        NOT NULL,
    role       TEXT                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    PRIMARY KEY (user_id, repo_id)
);