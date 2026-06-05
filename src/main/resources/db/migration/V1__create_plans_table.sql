CREATE TABLE IF NOT EXISTS plans (
    id SERIAL NOT NULL,
    title VARCHAR(256),
    event_type VARCHAR(256),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    description VARCHAR(256),
    PRIMARY KEY (id)
);