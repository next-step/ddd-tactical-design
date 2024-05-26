package kitchenpos.menus.application.tobo.application;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.tobe.domain.Product;

import java.util.Random;

import static kitchenpos.products.tobe.application.ProductFixture.createProductRequest;


public class MenuProductFixture {

    public static MenuProduct createMenuProductRequest() {
        final MenuProduct menuProduct = new MenuProduct();
        menuProduct.setSeq(new Random().nextLong());
        menuProduct.setProduct(createProductRequest("후라이드", 16000L));
        menuProduct.setQuantity(2L);
        return menuProduct;
    }

    public static MenuProduct createMenuProductRequest(final Product product, final long quantity) {
        final MenuProduct menuProduct = new MenuProduct();
        menuProduct.setSeq(new Random().nextLong());
        menuProduct.setProduct(product);
        menuProduct.setQuantity(quantity);
        return menuProduct;
    }

}
