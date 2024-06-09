package kitchenpos.fixture;

import kitchenpos.deliveryorders.domain.DeliveryOrder;
import kitchenpos.deliveryorders.domain.DeliveryOrderStatus;
import kitchenpos.support.domain.OrderLineItems;

import java.util.List;

import static kitchenpos.fixture.Fixtures.orderLineItemRequest;

public class DeliveryOrderFixture {
    public static DeliveryOrder deliveryOrder(final DeliveryOrderStatus status) {
        return deliveryOrder(status, "서울시 송파구 위례성대로 2");
    }

    public static DeliveryOrder deliveryOrder(final DeliveryOrderStatus status, final String deliveryAddress) {
        return DeliveryOrder.create(OrderLineItems.from(List.of(orderLineItemRequest())), deliveryAddress, status);
    }
}
