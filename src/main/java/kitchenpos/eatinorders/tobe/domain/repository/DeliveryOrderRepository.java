package kitchenpos.eatinorders.tobe.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.DeliveryOrder;

public interface DeliveryOrderRepository {

    DeliveryOrder save(DeliveryOrder deliveryOrder);

    Optional<DeliveryOrder> findById(UUID id);

    List<DeliveryOrder> findAll();
}
