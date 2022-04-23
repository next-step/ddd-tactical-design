package kitchenpos.menus.tobe.domain;

import java.util.List;

public class MenuProducts {

    private final List<MenuProduct> menuProducts;

    public MenuProducts(final List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public int sum() {
        return this.menuProducts.stream()
                                .mapToInt(MenuProduct::amount)
                                .sum();
    }
}
