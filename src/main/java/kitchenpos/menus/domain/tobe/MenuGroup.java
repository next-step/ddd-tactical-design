package kitchenpos.menus.domain.tobe;

import java.util.UUID;

public class MenuGroup {
    private final UUID id;
    private MenuGroupName name;

    public MenuGroup(final MenuGroupName name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
