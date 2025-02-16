package kitchenpos.products.tobe.fixture;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.tobe.domain.Product;

import java.util.Random;

import static kitchenpos.products.tobe.fixture.ProductFixture.createProductRequest;

public class MenuProductFixture {

    public static MenuProduct createMenuProduct() {
        final MenuProduct menuProduct = new MenuProduct();
        menuProduct.setSeq(new Random().nextLong());
        menuProduct.setProduct(createProductRequest());
        menuProduct.setQuantity(2L);
        return menuProduct;
    }

    public static MenuProduct createMenuProduct(final Product product, final long quantity) {
        final MenuProduct menuProduct = new MenuProduct();
        menuProduct.setSeq(new Random().nextLong());
        menuProduct.setProduct(product);
        menuProduct.setQuantity(quantity);
        return menuProduct;
    }
}
