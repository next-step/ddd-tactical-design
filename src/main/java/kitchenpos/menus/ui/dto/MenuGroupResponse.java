package kitchenpos.menus.ui.dto;


import kitchenpos.menus.tobe.domain.MenuGroup;

import java.util.UUID;

public class MenuGroupResponse {

    private UUID id;

    private String name;

    public MenuGroupResponse(MenuGroup menuGroup) {
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
