package kitchenpos.eatinorders.tobe.domain.menu;

import java.util.UUID;

public class MenuGroup {
    private UUID id;
    private String name;

    protected MenuGroup() {}

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
