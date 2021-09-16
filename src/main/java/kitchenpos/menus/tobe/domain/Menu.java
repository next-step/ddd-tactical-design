package kitchenpos.menus.tobe.domain;

import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.products.tobe.domain.Price;

import java.util.UUID;

public class Menu {
    private final UUID id;

    private final DisplayedName name;

    private final Price price;

    private boolean displayed;

    private final MenuGroup menuGroup;

    private final MenuProducts menuProducts;

    public Menu(final UUID id, final DisplayedName name, final Price price, final boolean displayed, final MenuGroup menuGroup, final MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.displayed = displayed;
        this.menuGroup = menuGroup;
        this.menuProducts = menuProducts;
    }

    public void display() {
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }
}
