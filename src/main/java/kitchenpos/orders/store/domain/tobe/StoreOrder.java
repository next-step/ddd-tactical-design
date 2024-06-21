package kitchenpos.orders.store.domain.tobe;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kitchenpos.orders.common.domain.OrderType;
import kitchenpos.orders.common.domain.tobe.Order;
import kitchenpos.orders.common.domain.tobe.OrderLineItem;

import java.util.List;

@Entity
public class StoreOrder extends Order {

    @ManyToOne
    @JoinColumn(
            name = "order_table_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
    )
    private OrderTable orderTable;

    protected StoreOrder() {
    }

    public StoreOrder(List<OrderLineItem> orderLineItems, OrderTable orderTable) {
        super(OrderType.EAT_IN, orderLineItems);

        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
        this.orderTable = orderTable;
    }

    @Override
    public void complete() {
        super.complete();
        orderTable.clear();
    }
}
