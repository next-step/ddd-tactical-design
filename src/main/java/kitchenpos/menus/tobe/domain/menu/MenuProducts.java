package kitchenpos.menus.tobe.domain.menu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MenuProducts {

    private final List<MenuProduct> menuProducts = new ArrayList<>();

    public int size() {
        return this.menuProducts.size();
    }

    public void add(MenuProduct menuProduct) {
        this.menuProducts.add(menuProduct);
    }

    public BigDecimal sum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (MenuProduct menuProduct : this.menuProducts) {
            sum = sum.add(menuProduct.sum());
        }
        return sum;
    }
}
