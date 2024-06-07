package kitchenpos.products.tobe.dto;

import java.math.BigDecimal;

public class ProductDto {
    private String productId;
    private String productName;
    private BigDecimal productPrice;

    public ProductDto(String productId, String productName, BigDecimal productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }
}
