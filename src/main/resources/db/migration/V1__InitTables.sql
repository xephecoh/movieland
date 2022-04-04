CREATE TABLE test (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    test_date DATE NOT NULL,
    unique (name)
);
