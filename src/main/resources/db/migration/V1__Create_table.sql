CREATE TABLE customer (
    id         VARCHAR(50) NOT NULL PRIMARY KEY,
    first_name            VARCHAR(50),
    last_name            VARCHAR(50)
);

CREATE TABLE address (
    id         VARCHAR(50) NOT NULL PRIMARY KEY,
    city            VARCHAR(50)
);

CREATE TABLE customer_address (
    customer_id         VARCHAR(50) NOT NULL,
    address_id         VARCHAR(50) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (address_id) REFERENCES address (id),
    UNIQUE INDEX customer_address_idx_1 (customer_id, address_id)
);