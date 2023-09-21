package kitchenpos.eatinorders.tobe.domain;

import java.util.List;
import java.util.UUID;

public class OrderCreateRequested {

    private final List<UUID> menuIds;

    public OrderCreateRequested(List<UUID> menuIds) {
        this.menuIds = menuIds;
    }

    public List<UUID> getMenuIds() {
        return menuIds;
    }
}
