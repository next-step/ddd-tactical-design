package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.MenuProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MenuProducts {
    private final List<MenuProduct> menuProducts;

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = new ArrayList<>(menuProducts);
    }

    public int totalAmount() {
        return menuProducts.stream()
                .mapToInt(it -> it.getAmount())
                .sum();
    }

    public void changePriceById(UUID productId, int newPrice) {
        Optional<MenuProduct> product = menuProducts.stream()
                .filter(menuProduct -> menuProduct.getId().equals(productId))
                .findFirst();
        product.ifPresent(menuProduct -> menuProduct.changePrice(newPrice));
    }
}
