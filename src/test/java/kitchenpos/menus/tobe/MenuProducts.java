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

    public void changedProductPrice(final long productId, final long changedFirstPrice) {
        menuProducts.stream()
                .filter(it -> it.isSameProduct(productId))
                .findFirst()
                .ifPresentOrElse(it -> it.changePrice(changedFirstPrice),
                        () -> {
                            throw new InvalidMenuArgumentException();
                        }
                );
    }

    public boolean hasProduct(final Long productId) {
        return menuProducts.stream()
                .anyMatch(it -> it.isSameProduct(productId));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MenuProducts that = (MenuProducts) o;
        return Objects.equals(menuProducts, that.menuProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(menuProducts);
    }
}
