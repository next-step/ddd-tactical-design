package kitchenpos.eatinorders.domain.event;

import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class OrderCreateRequested extends ApplicationEvent {

    private final List<UUID> menuIds;

    public OrderCreateRequested(List<UUID> menuIds) {
        super(OrderCreateRequested.class);
        this.menuIds = menuIds;
    }

    public List<UUID> getMenuIds() {
        return menuIds;
    }
}
