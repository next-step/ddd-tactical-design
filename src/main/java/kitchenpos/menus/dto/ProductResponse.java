package kitchenpos.menus.dto;

import java.math.BigDecimal;

public class ProductResponse {
    private Long productId;
    private BigDecimal price;

    public ProductResponse(Long productId, BigDecimal price) {
        this.productId = productId;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
