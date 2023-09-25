package kitchenpos.eatinorders.application.eatinorder.port.in;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class EatinOrderInitCommand {

    private final UUID orderTableId;
    private final List<OrderLineItemCommand> orderLineItemCommands;

    public EatinOrderInitCommand(final UUID orderTableId,
        final List<OrderLineItemCommand> orderLineItemCommands) {

        this.orderTableId = orderTableId;
        this.orderLineItemCommands = orderLineItemCommands;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<OrderLineItemCommand> getOrderLineItemCommands() {
        return orderLineItemCommands;
    }

    public List<UUID> getOrderLineItemMenuIds() {
        return orderLineItemCommands.stream()
            .map(OrderLineItemCommand::getMenuId)
            .collect(Collectors.toUnmodifiableList());
    }

    public static final class OrderLineItemCommand {

        private final UUID menuId;
        private final int quantity;

        public OrderLineItemCommand(final UUID menuId, final int quantity) {
            this.menuId = menuId;
            this.quantity = quantity;
        }

        public UUID getMenuId() {
            return menuId;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
