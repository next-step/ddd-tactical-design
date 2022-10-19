create table eat_in_order_line_item
(
    seq      bigint     not null auto_increment,
    quantity bigint     not null,
    menu_id  binary(16) not null,
    eat_in_order_id binary(16) not null,
    primary key (seq)
) engine = InnoDB;

create table eat_in_order_table
(
    id               binary(16)   not null,
    occupied         bit          not null,
    name             varchar(255) not null,
    number_of_guests integer      not null,
    primary key (id)
) engine = InnoDB;

create table eat_in_order
(
    id               binary(16)   not null,
    eat_in_order_date_time  datetime(6)  not null,
    status           varchar(255) not null,
    eat_in_order_table_id   binary(16) not null,
    primary key (id)
) engine = InnoDB;

alter table eat_in_order_line_item
    add constraint fk_eat_in_order_line_item_to_menu
        foreign key (menu_id)
            references menu (id);

alter table eat_in_order_line_item
    add constraint fk_eat_in_order_line_item_to_eat_in_order
        foreign key (eat_in_order_id)
            references eat_in_order (id);

alter table eat_in_order
    add constraint fk_eat_in_order_to_eat_in_order_table
        foreign key (eat_in_order_table_id)
            references eat_in_order (id);
