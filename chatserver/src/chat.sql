create database chat default character set 'ut8';
use chat;
show tables;
create table users(
	id int primary key auto_increment,
	uname varchar(20) not null,
	pwd varchar(30) not null,
	sex enum ('男','女')
);
select * from users;
delete from users where ;
insert into users value(100,'zhang','123','男');
insert into users value(null,'王丽','111','女');

drop table message;
create table message(
	sendId int not null,
	receiveId int not null,
	context varchar(300),
	sendTime datetime,
	primary key(sendId,receiveId)
);

intsert into message value(12,23,'123','2015-2-2');