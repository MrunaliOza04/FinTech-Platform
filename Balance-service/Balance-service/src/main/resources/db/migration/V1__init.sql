CREATE TABLE t_balance
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    account_number VARCHAR(255),
    balance DECIMAL(19,2),
    PRIMARY KEY(id)
);