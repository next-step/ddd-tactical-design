package kitchenpos.eatinorders.tobe.domain.order;

import java.util.Objects;
import kitchenpos.eatinorders.tobe.domain.order.menu.Menu;

public final class OrderLineItem {

    private final OrderLineItemId id;
    private final Menu menu;
    private final long quantity;

    public OrderLineItem(OrderLineItemId id, Menu menu, long quantity) {
        if (Objects.isNull(menu)) {
            throw new IllegalArgumentException("menu는 null일 수 없습니다.");
        }
        this.id = id;
        this.menu = menu;
        this.quantity = quantity;
    }

    public OrderLineItem(Menu menu, long quantity) {
        this(null, menu, quantity);
    }
}
