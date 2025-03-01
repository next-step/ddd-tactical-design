package kitchenpos.legacy.order.eatinorder.domain.repository;

import kitchenpos.legacy.order.common.model.OrderStatus;
import kitchenpos.legacy.order.common.model.OrderTable;
import kitchenpos.legacy.order.eatinorder.domain.model.EatInOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EatInOrderRepository {

    EatInOrder save(EatInOrder order);

    Optional<EatInOrder> findById(UUID id);

    List<EatInOrder> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus status);

}
