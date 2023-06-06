CREATE TABLE product
(
    `id`    BIGINT(20) NOT NULL AUTO_INCREMENT,
    `name`  VARCHAR(255) NOT NULL,
    `price` DOUBLE       NOT NULL,
    PRIMARY KEY (id)


) engine = InnoDB;