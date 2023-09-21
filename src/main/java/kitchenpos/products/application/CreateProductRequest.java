package kitchenpos.products.application;

import kitchenpos.products.domain.DisplayedName;
import kitchenpos.products.domain.Price;

public class CreateProductRequest {
    private DisplayedName displayedName;
    private Price price;

    public DisplayedName getDisplayedName() {
        return displayedName;
    }

    public Price getPrice() {
        return price;
    }
}
