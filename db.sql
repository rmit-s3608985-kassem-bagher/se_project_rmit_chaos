create table supermarket.customer
(
	id int not null auto_increment
		primary key,
	full_name varchar(200) null,
	balance decimal default '0' null,
	points int default '0' null,
	username varchar(50) null,
	password varchar(256) null,
	test int null,
	constraint customer_id_uindex
		unique (id),
	constraint customer_username_uindex
		unique (username)
)
;

create table supermarket.employee
(
	id int not null auto_increment
		primary key,
	constraint employee_id_uindex
		unique (id)
)
;

