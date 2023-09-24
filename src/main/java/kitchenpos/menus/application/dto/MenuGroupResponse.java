package kitchenpos.menus.application.dto;

import java.util.UUID;

import kitchenpos.menus.domain.MenuGroup;

public class MenuGroupResponse {
    private UUID id;
    private String name;

    public MenuGroupResponse(final UUID id, final String name) {
        this.id = id;
        this.name = name;
    }

    public MenuGroupResponse(final MenuGroup menuGroup) {
        this(menuGroup.getId(), menuGroup.getName());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
