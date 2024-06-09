package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.EatInOrderLineItem;

import java.util.UUID;

public interface MenuDomainService {
    EatInOrderLineItem fetchOrderLineItem(UUID menuId, long quantity);
}
