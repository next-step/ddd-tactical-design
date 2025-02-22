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

    public MenuProducts changedProductPrice(final long productId, final long changedFirstPrice) {
        final MenuProduct menuProduct = menuProducts.stream()
                .filter(it -> it.isSame(productId))
                .findFirst()
                .map(it -> it.changePrice(changedFirstPrice))
                .orElseThrow(InvalidMenuArgumentException::new);
        return new MenuProducts(menuProducts.stream()
                .map(it -> it.isSame(productId) ? menuProduct : it)
                .toList());
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
