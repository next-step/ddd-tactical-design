package kitchenpos.menus.tobe.menu.domain;

import java.math.BigDecimal;

public class ProductPriceDto {
    private Long productId;
    private BigDecimal price;

    public ProductPriceDto(final Long productId, final BigDecimal price) {
        this.productId = productId;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(final Long productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }
}
