package kitchenpos.legacy.order.deliveryorder.infra.repository;

import kitchenpos.legacy.order.deliveryorder.domain.model.DeliveryOrder;
import kitchenpos.legacy.order.deliveryorder.domain.repository.DeliveryOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaDeliveryOrderRepository extends DeliveryOrderRepository, JpaRepository<DeliveryOrder, UUID> {
}
