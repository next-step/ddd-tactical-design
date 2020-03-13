package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MenuProducts {
    private List<MenuProduct> menuProducts;
    private BigDecimal price;

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = new ArrayList<>(menuProducts);
    }

    public MenuProducts(BigDecimal price, List<MenuProduct> menuProducts) {
        this.price = price;
        this.menuProducts = new ArrayList<>(menuProducts);
    }

    public List<MenuProduct> list() {
        return menuProducts;
    }
}
