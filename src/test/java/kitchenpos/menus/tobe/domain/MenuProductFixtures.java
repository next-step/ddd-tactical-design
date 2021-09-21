package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;

import java.util.Random;

public class MenuProductFixtures {
    public static MenuProduct menuProduct(final Product product, final Quantity quantity) {
        final MenuProduct menuProduct = new MenuProduct(new Random().nextLong(), product, quantity);
        return menuProduct;
    }
}
