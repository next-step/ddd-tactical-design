package kitchenpos.products.tobe.fixture;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.tobe.domain.Product;

import java.util.Random;

public class MenuProductFixture {

    public static MenuProduct createMenuProduct(final Product product, final long quantity) {
        final MenuProduct menuProduct = new MenuProduct();
        menuProduct.setSeq(new Random().nextLong());
        menuProduct.setProduct(product);
        menuProduct.setQuantity(quantity);
        return menuProduct;
    }
}
