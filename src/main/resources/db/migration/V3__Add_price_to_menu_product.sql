alter table menu_product
    add column price decimal(19, 2) default 0;

update menu_product mp, product p
set mp.price = p.price
where mp.product_id = p.id;
