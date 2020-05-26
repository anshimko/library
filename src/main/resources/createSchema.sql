CREATE SCHEMA IF NOT EXISTS `library`;

USE `library` ;

CREATE TABLE IF NOT EXISTS `library`.`authors` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `library`.`books` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE)
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `library`.`books_authors` (
  `authors_id` INT(11) NOT NULL,
  `books_id` INT(11) NOT NULL,
  PRIMARY KEY (`authors_id`, `books_id`),
  INDEX `authors_id` (`authors_id` ASC) VISIBLE,
  INDEX `books1_id` (`books_id` ASC) VISIBLE,
  CONSTRAINT `fk_authors`
    FOREIGN KEY (`authors_id`)
    REFERENCES `library`.`authors` (`id`),
  CONSTRAINT `fk_books1`
    FOREIGN KEY (`books_id`)
    REFERENCES `library`.`books` (`id`))

DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `library`.`catalogs` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `parent_id` INT(11) NULL DEFAULT NULL,
  `name` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `catalogs_par_id` (`parent_id` ASC) INVISIBLE,
  CONSTRAINT `fk_catalogs_par`
    FOREIGN KEY (`parent_id`)
    REFERENCES `library`.`catalogs` (`id`))

DEFAULT CHARACTER SET = utf8;



CREATE TABLE IF NOT EXISTS `library`.`books_catalogs` (
  `catalogs_id` INT(11) NOT NULL,
  `books_id` INT(11) NOT NULL,
  PRIMARY KEY (`catalogs_id`, `books_id`),
  INDEX `catalogs_id` (`catalogs_id` ASC) VISIBLE,
  INDEX `books1_id` (`books_id` ASC) VISIBLE,
  CONSTRAINT `fk_books2`
    FOREIGN KEY (`books_id`)
    REFERENCES `library`.`books` (`id`),
  CONSTRAINT `fk_catalogs`
    FOREIGN KEY (`catalogs_id`)
    REFERENCES `library`.`catalogs` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `library`.`user_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)

DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `library`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `user_role_id` INT(11) NOT NULL,
  `user_role_id1` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  INDEX `user_role_id` (`user_role_id1` ASC) VISIBLE,
  CONSTRAINT `fk_users_user_role1`
    FOREIGN KEY (`user_role_id1`)
    REFERENCES `library`.`user_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)

DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `library`.`rent_history` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `books_id` INT(11) NOT NULL,
  `users_id` INT(11) NOT NULL,
  `borrow_date` DATE NOT NULL,
  `return_date` DATE NOT NULL,
  `is_returned` BIT(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  INDEX `books_id` (`books_id` ASC) INVISIBLE,
  INDEX `fk_rent_history_users1_idx` (`users_id` ASC) VISIBLE,
  CONSTRAINT `fk_books`
    FOREIGN KEY (`books_id`)
    REFERENCES `library`.`books` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_rent_history_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `library`.`users` (`id`))

DEFAULT CHARACTER SET = utf8;


CREATE TABLE IF NOT EXISTS `library`.`users_info` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(254) NOT NULL,
  `users_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `users_id` (`users_id` ASC) INVISIBLE,
  CONSTRAINT `fk_users`
    FOREIGN KEY (`users_id`)
    REFERENCES `library`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)

DEFAULT CHARACTER SET = utf8;

