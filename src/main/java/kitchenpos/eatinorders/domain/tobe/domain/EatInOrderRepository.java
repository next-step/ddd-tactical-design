package kitchenpos.eatinorders.domain.tobe.domain;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderId;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableId;

import java.util.List;
import java.util.Optional;

public interface EatInOrderRepository {
    EatInOrder save(EatInOrder order);

    Optional<EatInOrder> findById(OrderId id);

    List<EatInOrder> findAll();

    List<EatInOrder> findAllByOrderTableId(OrderTableId id);

    boolean existsByOrderTableAndStatusNot(TobeOrderTable orderTable, OrderStatus status);
}

