package kitchenpos.eatinorders.tobe.dto;

import java.util.List;
import java.util.UUID;

public class FilteredMenuRequest {
    private List<UUID> menuIds;

    protected FilteredMenuRequest() {}

    public FilteredMenuRequest(final List<UUID> menuIds) {
        this.menuIds = menuIds;
    }

    public List<UUID> getMenuIds() {
        return menuIds;
    }
}
