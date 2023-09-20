package kitchenpos.order.strategy;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderType;

public interface OrderProcess {
    Order create(Order order);

    Order accept(Order order);

    Order serve(Order order);

    Order complete(Order order);

    Order startDelivery(Order order);

    Order completeDelivery(Order order);

    boolean support(OrderType orderType);
}
