package kitchenpos.eatinorders.tobe.domain.model;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EAT_IN")
public class EatInOrder extends Order {

    @Column(
        name = "order_table_id",
        columnDefinition = "varbinary(16)",
        nullable = false
    )
    private UUID orderTableId;

    protected EatInOrder() {
    }

    @Override
    public UUID getOrderTableId() {
        return orderTableId;
    }

    @Override
    public OrderType getType() {
        return OrderType.EAT_IN;
    }

    public EatInOrder(final UUID orderTableId, final OrderLineItem... orderLineItems) {
        this(orderTableId, Arrays.asList(orderLineItems));
    }

    public EatInOrder(final UUID orderTableId, final List<OrderLineItem> orderLineItems) {
        super(orderLineItems);
        this.orderTableId = orderTableId;
    }

    EatInOrder(final UUID orderTableId, final OrderStatus orderStatus, final OrderLineItem... orderLineItems) {
        super(orderStatus, orderLineItems);
        this.orderTableId = orderTableId;
    }

}
