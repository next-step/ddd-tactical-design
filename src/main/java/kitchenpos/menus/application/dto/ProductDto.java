package kitchenpos.menus.application.dto;

import java.util.UUID;

public class ProductDto {
    private final UUID productId;
    private final Long price;

    public ProductDto(UUID productId, Long price) {
        this.productId = productId;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getPrice() {
        return price;
    }
}
