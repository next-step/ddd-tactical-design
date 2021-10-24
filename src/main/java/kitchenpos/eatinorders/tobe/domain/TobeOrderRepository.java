package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TobeOrderRepository {
    TobeOrder save(TobeOrder order);

    Optional<TobeOrder> findById(UUID id);

    List<TobeOrder> findAll();

    Optional<TobeOrder> findByOrderTableId(UUID orderTableId);

    boolean existsByOrderTableIdAndStatusNot(UUID orderTableId, OrderStatus status);
}

