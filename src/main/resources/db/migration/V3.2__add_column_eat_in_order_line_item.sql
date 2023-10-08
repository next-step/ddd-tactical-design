ALTER TABLE eat_in_order_line_item ADD COLUMN price decimal(19, 2) not null COMMENT '매장 주문 항목 가격';

UPDATE eat_in_order_line_item AS eo
    JOIN menu AS m ON eo.menu_id = m.id
    SET eo.price = m.price
WHERE 1 = 1;
