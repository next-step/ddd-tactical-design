package kitchenpos.menu.domain.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
                .map(MenuProduct::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<MenuProduct> value() {
        return Collections.unmodifiableList(menuProducts);
    }

    public void changeMenuProductPrice(UUID productId, BigDecimal price) {
        menuProducts.stream()
                .filter(menuProduct -> menuProduct.getProductId().equals(productId))
                .findFirst()
                .ifPresent(menuProduct -> menuProduct.changeMenuProductPrice(price));
    }
}
