package kitchenpos.menus.tobe.ui.dto.response;

import kitchenpos.menus.tobe.domain.MenuGroup;

import java.util.UUID;

public class MenuGroupCreateResponse {
    private UUID id;
    private String name;

    private MenuGroupCreateResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MenuGroupCreateResponse of(MenuGroup menuGroup) {
        return new MenuGroupCreateResponse(menuGroup.getId(), menuGroup.getName().getValue());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
