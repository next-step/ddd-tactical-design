package kitchenpos.fixture;

import kitchenpos.support.domain.OrderLineItems;
import kitchenpos.takeoutorders.domain.TakeoutOrder;
import kitchenpos.takeoutorders.domain.TakeoutOrderStatus;

import java.util.List;

import static kitchenpos.fixture.Fixtures.orderLineItemRequest;

public class TakeoutOrderFixture {

    public static TakeoutOrder takeoutOrder(final TakeoutOrderStatus status) {
        return TakeoutOrder.create(OrderLineItems.from(List.of(orderLineItemRequest())), status);
    }
}
