package kitchenpos.menus.service;

import java.util.UUID;

import kitchenpos.menus.domain.MenuGroup;

public class MenuGroupFixture {
    private UUID id;
    private String name;

    public MenuGroupFixture() {
        id = UUID.randomUUID();
        name = "추천 메뉴";
    }

    public static MenuGroupFixture builder() {
        return new MenuGroupFixture();
    }

    public MenuGroupFixture name(String name) {
        this.name = name;
        return this;
    }

    public MenuGroup build() {
        return new MenuGroup(id, name);
    }
}
