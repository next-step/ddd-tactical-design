package kitchenpos.menus.application;

import java.util.UUID;

import kitchenpos.menus.domain.Price;

public class ProductDto {
    private final UUID productId;
    private final Price price;

    public ProductDto(UUID productId, Price price) {
        this.productId = productId;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }

    public Price getPrice() {
        return price;
    }
}
