package kitchenpos.order.common.domain.repository;

import java.util.List;
import java.util.Optional;
import kitchenpos.order.common.domain.entity.Order;
import kitchenpos.order.common.domain.model.OrderId;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(OrderId id);

    List<Order> findAll();

}

