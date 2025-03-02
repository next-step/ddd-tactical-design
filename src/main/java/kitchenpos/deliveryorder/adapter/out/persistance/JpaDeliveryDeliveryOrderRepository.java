package kitchenpos.deliveryorder.adapter.out.persistance;

import kitchenpos.deliveryorder.application.port.out.DeliveryOrderRepository;
import kitchenpos.deliveryorder.domain.model.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaDeliveryDeliveryOrderRepository extends DeliveryOrderRepository, JpaRepository<DeliveryOrder, UUID> {
}
