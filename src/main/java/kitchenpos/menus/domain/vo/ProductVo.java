package kitchenpos.menus.domain.vo;

import java.util.UUID;

public class ProductVo {
    private final UUID productId;
    private final Long price;

    public ProductVo(UUID productId, Long price) {
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
