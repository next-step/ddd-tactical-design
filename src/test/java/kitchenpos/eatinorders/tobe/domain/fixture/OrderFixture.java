package kitchenpos.eatinorders.tobe.domain.fixture;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.eatinorders.tobe.domain.model.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

public class OrderFixture {

    public static Order ORDER_WITH_TABLE(final OrderTable ordertable) {
        return Order.create(
                UUID.randomUUID(),
                ordertable.getId(),
                new OrderLineItems(
                        Collections.singletonList(
                                new OrderLineItem(
                                        UUID.randomUUID(),
                                        UUID.randomUUID(),
                                        new Price(BigDecimal.valueOf(16_000L)),
                                        1L
                                )
                        )
                ),
                order -> {
                }
        );
    }
}
