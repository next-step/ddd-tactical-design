package kitchenpos.order.common.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.order.common.domain.entity.Order;

public interface OrderService {
    Order create(final Order request);
    Order accept(final UUID orderId);
    Order serve(final UUID orderId);
    Order startDelivery(final UUID orderId);
    Order completeDelivery(final UUID orderId);
    Order complete(final UUID orderId);
    List<Order> findAll();
}
