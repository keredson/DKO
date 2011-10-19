CREATE TABLE IF NOT EXISTS `mysite`.`blog` (
  `id` INTEGER  NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256),
  `tagline` VARCHAR(4096),
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mysite`.`author` (
  `id` INTEGER  NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256),
  `email` VARCHAR(4096),
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mysite`.`entry` (
  `id` INTEGER  NOT NULL AUTO_INCREMENT,
  `blog_id` INTEGER  NOT NULL,
  `headline` VARCHAR(4096),
  `body_text` LONGTEXT,
  `publish_date` TIMESTAMP,
  `modify_date` TIMESTAMP,
  `rating` INTEGER,
  PRIMARY KEY (`id`),
  CONSTRAINT `entry_blog_fk_constraint` FOREIGN KEY `entry_blog_fk_constraint` (`blog_id`)
    REFERENCES `blog` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `mysite`.`entry_author` (
  `author_id` INTEGER  NOT NULL,
  `entry_id` INTEGER  NOT NULL,
  CONSTRAINT `entry_author_entry_fk_constraint` FOREIGN KEY `entry_author_entry_fk_constraint` (`entry_id`)
    REFERENCES `entry` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `entry_author_author_fk_constraint` FOREIGN KEY `entry_author_author_fk_constraint` (`author_id`)
    REFERENCES `author` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

