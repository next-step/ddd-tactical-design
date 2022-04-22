package kitchenpos.products.dto;

import kitchenpos.products.domain.tobe.domain.vo.ProductId;
import kitchenpos.support.dto.DTO;

import java.math.BigDecimal;

public class ProductPriceChangeRequest extends DTO {
    private BigDecimal price;
    private ProductId productId;

    public ProductPriceChangeRequest() {
    }

    public ProductPriceChangeRequest(ProductId productId, BigDecimal price) {
        this.price = price;
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }
}
