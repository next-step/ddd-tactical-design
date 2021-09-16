package kitchenpos.menus.tobe.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuProducts {
    private final List<MenuProduct> menuProducts;

    public MenuProducts(final List<MenuProduct> menuProducts) {
        validate(menuProducts);
        this.menuProducts = new ArrayList<>(menuProducts);
    }

    private void validate(final List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
