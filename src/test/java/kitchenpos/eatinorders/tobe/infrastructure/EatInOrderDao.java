package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;

import java.util.List;

public interface EatInOrderDao {
    void save(EatInOrder eatInOrder);

    EatInOrder findById(EatInOrderId id, List<EatInOrderLineItem> eatInOrderLineItems);

    List<EatInOrder> findAll(List<EatInOrderLineItem> eatInOrderLineItems);

    boolean existsByOrderTableAndStatusNot(OrderTableId orderTableId, EatInOrderStatus eatInOrderStatus);
}
