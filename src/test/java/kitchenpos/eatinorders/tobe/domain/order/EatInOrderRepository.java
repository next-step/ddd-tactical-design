package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;

import java.util.List;
import java.util.Optional;

public interface EatInOrderRepository {
    EatInOrder save(EatInOrder order);

    Optional<EatInOrder> findById(EatInOrderId id);

    List<EatInOrder> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTableId orderTable, EatInOrderStatus orderStatus);
}
