package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;

import java.util.List;
import java.util.UUID;

public class MenuProducts {
    private List<MenuProduct> menuProducts;

    private MenuProducts(final List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public static MenuProducts of(final List<MenuProduct> menuProducts) {
        return new MenuProducts(menuProducts);
    }


    public Price calculateSum() {
        return menuProducts.stream()
                .map(menuProduct -> menuProduct.getProductPrice().multiply(menuProduct.getQuantity()))
                .reduce(Price.ZERO, Price::add);
    }

    public int getSize() {
        return menuProducts.size();
    }

    public boolean hasProduct(UUID productId) {
        return menuProducts.stream().anyMatch(menuProduct -> menuProduct.isMadeOf(productId));
    }
}
