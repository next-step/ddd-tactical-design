package kitchenpos.deliveryorders.infra;

import java.util.UUID;
import kitchenpos.deliveryorders.domain.DeliveryOrder;
import kitchenpos.deliveryorders.domain.DeliveryOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeliveryOrderRepository
    extends DeliveryOrderRepository, JpaRepository<DeliveryOrder, UUID> {}
