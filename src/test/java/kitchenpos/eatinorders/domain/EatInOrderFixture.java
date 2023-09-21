package kitchenpos.eatinorders.domain;

import java.util.List;
import java.util.UUID;

import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderLineItem;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderLineItems;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderMenu;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderMenuPrice;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderQuantity;

public class EatInOrderFixture {
    public static EatInOrderLineItems createOrderLineItems() {
        return new EatInOrderLineItems(List.of(createOrderLineItem()));
    }

    public static EatInOrderLineItem createOrderLineItem() {
        return new EatInOrderLineItem(createOrderMenu(), EatInOrderQuantity.of(3));
    }

    public static EatInOrderMenu createOrderMenu() {
        return new EatInOrderMenu(UUID.randomUUID(), EatInOrderMenuPrice.of(13_000L));
    }

    public static EatInOrderMenu createOrderMenu(long price) {
        return new EatInOrderMenu(UUID.randomUUID(), EatInOrderMenuPrice.of(price));
    }
}
