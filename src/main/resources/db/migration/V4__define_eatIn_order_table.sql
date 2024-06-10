CREATE TABLE eat_in_orders
(
    id              binary(16) primary key,
    status          varchar(255) not null,
    order_date_time datetime(6)  not null,
    order_table_id  binary(16)
);

CREATE TABLE eat_in_order_line_items
(
    seq             bigint auto_increment primary key,
    eat_in_order_id binary(16)     not null,
    quantity        bigint         not null,
    menu_id         binary(16)     not null,
    price           decimal(19, 2) not null
)
