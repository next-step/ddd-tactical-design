package kitchenpos.menus.domain.tobe;

import kitchenpos.common.domain.Price;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MenuProducts {
    private final List<MenuProduct> menuProducts;

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = new ArrayList<>(menuProducts);
    }

    public Price totalAmount() {
        BigDecimal sum = menuProducts.stream()
                .map(menuProduct -> menuProduct.amount().getValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Price(sum);
    }
}
