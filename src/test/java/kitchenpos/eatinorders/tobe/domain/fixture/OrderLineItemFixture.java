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
}
