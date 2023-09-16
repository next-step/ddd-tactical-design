package kitchenpos.menus.application.dto;

import kitchenpos.menus.tobe.domain.MenuGroup;

import java.util.UUID;

public class MenuGroupResponse {
    private UUID id;
    private String name;

    public MenuGroupResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroupResponse from(MenuGroup menuGroup) {
        return new MenuGroupResponse(
                menuGroup.getId(),
                menuGroup.getName().getValue()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
