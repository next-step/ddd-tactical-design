package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.vo.OrderTableId;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class EatInOrder {
    private final UUID id = UUID.randomUUID();
    private final EatInOrderLineItems eatInOrderLineItems;
    private final OrderTableId orderTableId;

    public EatInOrder(final List<EatInOrderLineItem> eatInOrderLineItems, final OrderTableId orderTableId) {
        if (Objects.isNull(orderTableId)) {
            throw new IllegalArgumentException();
        }
        this.eatInOrderLineItems = new EatInOrderLineItems(eatInOrderLineItems);
        this.orderTableId = orderTableId;
    }

    public List<UUID> menuIds() {
        return eatInOrderLineItems.menuIds();
    }

    public OrderTableId orderTableId() {
        return orderTableId;
    }

    public int size() {
        return eatInOrderLineItems.size();
    }

    public void verify(final EatInOrderMenus eatInOrderMenus) {
        eatInOrderLineItems.verify(eatInOrderMenus);
    }

    public void verify(final OrderTable orderTable) {
        if (!orderTable.isOccupiedValue()) {
            throw new IllegalArgumentException();
        }
    }

    public UUID getId() {
        return id;
    }
}
