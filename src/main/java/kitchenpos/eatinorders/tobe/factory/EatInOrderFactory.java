package kitchenpos.eatinorders.tobe.factory;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.OrderTable;

@Component
public class EatInOrderFactory implements OrderFactory {
    @Override
    public Order createOrder(OrderLineItems orderLineItems, OrderTable orderTable, String deliveryAddress) {
        return new EatInOrder(
            OrderStatus.WAITING,
            LocalDateTime.now(),
            orderLineItems,
            orderTable
        );
    }
}
