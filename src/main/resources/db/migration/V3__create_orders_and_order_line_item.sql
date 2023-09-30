create table delivery_order_line_item
(
    seq               bigint     not null auto_increment,
    quantity          bigint     not null,
    menu_id           binary(16) not null,
    delivery_order_id binary(16) not null,
    primary key (seq)
) engine = InnoDB;

create table eat_in_order_line_item
(
    seq             bigint     not null auto_increment,
    quantity        bigint     not null,
    menu_id         binary(16) not null,
    eat_in_order_id binary(16) not null,
    primary key (seq)
) engine = InnoDB;

create table take_out_order_line_item
(
    seq               bigint     not null auto_increment,
    quantity          bigint     not null,
    menu_id           binary(16) not null,
    take_out_order_id binary(16) not null,
    primary key (seq)
) engine = InnoDB;


create table eat_in_orders
(
    eat_in_order_id binary(16)   not null,
    order_date_time datetime(6)  not null,
    status          varchar(255) not null,
    type            varchar(255) not null,
    order_table_id  binary(16),
    primary key (eat_in_order_id)
) engine = InnoDB;

create table delivery_orders
(
    delivery_order_id binary(16)   not null,
    delivery_address  varchar(255),
    order_date_time   datetime(6)  not null,
    status            varchar(255) not null,
    type              varchar(255) not null,
    order_table_id    binary(16),
    primary key (delivery_order_id)
) engine = InnoDB;

create table take_out_orders
(
    take_out_order_id binary(16)   not null,
    delivery_address  varchar(255),
    order_date_time   datetime(6)  not null,
    status            varchar(255) not null,
    type              varchar(255) not null,
    order_table_id    binary(16),
    primary key (take_out_order_id)
) engine = InnoDB;
