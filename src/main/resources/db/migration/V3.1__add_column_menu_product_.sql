ALTER TABLE menu_product ADD COLUMN price decimal(19, 2) not null COMMENT '메뉴 가격';

UPDATE menu_product AS mp
JOIN product AS p ON mp.product_id = p.id
SET mp.price = p.price
WHERE 1 = 1;
