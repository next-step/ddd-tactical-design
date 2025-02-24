package kitchenpos.order.eatinorder.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import java.util.UUID;
import kitchenpos.order.common.domain.Order;
import kitchenpos.order.common.domain.OrderType;

@Entity
@DiscriminatorValue("EAT_IN")
public class EatInOrder extends Order {

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @ManyToOne
    @JoinColumn(
        name = "order_table_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    @Transient
    private UUID orderTableId;

    public EatInOrder() {
    }

    @Override
    public OrderType getType() {
        return OrderType.EAT_IN;
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public void setStatus(EatInOrderStatus status) {
        this.status = status;
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
