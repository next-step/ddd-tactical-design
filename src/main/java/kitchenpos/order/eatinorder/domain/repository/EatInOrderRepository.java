package kitchenpos.order.eatinorder.domain.repository;

import kitchenpos.order.eatinorder.domain.model.EatInOrder;
import kitchenpos.order.eatinorder.domain.model.EatInOrderFlow;
import kitchenpos.order.eatinorder.domain.model.EatInOrderStatus;
import kitchenpos.order.eatinorder.domain.model.OrderTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EatInOrderRepository {
    Optional<EatInOrder> findById(UUID eatInOrderId);

    EatInOrder save(EatInOrder eatInOrder);

    boolean existsByOrderTableAndEatInOrderFlowNot(OrderTable orderTable, EatInOrderFlow eatInOrderFlow);

    List<EatInOrder> findAll();
}
