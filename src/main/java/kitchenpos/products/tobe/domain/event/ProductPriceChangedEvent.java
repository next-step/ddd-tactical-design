package kitchenpos.products.tobe.domain.event;

import kitchenpos.common.event.Event;
import kitchenpos.common.vo.Price;
import kitchenpos.products.tobe.domain.ProductId;

public class ProductPriceChangedEvent extends Event {

    private final ProductId id;

    private final Price newPrice;

    public ProductPriceChangedEvent(final ProductId id, final Price newPrice) {
        super();
        this.id = id;
        this.newPrice = newPrice;
    }

    public ProductId getId() {
        return id;
    }

    public Price getPrice() {
        return newPrice;
    }
}
