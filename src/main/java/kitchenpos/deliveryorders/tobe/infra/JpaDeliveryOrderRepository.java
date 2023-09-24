package kitchenpos.deliveryorders.tobe.infra;

import kitchenpos.deliveryorders.tobe.domain.DeliveryOrder;
import kitchenpos.deliveryorders.tobe.domain.DeliveryOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaDeliveryOrderRepository extends DeliveryOrderRepository, JpaRepository<DeliveryOrder, UUID> {
}
