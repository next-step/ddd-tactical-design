package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.MenuGroup;

public class MenuGroupCreateRequest {
    private String name;

    public MenuGroup toEntity() {
        return new MenuGroup(name);
    }

    public String getName() {
        return name;
    }
}
