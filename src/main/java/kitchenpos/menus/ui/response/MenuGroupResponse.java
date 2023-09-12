package kitchenpos.menus.ui.response;

import kitchenpos.menus.tobe.domain.MenuGroup;

import java.util.UUID;

public class MenuGroupResponse {

    private UUID id;
    private String name;

    public MenuGroupResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroupResponse of(MenuGroup menuGroup) {
        return new MenuGroupResponse(menuGroup.getId(), menuGroup.getName());
    }

    public UUID getId() {
        return id;
    }
}
