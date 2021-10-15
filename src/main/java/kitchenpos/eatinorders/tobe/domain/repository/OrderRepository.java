package kitchenpos.eatinorders.tobe.domain.repository;

import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;

public interface OrderRepository {

    Order save(final Order order);

    boolean existsByOrderTableAndStatusNot(final OrderTable orderTable, final OrderStatus orderStatus);
}
