package kitchenpos.takeout_orders;

import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.takeoutorders.domain.OrderStatus;
import kitchenpos.takeoutorders.domain.TakeoutOrder;
import kitchenpos.takeoutorders.domain.TakeoutOrderLineItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static kitchenpos.Fixtures.menu;

public class TakeoutOrderFixtures {
    public static TakeoutOrder order(final OrderStatus status, final ProductRepository productRepository) {
        final TakeoutOrder order = new TakeoutOrder();
        order.setId(UUID.randomUUID());
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(List.of(orderLineItem(productRepository)));
        return order;
    }

    public static TakeoutOrderLineItem orderLineItem(ProductRepository productRepository) {
        final TakeoutOrderLineItem orderLineItem = new TakeoutOrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenu(menu(productRepository));
        return orderLineItem;
    }
}
