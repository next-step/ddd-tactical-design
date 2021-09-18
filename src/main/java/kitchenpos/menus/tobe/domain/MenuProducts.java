package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuProducts {
    private final List<MenuProduct> menuProducts;

    public MenuProducts(final List<MenuProduct> menuProducts) {
        validate(menuProducts);
        this.menuProducts = new ArrayList<>(menuProducts);
    }

    private void validate(final List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있습니다.");
        }
    }

    public BigDecimal getTotalMenuProductsPrice() {
        return menuProducts.stream()
                .map(MenuProduct::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
