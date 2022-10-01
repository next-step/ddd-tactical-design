package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.ProductDomainEvent;

import java.util.UUID;

public class ProductPriceChangeEventProduct extends ProductDomainEvent {
    private UUID id;

    public ProductPriceChangeEventProduct(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
