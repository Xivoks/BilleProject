CREATE TABLE USER
(
    `id`       BIGINT(20) NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL,
    `email`    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)


) engine = InnoDB;