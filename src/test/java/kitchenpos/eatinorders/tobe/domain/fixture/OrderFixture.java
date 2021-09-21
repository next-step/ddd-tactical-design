package kitchenpos.eatinorders.tobe.domain.fixture;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.eatinorders.tobe.domain.model.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

public class OrderFixture {

    public static Order ORDER_WITH_TABLE_AND_STATUS(final OrderTable ordertable, final OrderStatus orderStatus) {
        return new Order(
                UUID.randomUUID(),
                ordertable.getId(),
                orderStatus,
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
