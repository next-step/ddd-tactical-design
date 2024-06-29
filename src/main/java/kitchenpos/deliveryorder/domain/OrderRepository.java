package kitchenpos.deliveryorder.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    DeliveryOrder save(DeliveryOrder order);

    Optional<DeliveryOrder> findById(UUID id);

    List<DeliveryOrder> findAll();
}

