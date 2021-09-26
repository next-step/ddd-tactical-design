package kitchenpos.eatinorders.tobe.domain.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("EAT_IN")
public class EatInOrder extends Order {

    @ManyToOne
    @JoinColumn(
        name = "order_table_id",
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

//    @Column(name = "order_table_id", columnDefinition = "varbinary(16)")
//    private UUID orderTableId;

    protected EatInOrder() {
    }

    public EatInOrder(final OrderTable orderTable, final OrderLineItem... orderLineItems) {
        super(orderLineItems);
        this.orderTable = orderTable;
    }

    EatInOrder(final OrderTable orderTable, final OrderStatus orderStatus, final OrderLineItem... orderLineItems) {
        super(orderStatus, orderLineItems);
        this.orderTable = orderTable;
    }

    @Override
    public Order complete() {
        return super.complete();
    }
}
