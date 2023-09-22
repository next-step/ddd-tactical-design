package kitchenpos.menus.domain.tobe;

import java.util.UUID;

public class MenuGroup {
    private final UUID id;
    private final String name;

    public MenuGroup(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
