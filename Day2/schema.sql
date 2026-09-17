CREATE DATABASE IF NOT EXISTS demo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE demo;

CREATE TABLE IF NOT EXISTS studentCard (
    studentId VARCHAR(10) PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    money DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    status VARCHAR(16) NOT NULL DEFAULT 'ACTIVE'
);

-- If you already had the old table without a status column, run this once instead:
-- ALTER TABLE studentCard ADD COLUMN status VARCHAR(16) NOT NULL DEFAULT 'ACTIVE';
