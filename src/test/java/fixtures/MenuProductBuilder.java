package fixtures;


import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.products.domain.Product;

import java.util.UUID;

public class MenuProductBuilder {

    private Product product;
    private long quantity = 0;
    private UUID productId;


    public MenuProductBuilder withProduct(Product product) {
        this.product = product;
        this.productId = product.getId();
        return this;
    }

    public MenuProductBuilder withQuantity(long quantity) {
        this.quantity = quantity;
        return this;
    }

    public MenuProduct build() {
        MenuProduct menuProduct = new MenuProduct();
        menuProduct.setSeq(1L);
        menuProduct.setProduct(product);
        menuProduct.setQuantity(quantity);
        menuProduct.setProductId(productId);
        return menuProduct;
    }
}
