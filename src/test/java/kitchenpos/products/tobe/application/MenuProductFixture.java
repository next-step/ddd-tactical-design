package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.dto.ProductDto;

import java.util.Random;
import java.util.UUID;

public class MenuProductFixture {

    public static MenuProduct createMenuProductRequest(final Product tobeProduct, final long quantity) {
        final kitchenpos.products.domain.Product asisProduct = new kitchenpos.products.domain.Product();
        asisProduct.setId(tobeProduct.getId());
        asisProduct.setName(tobeProduct.getDisplayName().getName());
        asisProduct.setPrice(tobeProduct.getPrice().getPrice());

        final MenuProduct requestMenuProduct = new MenuProduct();
        requestMenuProduct.setSeq(new Random().nextLong());
        requestMenuProduct.setProduct(asisProduct);
        requestMenuProduct.setQuantity(quantity);

        return requestMenuProduct;
    }
}
