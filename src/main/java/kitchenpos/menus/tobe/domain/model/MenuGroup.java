package kitchenpos.menus.tobe.domain.model;

import kitchenpos.menus.tobe.domain.vo.MenuGroupName;

import java.util.UUID;

public final class MenuGroup {

    private UUID id;
    private MenuGroupName name;

    public MenuGroup(final String name) {
        this.id = UUID.randomUUID();
        this.name = new MenuGroupName(name);
    }

    public UUID getId() {
        return id;
    }

    public MenuGroupName getName() {
        return name;
    }
}
