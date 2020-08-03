use Reservation;

drop table if exists users;
drop table if exists blacklist;
drop table if exists challenges;
drop table if exists images;
drop table if exists time_table;
drop table if exists reservations;
drop table if exists last_reset;

create table users (
	username varchar(60) primary key,
    password varchar(60),
    mail varchar(60) unique,
    avatar varchar(60)
);
insert into users values ('Bot1', 'fbef64b2c3fc970f7f87bc69f5fa2a968657e569', 
			'example@freeuni.edu.ge', 'pic.jpg');
insert into users values ('Bot2', 'fbef64b2c3fc970f7f87bc69f5fa2a968657e569', 
			'example2@freeuni.edu.ge', 'pic.jpg');

create table blacklist(
	username varchar(60) primary key
);

create table challenges(
	id int unique auto_increment,
	fromUser varchar(60) ,
    toUser varchar(60),
    meeting_time int,
    computerID int
);

create table images(
	url varchar(60) primary key,
    name varchar(60)
);

insert into images values("pic.jpg", "FreeUni Logo");
insert into images values("1.png", "Uturi");
insert into images values("2.png", "Dreaded Girl");
insert into images values("3.png", "Panthera");
insert into images values("4.png", "Cruella");
insert into images values("5.png", "Beard Man");
insert into images values("6.png", "Mr. Banana");
insert into images values("7.png", "Medusa");
insert into images values("8.png", "Moss");

create table time_table(
    id int primary key,
    meeting_time int,
    computerID int,
    text varchar(60),
    color varchar(60)
);

create table orders(
	username varchar(60) primary key,
    orders_num int,
    bonus_num int 
);

create table reservations(
	username varchar(60),
	time int,
	computerID int
);

create table last_reset(
	date varchar(60)
);

insert into last_reset values("0000-00-00 00:00:00");