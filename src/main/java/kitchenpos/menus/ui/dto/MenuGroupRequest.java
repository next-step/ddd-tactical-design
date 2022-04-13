package kitchenpos.menus.ui.dto;


import kitchenpos.menus.tobe.domain.MenuGroup;

public class MenuGroupRequest {

    private String name;

    public MenuGroupRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public MenuGroup toEntity() {
        return new MenuGroup(name);
    }
}
