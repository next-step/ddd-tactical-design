package kitchenpos.eatinorders.tobe.dto;

import java.util.UUID;

public class MenuProductResponse {
    private Long seq;
    private ProductResponse product;
    private long quantity;
    private UUID productId;

    protected MenuProductResponse() {}

    public Long getSeq() {
        return seq;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }
}
