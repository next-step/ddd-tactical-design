package kitchenpos.products.tobe.domain;

import java.util.UUID;

public class ProductPriceChangeEventProduct {
    private UUID id;

    public ProductPriceChangeEventProduct( UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
