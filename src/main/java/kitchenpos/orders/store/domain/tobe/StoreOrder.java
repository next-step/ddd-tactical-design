package kitchenpos.orders.store.domain.tobe;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.orders.common.domain.OrderType;
import kitchenpos.orders.common.domain.tobe.Order;
import kitchenpos.orders.common.domain.tobe.OrderLineItem;
import kitchenpos.orders.common.domain.tobe.OrderLineItems;

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

    public StoreOrder(OrderLineItems orderLineItems, OrderTable orderTable) {
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

    public OrderTable getOrderTable() {
        return orderTable;
    }
}
