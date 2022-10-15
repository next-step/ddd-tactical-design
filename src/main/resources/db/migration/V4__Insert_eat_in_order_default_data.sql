insert into eat_in_order_table (id, occupied, name, number_of_guests)
values (x'8d71004329b6420e8452233f5a035520', false, '1번', 0);
insert into eat_in_order_table (id, occupied, name, number_of_guests)
values (x'6ab59e8106eb441684e99faabc87c9ca', false, '2번', 0);
insert into eat_in_order_table (id, occupied, name, number_of_guests)
values (x'ae92335ccd264626b7979e4ae8c4efbd', false, '3번', 0);
insert into eat_in_order_table (id, occupied, name, number_of_guests)
values (x'a9858d4b80d0428881f48f41596a23fb', false, '4번', 0);
insert into eat_in_order_table (id, occupied, name, number_of_guests)
values (x'3faec3ab5217405daaa2804f87697f84', false, '5번', 0);
insert into eat_in_order_table (id, occupied, name, number_of_guests)
values (x'815b8395a2ad4e3589dc74c3b2191478', false, '6번', 0);
insert into eat_in_order_table (id, occupied, name, number_of_guests)
values (x'7ce8b3a235454542ab9cb3d493bbd4fb', false, '7번', 0);
insert into eat_in_order_table (id, occupied, name, number_of_guests)
values (x'7bdb1ffde36e4e2b94e3d2c14d391ef3', false, '8번', 0);

insert into eat_in_order (id, delivery_address, eat_in_order_date_time, status, eat_in_order_table_id)
values (x'69d78f383bff457cbb7226319c985fd8', '2021-07-27', 'WAITING', x'6ab59e8106eb441684e99faabc87c9ca');
insert into eat_in_order (id, delivery_address, eat_in_order_date_time, status, eat_in_order_table_id)
values (x'98da3d3859e04dacbbaeebf6560a43bd', '2021-07-27', 'COMPLETED', x'8d71004329b6420e8452233f5a035520');
insert into eat_in_order (id, delivery_address, eat_in_order_date_time, status, eat_in_order_table_id)
values (x'd7cc15b3e32c4bc8b440d3067b35522e', '2021-07-27', 'COMPLETED', x'8d71004329b6420e8452233f5a035520');

insert into eat_in_order_line_item (quantity, menu_id, eat_in_order_id)
values (1, x'f59b1e1cb145440aaa6f6095a0e2d63b', x'69d78f383bff457cbb7226319c985fd8');
insert into eat_in_order_line_item (quantity, menu_id, eat_in_order_id)
values (1, x'f59b1e1cb145440aaa6f6095a0e2d63b', x'98da3d3859e04dacbbaeebf6560a43bd');
insert into eat_in_order_line_item (quantity, menu_id, eat_in_order_id)
values (1, x'f59b1e1cb145440aaa6f6095a0e2d63b', x'd7cc15b3e32c4bc8b440d3067b35522e');
