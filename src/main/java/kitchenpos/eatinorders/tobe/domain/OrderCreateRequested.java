package kitchenpos.eatinorders.tobe.domain;

import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class OrderCreateRequested implements BaseEvent {

    private final List<UUID> menuIds;

    public OrderCreateRequested(List<UUID> menuIds) {
        this.menuIds = menuIds;
    }

    public List<UUID> getMenuIds() {
        return menuIds;
    }
}
