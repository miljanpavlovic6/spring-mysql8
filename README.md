# Deployment on Linux/CENTOS 7
```
sudo su 
yum install -y git 
yum install -y java-1.8.0-openjdk
java -version
yum install -y wget
git clone https://github.com/miljanpavlovic6/spring-mysql8.git
cd spring-mysql8
chmod 777 mysql80-community-release-el7-7.noarch.rpm app-0.0.1.jar
```
## Mysql8 installation
```
sudo yum install -y mysql80-community-release-el7-7.noarch.rpm
sudo yum install -y mysql-community-server
systemctl start mysqld
systemctl status mysqld
sudo grep 'temporary password' /var/log/mysqld.log
mysql -uroot -p
ALTER USER 'root'@'%' IDENTIFIED BY 'MyNewPass4!';
```
### Create instana user and add privileges from [instana documentation](https://www.ibm.com/docs/en/instana-observability/current?topic=technologies-monitoring-mysql#required-db-permissions).
```
CREATE USER 'instana'@'%' IDENTIFIED BY 'MyNewPass4!';
GRANT ALL PRIVILEGES ON * . * TO 'instana'@'%';
FLUSH PRIVILEGES;
ALTER USER 'instana'@'%' IDENTIFIED WITH mysql_native_password BY 'MyNewPass4!';
FLUSH PRIVILEGES;
GRANT PROCESS ON *.* TO 'instana'@'%';
GRANT SELECT ON PERFORMANCE_SCHEMA.* TO 'instana'@'%';
GRANT REPLICATION CLIENT ON *.* TO 'instana'@'%';
GRANT SELECT ON performance_schema.events_waits_summary_global_by_event_name TO 'instana'@'%';
GRANT SELECT ON performance_schema.events_statements_summary_by_digest TO 'instana'@'%';
GRANT SELECT ON performance_schema.events_statements_summary_global_by_event_name TO 'instana'@'%';
GRANT SELECT ON performance_schema.replication_connection_status TO 'instana'@'%';
FLUSH PRIVILEGES;

CREATE USER 'root'@'%' IDENTIFIED BY 'MyNewPass4!';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

quit;
service mysqld restart
```
### Insert data
```
mysql -uroot -p;
```
> Password:
```
MyNewPass4!
```
```
CREATE DATABASE `development`;
USE `development`;

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
```
## Springboot app
> Modify the SERVICE_NAME, PATH_TO_JAR, and choose a PID_PATH_NAME for the file you are going to use to store your service ID.
```
sudo vi /etc/init.d/springapp
```
> And put below code
```
#!/bin/sh
SERVICE_NAME=springapp
PATH_TO_JAR=/home/miljan_pavlovic/spring-mysql8/app-0.0.1.jar
PID_PATH_NAME=/tmp/springapp-pid
case $1 in
    start)
        echo "Starting $SERVICE_NAME ..."
        if [ ! -f $PID_PATH_NAME ]; then
            nohup java -jar $PATH_TO_JAR /tmp 2>> /dev/null >> /dev/null &
                        echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is already running ..."
        fi
    ;;
    stop)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stoping ..."
            kill $PID;
            echo "$SERVICE_NAME stopped ..."
            rm $PID_PATH_NAME
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
    restart)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stopping ...";
            kill $PID;
            echo "$SERVICE_NAME stopped ...";
            rm $PID_PATH_NAME
            echo "$SERVICE_NAME starting ..."
            nohup java -jar $PATH_TO_JAR /tmp 2>> /dev/null >> /dev/null &
                        echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
esac
```
```
chmod +x /etc/init.d/springapp
service springapp start
```
## [Install instana agent](https://www.ibm.com/docs/en/instana-observability/current?topic=agents-installing-host-agent-linux#installing-the-host-agent-on-linux)
```
mkdir /opt/instana/agent/deploy
cd /opt/instana/agent/deploy
wget https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.20/mysql-connector-java-8.0.20.jar
chmod 777 mysql-connector-java-8.0.20.jar
```
> Edit configuration.yaml
```
vi /opt/instana/agent/etc/instana/configuration.yaml
```
```
789 # Mysql
790 com.instana.plugin.mysql:
791   user: 'instana'
792   password: 'MyNewPass4!'
```

## Add host ip into C:\Windows\System32\drivers\etc\hosts like this below
```
104.197.178.237 mip-mysql8
```
### In the address bar put http://104.197.178.237:8082/list/111
```
[{"achievementId":"666","displayName":"High_Flyer","description":"Win 49 games by only using your sword.","icon":"www.gom.com","displayOrder":1,"created":"2008-07-04T00:00:00.000+0000","updated":"2008-07-04T00:00:00.000+0000"}]
```
