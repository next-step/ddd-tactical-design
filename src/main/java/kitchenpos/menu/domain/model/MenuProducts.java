package kitchenpos.menu.domain.model;

import kitchenpos.menu.domain.exception.MenuProductValidationException;

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
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new MenuProductValidationException("메뉴 상품은 1개 이상 입력해야 합니다.");
        }
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
