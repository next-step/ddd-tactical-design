package kitchenpos.fixture;

import kitchenpos.takeoutorders.domain.TakeoutOrder;
import kitchenpos.takeoutorders.domain.TakeoutOrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static kitchenpos.fixture.Fixtures.orderLineItem;

public class TakeoutOrderFixture {

    public static TakeoutOrder takeoutOrder(final TakeoutOrderStatus status) {
        final TakeoutOrder order = new TakeoutOrder();
        order.setId(UUID.randomUUID());
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(List.of(orderLineItem()));
        return order;
    }
}
