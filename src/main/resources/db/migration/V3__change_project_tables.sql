create table delivery_orders
(
    id               binary(16)   not null,
    delivery_address varchar(255),
    order_date_time  datetime(6)  not null,
    status           varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table takeout_orders
(
    id               binary(16)   not null,
    order_date_time  datetime(6)  not null,
    status           varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table eat_in_orders
(
    id               binary(16)   not null,
    delivery_address varchar(255),
    order_date_time  datetime(6)  not null,
    status           varchar(255) not null,
    order_table_id   binary(16),
    primary key (id)
) engine = InnoDB;

ALTER TABLE order_line_item
ADD COLUMN delivery_order_id binary(16);

ALTER TABLE order_line_item
ADD COLUMN takeout_order_id binary(16);

ALTER TABLE order_line_item
ADD COLUMN eat_in_order_id binary(16);

alter table order_line_item
    add constraint fk_order_line_item_to_delivery_orders
        foreign key (delivery_order_id)
            references delivery_orders (id);

alter table order_line_item
    add constraint fk_order_line_item_to_takeout_orders
        foreign key (takeout_order_id)
            references takeout_orders (id);

alter table order_line_item
    add constraint fk_order_line_item_to_eat_in_orders
        foreign key (eat_in_order_id)
            references eat_in_orders (id);

alter table eat_in_orders
    add constraint fk_eat_in_orders_to_order_table
        foreign key (order_table_id)
            references order_table (id);
