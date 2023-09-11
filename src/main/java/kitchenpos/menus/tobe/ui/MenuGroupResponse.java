package kitchenpos.menus.tobe.ui;


import kitchenpos.menus.tobe.domain.MenuGroup;

public class MenuGroupResponse {

    private final String id;

    private final String name;

    public MenuGroupResponse(MenuGroup menuGroup) {
        this.id = menuGroup.getId();
        this.name = menuGroup.getName();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
