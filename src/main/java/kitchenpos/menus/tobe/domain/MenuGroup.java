package kitchenpos.menus.tobe.domain;

import java.util.UUID;

public class MenuGroup {

    private final UUID id;
    private final String name;
    public MenuGroup(final UUID id, final String name) {
        this.id = id;
        this.name = name;
    }

}
