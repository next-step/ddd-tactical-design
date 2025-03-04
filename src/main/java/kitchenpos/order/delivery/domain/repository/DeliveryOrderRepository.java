package kitchenpos.order.delivery.domain.repository;

import java.util.Optional;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.delivery.domain.entity.DeliveryOrder;

public interface DeliveryOrderRepository {
    Optional<DeliveryOrder> findById(OrderId id);
}

