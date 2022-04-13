package kitchenpos.menus.tobe.domain.model;

import kitchenpos.global.domain.vo.Name;

import java.util.UUID;

public final class MenuGroup {

    private UUID id;
    private Name name;

    public MenuGroup(final Name name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }
}
