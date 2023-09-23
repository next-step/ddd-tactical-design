package kitchenpos.order.supports.factory;

import kitchenpos.order.domain.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class OrderCreateFactory {

    private final EatInOrderCreateService eatInOrderCreateService;
    private final TakeOutOrderCreateService takeOutOrderCreateService;
    private final DeliveryOrderCreateService deliveryOrderCreateService;

    public OrderCreateFactory(EatInOrderCreateService eatInOrderCreateService, TakeOutOrderCreateService takeOutOrderCreateService, DeliveryOrderCreateService deliveryOrderCreateService) {
        this.eatInOrderCreateService = eatInOrderCreateService;
        this.takeOutOrderCreateService = takeOutOrderCreateService;
        this.deliveryOrderCreateService = deliveryOrderCreateService;
    }

    public static Order deliveryOrder(Order order, String deliveryAddress) {
        return new Order(UUID.randomUUID(), OrderType.DELIVERY, OrderStatus.WAITING, LocalDateTime.now(), order.getOrderLineItems(), deliveryAddress, null, null);
    }

    public static Order takeOutOrder(Order order) {
        return new Order(UUID.randomUUID(), OrderType.TAKEOUT, OrderStatus.WAITING, LocalDateTime.now(), order.getOrderLineItems(), null, null, null);
    }

    public static Order eatInOrder(Order order, OrderTable orderTable) {
        return new Order(UUID.randomUUID(), OrderType.EAT_IN, OrderStatus.WAITING, LocalDateTime.now(), order.getOrderLineItems(), null, orderTable, orderTable.getId());
    }

    public Order createOrder(Order order) {

        if (order.getType() == OrderType.EAT_IN) {
            return this.eatInOrderCreateService.create(order);
        } else if (order.getType() == OrderType.TAKEOUT) {
            return this.takeOutOrderCreateService.create(order);
        } else if (order.getType() == OrderType.DELIVERY) {
            return this.deliveryOrderCreateService.create(order);
        }
        throw new IllegalArgumentException("주문 타입이 올바르지 않습니다.");
    }
}
