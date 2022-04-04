package kitchenpos.menus.tobe.domain.model;

import kitchenpos.global.domain.vo.Name;

import java.util.UUID;

public final class MenuGroup {

    private UUID id;
    private Name name;

    public MenuGroup(final String name) {
        this.id = UUID.randomUUID();
        this.name = new Name(name);
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }
}
