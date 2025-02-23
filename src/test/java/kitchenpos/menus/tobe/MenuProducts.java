package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.exception.InvalidMenuArgumentException;
import kitchenpos.menus.tobe.vo.MenuId;

import java.util.ArrayList;
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
        final MenuProduct menuProduct = menuProducts.stream()
                .filter(it -> it.isSameProduct(productId))
                .findFirst()
                .orElseThrow(InvalidMenuArgumentException::new);
        menuProduct.changePrice(changedFirstPrice);
    }

    public boolean hasProduct(final Long productId) {
        return menuProducts.stream()
                .anyMatch(it -> it.isSameProduct(productId));
    }

    public void setMenuId(final MenuId menuId) {
        menuProducts.forEach(it -> it.setMenuId(menuId));
    }

    public List<MenuProduct> menuProducts() {
        return new ArrayList<>(menuProducts);
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
