package kitchenpos.menus.domain.tobe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuProducts {
    private List<MenuProduct> menuProducts = new ArrayList<>();

    public MenuProducts(final List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("menuProducts is required");
        }

        this.menuProducts = menuProducts;
    }

    public MenuProducts(final MenuProduct... menuProducts) {
        this(List.of(menuProducts));
    }

    public BigDecimal totalPrice() {
        return this.menuProducts.stream()
            .map(MenuProduct::totalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuProducts that = (MenuProducts) o;

        return Objects.equals(menuProducts, that.menuProducts);
    }

    @Override
    public int hashCode() {
        return menuProducts != null ? menuProducts.hashCode() : 0;
    }
}
