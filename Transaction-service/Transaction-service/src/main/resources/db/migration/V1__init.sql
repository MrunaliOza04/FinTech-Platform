CREATE TABLE t_transactions
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    transaction_number VARCHAR(255),
    from_account VARCHAR(255),
    to_account VARCHAR(255),
    amount DECIMAL(19,2),
    transaction_type VARCHAR(50),
    PRIMARY KEY(id)
);