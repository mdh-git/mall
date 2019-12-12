# mysql主从复制

## 1.docker中创建Master实例

~~~
docker run -p 3307:3306 --name mysql-master 
-v /mydata/mysql/master/log:/var/log/mysql 
-v /mydata/mysql/master/data:/var/lib/mysql 
-v /mydata/mysql/master/conf:/etc/mysql 
-e MYSQL_ROOT_PASSWORD=root 
-d mysql:5.7

参数说明
	-p 3307:3306：将容器的3306端口映射到主机的3307端口
	-v /mydata/mysql/master/conf:/etc/mysql：将配置文件夹挂在到主机
	-v /mydata/mysql/master/log:/var/log/mysql：将日志文件夹挂载到主机
	-v /mydata/mysql/master/data:/var/lib/mysql/：将配置文件夹挂载到主机
	-e MYSQL_ROOT_PASSWORD=root：初始化root用户的密码

修改master基本配置
vim /mydata/mysql/master/conf/my.cnf

[client]
default-character-set=utf8
 
[mysql]
default-character-set=utf8
 
[mysqld]
init_connect='SET collation_connection = utf8_unicode_ci'
init_connect='SET NAMES utf8'
character-set-server=utf8
collation-server=utf8_unicode_ci
skip-character-set-client-handshake
skip-name-resolve
注意：skip-name-resolve一定要加，不然连接mysql会超级慢

添加master主从复制部分配置
开启binlog
server_id=1	
log-bin=mysql-bin
read-only=0
binlog-do-db=gmall_ums
binlog-do-db=gmall_pms
binlog-do-db=gmall_oms
binlog-do-db=gmall_sms
binlog-do-db=gmall_cms


replicate-ignore-db=mysql
replicate-ignore-db=sys
replicate-ignore-db=information_schema
replicate-ignore-db=performance_schema
重启master
~~~

## 2.创建Slave实例

~~~
docker run -p 3316:3306 --name mysql-slaver-01 \
-v /mydata/mysql/slaver/log:/var/log/mysql \
-v /mydata/mysql/slaver/data:/var/lib/mysql \
-v /mydata/mysql/slaver/conf:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=root \
-d mysql:5.7 

修改slave基本配置
vim /mydata/mysql/slaver/conf/my.cnf

[client]
default-character-set=utf8
 
[mysql]
default-character-set=utf8
 
[mysqld]
init_connect='SET collation_connection = utf8_unicode_ci'
init_connect='SET NAMES utf8'
character-set-server=utf8
collation-server=utf8_unicode_ci
skip-character-set-client-handshake
skip-name-resolve 
添加master主从复制部分配置
server_id=2
log-bin=mysql-bin
read-only=1
binlog-do-db=gmall_ums
binlog-do-db=gmall_pms
binlog-do-db=gmall_oms
binlog-do-db=gmall_sms
binlog-do-db=gmall_cms


replicate-ignore-db=mysql
replicate-ignore-db=sys
replicate-ignore-db=information_schema
replicate-ignore-db=performance_schema
重启slaver

~~~

## 3.授权同步

~~~
1、进入master容器
docker exec -it mysql /bin/bash

2、进入mysql内部 （mysql –uroot -p）

   1）、授权root可以远程访问（ 主从无关，为了方便我们远程连接mysql）
grant all privileges on *.* to 'root'@'%' identified by 'root' with grant option;
flush privileges;
   2）、添加用来同步的用户
       GRANT REPLICATION SLAVE ON *.* to 'backup'@'%' identified by '123456';
3、查看master状态
   show master status\G;

~~~

## 4.配置slaver同步master数据

~~~
1、进入slaver容器
docker exec -it mysql-slaver-01 /bin/bash
2、进入mysql内部（mysql –uroot -p）
   1）、授权root可以远程访问（ 主从无关，为了方便我们远程连接mysql）
grant all privileges on *.* to 'root'@'%' identified by 'root' with grant option;
flush privileges;

   2）、设置主库连接
change master to master_host='192.168.159.128',master_user='backup',master_password='123456',master_log_file='mysql-bin.000001',master_log_pos=0,master_port=3307;
  3）、启动从库同步
start slave;
  4）、查看从库状态
     show slave status\G;
~~~



~~~
1）主从数据库在自己配置文件中声明需要同步哪个数据库，忽略哪个数据库等信息。并且server-id不能一样
2）主库授权某个账号密码来同步自己的数据
3）从库使用这个账号密码连接主库来同步数据

~~~



# 主从复制代码实现

~~~
使用 Sharding-JDBC
网址 https://shardingsphere.apache.org/document/current/cn/quick-start/sharding-jdbc-quick-start/

暂未实现,暂时用druid作为数据源
~~~

