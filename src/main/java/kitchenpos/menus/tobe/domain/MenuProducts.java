package kitchenpos.menus.tobe.domain;

import java.util.List;
import kitchenpos.common.model.Price;

public class MenuProducts {

    private List<MenuProduct> menuProducts;

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    Price computePriceSum() {
        return menuProducts.stream()
            .map(menuProduct -> menuProduct.priceSum())
            .reduce(Price.ZERO, Price::add);
    }

}
