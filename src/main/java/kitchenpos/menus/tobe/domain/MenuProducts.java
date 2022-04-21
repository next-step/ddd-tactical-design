package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuProducts {
    private List<MenuProduct> menuProducts = new ArrayList<>();

    public MenuProducts(List<MenuProduct> menuProducts) {
        validateProducts(menuProducts);
        this.menuProducts = menuProducts;
    }

    private void validateProducts(List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal totalPrice() {
        return menuProducts.stream()
                .map(menuProduct -> menuProduct.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
