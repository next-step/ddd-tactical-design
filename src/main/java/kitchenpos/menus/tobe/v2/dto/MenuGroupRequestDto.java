package kitchenpos.menus.tobe.v2.dto;

import kitchenpos.menus.tobe.v2.domain.MenuGroup;

public class MenuGroupRequestDto {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuGroup toEntity() {
        return new MenuGroup(name);
    }
}
