package kitchenpos.deliveryorder.adapter.out.persistance;

import kitchenpos.deliveryorder.application.port.out.DeliveryOrderRepository;
import kitchenpos.deliveryorder.domain.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaDeliveryOrderRepository extends DeliveryOrderRepository, JpaRepository<DeliveryOrder, UUID> {
}
