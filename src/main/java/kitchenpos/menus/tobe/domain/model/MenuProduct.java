package kitchenpos.menus.tobe.domain.model;

import kitchenpos.global.domain.vo.Price;
import kitchenpos.menus.tobe.domain.dto.ProductResponse;

import java.util.UUID;

public final class MenuProduct {

    private Long id;
    private Price price;
    private UUID productId;
    private long quantity;

    public MenuProduct(ProductResponse product, long quantity) {
        this.price = product.getPrice();
        this.quantity = quantity;
        this.productId = product.getId();
    }

    public Price getSubTotalPrice() {
        return this.price.multiply(this.quantity);
    }
}
