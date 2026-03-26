CREATE TABLE demo_account
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(64)    NOT NULL UNIQUE,
    balance  DECIMAL(10, 2) NOT NULL DEFAULT 0
);

INSERT INTO demo_account (username, balance)
VALUES ('alice', 1000.00),
       ('bob', 1000.00);
