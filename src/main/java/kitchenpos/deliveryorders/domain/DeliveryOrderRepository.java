package kitchenpos.deliveryorders.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryOrderRepository {
    DeliveryOrder save(DeliveryOrder deliveryOrder);
    Optional<DeliveryOrder> findById(final UUID id);
    List<DeliveryOrder> findAll();
}
