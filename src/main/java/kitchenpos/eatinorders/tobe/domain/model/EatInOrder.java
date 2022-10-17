package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.common.Order;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@DiscriminatorValue("EAT_IN")
public class EatInOrder extends Order {

    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;
    @Column(name = "order_table_id", columnDefinition = "binary(16)")
    private UUID orderTableId;

    protected EatInOrder() {
    }

    public EatInOrder(OrderTable orderTable) {
        this(null, orderTable);
    }

    public EatInOrder(OrderPolicy orderPolicy, OrderTable orderTable, OrderLineItem... orderLineItems) {
        super(LocalDateTime.now());
        this.status = EatInOrderStatus.WAITING;
        occupyTable(orderTable);
        addOrderLineItems(orderPolicy, orderLineItems);
    }

    private void occupyTable(OrderTable orderTable) {
        orderTable.occupy();
        this.orderTableId = orderTable.getId();
    }

    private void addOrderLineItems(OrderPolicy orderPolicy, OrderLineItem... orderLineItems) {
        orderPolicy.validateOlis(List.of(orderLineItems));
        addOrderLineItems(orderLineItems);
    }

    public boolean orderTableEq(UUID orderTableId) {
        return this.orderTableId.equals(orderTableId);
    }

    public boolean statusNotEq(EatInOrderStatus status) {
        return this.status != status;
    }
}
