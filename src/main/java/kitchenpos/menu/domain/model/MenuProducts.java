package kitchenpos.menu.domain.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuProducts that = (MenuProducts) o;
        return Objects.equals(menuProducts, that.menuProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(menuProducts);
    }
}
