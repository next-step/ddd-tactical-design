package kitchenpos.menus.domain.tobe;

import java.util.ArrayList;
import java.util.List;

public class MenuProducts {
    private final List<MenuProduct> menuProducts;

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = new ArrayList<>(menuProducts);
    }

    public int totalAmount() {
        return menuProducts.stream()
                .mapToInt(MenuProduct::amount)
                .sum();
    }
}
