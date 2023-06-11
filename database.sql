--create database and table
CREATE DATABASE `development` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `game` (
  `id` bigint(20) NOT NULL PRIMARY KEY,
  `displayname` varchar(255) NOT NULL,
  `gameid` varchar(255) NOT NULL UNIQUE KEY
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

INSERT INTO `development`.`game`(`id`,`displayname`,`gameid`)VALUES(1,'Ninja Warriot Pro Deluxe Plus','QQtdv4owdT');
INSERT INTO `development`.`game`(`id`,`displayname`,`gameid`)VALUES(2,'Call of Duty','QQqqqqwwww');
INSERT INTO `development`.`game`(`id`,`displayname`,`gameid`)VALUES(3,'Crysis','wwwQQQSSS');


INSERT INTO `development`.`achievement`(`id`,`achievementid`,`game_id`, `displayname`,`description`, `icon`, `displayorder`, `created`,`updated`)
VALUES(1,'21',1,'High_Flyer', 'Win 49 games by only using your sword.','www.gom.com',1, '2008-7-04','2008-7-04');

INSERT INTO `development`.`achievement`(`id`,`achievementid`,`game_id`, `displayname`,`description`, `icon`, `displayorder`, `created`,`updated`)
VALUES(2,'gfds',1,'Causal_Gamer', 'Win 1000 games. You need to defeat a giant cookie.','www.gom.com',2, '2008-7-04','2008-7-04');

INSERT INTO `development`.`achievement`(`id`,`achievementid`,`game_id`, `displayname`,`description`, `icon`, `displayorder`, `created`,`updated`)
VALUES(3,'125yhy',1,'Raze_the_Roof', 'I dont think robots will ever be able to automate achievement names, which is kind of weird because robots are able to automate a lot of other things that seem much more difficult.','www.gom.com',3, '2008-7-04','2008-7-04');

commit;

--creating user for mysql 8
--log in with
CREATE USER 'instana'@'localhost' IDENTIFIED BY 'MyNewPass4!';
GRANT ALL PRIVILEGES ON * . * TO 'instana'@'localhost';
FLUSH PRIVILEGES;

ALTER USER 'instana'@'localhost' IDENTIFIED WITH mysql_native_password BY 'MyNewPass4';
FLUSH PRIVILEGES;

GRANT PROCESS ON *.* TO 'instana'@'localhost';
GRANT SELECT ON PERFORMANCE_SCHEMA.* TO 'instana'@'localhost';
GRANT REPLICATION CLIENT ON *.* TO 'instana'@'localhost';
GRANT SELECT ON performance_schema.events_waits_summary_global_by_event_name TO 'instana'@'localhost';
GRANT SELECT ON performance_schema.events_statements_summary_by_digest TO 'instana'@'localhost';
GRANT SELECT ON performance_schema.events_statements_summary_global_by_event_name TO 'instana'@'localhost';
GRANT SELECT ON performance_schema.replication_connection_status TO 'instana'@'localhost';
FLUSH PRIVILEGES;






