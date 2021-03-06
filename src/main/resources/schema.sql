DROP TABLE IF EXISTS Enroll;

CREATE TABLE Enroll (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP,
    price DECIMAL,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL
);

DROP TABLE IF EXISTS User;

CREATE TABLE User (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL
);

DROP TABLE IF EXISTS Course;

CREATE TABLE Course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(500)
);

ALTER TABLE Enroll ADD CONSTRAINT fk_id_user
FOREIGN KEY(user_id) REFERENCES User (id);

ALTER TABLE Enroll ADD CONSTRAINT fk_id_course
FOREIGN KEY(course_id) REFERENCES Course (id);
