CREATE TABLE IF NOT EXISTS items (
id SERIAL PRIMARY KEY,
description TEXT,
created TIMESTAMP,
done BOOLEAN,
user_id INT references users (id)
    );

CREATE TABLE IF NOT EXISTS users (
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    TEXT UNIQUE,
    password TEXT
);
