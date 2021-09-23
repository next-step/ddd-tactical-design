package kitchenpos.eatinorders.tobe.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FilteredMenuRequest {
    private List<UUID> menuIds;

    protected FilteredMenuRequest() {}

    public FilteredMenuRequest(final List<UUID> menuIds) {
        this.menuIds = Collections.unmodifiableList(menuIds);
    }

    public List<UUID> getMenuIds() {
        return new ArrayList<>(menuIds);
    }
}
