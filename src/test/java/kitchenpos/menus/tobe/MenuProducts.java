package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.exception.InvalidMenuArgumentException;

import java.util.List;
import java.util.Objects;

public class MenuProducts {
    private final List<MenuProduct> menuProducts;

    public MenuProducts(final List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new InvalidMenuArgumentException();
        }
        this.menuProducts = menuProducts;
    }

    public long totalAmount() {
        return menuProducts.stream()
                .mapToLong(MenuProduct::amount)
                .sum();
    }
}
