package kitchenpos.order.eatin.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import kitchenpos.order.common.domain.entity.Order;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.entity.OrderType;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItems;
import kitchenpos.order.common.domain.model.OrderVo.Create;
import kitchenpos.order.eatin.domain.entity.OrderTable;

@Entity
@DiscriminatorValue("EAT_IN")
public class EatInOrder extends Order {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_table_id", insertable = false, updatable = false)
    private OrderTable orderTable;

    protected EatInOrder() {}

    public EatInOrder(OrderId orderId,
        OrderLineItems orderLineItems, OrderTableId orderTableId) {
        super(orderId, OrderType.EAT_IN, OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, orderTableId);
    }

    public static EatInOrder createEatInOrder(OrderId orderId, Create request, OrderLineItems orderLineItems) {
        return new EatInOrder(orderId, orderLineItems, request.orderTableId());
    }

    public EatInOrder(OrderTable orderTable) {
        this.orderTable = orderTable;
    }
}
