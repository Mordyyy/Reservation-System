use Reservation;

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