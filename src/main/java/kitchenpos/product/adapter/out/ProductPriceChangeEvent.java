package kitchenpos.product.adapter.out;

import java.util.UUID;

public final class ProductPriceChangeEvent {

    private final UUID id;

    public ProductPriceChangeEvent(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
