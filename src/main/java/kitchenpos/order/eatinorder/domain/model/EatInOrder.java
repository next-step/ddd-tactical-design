package kitchenpos.order.eatinorder.domain.model;

import jakarta.persistence.*;
import kitchenpos.order.common.model.Order;
import kitchenpos.order.common.model.OrderTable;
import kitchenpos.order.common.model.OrderType;

import java.util.UUID;

@Entity
@DiscriminatorValue("EAT_IN")
public class EatInOrder extends Order {

    @ManyToOne
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    @Transient
    private UUID orderTableId;

    @Override
    public OrderType getType() {
        return OrderType.EAT_IN;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public void setOrderTable(final OrderTable orderTable) {
        this.orderTable = orderTable;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public void setOrderTableId(final UUID orderTableId) {
        this.orderTableId = orderTableId;
    }
}
