package kitchenpos.menus.tobe.domain;

import java.util.List;

public final class MenuBuilder {
    private Long id;
    private String name;
    private Long menuGroupId;
    private List<MenuProduct> menuProducts;
    private MenuPrice menuPrice;

    private MenuBuilder() {
    }

    public static MenuBuilder aMenu() {
        return new MenuBuilder();
    }

    public MenuBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public MenuBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MenuBuilder withMenuGroupId(Long menuGroupId) {
        this.menuGroupId = menuGroupId;
        return this;
    }

    public MenuBuilder withMenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
        return this;
    }

    public MenuBuilder withMenuPrice(MenuPrice menuPrice) {
        this.menuPrice = menuPrice;
        return this;
    }

    public Menu build() {
        return new Menu(id, name, menuPrice, menuGroupId, menuProducts);
    }
}
