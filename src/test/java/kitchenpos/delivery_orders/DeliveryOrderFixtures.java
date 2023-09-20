package kitchenpos.delivery_orders;

import kitchenpos.deliveryorders.domain.DeliveryOrder;
import kitchenpos.deliveryorders.domain.DeliveryOrderLineItem;
import kitchenpos.deliveryorders.domain.OrderStatus;
import kitchenpos.products.tobe.domain.ProductRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import static kitchenpos.Fixtures.menu;

public class DeliveryOrderFixtures {

    public static DeliveryOrder order(final OrderStatus status, final String deliveryAddress, final ProductRepository productRepository) {
        final DeliveryOrder order = new DeliveryOrder();
        order.setId(UUID.randomUUID());
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem(productRepository)));
        order.setDeliveryAddress(deliveryAddress);
        return order;
    }

    public static DeliveryOrderLineItem orderLineItem(final ProductRepository productRepository) {
        final DeliveryOrderLineItem orderLineItem = new DeliveryOrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenu(menu(productRepository));
        return orderLineItem;
    }
}
