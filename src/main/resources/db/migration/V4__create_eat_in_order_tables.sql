create table eat_in_order
(
    id               binary(16) not null,
    status           varchar(255) not null,
    order_date_time  datetime(6) not null,
    order_table_id   binary(16),
    primary key (id),
    constraint fk_eat_in_order_to_order_table
        foreign key (order_table_id)
            references order_table (id)
) engine = InnoDB;

ALTER TABLE order_line_item
    ADD COLUMN price decimal(19, 2) NOT NULL;

create table eat_in_order_line_item
(
    seq      bigint auto_increment
        primary key,
    quantity bigint         not null,
    menu_id  binary(16)     not null,
    order_id binary(16)     not null,
    price    decimal(19, 2) not null,
    constraint fk_order_line_item_to_menu
        foreign key (menu_id) references menu (id),
    constraint fk_order_line_item_to_orders
        foreign key (order_id) references orders (id)
);

