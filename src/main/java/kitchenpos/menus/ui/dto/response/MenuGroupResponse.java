package kitchenpos.menus.ui.dto.response;

import kitchenpos.menus.domain.MenuGroup;

import java.util.UUID;

public class MenuGroupResponse {
    private UUID id;
    private String name;

    private MenuGroupResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroupResponse of(MenuGroup menuGroup) {
        return new MenuGroupResponse(menuGroup.getId(), menuGroup.getName().getValue());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
