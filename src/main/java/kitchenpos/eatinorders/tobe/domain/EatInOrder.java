package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.sharedkernel.Order;
import kitchenpos.sharedkernel.OrderStatus;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "eat_in_order")
@Entity
public class EatInOrder extends Order {
    @Embedded
    private EatInOrderLineItems eatInOrderLineItems;

    private UUID orderTableId;

    protected EatInOrder() {

    }

    public EatInOrder(
        final UUID id,
        final OrderStatus status,
        final LocalDateTime orderDateTime,
        final EatInOrderLineItems eatInOrderLineItems,
        final UUID orderTableId
    ) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.orderTableId = orderTableId;
    }

    public EatInOrderLineItems getOrderLineItems() {
        return eatInOrderLineItems;
    }

    public List<EatInOrderLineItem> getOrderLineItemList() {
        return eatInOrderLineItems.getOrderLineItemList();
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public static EatInOrder create(
        final List<Menu> menus,
        final List<EatInOrderLineItem> eatInOrderLineItemList,
        final OrderTable orderTable
    ) {
        validateOrderTable(orderTable);
        return new EatInOrder(
            UUID.randomUUID(),
            OrderStatus.WAITING,
            LocalDateTime.now(),
            new EatInOrderLineItems(eatInOrderLineItemList, createMenuMap(menus)),
            orderTable.getId()
        );
    }

    private static void validateOrderTable(final OrderTable orderTable) {
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
    }
}
