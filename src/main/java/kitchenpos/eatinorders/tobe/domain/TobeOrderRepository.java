package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.application.TobeOrderTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TobeOrderRepository {
    TobeOrder save(TobeOrder order);

    Optional<TobeOrder> findById(UUID id);

    List<TobeOrder> findAll();

    boolean existsByOrderTableAndStatusNot(TobeOrderTable orderTable, OrderStatus status);
}
