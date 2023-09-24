package kitchenpos.menus.application.menugroup.dto;

import kitchenpos.menus.domain.menugroup.MenuGroup;

import java.util.UUID;

public class MenuGroupResponse {
    private final UUID id;

    private final String name;

    public MenuGroupResponse(final MenuGroup menuGroup) {
        this.id = menuGroup.getId();
        this.name = menuGroup.getName();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
