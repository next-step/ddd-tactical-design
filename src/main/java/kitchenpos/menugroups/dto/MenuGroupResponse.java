package kitchenpos.menugroups.dto;

import kitchenpos.menugroups.domain.MenuGroup;

import java.util.UUID;

public class MenuGroupResponse {
    private final UUID id;
    private final String name;

    private MenuGroupResponse(final UUID id, final String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static MenuGroupResponse from(final MenuGroup menuGroup) {
        return new MenuGroupResponse(menuGroup.getId(), menuGroup.getName());
    }
}
