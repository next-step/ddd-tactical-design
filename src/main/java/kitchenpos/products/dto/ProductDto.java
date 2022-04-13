package kitchenpos.products.dto;

import kitchenpos.products.domain.tobe.domain.TobeProduct;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;
import kitchenpos.support.dto.DTO;

import java.math.BigDecimal;

public class ProductDto extends DTO {
    private ProductId productId;
    private String productName;
    private BigDecimal price;

    public ProductDto() {
    }

    public ProductDto(ProductId productId, String productName, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public ProductDto(TobeProduct product) {
        this.productId = product.getId();
        this.productName = product.getName().getValue();
        this.price = product.getPrice().getValue();
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
