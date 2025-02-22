package kitchenpos.deliveryorder.application.port.out;

import kitchenpos.deliveryorder.domain.DeliveryOrder;
import kitchenpos.deliveryorder.domain.DeliveryOrderStatus;
import kitchenpos.eatinorder.domain.EatInOrderTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryOrderRepository {
    DeliveryOrder save(DeliveryOrder order);

    Optional<DeliveryOrder> findById(UUID id);

    List<DeliveryOrder> findAll();

    boolean existsByOrderTableAndStatusNot(EatInOrderTable orderTable, DeliveryOrderStatus status);
}

