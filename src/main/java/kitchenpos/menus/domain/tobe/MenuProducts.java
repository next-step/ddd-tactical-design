package kitchenpos.menus.domain.tobe;

import java.math.BigDecimal;
import java.util.List;

public class MenuProducts {
    private final List<MenuProduct> menuProducts;

    public MenuProducts(final List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public BigDecimal totalAmount(){
        return menuProducts.stream()
                .map(MenuProduct::amount)
                .reduce(BigDecimal::add).get();
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

}
