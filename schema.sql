DROP DATABASE IF EXISTS city_complaint_system;

CREATE DATABASE IF NOT EXISTS city_complaint_system;

USE city_complaint_system;

DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS user
(
    id       INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(256) UNIQUE                    NOT NULL,
    password VARCHAR(64)                            NOT NULL,
    type     enum ('customer', 'admin', 'employee') NOT NULL
);


DROP TABLE IF EXISTS customer;

CREATE TABLE IF NOT EXISTS customer
(
    user_id    INT UNSIGNED PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    phone      VARCHAR(16)  NOT NULL,
    address    VARCHAR(128) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS employee;

CREATE TABLE IF NOT EXISTS employee
(
    user_id    INT UNSIGNED PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    phone      VARCHAR(16)  NOT NULL,
    address    VARCHAR(128) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS admin;

CREATE TABLE IF NOT EXISTS admin
(
    user_id    INT UNSIGNED PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    phone      VARCHAR(16)  NOT NULL,
    address    VARCHAR(128) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS complaint;

CREATE TABLE IF NOT EXISTS complaint
(
    id          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    customer_id INT UNSIGNED NOT NULL,
    employee_id INT UNSIGNED,
    title       VARCHAR(100) NOT NULL,
    description TEXT         NOT NULL,
    date        DATETIME     NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employee (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS reply;

CREATE TABLE IF NOT EXISTS reply
(
    id          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    complain_id INT UNSIGNED NOT NULL,
    user_id     INT UNSIGNED NOT NULL,
    description TEXT         NOT NULL,
    date        DATETIME     NOT NULL,
    FOREIGN KEY (complain_id) REFERENCES complaint (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE
);