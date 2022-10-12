package kitchenpos.eatinorders.tobe.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderStatus;

public interface OrderRepository {

  Order save(Order order);

  Optional<Order> findById(UUID id);

  List<Order> findAll();

  boolean existsByOrderTableAndStatusNot(UUID orderTableId, OrderStatus status);

}
