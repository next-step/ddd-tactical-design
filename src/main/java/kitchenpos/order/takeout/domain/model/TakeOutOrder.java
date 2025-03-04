package kitchenpos.order.takeout.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import kitchenpos.order.common.domain.entity.Order;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.entity.OrderType;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItems;

@Entity
@DiscriminatorValue("TAKEOUT")
public class TakeOutOrder extends Order {

    protected TakeOutOrder() {}

    public TakeOutOrder(OrderId orderId, OrderLineItems orderLineItems) {
        super(orderId, OrderType.TAKEOUT, OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, null);
    }

    public static TakeOutOrder createTakeoutOrder(OrderId orderId, OrderLineItems orderLineItems) {
        return new TakeOutOrder(orderId, orderLineItems);
    }
}
