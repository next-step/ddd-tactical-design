package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.List;

public class MenuProducts {

    private final List<MenuProduct> menuProducts;

    public MenuProducts(final List<MenuProduct> menuProducts) {
        validate(menuProducts);
        this.menuProducts = menuProducts;
    }

    private void validate(final List<MenuProduct> menuProducts) {
        if (menuProducts.isEmpty()) {
            throw new IllegalArgumentException("1 개 이상의 메뉴 상품을 가져야 합니다");
        }
    }

    public BigDecimal getAmount() {
        return menuProducts.stream()
                .map(MenuProduct::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
