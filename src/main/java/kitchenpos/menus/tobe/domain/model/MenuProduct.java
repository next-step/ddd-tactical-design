package kitchenpos.menus.tobe.domain.model;

import kitchenpos.global.domain.vo.Price;

import java.util.UUID;

public final class MenuProduct {

    private Long id;
    private Price price;
    private long quantity;

    private UUID productId;

    public MenuProduct(Price price, long quantity, UUID productId) {
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
    }

    public Price getSubTotalPrice() {
        return this.price.multiply(this.quantity);
    }
}
