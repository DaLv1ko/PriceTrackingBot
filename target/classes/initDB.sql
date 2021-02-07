DROP TABLE IF EXISTS links;
DROP TABLE IF EXISTS users;
CREATE SEQUENCE global_seq START WITH 1 INCREMENT 1;

CREATE TABLE users
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    chat_id    BIGINT UNIQUE                NOT NULL,
    bot_state  VARCHAR
);

CREATE TABLE links
(
    id             INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id        INTEGER NOT NULL,
    link           VARCHAR,
    price          INTEGER

);