package kitchenpos.products.tobe.domain.event;

import kitchenpos.products.tobe.domain.TobeProduct;

public class ProductChangedPriceEvent {
    private final TobeProduct product;

    public ProductChangedPriceEvent(TobeProduct product) {
        this.product = product;
    }

    public TobeProduct getProduct() {
        return product;
    }
}
