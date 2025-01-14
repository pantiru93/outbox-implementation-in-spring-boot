CREATE TABLE outbox (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    event_type VARCHAR(255) NOT NULL,
    payload NVARCHAR(MAX) NOT NULL,
    partition_key VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'NOT_STARTED',
    created_at DATETIME2 DEFAULT GETDATE(),
    processed_at DATETIME2
);

CREATE TABLE shedlock (
    name VARCHAR(64) NOT NULL,
    lock_until DATETIME2 NOT NULL,
    locked_at DATETIME2 NOT NULL,
    locked_by VARCHAR(255) NOT NULL,
    PRIMARY KEY (name)
); 