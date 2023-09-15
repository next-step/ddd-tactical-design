package kitchenpos.products.tobe.domain;

import java.util.UUID;

public class ChangeProductPriceEvent {

    private Product product;

    public ChangeProductPriceEvent(Product product) {
        this.product = product;
    }

    public UUID getProductId() {
        return product.getId();
    }
}
