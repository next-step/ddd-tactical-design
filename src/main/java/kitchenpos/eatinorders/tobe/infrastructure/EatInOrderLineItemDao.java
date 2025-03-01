package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;

import java.util.List;

public interface EatInOrderLineItemDao {
    void saveAll(List<EatInOrderLineItem> eatInOrderLineItems);

    List<EatInOrderLineItem> findAllByEatInOrderId(EatInOrderId id);

    List<EatInOrderLineItem> findAll();
}
