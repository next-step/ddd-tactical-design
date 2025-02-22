package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.vo.MenuName;
import kitchenpos.menus.tobe.vo.MenuPrice;

import java.util.List;
import java.util.UUID;

public class Menu {
    private final UUID id;
    private final MenuName name;
    private final MenuPrice price;
    private final MenuGroup menuGroup;
    private final List<MenuProduct> menuProducts;
    private final boolean displayed;

    public Menu(final UUID id, final String name, final long price, final MenuGroup menuGroup, final List<MenuProduct> menuProducts, final boolean displayed) {
        this(id, new MenuName(name), new MenuPrice(price), menuGroup, menuProducts, displayed);
    }

    public Menu(final UUID id, final MenuName name, final MenuPrice price, final MenuGroup menuGroup, final List<MenuProduct> menuProducts, final boolean displayed) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.menuProducts = menuProducts;
        this.displayed = displayed;
    }
}
