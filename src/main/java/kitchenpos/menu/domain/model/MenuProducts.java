package kitchenpos.menu.domain.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class MenuProducts {
    private final List<MenuProduct> menuProducts;

    private MenuProducts(final List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public static MenuProducts of(final List<MenuProduct> menuProducts) {
        return new MenuProducts(menuProducts);
    }

    public BigDecimal getTotalPrice() {
        return menuProducts.stream()
                .map(MenuProduct::calculatePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<MenuProduct> value() {
        return Collections.unmodifiableList(menuProducts);
    }
}
