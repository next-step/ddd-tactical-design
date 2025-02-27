package kitchenpos.order.deliveryorder.domain.repository;

import kitchenpos.order.deliveryorder.domain.model.DeliveryOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryOrderRepository {

    DeliveryOrder save(DeliveryOrder order);

    Optional<DeliveryOrder> findById(UUID id);

    List<DeliveryOrder> findAll();

}
