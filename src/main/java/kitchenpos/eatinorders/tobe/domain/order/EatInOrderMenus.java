package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItems;

public interface EatInOrderMenus {

    void validate(final EatInOrderLineItems items);
}
