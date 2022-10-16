package kitchenpos.eatinorders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import kitchenpos.eatinorders.domain.*;

public class EatInOrderFixtures {

    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static EatInOrderTable eatInOrderTable() {
        return eatInOrderTable(false, 0);
    }

    public static EatInOrderTable eatInOrderTable(final boolean occupied, final int numberOfGuests) {
        return new EatInOrderTable(
            UUID.randomUUID(),
            new EatInOrderTableName("1번"),
            new NumberOfGuests(numberOfGuests),
            occupied
        );
    }

    public static EatInOrder eatInOrder(final EatInOrderStatus status, final EatInOrderTable eatInOrderTable) {
        final EatInOrder eatInOrder = new EatInOrder();
        eatInOrder.setId(UUID.randomUUID());
        eatInOrder.setStatus(status);
        eatInOrder.setEatInOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        eatInOrder.setOrderLineItems(Arrays.asList(orderLineItem()));
        eatInOrder.setOrderTable(eatInOrderTable);
        return eatInOrder;
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
