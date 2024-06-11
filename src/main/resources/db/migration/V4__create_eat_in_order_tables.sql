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