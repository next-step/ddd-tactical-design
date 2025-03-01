package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrder;

public interface EatInOrderDao {
    void save(EatInOrder eatInOrder);
}
