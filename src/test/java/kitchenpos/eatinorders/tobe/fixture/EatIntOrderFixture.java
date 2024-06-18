package kitchenpos.eatinorders.tobe.fixture;

import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.OrderLineItems;
import kitchenpos.menus.tobe.domain.MenuPrice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EatIntOrderFixture {

    public static OrderLineItems createOrderLineItems(int itemSize) {
        return OrderLineItems.of(
                createEatInOrderLineItem(itemSize)
        );
    }

    public static List<EatInOrderLineItem> createEatInOrderLineItem(int size) {
        List<EatInOrderLineItem> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(createEatInOrderLineItem());
        }
        return list;
    }

    public static EatInOrderLineItem createEatInOrderLineItem() {
        return EatInOrderLineItem.create(UUID.randomUUID(), MenuPrice.of(BigDecimal.valueOf(20_000)), 1L);
    }

    public static EatInOrder createEatInOrder(UUID orderTableId) {
        OrderLineItems orderLineItems = createOrderLineItems(3);
        return EatInOrder.startWaiting(orderTableId, orderLineItems);
    }
}
