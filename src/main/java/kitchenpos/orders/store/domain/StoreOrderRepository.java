package kitchenpos.orders.store.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.orders.common.domain.OrderStatus;
import kitchenpos.orders.store.domain.tobe.OrderTable;
import kitchenpos.orders.store.domain.tobe.StoreOrder;

public interface StoreOrderRepository {

    StoreOrder save(StoreOrder order);

    Optional<StoreOrder> findById(UUID id);

    List<StoreOrder> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus status);
}

