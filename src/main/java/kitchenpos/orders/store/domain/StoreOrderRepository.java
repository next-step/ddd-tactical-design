package kitchenpos.orders.store.domain;

import kitchenpos.orders.common.domain.OrderStatus;
import kitchenpos.orders.store.domain.tobe.StoreOrder;
import kitchenpos.orders.store.domain.tobe.OrderTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreOrderRepository {

    StoreOrder save(StoreOrder order);

    Optional<StoreOrder> findById(UUID id);

    List<StoreOrder> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus status);
}

