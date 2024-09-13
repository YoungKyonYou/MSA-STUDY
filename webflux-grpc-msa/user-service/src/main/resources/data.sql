CREATE TABLE `users` (
                         `id` BIGINT NOT NULL AUTO_INCREMENT,
                         `email` VARCHAR(50) NOT NULL UNIQUE,
                         `name` VARCHAR(50) NOT NULL,
                         `user_id` VARCHAR(255) NOT NULL UNIQUE,
                         `encrypted_pwd` VARCHAR(255) NOT NULL UNIQUE,
                         PRIMARY KEY (`id`)
);