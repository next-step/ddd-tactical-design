package kitchenpos.order.delivery.infrastructure.persistence;

import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.delivery.domain.entity.DeliveryOrder;
import kitchenpos.order.delivery.domain.repository.DeliveryOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeliveryOrderRepository extends DeliveryOrderRepository, JpaRepository<DeliveryOrder, OrderId> {

}
