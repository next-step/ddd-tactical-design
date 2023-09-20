package kitchenpos.eatinorders.domain.strategy;

import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderType;

public interface OrderProcess {
    Order create(Order order);

    Order accept(Order order);

    Order serve(Order order);

    Order complete(Order order);

    Order startDelivery(Order order);

    Order completeDelivery(Order order);

    boolean support(OrderType orderType);
}
