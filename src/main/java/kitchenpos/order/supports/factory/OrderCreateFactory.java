package kitchenpos.order.supports.factory;

import kitchenpos.order.deliveryorders.domain.DeliveryOrderCreateService;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.domain.OrderType;
import kitchenpos.order.eatinorders.domain.EatInOrderCreateService;
import kitchenpos.order.takeoutorders.domain.TakeOutOrderCreateService;
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
        validOrderType(order);
        return new Order(UUID.randomUUID(), OrderType.DELIVERY, OrderStatus.WAITING, LocalDateTime.now(), order.getOrderLineItems(), deliveryAddress, null, null);
    }

    public static Order takeOutOrder(Order order) {
        validOrderType(order);
        return new Order(UUID.randomUUID(), OrderType.TAKEOUT, OrderStatus.WAITING, LocalDateTime.now(), order.getOrderLineItems(), null, null, null);
    }

    public static Order eatInOrder(Order order, OrderTable orderTable) {
        validOrderType(order);
        return new Order(UUID.randomUUID(), OrderType.EAT_IN, OrderStatus.WAITING, LocalDateTime.now(), order.getOrderLineItems(), null, orderTable, orderTable.getId());
    }

    private static void validOrderType(Order order) {
        if (order == null) throw new IllegalArgumentException("주문이 존재하지 않습니다.");
        if (order.getType() == null) throw new IllegalArgumentException("주문 타입이 존재하지 않습니다.");
    }
}
