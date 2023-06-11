
CREATE DATABASE `development`;
USE `development`;

CREATE TABLE `game` (
  `id` bigint(20) NOT NULL PRIMARY KEY,
  `displayname` varchar(255) NOT NULL,
  `gameid` varchar(255) NOT NULL UNIQUE KEY
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
commit;

CREATE TABLE `achievement` (
  `id` bigint(20) NOT NULL PRIMARY KEY,
  `achievementid` varchar(255) NOT NULL UNIQUE KEY,
  `game_id` bigint(20) NOT NULL,
  `displayname` varchar(100) NOT NULL,
  `description` varchar(500) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `displayorder` int(11) DEFAULT NULL,
  `created` datetime(6) NOT NULL,
  `updated` datetime(6) NOT NULL,
  CONSTRAINT FOREIGN KEY (`game_id`) REFERENCES `game` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
commit;
INSERT INTO `development`.`game`(`id`,`displayname`,`gameid`)VALUES(1,'Ninja Warriot Pro Deluxe Plus','111');
INSERT INTO `development`.`game`(`id`,`displayname`,`gameid`)VALUES(2,'Call of Duty','222');
INSERT INTO `development`.`game`(`id`,`displayname`,`gameid`)VALUES(3,'Crysis','333');
commit;
INSERT INTO `development`.`achievement`(`id`,`achievementid`,`game_id`, `displayname`,`description`, `icon`, `displayorder`, `created`,`updated`)
VALUES(1,'666',1,'High_Flyer', 'Win 49 games by only using your sword.','www.gom.com',1, '2008-7-04','2008-7-04');
INSERT INTO `development`.`achievement`(`id`,`achievementid`,`game_id`, `displayname`,`description`, `icon`, `displayorder`, `created`,`updated`)
VALUES(2,'555',2,'Causal_Gamer', 'Win 1000 games. You need to defeat a giant cookie.','www.gom.com',2, '2008-7-04','2008-7-04');
INSERT INTO `development`.`achievement`(`id`,`achievementid`,`game_id`, `displayname`,`description`, `icon`, `displayorder`, `created`,`updated`)
VALUES(3,'444',3,'Raze_the_Roof', 'I dont think robots will ever be able to automate achievement names, which is kind of weird because robots are able to automate a lot of other things that seem much more difficult.','www.gom.com',3, '2008-7-04','2008-7-04');
commit;







