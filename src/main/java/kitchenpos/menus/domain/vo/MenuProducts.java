package kitchenpos.menus.domain.vo;

import kitchenpos.menus.domain.exception.InvalidMenuProductsException;
import kitchenpos.menus.domain.MenuProduct;

import java.util.List;
import java.util.Objects;

public class MenuProducts {
    final List<MenuProduct> menuProducts;

    public MenuProducts(List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new InvalidMenuProductsException();
        }
        this.menuProducts = menuProducts;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public int size() {
        return menuProducts.size();
    }
}
