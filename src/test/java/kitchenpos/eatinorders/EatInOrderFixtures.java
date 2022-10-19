package kitchenpos.eatinorders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import kitchenpos.eatinorders.domain.*;

public class EatInOrderFixtures {

    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static EatInOrder eatInOrder(final EatInOrderStatus status, final UUID eatInOrderTableId) {
        return new EatInOrder(
            UUID.randomUUID(),
            status,
            LocalDateTime.of(2020, 1, 1, 12, 0),
            new EatInOrderLineItems(List.of(orderLineItem())),
            eatInOrderTableId
        );
    }

    public static EatInOrderLineItem orderLineItem() {
        return new EatInOrderLineItem(
            new Random().nextLong(),
            UUID.randomUUID(),
            1,
            new EatInOrderLineItemPrice(BigDecimal.valueOf(19_000))
        );
    }
}
