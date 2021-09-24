package kitchenpos.menus.tobe.ui.dto;

import kitchenpos.menus.tobe.domain.MenuGroup;

public class MenuGroupRequest {
    private final String name;

    public MenuGroupRequest(final String name) {
        this.name = name;
    }

    public MenuGroup toEntity() {
        return new MenuGroup(name);
    }
}
