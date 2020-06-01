-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema library
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema library
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `library` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `library` ;

-- -----------------------------------------------------
-- Table `library`.`authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`authors` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `library`.`books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`books` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `library`.`books_authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`books_authors` (
  `authors_id` INT(11) NOT NULL,
  `books_id` INT(11) NOT NULL,
  INDEX `authors_id` (`authors_id` ASC) VISIBLE,
  INDEX `books1_id` (`books_id` ASC) VISIBLE,
  CONSTRAINT `fk_authors`
    FOREIGN KEY (`authors_id`)
    REFERENCES `library`.`authors` (`id`),
  CONSTRAINT `fk_books1`
    FOREIGN KEY (`books_id`)
    REFERENCES `library`.`books` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `library`.`catalogs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`catalogs` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `parent_id` INT(11) NULL DEFAULT NULL,
  `name` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `catalogs_par_id` (`parent_id` ASC) INVISIBLE,
  CONSTRAINT `fk_catalogs_par`
    FOREIGN KEY (`parent_id`)
    REFERENCES `library`.`catalogs` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `library`.`books_catalogs`
-- -----------------------------------------------------
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


-- -----------------------------------------------------
-- Table `library`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`user_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `library`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `user_role_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  INDEX `user_role_id` (`user_role_id` ASC) INVISIBLE,
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`user_role_id`)
    REFERENCES `library`.`user_role` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `library`.`rent_history`
-- -----------------------------------------------------
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
    REFERENCES `library`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `library`.`users_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library`.`users_info` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(254) NOT NULL,
  `users_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_users_info_users1_idx` (`users_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_info_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `library`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
