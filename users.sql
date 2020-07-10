use Reservation;

drop table if exists users;
drop table if exists blacklist;
drop table if exists challenges;

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
	fromUser varchar(60) ,
    toUser varchar(60),
    meeting_time int,
    computerID int
);