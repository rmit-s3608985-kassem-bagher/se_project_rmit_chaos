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
	constraint customer_username_uindex
		unique (username)
)
;

create table supermarket.discount
(
	quantity int not null,
	product int not null,
	percentage int null,
	primary key (quantity, product)
)
;

create index discount_product_id_fk
	on discount (product)
;

create table supermarket.employee
(
	id int not null auto_increment
		primary key,
	name varchar(200) null,
	password varchar(256) null,
	username varchar(50) null,
	role int null,
	constraint employee_username_uindex
		unique (username)
)
;

create table supermarket.`order`
(
	id int not null auto_increment
		primary key,
	customer int null,
	date mediumtext null,
	subtotal decimal default '0' null,
	total decimal default '0' null,
	discount decimal default '0' null,
	status varchar(20) null
)
;

comment on column `order`.status is 'pending
placed
canceled'
;

create table supermarket.order_item
(
	`order` int null,
	product int null,
	quantity int null,
	total int null,
	constraint order_item_order_id_fk
		foreign key (order) references supermarket.`order` (id)
)
;

create index order_item_order_id_fk
	on order_item (`order`)
;

create table supermarket.product
(
	id int not null auto_increment
		primary key,
	name varchar(50) null,
	unit_price decimal default '0' null,
	stock_level int null,
	replenish_level int null,
	type varchar(3) default 'pcs' null,
	supplier int null
)
;

create index product_supplier_id_fk
	on product (supplier)
;

comment on column product.type is 'pcs
kg'
;

alter table discount
	add constraint discount_product_id_fk
		foreign key (product) references supermarket.product (id)
;

create table supermarket.purchase_order
(
	id int not null auto_increment
		primary key,
	date mediumtext null,
	employee int null,
	supplier int null,
	constraint purchase_order_employee_id_fk
		foreign key (employee) references supermarket.employee (id)
)
;

create index purchase_order_employee_id_fk
	on purchase_order (employee)
;

create index purchase_order_supplier_id_fk
	on purchase_order (supplier)
;

create table supermarket.purchase_order_item
(
	purchase_order int null,
	product int null,
	quantity int null,
	total decimal default '0' null
)
;

create table supermarket.supplier
(
	id int not null auto_increment
		primary key,
	name varchar(100) null,
	address varchar(200) null,
	postcode int null,
	phone varchar(15) null
)
;

alter table product
	add constraint product_supplier_id_fk
		foreign key (supplier) references supermarket.supplier (id)
;

alter table purchase_order
	add constraint purchase_order_supplier_id_fk
		foreign key (supplier) references supermarket.supplier (id)
;

