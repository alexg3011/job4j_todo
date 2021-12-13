CREATE TABLE IF NOT EXISTS mark (
                                     id       SERIAL PRIMARY KEY,
                                     name     TEXT

);

CREATE TABLE IF NOT EXISTS model (
                                    id       SERIAL PRIMARY KEY,
                                    name     TEXT
);

CREATE TABLE IF NOT EXISTS mark_model (
                                     id       SERIAL PRIMARY KEY,
                                     CarMark_id INT REFERENCES mark(id),
                                     cars_id INT REFERENCES model(id)
);