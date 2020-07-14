use Reservation;

drop table if exists users;
drop table if exists blacklist;
drop table if exists challenges;
drop table if exists images;
drop table if exists time_table;
drop table if exists reservations;

create table users (
	username varchar(60) primary key,
    password varchar(60),
    mail varchar(60) unique,
    avatar varchar(60),
    cancelledOrders int
);

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

create table time_table(
    id int primary key,
    meeting_time int,
    computerID int,
    text varchar(60),
    color varchar(60)
);

create table reservations(
	username varchar(60),
	time int,
	computerID int
);