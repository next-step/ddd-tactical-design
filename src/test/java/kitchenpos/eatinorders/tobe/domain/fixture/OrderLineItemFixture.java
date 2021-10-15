package kitchenpos.eatinorders.tobe.domain.fixture;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderLineItemFixture {

    public static OrderLineItem DEFAULT_ORDER_LINE_ITEM() {
        return new OrderLineItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Price(BigDecimal.valueOf(16_000L)),
                1L
        );
    }

    public static OrderLineItem ORDER_LINE_ITEM_WITH_MENU_ID(final UUID menuId) {
        return new OrderLineItem(
                UUID.randomUUID(),
                menuId,
                new Price(BigDecimal.valueOf(16_000L)),
                1L
        );
    }

    public static OrderLineItem ORDER_LINE_ITEM_WITH_MENU_ID_AND_PRICE(final UUID menuId, final long price) {
        return new OrderLineItem(
                UUID.randomUUID(),
                menuId,
                new Price(BigDecimal.valueOf(price)),
                1L
        );
    }
}
