package kitchenpos.menus.tobe.domain;

import java.util.List;
import java.util.Objects;

public class MenuProducts {
    private final List<MenuProduct> menuProducts;

    public MenuProducts(List<MenuProduct> menuProducts) {
        if (menuProducts.size() < 1) {
            throw new IllegalArgumentException(String.format("메뉴에 속한 상품은 1개 이상이어야 합니다. 현재 값: %s", menuProducts.size()));
        }
        this.menuProducts = menuProducts;
    }

    public Price totalPrice() {
        return menuProducts.stream()
                .map(MenuProduct::getPrice)
                .reduce(new Price(0), Price::sum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProducts that = (MenuProducts) o;
        return Objects.equals(menuProducts, that.menuProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuProducts);
    }
}
