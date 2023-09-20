package kitchenpos.deliveryorders.domain;

import java.util.List;
import java.util.UUID;

public class DeliveryOrderFixture {
    public static DeliveryOrderLineItems createOrderLineItems() {
        return new DeliveryOrderLineItems(List.of(createOrderLineItem()));
    }

    public static DeliveryOrderLineItem createOrderLineItem() {
        return new DeliveryOrderLineItem(createOrderMenu(), DeliveryOrderQuantity.of(3));
    }

    public static DeliveryOrderMenu createOrderMenu() {
        return new DeliveryOrderMenu(UUID.randomUUID(), DeliveryOrderMenuPrice.of(13_000L));
    }

    public static DeliveryOrderMenu createOrderMenu(long price) {
        return new DeliveryOrderMenu(UUID.randomUUID(), DeliveryOrderMenuPrice.of(price));
    }
}
